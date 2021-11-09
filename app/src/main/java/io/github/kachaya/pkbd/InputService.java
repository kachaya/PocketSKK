package io.github.kachaya.pkbd;

import android.content.SharedPreferences;
import android.inputmethodservice.InputMethodService;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

import androidx.preference.PreferenceManager;

public class InputService extends InputMethodService {

    // 制御用の特別な文字
    public static final char CHAR_KANA = '\uEF01';      // かなモードへ移行するための文字(Alt+Space)
    public static final char CHAR_CTRL_B = '\u0002';    // Ctrl+B
    public static final char CHAR_CTRL_F = '\u0006';    // Ctrl+F
    public static final char CHAR_CTRL_G = '\u0007';    // Ctrl+G
    public static final char CHAR_CTRL_Q = '\u0011';    // Ctrl+Q
    public static final char CHAR_CTRL_J = '\u001F';    // Ctrl+Jの0x0Aは'\n'と競合するので別の値で代用する
    private static final String TAG = "InputService";

    // 変換モード
    private static final int CONVERT_MODE_DIRECT = 0;   // 「■モード」確定入力モード
    private static final int CONVERT_MODE_KEYWORD = 1;  // 「▽モード」見出し語入力モード
    private static final int CONVERT_MODE_SELECT = 2;   // 「▼モード」辞書変換モード

    private int mConvertMode;       // 変換モード

    // 文字列構成用
    private final StringBuilder mRomaji = new StringBuilder();      // ローマ字構成用
    private final StringBuilder mComposing = new StringBuilder();   // 見出し語構成用
    private final StringBuilder mOkurigana = new StringBuilder();   // 送り仮名構成用
    private char mOkuriChar;                                        // 送り仮名開始英字(辞書検索用)

    // 入力モード関連
    private boolean mIsKana;        // false:英数モード、true:仮名モード
    private boolean mIsWide;        // false:アスキーモード、true:全英モード
    private boolean mIsKatakana;    // false:かなモード、true:カナモード
    private boolean mIsAbbrev;      // false:仮名見出し、true:半角英数見出し

    // 候補
    private String mKeyword;            // 「▼モード」に渡す辞書検索用文字列
    private String[] mCandidateArray;   // 「▼モード」で辞書検索した結果の候補文字列
    private int mCandidateIndex;        // 「▼モード」で選択中の候補の番号(0～)

    // 部品
    private Dictionary mDictionary;
    private Converter mConverter;
    private InputView mInputView;

    private static boolean isKeywordStartChar(char ch) {
        return ch >= 'A' && ch <= 'Z' && ch != 'L' && ch != 'Q';
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        super.onCreate();
        mDictionary = new Dictionary(this);
        mConverter = new Converter();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        mDictionary.commit();
        super.onDestroy();
    }

    @Override
    public boolean onEvaluateFullscreenMode() {
        return false;   // フルスクリーンモード無効
    }

    @Override
    public void onInitializeInterface() {
        Log.d(TAG, "onInitializeInterface");
        super.onInitializeInterface();
        mInputView = new InputView(this, null);
    }

    @Override
    public View onCreateInputView() {
        Log.d(TAG, "onCreateInputView");
        return mInputView;
    }

    @Override
    public void onStartInput(EditorInfo attribute, boolean restarting) {
        Log.d(TAG, "onStartInput restarting=" + restarting + ",isInputViewShown=" + isInputViewShown());
        super.onStartInput(attribute, restarting);
        startDirectHalfLatinMode();
    }

    @Override
    public void onFinishInput() {
        Log.d(TAG, "onFinishInput");
        mCandidateArray = null;
        mInputView.clearCandidates();
        startDirectHalfLatinMode();
        super.onFinishInput();
    }

