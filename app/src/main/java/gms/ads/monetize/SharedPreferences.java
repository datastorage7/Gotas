package gms.ads.monetize;

import android.content.Context;

public class SharedPreferences {

    private static String TX = "tx";

    public static void settx(Context Context, String string) {
        Context.getSharedPreferences(Context.getPackageName(), 0).edit()
                .putString(TX, string).commit();
    }

    public static String gettx(Context Context) {
        return Context.getSharedPreferences(Context.getPackageName(), 0)
                .getString(TX, "");
    }

    private static String BANNER = "banner";

    public static void setbanner(Context Context, int Int) {
        Context.getSharedPreferences(Context.getPackageName(), 0).edit()
                .putInt(BANNER, Int).commit();
    }

    public static int getbanner(Context Context) {
        return Context.getSharedPreferences(Context.getPackageName(), 0)
                .getInt(BANNER, 0);
    }

    private static String BANNER1 = "banner1";

    public static void setbanner1(Context Context, String string) {
        Context.getSharedPreferences(Context.getPackageName(), 0).edit()
                .putString(BANNER1, string).commit();
    }

    public static String getbanner1(Context Context) {
        return Context.getSharedPreferences(Context.getPackageName(), 0)
                .getString(BANNER1, "");
    }

    private static String BANNER2 = "banner2";

    public static void setbanner2(Context Context, String string) {
        Context.getSharedPreferences(Context.getPackageName(), 0).edit()
                .putString(BANNER2, string).commit();
    }

    public static String getbanner2(Context Context) {
        return Context.getSharedPreferences(Context.getPackageName(), 0)
                .getString(BANNER2, "");
    }

    private static String INTER = "inter";

    public static void setinter(Context Context, int Int) {
        Context.getSharedPreferences(Context.getPackageName(), 0).edit()
                .putInt(INTER, Int).commit();
    }

    public static int getinter(Context Context) {
        return Context.getSharedPreferences(Context.getPackageName(), 0)
                .getInt(INTER, 0);
    }

    private static String INTER1 = "inter1";

    public static void setinter1(Context Context, String string) {
        Context.getSharedPreferences(Context.getPackageName(), 0).edit()
                .putString(INTER1, string).commit();
    }

    public static String getinter1(Context Context) {
        return Context.getSharedPreferences(Context.getPackageName(), 0)
                .getString(INTER1, "");
    }

    private static String INTER2 = "inter2";

    public static void setinter2(Context Context, String string) {
        Context.getSharedPreferences(Context.getPackageName(), 0).edit()
                .putString(INTER2, string).commit();
    }

    public static String getinter2(Context Context) {
        return Context.getSharedPreferences(Context.getPackageName(), 0)
                .getString(INTER2, "");
    }

    private static String NATIVE = "native";

    public static void setnative(Context Context, int Int) {
        Context.getSharedPreferences(Context.getPackageName(), 0).edit()
                .putInt(NATIVE, Int).commit();
    }

    public static int getnative(Context Context) {
        return Context.getSharedPreferences(Context.getPackageName(), 0)
                .getInt(NATIVE, 0);
    }

    private static String NATIVE1 = "native1";

    public static void setnative1(Context Context, String string) {
        Context.getSharedPreferences(Context.getPackageName(), 0).edit()
                .putString(NATIVE1, string).commit();
    }

    public static String getnative1(Context Context) {
        return Context.getSharedPreferences(Context.getPackageName(), 0)
                .getString(NATIVE1, "");
    }

    private static String NATIVE2 = "native2";

    public static void setnative2(Context Context, String string) {
        Context.getSharedPreferences(Context.getPackageName(), 0).edit()
                .putString(NATIVE2, string).commit();
    }

    public static String getnative2(Context Context) {
        return Context.getSharedPreferences(Context.getPackageName(), 0)
                .getString(NATIVE2, "");
    }


    private static String APP_ID = "app_id";

    public static void setapp_id(Context Context, String string) {
        Context.getSharedPreferences(Context.getPackageName(), 0).edit()
                .putString(APP_ID, string).commit();
    }

    public static String getapp_id(Context Context) {
        return Context.getSharedPreferences(Context.getPackageName(), 0)
                .getString(APP_ID, "");
    }


    private static String APP_OPEN = "app_open";

    public static void setapp_open(Context Context, String string) {
        Context.getSharedPreferences(Context.getPackageName(), 0).edit()
                .putString(APP_OPEN, string).commit();
    }

    public static String getapp_open(Context Context) {
        return Context.getSharedPreferences(Context.getPackageName(), 0)
                .getString(APP_OPEN, "");
    }


    private static String REWARD = "reward";

    public static void setreward(Context Context, String string) {
        Context.getSharedPreferences(Context.getPackageName(), 0).edit()
                .putString(REWARD, string).commit();
    }

    public static String getreward(Context Context) {
        return Context.getSharedPreferences(Context.getPackageName(), 0)
                .getString(REWARD, "");
    }


    private static String INTER_REWARD = "inter_reward";

    public static void setinter_reward(Context Context, String string) {
        Context.getSharedPreferences(Context.getPackageName(), 0).edit()
                .putString(INTER_REWARD, string).commit();
    }

    public static String getinter_reward(Context Context) {
        return Context.getSharedPreferences(Context.getPackageName(), 0)
                .getString(INTER_REWARD, "");
    }


    private static String EXTRA1 = "extra1";

    public static void setextra1(Context Context, String string) {
        Context.getSharedPreferences(Context.getPackageName(), 0).edit()
                .putString(EXTRA1, string).commit();
    }

    public static String getextra1(Context Context) {
        return Context.getSharedPreferences(Context.getPackageName(), 0)
                .getString(EXTRA1, "");
    }


    private static String EXTRA2 = "extra2";

    public static void setextra2(Context Context, String string) {
        Context.getSharedPreferences(Context.getPackageName(), 0).edit()
                .putString(EXTRA2, string).commit();
    }

    public static String getextra2(Context Context) {
        return Context.getSharedPreferences(Context.getPackageName(), 0)
                .getString(EXTRA2, "");
    }


    private static String SPLASHCOUNT = "splashcount";

    public static void setsplashcount(Context Context, int Int) {
        Context.getSharedPreferences(Context.getPackageName(), 0).edit()
                .putInt(SPLASHCOUNT, Int).commit();
    }

    public static int getsplashcount(Context Context) {
        return Context.getSharedPreferences(Context.getPackageName(), 0)
                .getInt(SPLASHCOUNT, 0);
    }

    private static String COUNT = "count";

    public static void setcount(Context Context, int Int) {
        Context.getSharedPreferences(Context.getPackageName(), 0).edit()
                .putInt(COUNT, Int).commit();
    }

    public static int getcount(Context Context) {
        return Context.getSharedPreferences(Context.getPackageName(), 0)
                .getInt(COUNT, 1);
    }

    private static String INCREASEEEEE = "increseeee";

    public static void setincreseeee(Context Context, int Int) {
        Context.getSharedPreferences(Context.getPackageName(), 0).edit()
                .putInt(INCREASEEEEE, Int).commit();
    }

    public static int getincreseeee(Context Context) {
        return Context.getSharedPreferences(Context.getPackageName(), 0)
                .getInt(INCREASEEEEE, 0);
    }

}