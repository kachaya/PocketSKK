package io.github.kachaya.pkbd;

import java.util.HashMap;

public class Converter {

    // ローマ字
    private static final HashMap<String, String> mRomajiMap = new HashMap<String, String>() {
        {
            put("a", "あ");
            put("i", "い");
            put("u", "う");
            put("e", "え");
            put("o", "お");

            put("ba", "ば");
            put("bi", "び");
            put("bu", "ぶ");
            put("be", "べ");
            put("bo", "ぼ");

            put("bya", "びゃ");
            put("byi", "びぃ");
            put("byu", "びゅ");
            put("bye", "びぇ");
            put("byo", "びょ");

            put("ca", "か");
            put("ci", "し");
            put("cu", "く");
            put("ce", "せ");
            put("co", "こ");

            put("cha", "ちゃ");
            put("chi", "ち");
            put("chu", "ちゅ");
            put("che", "ちぇ");
            put("cho", "ちょ");

            put("cya", "ちゃ");
            put("cyi", "ちぃ");
            put("cyu", "ちゅ");
            put("cye", "ちぇ");
            put("cyo", "ちょ");

            put("da", "だ");
            put("di", "ぢ");
            put("du", "づ");
            put("de", "で");
            put("do", "ど");

            put("dha", "でゃ");
            put("dhi", "でぃ");
            put("dhu", "でゅ");
            put("dhe", "でぇ");
            put("dho", "でょ");

            put("dya", "ぢゃ");
            put("dyi", "ぢぃ");
            put("dyu", "ぢゅ");
            put("dye", "ぢぇ");
            put("dyo", "ぢょ");

            put("fa", "ふぁ");
            put("fi", "ふぃ");
            put("fu", "ふ");
            put("fe", "ふぇ");
            put("fo", "ふぉ");

            put("fya", "ふゃ");
            put("fyi", "ふぃ");
            put("fyu", "ふゅ");
            put("fye", "ふぇ");
            put("fyo", "ふょ");

            put("ga", "が");
            put("gi", "ぎ");
            put("gu", "ぐ");
            put("ge", "げ");
            put("go", "ご");

            put("gya", "ぎゃ");
            put("gyi", "ぎぃ");
            put("gyu", "ぎゅ");
            put("gye", "ぎぇ");
            put("gyo", "ぎょ");

            put("ha", "は");
            put("hi", "ひ");
            put("hu", "ふ");
            put("he", "へ");
            put("ho", "ほ");

            put("hya", "ひゃ");
            put("hyi", "ひぃ");
            put("hyu", "ひゅ");
            put("hye", "ひぇ");
            put("hyo", "ひょ");

            put("ja", "じゃ");
            put("ji", "じ");
            put("ju", "じゅ");
            put("je", "じぇ");
            put("jo", "じょ");

            put("jya", "じゃ");
            put("jyi", "じぃ");
            put("jyu", "じゅ");
            put("jye", "じぇ");
            put("jyo", "じょ");

            put("ka", "か");
            put("ki", "き");
            put("ku", "く");
            put("ke", "け");
            put("ko", "こ");

            put("kya", "きゃ");
            put("kyi", "きぃ");
            put("kyu", "きゅ");
            put("kye", "きぇ");
            put("kyo", "きょ");

            put("ma", "ま");
            put("mi", "み");
            put("mu", "む");
            put("me", "め");
            put("mo", "も");

            put("mya", "みゃ");
            put("myi", "みぃ");
            put("myu", "みゅ");
            put("mye", "みぇ");
            put("myo", "みょ");

            put("na", "な");
            put("ni", "に");
            put("nu", "ぬ");
            put("ne", "ね");
            put("no", "の");

            put("nya", "にゃ");
            put("nyi", "にぃ");
            put("nyu", "にゅ");
            put("nye", "にぇ");
            put("nyo", "にょ");

            put("pa", "ぱ");
            put("pi", "ぴ");
            put("pu", "ぷ");
            put("pe", "ぺ");
            put("po", "ぽ");

            put("pya", "ぴゃ");
            put("pyi", "ぴぃ");
            put("pyu", "ぴゅ");
            put("pye", "ぴぇ");
            put("pyo", "ぴょ");

            put("ra", "ら");
            put("ri", "り");
            put("ru", "る");
            put("re", "れ");
            put("ro", "ろ");

            put("rya", "りゃ");
            put("ryi", "りぃ");
            put("ryu", "りゅ");
            put("rye", "りぇ");
            put("ryo", "りょ");

            put("sa", "さ");
            put("si", "し");
            put("su", "す");
            put("se", "せ");
            put("so", "そ");

            put("sha", "しゃ");
            put("shi", "し");
            put("shu", "しゅ");
            put("she", "しぇ");
            put("sho", "しょ");

            put("sya", "しゃ");
            put("syi", "しぃ");
            put("syu", "しゅ");
            put("sye", "しぇ");
            put("syo", "しょ");

            put("ta", "た");
            put("ti", "ち");
            put("tu", "つ");
            put("te", "て");
            put("to", "と");

            put("tha", "てゃ");
            put("thi", "てぃ");
            put("thu", "てゅ");
            put("the", "てぇ");
            put("tho", "てょ");

            put("tsa", "つぁ");
            put("tsi", "つぃ");
            put("tsu", "つ");
            put("tse", "つぇ");
            put("tso", "つぉ");

            put("tya", "ちゃ");
            put("tyi", "ちぃ");
            put("tyu", "ちゅ");
            put("tye", "ちぇ");
            put("tyo", "ちょ");

            put("va", "ゔぁ");
            put("vi", "ゔぃ");
            put("vu", "ゔ");
            put("ve", "ゔぇ");
            put("vo", "ゔぉ");

            put("vya", "ゔゃ");
            put("vyi", "ゔぃ");
            put("vyu", "ゔゅ");
            put("vye", "ゔぇ");
            put("vyo", "ゔょ");

            put("wa", "わ");
            put("wi", "うぃ");
            put("wu", "う");
            put("we", "うぇ");
            put("wo", "を");

            put("xa", "ぁ");
            put("xi", "ぃ");
            put("xu", "ぅ");
            put("xe", "ぇ");
            put("xo", "ぉ");

            put("xya", "ゃ");
            put("xyi", "ぃ");
            put("xyu", "ゅ");
            put("xye", "ぇ");
            put("xyo", "ょ");

            put("ya", "や");
            put("yi", "い");
            put("yu", "ゆ");
            put("ye", "いぇ");
            put("yo", "よ");

            put("za", "ざ");
            put("zi", "じ");
            put("zu", "ず");
            put("ze", "ぜ");
            put("zo", "ぞ");

            put("zya", "じゃ");
            put("zyi", "じぃ");
            put("zyu", "じゅ");
            put("zye", "じぇ");
            put("zyo", "じょ");

            // 五段そろっていないもの

            put("wyi", "ゐ");
            put("wye", "ゑ");

            put("xka", "ゕ");
            put("xke", "ゖ");
            put("xtsu", "っ");
            put("xtu", "っ");
            put("xwa", "ゎ");

            // 「ん」

            put("n'", "ん");
            put("nn", "ん");

            // 子音の前の「ん」

            put("nb", "ん,b");
            put("nc", "ん,c");
            put("nd", "ん,d");
            put("nf", "ん,f");
            put("ng", "ん,g");
            put("nh", "ん,h");
            put("nj", "ん,j");
            put("nk", "ん,k");
            put("nm", "ん,m");
            put("np", "ん,p");
            put("nr", "ん,r");
            put("ns", "ん,s");
            put("nt", "ん,t");
            put("nv", "ん,v");
            put("nw", "ん,w");
            put("nx", "ん,x");
            put("nz", "ん,z");

            // 子音の前の「っ」

            put("bb", "っ,b");
            put("cc", "っ,c");
            put("dd", "っ,d");
            put("ff", "っ,f");
            put("gg", "っ,g");
            put("hh", "っ,h");
            put("jj", "っ,j");
            put("kk", "っ,k");
            put("mm", "っ,m");
            put("pp", "っ,p");
            put("rr", "っ,r");
            put("ss", "っ,s");
            put("tt", "っ,t");
            put("vv", "っ,v");
            put("ww", "っ,w");
            put("xx", "っ,x");
            put("yy", "っ,y");
            put("zz", "っ,z");

            // 記号のローマ字変換は基本的に半角、一部全角

            put(" ", " ");       // スペース[設定項目]
            put("!", "！");      // 全角感嘆符
            put("\"", "\"");
            put("#", "#");
            put("$", "$");
            put("%", "%");
            put("&", "&");
            put("'", "'");
            put("(", "(");
            put(")", ")");
            put("*", "*");
            put("+", "+");
            put(",", "、");      // 句点[設定項目]
            put("-", "ー");      // 長音
            put(".", "。");      // 読点[設定項目]
            //put("/", "/");      // ■モードではabbrevモードの関係で使えない
            put("0", "0");
            put("1", "1");
            put("2", "2");
            put("3", "3");
            put("4", "4");
            put("5", "5");
            put("6", "6");
            put("7", "7");
            put("8", "8");
            put("9", "9");
            put(":", "：");      // 全角
            put(";", "；");      // 全角
            put("<", "<");
            put("=", "=");
            put(">", ">");
            put("?", "？");      // 全角疑問符
            put("@", "@");
            put("[", "「");      // 開き鍵括弧
            put("\\", "\\");
            put("]", "」");      // 閉じ鍵括弧
            put("^", "^");
            put("_", "_");
            put("`", "`");
            put("{", "{");
            put("|", "|");
            put("}", "}");
            put("~", "～");      // 全角波ダッシュ
            put("\u00A5", "￥"); // 半角'¥'⇒全角円マーク

            // 全角記号（'z'で始まる2文字）
            // 他のSKK実装(DDSKK, CorvusSKK等)を参考にしている

            put("z ", "\u3000"); // 全角スペース[DDSKK,CorvusSKK]
            put("z(", "（");     // 開き丸括弧[DDSKK,CorvusSKK]
            put("z)", "）");     // 閉じ丸括弧[DDSKK,CorvusSKK]
            put("z*", "※");     // コメ印[DDSKK]
            put("z,", "‥");     // 二点リーダー[DDSKK,CorvusSKK]
            put("z-", "\u301C"); // 「〜」波ダッシュ[DDSKK,CorvusSKK]
            put("z.", "…");      // 三点リーダー[DDSKK,CorvusSKK]
            put("z/", "・");     // 中点[DDSKK,CorvusSKK]

            put("z0", "○");     // 白丸印[DDSKK]

            put("z:", "゜");     // 半濁点[DDSKK,CorvusSKK]
            put("z;", "゛");     // 濁点[DDSKK,CorvusSKK]

            put("z@", "◎");     // 二重丸印[DDSKK]

            put("zL", "⇒");     // 右白抜き矢印[DDSKK,CorvusSKK]

            put("z[", "『");     // 開き二重鍵括弧[DDSKK,CorvusSKK]
            put("z]", "』");     // 閉じ二重鍵括弧[DDSKK,CorvusSKK]

            put("zh", "←");     // 左矢印[DDSKK,CorvusSKK]
            put("zj", "↓");     // 下矢印[DDSKK,CorvusSKK]
            put("zk", "↑");     // 上矢印[DDSKK,CorvusSKK]
            put("zl", "→");     // 右矢印[DDSKK,CorvusSKK]

            put("zn", "ー");     // 長音[DDSKK]

            put("z{", "【");     // 開き墨付括弧[DDSKK,CorvusSKK]
            put("z}", "】");     // 閉じ墨付括弧[DDSKK,CorvusSKK]
            put("z~", "\uFF5E"); // 「～」全角チルダ[DDSKK,CorvusSKK]

        }
    };

