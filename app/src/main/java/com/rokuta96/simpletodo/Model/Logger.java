package com.rokuta96.simpletodo.Model;

import android.util.Log;

/**
 * Logのラッパークラス
 * ・標準Logクラスだと「"message:" + id」のような時にproguardから処理自体のコストを削除できないため、
 * Logのラッパークラスで可変長引数を使うことでRelease時のコストを標準Logクラスより減らせる
 * 以下記述をproguardに追加することでRelease時にLogを削除できる
 * -assumenosideeffects class com.example.Logger { *; }
 */
public class Logger {

    public static void v(String msg) {
        final String tag = createTag();
        Log.v(tag, msg);
    }

    public static void v(String format, Object... args) {
        final String tag = createTag();
        final String msg = String.format(format, args);
        Log.v(tag, msg);
    }

    public static void v(String msg, Throwable e) {
        final String tag = createTag();
        Log.v(tag, msg, e);
    }

    public static void v(String format, Throwable e, Object... args) {
        final String tag = createTag();
        final String msg = String.format(format, args);
        Log.v(tag, msg, e);
    }

    public static void d(String msg) {
        final String tag = createTag();
        Log.d(tag, msg);
    }

    public static void d(String format, Object... args) {
        final String tag = createTag();
        final String msg = String.format(format, args);
        Log.d(tag, msg);
    }

    public static void d(String msg, Throwable e) {
        final String tag = createTag();
        Log.d(tag, msg, e);
    }

    public static void d(String format, Throwable e, Object... args) {
        final String tag = createTag();
        final String msg = String.format(format, args);
        Log.d(tag, msg, e);
    }

    public static void i(String msg) {
        final String tag = createTag();
        Log.i(tag, msg);
    }

    public static void i(String format, Object... args) {
        final String tag = createTag();
        final String msg = String.format(format, args);
        Log.i(tag, msg);
    }

    public static void i(String msg, Throwable e) {
        final String tag = createTag();
        Log.i(tag, msg, e);
    }

    public static void i(String format, Throwable e, Object... args) {
        final String tag = createTag();
        final String msg = String.format(format, args);
        Log.i(tag, msg, e);
    }

    public static void w(String msg) {
        final String tag = createTag();
        Log.w(tag, msg);
    }

    public static void w(String format, Object... args) {
        final String tag = createTag();
        final String msg = String.format(format, args);
        Log.w(tag, msg);
    }

    public static void w(String msg, Throwable e) {
        final String tag = createTag();
        Log.w(tag, msg, e);
    }

    public static void w(String format, Throwable e, Object... args) {
        final String tag = createTag();
        final String msg = String.format(format, args);
        Log.w(tag, msg, e);
    }

    public static void e(String msg) {
        final String tag = createTag();
        Log.e(tag, msg);
    }

    public static void e(String format, Object... args) {
        final String tag = createTag();
        final String msg = String.format(format, args);
        Log.e(tag, msg);
    }

    public static void e(String msg, Throwable e) {
        final String tag = createTag();
        Log.e(tag, msg, e);
    }

    public static void e(String format, Throwable e, Object... args) {
        final String tag = createTag();
        final String msg = String.format(format, args);
        Log.e(tag, msg, e);
    }

    /**
     * ログに表示するタグ（クラス名＋メソッド名＋行番号）を作成
     *
     * @return タグ用文字列
     */
    private static String createTag() {
        StringBuilder sb = new StringBuilder();
        sb.append("<<");
        try {
            throw new Exception();
        } catch (Exception e) {
            // スタックトレースから呼び出し元を取得
            StackTraceElement[] es = e.getStackTrace();
            if (es != null && es.length >= 2) {
                String fullname = es[2].getClassName();
                sb.append(fullname.substring(fullname.lastIndexOf(".") + 1))
                        .append('.')
                        .append(es[2].getMethodName())
                        .append('-')
                        .append(String.valueOf(es[2].getLineNumber()))
                        .append('@')
                        .append(Thread.currentThread().getId());
            }
        }
        sb.append(">>");

        return sb.toString();
    }
}
