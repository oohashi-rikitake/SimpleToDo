package com.rokuta96.simpletodo.Model;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class Util {

    /**
     * 文字列が数値か？
     *
     * @param str 検査したい文字列
     * @return true数値、false数値以外
     */
    public static boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            // 処理なしで問題なし
            return false;
        }
    }

    public static String encodeUrl(String target) {
        String encodedString = target;
        try {
            encodedString = URLEncoder.encode(target, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Logger.w("Exception", e);
        }

        return encodedString;
    }

    public static String decodeUrl(String target) {
        String decodedString = target;
        try {
            decodedString = URLDecoder.decode(target, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Logger.w("Exception", e);
        }

        return decodedString;
    }
}
