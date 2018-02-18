package com.rokuta96.simpletodo.Model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.PrintWriter;
import java.io.StringWriter;

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

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    /**
     * スリープラッパー
     *
     * @param millis ミリ秒
     */
    public static void sleep(int millis) {
        Logger.d("Start mills:%d", millis);
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            // 処理なしで問題なし
            Logger.w("InterruptedException", e);
        }
    }

    /**
     * スタックトレースを文字列に変換
     *
     * @param e
     * @return スタックトレース文字列
     */
    public static String toStringStackTrace(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}