    public Converter() {
    }

    // 全角カタカナ変換
    public static char toWideKatakana(char ch) {
        if (ch >= 'ぁ' && ch <= 'ゖ') {
            return (char) (ch - 'ぁ' + 'ァ');
        }
        return ch;
    }

    public static String toWideKatakana(CharSequence cs) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cs.length(); i++) {
            sb.append(toWideKatakana(cs.charAt(i)));
        }
        return sb.toString();
    }
    
    // 半角カタカナ変換
    public static String toHalfKatakana(CharSequence cs) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cs.length(); i++) {
            char key = cs.charAt(i);
            String val = narrowKatakanaMap.get(key);
            if (val != null) {
                sb.append(val);
            } else {
                sb.append(key);
            }
        }
        return sb.toString();
    }

    private static final HashMap<Character, String> narrowKatakanaMap = new HashMap<Character, String>() {
        {
            put('あ', "ｱ");
            put('い', "ｲ");
            put('う', "ｳ");
            put('え', "ｴ");
            put('お', "ｵ");
            put('か', "ｶ");
            put('き', "ｷ");
            put('く', "ｸ");
            put('け', "ｹ");
            put('こ', "ｺ");
            put('さ', "ｻ");
            put('し', "ｼ");
            put('す', "ｽ");
            put('せ', "ｾ");
            put('そ', "ｿ");
            put('た', "ﾀ");
            put('ち', "ﾁ");
            put('つ', "ﾂ");
            put('て', "ﾃ");
            put('と', "ﾄ");
            put('な', "ﾅ");
            put('に', "ﾆ");
            put('ぬ', "ﾇ");
            put('ね', "ﾈ");
            put('の', "ﾉ");
            put('は', "ﾊ");
            put('ひ', "ﾋ");
            put('ふ', "ﾌ");
            put('へ', "ﾍ");
            put('ほ', "ﾎ");
            put('ま', "ﾏ");
            put('み', "ﾐ");
            put('む', "ﾑ");
            put('め', "ﾒ");
            put('も', "ﾓ");
            put('や', "ﾔ");
            put('ゆ', "ﾕ");
            put('よ', "ﾖ");
            put('ら', "ﾗ");
            put('り', "ﾘ");
            put('る', "ﾙ");
            put('れ', "ﾚ");
            put('ろ', "ﾛ");
            put('わ', "ﾜ");
            put('を', "ｦ");
            put('ん', "ﾝ");

            put('が', "ｶﾞ");
            put('ぎ', "ｷﾞ");
            put('ぐ', "ｸﾞ");
            put('げ', "ｹﾞ");
            put('ご', "ｺﾞ");
            put('ざ', "ｻﾞ");
            put('じ', "ｼﾞ");
            put('ず', "ｽﾞ");
            put('ぜ', "ｾﾞ");
            put('ぞ', "ｿﾞ");
            put('だ', "ﾀﾞ");
            put('ぢ', "ﾁﾞ");
            put('づ', "ﾂﾞ");
            put('で', "ﾃﾞ");
            put('ど', "ﾄﾞ");
            put('ば', "ﾊﾞ");
            put('び', "ﾋﾞ");
            put('ぶ', "ﾌﾞ");
            put('べ', "ﾍﾞ");
            put('ぼ', "ﾎﾞ");
            put('ぱ', "ﾊﾟ");
            put('ぴ', "ﾋﾟ");
            put('ぷ', "ﾌﾟ");
            put('ぺ', "ﾍﾟ");
            put('ぽ', "ﾎﾟ");

            put('ゔ', "ｳﾞ");

            put('ぁ', "ｧ");
            put('ぃ', "ｨ");
            put('ぅ', "ｩ");
            put('ぇ', "ｪ");
            put('ぉ', "ｫ");
            put('ゃ', "ｬ");
            put('ゅ', "ｭ");
            put('ょ', "ｮ");
            put('っ', "ｯ");
            put('ー', "ｰ");
            put('。', "｡");
            put('、', "､");
            put('「', "｢");
            put('」', "｣");
            put('・', "･");
            put('゛', "ﾞ");
            put('゜', "ﾟ");
        }
    };

    // 文字を全角英数へ変換
    public static char toWideLatin(char ch) {
        if (ch == '\u0020') {
            return '\u3000';    // 全角スペース
        }
        if (ch == '\u00A5') {   // 「¥」
            return '￥';
        }
        if (ch > '\u0020' && ch < '\u007F') {
            return (char) ((ch - '\u0020') + '\uFF00');
        }
        return ch;
    }

    // 文字列を全角英数へ変換
    public static String toWideLatin(CharSequence cs) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cs.length(); i++) {
            sb.append(toWideLatin(cs.charAt(i)));
        }
        return sb.toString();
    }

    // 構成中文字列の変換
    // String[0]:変換できた部分
    // String[1]:変換できなかったローマ字部分
    public String[] parseRomaji(String key) {
        String value = mRomajiMap.get(key);
        if (value == null) {
            return new String[]{"", key};
        }
        String[] ss = value.split(",");
        if (ss.length == 1) {
            return new String[]{ss[0], ""};
        }
        return ss;
    }

    public void putRomajiMap(String key, String value) {
        mRomajiMap.put(key, value);
    }

}