    @Override
    public void onStartInputView(EditorInfo attribute, boolean restarting) {
        Log.d(TAG, "onStartInputView restarting=" + restarting);
        super.onStartInputView(attribute, restarting);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        mConverter.putRomajiMap(",", sharedPreferences.getString("kuten", "、"));
        mConverter.putRomajiMap(".", sharedPreferences.getString("touten", "。"));
        mConverter.putRomajiMap(" ", sharedPreferences.getString("space", " "));

        boolean startKana = sharedPreferences.getBoolean("start_kana", true);
        if (startKana) {
            switch (attribute.inputType & InputType.TYPE_MASK_CLASS) {
                case InputType.TYPE_CLASS_NUMBER:
                case InputType.TYPE_CLASS_DATETIME:
                case InputType.TYPE_CLASS_PHONE:
                    startKana = false;
                    break;
                case InputType.TYPE_CLASS_TEXT:
                    switch (attribute.inputType & InputType.TYPE_MASK_VARIATION) {
                        case InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS:
                        case InputType.TYPE_TEXT_VARIATION_PASSWORD:
                        case InputType.TYPE_TEXT_VARIATION_URI:
                        case InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD:
                        case InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS:
                        case InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD:
                            startKana = false;
                            break;
                        default:
                            break;
                    }
                default:
                    break;
            }
        }
        if (startKana) {
            startDirectHiraganaMode();
        } else {
            startDirectHalfLatinMode();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Log.d(TAG, "onKeyDown isInputViewShown=" + isInputViewShown() + ",event=" + event);
        InputConnection ic = getCurrentInputConnection();
        if (ic == null) {
            return super.onKeyDown(keyCode, event);
        }
        if (mInputView.onKeyDown(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void icCommitText(CharSequence cs) {
        InputConnection ic = getCurrentInputConnection();
        if (ic != null) {
            ic.commitText(cs, 1);
        }
    }

    private void icSetComposingText(CharSequence cs) {
        InputConnection ic = getCurrentInputConnection();
        if (ic != null) {
            ic.setComposingText(cs, 1);
        }
    }

    private void icSendDpadLeftKey() {
        sendDownUpKeyEvents(KeyEvent.KEYCODE_DPAD_LEFT);
    }

    private void icSendDpadRightKey() {
        sendDownUpKeyEvents(KeyEvent.KEYCODE_DPAD_RIGHT);
    }

    private void icSendEnterKey() {
        sendDownUpKeyEvents(KeyEvent.KEYCODE_ENTER);
    }

    private void icSendDelKey() {
        sendDownUpKeyEvents(KeyEvent.KEYCODE_DEL);
    }

    /*
     * 文字入力一次受け
     * ・入力ビューからの文字入力
     * ・入力文字処理内からの再帰呼び出し（大文字による▽モード開始等）
     */
    public void processChar(char ch) {
        switch (mConvertMode) {
            case CONVERT_MODE_DIRECT:   // 「■モード」
                if (mIsKana) {
                    processCharDirectKana(ch);
                } else {
                    processCharDirectLatin(ch);
                }
                break;

            case CONVERT_MODE_KEYWORD:  // 「▽モード」
                if (mIsAbbrev) {
                    processCharKeywordAbbrev(ch);
                } else {
                    processCharKeywordKana(ch);
                }
                break;

            case CONVERT_MODE_SELECT:   // 「▼モード」
                processCharSelect(ch);
                break;

            default:
                Log.i("processChar", "Unexpected value: mConvertMode=" + mConvertMode);
        }
    }

    /*
     * 「■モード」処理
     */

    // 「■モード」に戻る(アスキー・全英・かな・カナ)
    private void startDirectMode() {
        mConvertMode = CONVERT_MODE_DIRECT;
        mRomaji.setLength(0);
        icSetComposingText("");
    }

    // 「■モード」アスキーモード開始
    private void startDirectHalfLatinMode() {
        mIsKana = false;
        mIsWide = false;
        hideStatusIcon();                               // アイコン表示なし
        startDirectMode();
    }

    // 「■モード」全英モード開始
    private void startDirectWideLatinMode() {
        mIsKana = false;
        mIsWide = true;
        showStatusIcon(R.drawable.ic_stat_wide);        // アイコン表示[Ａ]
        startDirectMode();
    }

    // 「■モード」かなモード開始
    private void startDirectHiraganaMode() {
        mIsKana = true;
        mIsKatakana = false;
        showStatusIcon(R.drawable.ic_stat_hiragana);    // アイコン表示[あ]
        startDirectMode();
    }

    // 「■モード」カナモード開始
    private void startDirectKatakanaMode() {
        mIsKana = true;
        mIsKatakana = true;
        showStatusIcon(R.drawable.ic_stat_katakana);    // アイコン表示[ア]
        startDirectMode();
    }

    // 「■モード」ローマ字の先頭に'n'が残っていたら仮名にして出力
    private void flushDirectRomaji() {
        if (mRomaji.length() == 1 && mRomaji.charAt(0) == 'n') {
            if (mIsKatakana) {
                icCommitText("ン");
            } else {
                icCommitText("ん");
            }
        }
        mRomaji.setLength(0);
        icSetComposingText("");
    }

    // 「■モード」英数(アスキーモード・全英モード共通)処理
    private void processCharDirectLatin(char ch) {
        if (ch == CHAR_KANA) {
            startDirectHiraganaMode();      // 「かなモード」へ
        } else if (ch == CHAR_CTRL_J) {
            startDirectHiraganaMode();      // 「かなモード」へ
        } else if (ch == CHAR_CTRL_B) {
            icSendDpadLeftKey();
        } else if (ch == CHAR_CTRL_F) {
            icSendDpadRightKey();
        } else if (ch == '\b') {
            icSendDelKey();
        } else if (ch == '\n') {
            icSendEnterKey();
        } else if (ch >= ' ') {
            if (mIsWide) {
                ch = Converter.toWideLatin(ch);
            }
            icCommitText(Character.toString(ch));
        }
    }

    // ■モード：仮名(ひらがな・カタカナ共通)処理
    private void processCharDirectKana(char ch) {
        int len = mRomaji.length();
        if (ch == CHAR_KANA) {
            startDirectHalfLatinMode();     // 「アスキーモード」へ
        } else if (ch == CHAR_CTRL_J) {
            flushDirectRomaji();
            startDirectHiraganaMode();      // 「かなモード」へ
        } else if (ch == CHAR_CTRL_G) {
            startDirectMode();              // キャンセル
        } else if (ch == CHAR_CTRL_B) {
            if (len == 0) {
                icSendDpadLeftKey();
            }
        } else if (ch == CHAR_CTRL_F) {
            if (len == 0) {
                icSendDpadRightKey();
            }
        } else if (ch == '\b') {
            if (len == 0) {
                icSendDelKey();
            } else {
                mRomaji.setLength(len - 1);
                icSetComposingText(mRomaji);
            }
        } else if (ch == '\n') {
            if (len == 0) {
                icSendEnterKey();
            } else {
                flushDirectRomaji();
                startDirectMode();      // リスタート
            }
        } else if (ch >= ' ') {
            String[] ss = mConverter.parseRomaji(mRomaji.toString() + ch);
            if (ss[0].length() != 0) {
                if (mIsKatakana) {
                    ss[0] = Converter.toWideKatakana(ss[0]);
                }
                icCommitText(ss[0]);        // 確定
                mRomaji.setLength(0);
                mRomaji.append(ss[1]);
                icSetComposingText(mRomaji);
            } else {
                if (ch == 'l') {
                    flushDirectRomaji();
                    startDirectHalfLatinMode();         // 「アスキーモード」へ
                } else if (ch == 'q') {
                    flushDirectRomaji();
                    if (mIsKatakana) {
                        startDirectHiraganaMode();      // 「かなモード」へ
                    } else {
                        startDirectKatakanaMode();      // 「カナモード」へ
                    }
                } else if (ch == 'L') {
                    flushDirectRomaji();
                    startDirectWideLatinMode();         // 「全英モード」へ
                } else if (ch == 'Q') {
                    flushDirectRomaji();
                    startKeywordKanaMode();             // 「▽モード」へ
                } else if (ch == '/') {
                    flushDirectRomaji();
                    startKeywordAbbrevMode();           // 「▽モード(abbrevモード)」へ
                } else if (isKeywordStartChar(ch)) {
                    flushDirectRomaji();
                    startKeywordKanaMode();             // 「▽モード」へ
                    processChar(Character.toLowerCase(ch)); // 今回入力された文字を一文字目として処理
                } else {
                    mRomaji.append(ch);
                    icSetComposingText(mRomaji);
                }
            }
        }
    }

    /*
     * 「▽モード」処理
     */

    // 「▽モード」へ戻る
    private void resumeKeywordMode() {
        Log.d(TAG, "resumeKeywordMode Keyword=" + mComposing + ",OkuriChar=" + mOkuriChar + ",Okurigana=" + mOkurigana + ",Composing=" + mRomaji);
        mConvertMode = CONVERT_MODE_KEYWORD;
        mRomaji.setLength(0);
        setComposingTextKeyword();
    }

    // 「▽モード」abbrevモード開始
    private void startKeywordAbbrevMode() {
        mConvertMode = CONVERT_MODE_KEYWORD;
        mIsAbbrev = true;
        showStatusIcon(R.drawable.ic_stat_abbrev);      // アイコン表示[Aｱ]
        mRomaji.setLength(0);
        mComposing.setLength(0);
        mOkurigana.setLength(0);
        mOkuriChar = '\0';
        setComposingTextKeyword();
    }

    // 「▽モード」仮名モード開始
    private void startKeywordKanaMode() {
        mConvertMode = CONVERT_MODE_KEYWORD;
        mIsAbbrev = false;
        if (mIsKatakana) {
            showStatusIcon(R.drawable.ic_stat_katakana);    // アイコン表示[ア]
        } else {
            showStatusIcon(R.drawable.ic_stat_hiragana);    // アイコン表示[あ]
        }
        mRomaji.setLength(0);
        mComposing.setLength(0);
        mOkurigana.setLength(0);
        mOkuriChar = '\0';
        setComposingTextKeyword();
    }

    // 「▽モード」構成テキストをテキストフィールドに表示
    private void setComposingTextKeyword() {
        InputConnection ic = getCurrentInputConnection();
        if (ic != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("▽");
            if (mIsKatakana) {
                sb.append(Converter.toWideKatakana(mComposing));  // 表示上はカタカナ
                sb.append(Converter.toWideKatakana(mOkurigana));
            } else {
                sb.append(mComposing);
                sb.append(mOkurigana);
            }
            sb.append(mRomaji);
            Log.d(TAG, "setComposingTextKeyword() text=" + sb);
            ic.setComposingText(sb, 1);
        }
    }

    // 「▽モード」abbrevモード処理
    private void processCharKeywordAbbrev(char ch) {
        if (ch == CHAR_KANA) {
            startDirectHiraganaMode();      // 「かなモード」へ
        } else if (ch == CHAR_CTRL_G) {
            startDirectMode();              // 「■モード」へ
        } else if (ch == CHAR_CTRL_J) {
            icCommitText(mComposing);       // そのまま確定
            startDirectMode();              // 「■モード」へ
        } else if (ch == CHAR_CTRL_Q) {
            icCommitText(Converter.toWideLatin(mComposing));    // 全角英数に変換して確定
            startDirectMode();              // 「■モード」へ
        } else if (ch == '\n') {
            icCommitText(mComposing);       // そのまま確定
            startDirectMode();              // 「■モード」へ
        } else if (ch == '\b') {
            if (mComposing.length() != 0) {
                mComposing.setLength(mComposing.length() - 1);
                setComposingTextKeyword();
            } else {
                startDirectMode();          // 「■モード」へ
            }
        } else if (ch == ' ') {
            mKeyword = mComposing.toString();
            mCandidateArray = mDictionary.search(mKeyword);
            if (mCandidateArray != null) {
                startSelectMode();     // 「▼モード」へ
            }
        } else if (ch > ' ') {
            mComposing.append(ch);
            setComposingTextKeyword();
        }
    }

    // 「▽モード」仮名モード処理
    private void processCharKeywordKana(char ch) {
        if (ch == CHAR_KANA) {
            startDirectHalfLatinMode();     // アスキーモードへ
        } else if (ch == CHAR_CTRL_G) {
            startDirectMode();          // ■モードに戻る
        } else if (ch == '\b') {
            //Log.d(TAG, "processCharKeywordKana(BS) mComposing=" + mComposing + ",mOkurigana=" + mOkurigana + ",mRomaji=" + mRomaji);
            if ((mComposing.length() + mOkurigana.length() + mRomaji.length()) == 0) {
                startDirectMode();     // ■モードに戻る
            } else {
                if (mRomaji.length() > 0) {
                    mRomaji.setLength(mRomaji.length() - 1);
                } else {
                    if (mOkurigana.length() > 0) {
                        mOkurigana.setLength(mOkurigana.length() - 1);
                    } else {
                        if (mComposing.length() > 0) {
                            mComposing.setLength(mComposing.length() - 1);
                        }
                    }
                }
                setComposingTextKeyword();
            }
        } else if (ch == '?') {
            mKeyword = mComposing.toString() + '>';     // 接頭辞エントリを検索
            mOkurigana.setLength(0);
            mCandidateArray = mDictionary.search(mKeyword);
            if (mCandidateArray != null) {
                startSelectMode();              // ▼モード(接頭辞)へ
            }
        } else if (ch == ' ') {
            if (mRomaji.length() == 1 && mRomaji.charAt(0) == 'n') {
                mComposing.append('ん');
            }
            mKeyword = mComposing.toString();
            mOkurigana.setLength(0);
            mCandidateArray = mDictionary.search(mKeyword);
            if (mCandidateArray != null) {
                startSelectMode();   // ▼モードへ
            }
        } else if (ch > ' ') {
            if (ch == 'l') {
                startDirectHalfLatinMode();      // アスキーモードへ
            } else if (ch == 'L') {
                startDirectWideLatinMode();      // 全英モードへ
            } else if (ch == 'q') {
                if (mRomaji.length() == 1 && mRomaji.charAt(0) == 'n') {
                    mComposing.append('ん');
                }
                if (mIsKatakana) {
                    icCommitText(mComposing);                           // ひらがなで確定
                } else {
                    icCommitText(Converter.toWideKatakana(mComposing)); // カタカナで確定
                }
                startDirectMode();          // ■モードへ
            } else if (ch == 'Q') {
                if (mRomaji.length() == 1 && mRomaji.charAt(0) == 'n') {
                    mComposing.append('ん');
                }
                icCommitText(mComposing);
                startKeywordKanaMode();        // ▽モードへ
            } else {
                if (isKeywordStartChar(ch)) {        // 'L','Q'以外の大文字
                    ch = Character.toLowerCase(ch);
                    if (mOkuriChar == '\0') {
                        mOkuriChar = ch;            // 初回は送り仮名文字として採用
                        mRomaji.setLength(0);    // 送り仮名用に初期化
                    }
                    // 下の処理に続く
                }
                if (mOkuriChar != '\0') {
                    // 送り仮名入力中
                    mRomaji.append(ch);
                    String[] ss = mConverter.parseRomaji(mRomaji.toString());
                    if (ss[0].length() != 0 && ss[1].length() == 0) {
                        mRomaji.setLength(0);
                        mKeyword = mComposing.toString() + mOkuriChar;  // 辞書の送りありエントリを使用
                        mOkurigana.append(ss[0]);
                        mCandidateArray = mDictionary.search(mKeyword);
                        if (mKeyword != null) {
                            startSelectMode();  // ▼モードへ
                        }
                    } else {
                        mRomaji.setLength(0);
                        mRomaji.append(ss[1]);
                        setComposingTextKeyword();
                    }
                } else {
                    mRomaji.append(ch); // 入力された文字を追加
                    String[] ss = mConverter.parseRomaji(mRomaji.toString());
                    if (ss[0].length() != 0) {
                        mComposing.append(ss[0]);
                        mRomaji.setLength(0);
                        mRomaji.append(ss[1]);
                    }
                    setComposingTextKeyword();  // 見出し語表示更新
                }
            }
        }
    }

    /*
     * 「▼モード」処理
     * ・mKeyword        見出し語(辞書検索に使用したもの)
     * ・mOkurigana      送り仮名(空文字列の場合もあり)
     * ・mCandidateArray 辞書から得た候補一覧(必ず要素あり)
     */

    // 「▼モード」開始
    private void startSelectMode() {
        mConvertMode = CONVERT_MODE_SELECT;
        mInputView.setCandidates(mCandidateArray, mOkurigana.toString(), mIsKatakana);
        selectCandidate(0);
    }

    // 「▼モード」構成中文字列表示
    private void setComposingTextSelect() {
        InputConnection ic = getCurrentInputConnection();
        if (ic != null) {
            StringBuilder sb = new StringBuilder();
            String candidate = mCandidateArray[mCandidateIndex];
            sb.append("▼");
            if (mIsKatakana) {
                sb.append(Converter.toWideKatakana(candidate));
                sb.append(Converter.toWideKatakana(mOkurigana));
            } else {
                sb.append(candidate);
                sb.append(mOkurigana);
            }
            ic.setComposingText(sb, 1);
        }
    }

    // 「▼モード」入力ビューからの候補ボタンクリック、キーによる次候補・前候補選択
    public void selectCandidate(int index) {
        mCandidateIndex = index;
        mInputView.selectCandidate(mCandidateIndex);
        setComposingTextSelect();
    }

    // 「▼モード」候補確定
    private void commitTextSelect() {
        mDictionary.add(mKeyword, mCandidateArray[mCandidateIndex]);    // 辞書登録

        String s = mCandidateArray[mCandidateIndex] + mOkurigana;
        if (mIsKatakana) {
            s = Converter.toWideKatakana(s);
        }
        icCommitText(s);
        mCandidateArray = null;
        mInputView.clearCandidates();
    }

    // 「▼モード」入力文字処理
    private void processCharSelect(char ch) {

        if (ch == ' ') {            // 次候補
            selectCandidate((mCandidateIndex + 1) % mCandidateArray.length);
        } else if (ch == 'x') {     // 前候補
            selectCandidate((mCandidateIndex + mCandidateArray.length - 1) % mCandidateArray.length);

        } else if (ch == '\b') {
            String s = mCandidateArray[mCandidateIndex] + mOkurigana;
            if (s.length() > 0) {
                s = s.substring(0, s.length() - 1); // 最後の文字を削除
            }
            if (mIsKatakana) {
                s = Converter.toWideKatakana(s);
            }
            icCommitText(s);            // 確定
            mCandidateArray = null;
            mInputView.clearCandidates();
            startDirectMode();          // ■モードへ戻る

        } else if (ch == CHAR_KANA) {
            commitTextSelect();         // 確定
            startDirectMode();          // ■モードへ戻る
        } else if (ch == CHAR_CTRL_J) {
            commitTextSelect();         // 確定
            startDirectMode();          // ■モードへ戻る
        } else if (ch == '\n') {
            commitTextSelect();         // 確定
            startDirectMode();          // ■モードへ戻る

        } else if (ch == CHAR_CTRL_G) {
            mCandidateArray = null;
            mInputView.clearCandidates();
            resumeKeywordMode();        // ▽モードへ戻る

        } else if (ch == '?') {     // ▼モードで接尾辞
            commitTextSelect();      // 確定・辞書登録
            startKeywordKanaMode(); // ▽モード(接尾辞)へ
            processChar('>');

        } else if (ch > ' ') {      // 暗黙の確定
            commitTextSelect();      // 確定
            startDirectMode();     // ■モードへ
            processChar(ch);        // 今回入力された文字で■モードの処理を呼び出し
        }
    }
}
