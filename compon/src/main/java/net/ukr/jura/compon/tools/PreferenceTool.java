package net.ukr.jura.compon.tools;

import android.content.Context;
import android.content.SharedPreferences;

import net.ukr.jura.compon.ComponGlob;

public class PreferenceTool {
    private static final String PREFERENCES_NAME = "simple_app_prefs";
    private static final String TUTORIAL = "tutorial";
    private static final String AUTH = "auth";
    private static final String USER_KEY = "user_key";
    private static final String SESSION_COOKIE = "session_cookie";
    private static final String SESSION_TOKEN = "session_token";

    public static void setNameBoolean(String name, boolean value) {
        getEditor().putBoolean(name, value).commit();
    }

    public static void setNameString(String name, String value) {
        getEditor().putString(name, value).commit();
    }

    public static void setTutorial(boolean value) {
        getEditor().putBoolean(TUTORIAL, value).commit();
    }

    public static boolean getTutorial() {
        return getSharedPreferences().getBoolean(TUTORIAL, false);
    }

    public static void setAuth(boolean value) {
        getEditor().putBoolean(AUTH, value).commit();
    }

    public static boolean getAuth() {
        return getSharedPreferences().getBoolean(AUTH, false);
    }

    public static void setSessionToken(String token) {
        getEditor().putString(SESSION_TOKEN, token).commit();
    }

    public static String getSessionToken() {
        return getSharedPreferences().getString(SESSION_TOKEN, null);
    }

    public static void setSessionCookie(String cookie) {
        getEditor().putString(SESSION_COOKIE, cookie).commit();
    }

    public static String getSessionCookie() {
        return getSharedPreferences().getString(SESSION_COOKIE, null);
    }

    public static void setUserKey(String user_key) {
        getEditor().putString(USER_KEY, user_key).commit();
    }

    public static String getUserKey() {
        return getSharedPreferences().getString(USER_KEY, "");
    }

    //  *************************************************
    private static SharedPreferences.Editor getEditor() {
        return ComponGlob.getInstance().context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).edit();
    }

    private static SharedPreferences getSharedPreferences() {
        return ComponGlob.getInstance().context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }
}
