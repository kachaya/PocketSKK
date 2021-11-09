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

    // 記号
    private static final HashMap<String, String> mSymbolMap = new HashMap<String, String>() {
        {
            // 記号のローマ字変換は基本的に半角、一部全角

            put(" ", " ");       // スペース[設定項目]
            put("!", "!");
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
            put("-", "ー");      // 長音[固定]
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
            put("?", "?");
            put("@", "@");
            put("[", "「");      // 開き鍵括弧[固定]
            put("\\", "\\");
            put("]", "」");      // 閉じ鍵括弧[固定]
            put("^", "^");
            put("_", "_");
            put("`", "`");
            put("{", "{");
            put("|", "|");
            put("}", "}");
            put("~", "～");      // 全角波ダッシュ

            // 全角記号（'z'で始まる2文字）
            // 他のSKK実装(DDSKK, CorvusSKK等)を参考にしているが、独自に拡張している

            put("z ", "\u3000"); // [共通]全角スペース
            put("z!", "！");         // 全角
            put("z\"", "＼");
            put("z#", "＃");
            put("z$", "§");         // 章標
            put("z%", "z%");
            put("z&", "z&");
            put("z'", "z'");
            put("z(", "（");     // [共通]開き丸括弧
            put("z)", "）");     // [共通]閉じ丸括弧
            put("z*", "※");     // [DDSKK]コメ印
            put("z+", "±");         //
            put("z,", "‥");     // [共通]二点リーダー
            put("z-", "\u301C"); // [共通]「〜」波ダッシュ
            put("z.", "…");      // [共通]三点リーダー
            put("z/", "・");     // [共通]中点

            put("z0", "○");     // [DDSKK]白丸印
            put("z1", "△");         // 白三角
            put("z2", "□");         // 白四角
            put("z3", "◇");         // 白菱形
            put("z4", "☆");         // 白星
            put("z5", "●");         // 黒丸印
            put("z6", "▲");         // 黒三角
            put("z7", "■");         // 黒四角
            put("z8", "◆");         // 黒菱形
            put("z9", "★");         // 黒星

            put("z:", "゜");     // [共通]半濁点
            put("z;", "゛");     // [共通]濁点
            put("z<", "≦");
            put("z=", "≒");
            put("z>", "≧");
            put("z?", "？");         // 全角

            put("z@", "◎");     // [DDSKK]二重丸印

            put("zH", "⇐");         // 左白抜き矢印
            put("zJ", "⇓");         // 下白抜き矢印
            put("zK", "⇑");         // 上白抜き矢印
            put("zL", "⇒");     // [共通]右白抜き矢印

            put("z[", "『");     // [共通]開き二重鍵括弧
            put("z\\", "z\\");
            put("z]", "』");     // [共通]閉じ二重鍵括弧
            put("z^", "z^");
            put("z_", "―");         //

            put("z`", "z`");

            put("zh", "←");     // [共通]左矢印
            put("zj", "↓");     // [共通]下矢印
            put("zk", "↑");     // [共通]上矢印
            put("zl", "→");     // [共通]右矢印
            put("zn", "ー");     // [DDSKK]長音記号
            put("zt", "〒");         // 郵便マーク
            put("zy", "￥");         // 円マーク

            put("z{", "【");     // [共通]開き墨付括弧
            put("z|", "z|");
            put("z}", "】");     // [共通]閉じ墨付括弧
            put("z~", "\uFF5E"); // [共通]全角チルダ「～」

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
