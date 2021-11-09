package io.github.kachaya.pkbd;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class InputView extends LinearLayout {

    private final InputService mInputService;
    private final Context mContext;

    private final HorizontalScrollView mCandidatesView;
    private final LinearLayout mCandidatesLayout;
    private final LinearLayout mSymbolView;

    private final Button[] mSymbolButton;

    // シンボル入力用ビューに表示する文字[2段][8カラム]
    private final String[][] symbolChars = {
            {"=", "~", "{", "}", "[", "]", "<", ">"},
            {"$", "%", "&", "^", "\\", "|", "`", ";"}
    };
    private Button[] mCandidateButton;
    private int mSymbolPage;
    private int mMetaState;

    public InputView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mInputService = (InputService) context;
        mMetaState = 0;

        View layout = LayoutInflater.from(context).inflate(R.layout.input, this);

        mCandidatesView = layout.findViewById(R.id.candidate_view);
        mCandidatesLayout = layout.findViewById(R.id.candidates_layout);

        mSymbolView = layout.findViewById(R.id.symbol_view);

        mCandidatesView.setVisibility(INVISIBLE);
        mSymbolView.setVisibility(VISIBLE);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
        mSymbolPage = 0;

        int style = R.style.SymbolButton;
        mSymbolButton = new Button[8];
        for (int i = 0; i < 8; i++) {
            Button b = new Button(new ContextThemeWrapper(mContext, style), null, style);
            b.setLayoutParams(lp);
            b.setOnClickListener(v -> onClickSymbolButton((Button) v));
            b.setText(symbolChars[mSymbolPage][i]);
            mSymbolView.addView(b);
            mSymbolButton[i] = b;
        }
    }

    // シンボルボタンのクリックハンドラ
    private void onClickSymbolButton(Button b) {
        CharSequence cs = b.getText();
        mInputService.processChar(cs.charAt(0));
    }

    // [sym]キー
    private void toggleSymbol() {
        if (mSymbolPage == 0) {
            mSymbolPage = 1;
        } else {
            mSymbolPage = 0;
        }
        for (int i = 0; i < 8; i++) {
            mSymbolButton[i].setText(symbolChars[mSymbolPage][i]);
        }
    }

    // 候補ボタンクリックハンドラ
    private void onClickCandidateButton(Button b) {
        int index = (int) b.getTag();
        mInputService.selectCandidate(index);
    }

    // 候補表示をけして記号表示に戻す
    public void clearCandidates() {
        mCandidatesLayout.removeAllViews();
        mSymbolView.setVisibility(VISIBLE);
        mCandidatesView.setVisibility(INVISIBLE);
        mCandidateButton = null;
    }

    // 候補文字列の配列をボタンにして並べて表示する
    public void setCandidates(String[] candidates, String okurigana, boolean isKatakana) {
        mCandidatesLayout.removeAllViews();
        mSymbolView.setVisibility(INVISIBLE);
        mCandidatesView.setVisibility(VISIBLE);
        mCandidateButton = new Button[candidates.length];

        int style = R.style.CandidateButton;
        for (int i = 0; i < candidates.length; i++) {
            Button b = new Button(new ContextThemeWrapper(mContext, style), null, style);
            b.setOnClickListener(v -> onClickCandidateButton((Button) v));
            b.setTag(i);                // 配列のインデックスをタグにする
            String s = candidates[i] + okurigana;
            if (isKatakana) {
                s = Converter.toWideKatakana(s);
            }
            b.setText(s);
            if (i == 0) {
                b.setBackgroundColor(Color.DKGRAY);     // 最初の候補を選択状態表示
            } else {
                b.setBackgroundColor(Color.BLACK);
            }
            mCandidatesLayout.addView(b);
            mCandidateButton[i] = b;
        }
    }

    // 候補ボタンを選択状態にする
    public void selectCandidate(int index) {
        if (mCandidateButton == null) {
            return;
        }
        Button b;
        int cX = mCandidatesView.getScrollX();
        int cW = mCandidatesView.getWidth();
        for (int i = 0; i < mCandidateButton.length; i++) {
            b = mCandidateButton[i];
            if (i == index) {
                // 見える場所にスクロールする
                int bT = b.getTop();
                int bL = b.getLeft();
                int bR = b.getRight();
                if (bL < cX) {
                    mCandidatesView.scrollTo(bL, bT);
                }
                if (bR > (cX + cW)) {
                    mCandidatesView.scrollTo(bR - cW, bT);
                }
                b.setBackgroundColor(Color.DKGRAY);
            } else {
                b.setBackgroundColor(Color.BLACK);
            }
        }
    }

    // ハードウェアキー処理
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.isCtrlPressed()) {
            // Ctrlとの同時押し
            switch (keyCode) {
                case KeyEvent.KEYCODE_B:
                    mInputService.processChar(InputService.CHAR_CTRL_B);
                    return true;
                case KeyEvent.KEYCODE_F:
                    mInputService.processChar(InputService.CHAR_CTRL_F);
                    return true;
                case KeyEvent.KEYCODE_G:
                    mInputService.processChar(InputService.CHAR_CTRL_G);
                    return true;
                case KeyEvent.KEYCODE_J:
                    mInputService.processChar(InputService.CHAR_CTRL_J);
                    return true;
                case KeyEvent.KEYCODE_Q:
                    mInputService.processChar(InputService.CHAR_CTRL_Q);
                    return true;
                default:
                    return false;   // その他の組み合わせ(C-v等)はアプリ側で処理させる
            }
        }
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return false;

            case KeyEvent.KEYCODE_SYM:
                this.toggleSymbol();
                return true;

            case KeyEvent.KEYCODE_SHIFT_LEFT:
            case KeyEvent.KEYCODE_SHIFT_RIGHT:
                mMetaState = KeyEvent.META_SHIFT_ON;
                return true;

            case KeyEvent.KEYCODE_ALT_LEFT:
            case KeyEvent.KEYCODE_ALT_RIGHT:
                mMetaState = KeyEvent.META_ALT_ON;
                return true;

            case KeyEvent.KEYCODE_DEL:
                mInputService.processChar('\b');
                return true;

            case KeyEvent.KEYCODE_ENTER:
                mInputService.processChar('\n');
                return true;

            default:
                mMetaState |= event.getMetaState();
                char ch = (char) event.getUnicodeChar(mMetaState);
                mMetaState = 0;
                if (ch == 0) {
                    return false;
                }
                // alt+spaceは'\uFE01'になる
                mInputService.processChar(ch);
                return true;
        }
    }

}
