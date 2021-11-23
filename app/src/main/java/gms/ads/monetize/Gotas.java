package gms.ads.monetize;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;
import com.tappx.sdk.android.TappxAdError;
import com.tappx.sdk.android.TappxBanner;
import com.tappx.sdk.android.TappxBannerListener;
import com.tappx.sdk.android.TappxInterstitial;
import com.tappx.sdk.android.TappxInterstitialListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class Gotas {

    Context Contextt;
    AdView mAdView, mAdView_exit;
    public InterstitialAd Splash_InterstialAd, InterstialAd, InterstialAd1;

    public RewardedAd mRewardedAd;
    RewardedInterstitialAd mrewardedInterstitialAd;

    public RewardedInterstitialAd rewardedInterstitialAd;

    String Tx_ID;
    String Interstial, Interstial1, Interstial2;
    String Banner, Banner1, Banner2;


    String Native_ID, Native_ID1, Native_ID2;

    String APP_ID, APP_OPEN, REWARD, INTER_REWARD, EXTRA1, EXTRA2;

    TappxBanner Tappxbanner;
    TappxInterstitial Splash_tappxInterstitial_preload, tappxInterstitial,
            tappxInterstitial_preload;


    public AppOpenAd.AppOpenAdLoadCallback loadCallback;
    private static boolean isShowingAd = false;
    private AppOpenAd appOpenAd = null;

    public static int Server_Yes_No = 1000;


    String Server = "";

    String TX = "", BR1 = "", BR2 = "", IN1 = "", IN2 = "";

    String NA1 = "", NA2 = "", AID = "", AO = "", RD = "", IRD = "", EX1 = "", EX2 = "";

    int Inter_Failed;

    AdView Pre_Load_mAdView;
    int Banner_Load_Not = 5;
    TappxBanner Pre_Tappxbanner;

    ConnectivityManager connec;

    String Packages;
    String Name;

    ProgressDialog Ad_ProgressDialog;

    ArrayList<HashMap<String, String>> contactList;
    public static int Exit_Menu_Decided = 0;
    Boolean doubleBackToExitPressedOnce = false;


    public interface OnRewardgetListner {
        public void OnReward(boolean b);
    }


    @SuppressWarnings("static-access")
    public Gotas(Context context, String Package, String name,
                 final String Tx_id, final String Interstial_ID,
                 final String Interstial_ID1, final String Banner_ID,
                 final String Banner_ID1, final String NativeID1, final String NativeID2,
                 final String App_id, final String App_Open, final String Reward,
                 final String Inter_Reward, final String Extra1, final String Extra2, String server) {

        Contextt = context;
        Packages = Package;
        Name = name;

        Server = server;


        MobileAds.initialize(Contextt, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus
                                                         initializationStatus) {
            }
        });

        connec = (ConnectivityManager) Contextt
                .getSystemService(Contextt.CONNECTIVITY_SERVICE);
        contactList = new ArrayList<HashMap<String, String>>();

        if (isNetworkConnected(Contextt) == true) {

            new GetData().execute();

        } else if (isNetworkConnected(Contextt) == false) {

            Exit_Menu_Decided = 0;
        }


        Tx_ID = Tx_id;

        Interstial1 = Interstial_ID;
        Interstial2 = Interstial_ID1;

        Banner1 = Banner_ID;
        Banner2 = Banner_ID1;

        Native_ID1 = NativeID1;
        Native_ID2 = NativeID2;

        APP_ID = App_id;
        APP_OPEN = App_Open;
        REWARD = Reward;
        INTER_REWARD = Inter_Reward;
        EXTRA1 = Extra1;
        EXTRA2 = Extra2;


    }


    public void Pre_Banner_Load(final int Which_Banner_Load) {

        if (SharedPreferences.getbanner(Contextt) == 0) {

            Banner = SharedPreferences.getbanner1(Contextt);

            SharedPreferences.setbanner(Contextt, 1);

        } else {

            Banner = SharedPreferences.getbanner2(Contextt);

            SharedPreferences.setbanner(Contextt, 0);

        }

        Pre_Load_mAdView = new AdView(Contextt);

        if (Which_Banner_Load == 4) {

            Pre_Load_mAdView.setAdSize(AdSize.MEDIUM_RECTANGLE);

        } else if (Which_Banner_Load == 3) {

            Pre_Load_mAdView.setAdSize(AdSize.LARGE_BANNER);

        } else if (Which_Banner_Load == 2) {

            Pre_Load_mAdView.setAdSize(AdSize.LARGE_BANNER);

        } else if (Which_Banner_Load == 1) {

            Pre_Load_mAdView.setAdSize(AdSize.SMART_BANNER);

        } else {

            Pre_Load_mAdView.setAdSize(AdSize.SMART_BANNER);

        }

        Pre_Load_mAdView.setAdUnitId(Banner);
        AdRequest adore = new AdRequest.Builder().build();
        Pre_Load_mAdView.loadAd(adore);
        Pre_Load_mAdView.setAdListener(new AdListener() {

            @Override
            public void onAdLoaded() {

                super.onAdLoaded();

                SharedPreferences.setsplashcount(Contextt, 0);

                Banner_Load_Not = 1;

            }

            @Override
            public void onAdFailedToLoad(int errorCode) {

                super.onAdFailedToLoad(errorCode);

                Banner_Load_Not = 0;

                Pre_Banner_Load_Tappx(Which_Banner_Load);

            }

        });

    }

    public void Pre_Banner_Load_Tappx(int Which_Banner_Load) {

        Pre_Tappxbanner = new TappxBanner(Contextt, SharedPreferences.gettx(Contextt));

        if (Which_Banner_Load == 4) {

            Pre_Tappxbanner
                    .setAdSize(com.tappx.sdk.android.TappxBanner.AdSize.BANNER_300x250);

        } else if (Which_Banner_Load == 3) {

            Pre_Tappxbanner
                    .setAdSize(com.tappx.sdk.android.TappxBanner.AdSize.BANNER_300x250);

        } else if (Which_Banner_Load == 2) {

            Pre_Tappxbanner
                    .setAdSize(com.tappx.sdk.android.TappxBanner.AdSize.BANNER_300x250);

        } else if (Which_Banner_Load == 1) {

            Pre_Tappxbanner
                    .setAdSize(com.tappx.sdk.android.TappxBanner.AdSize.SMART_BANNER);

        } else {

            Pre_Tappxbanner
                    .setAdSize(com.tappx.sdk.android.TappxBanner.AdSize.SMART_BANNER);

        }

        Pre_Tappxbanner.loadAd();
        Pre_Tappxbanner.setRefreshTimeSeconds(45);

        Pre_Tappxbanner.setListener(new TappxBannerListener() {
            @Override
            public void onBannerLoaded(TappxBanner tappxBanner) {

                SharedPreferences.setsplashcount(Contextt,
                        (SharedPreferences.getsplashcount(Contextt) + 1));

            }

            @Override
            public void onBannerLoadFailed(TappxBanner tappxBanner,
                                           TappxAdError tappxAdError) {

            }

            @Override
            public void onBannerClicked(TappxBanner tappxBanner) {

            }

            @Override
            public void onBannerExpanded(TappxBanner tappxBanner) {

            }

            @Override
            public void onBannerCollapsed(TappxBanner tappxBanner) {

            }
        });

    }

    public void Pre_Banner_Show(final RelativeLayout Ad_Layout,
                                final int Banner_Type) {

        if (Banner_Load_Not == 1) {

            try {

                Ad_Layout.addView(Pre_Load_mAdView);

                RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) Ad_Layout
                        .getLayoutParams();
                relativeParams.setMargins(0, 10, 0, 0);

                Ad_Layout.setLayoutParams(relativeParams);

            } catch (Exception e) {

            }

        }

        if (Banner_Load_Not == 0) {

            Pre_Banner_Show_Tappx(Ad_Layout);

        }

    }

    private void Pre_Banner_Show_Tappx(RelativeLayout Ad_Layout) {


        try {

            Ad_Layout.addView(Pre_Tappxbanner);

            RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) Ad_Layout
                    .getLayoutParams();
            relativeParams.setMargins(0, 10, 0, 0);

            Ad_Layout.setLayoutParams(relativeParams);

        } catch (Exception e) {

        }

    }

    @SuppressLint("NewApi")
    public void Splash_Icon(String App_Name, int Text_Color, int icLauncher,
                            final Context ads_context, final int Splash_Time) {

        if (isNetworkConnected(Contextt) == true) {

            int width = 480, Height = 800;

            final android.app.Dialog builder = new android.app.Dialog(ads_context);
            builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
            builder.getWindow().setBackgroundDrawable(
                    new ColorDrawable(Color.BLACK));
            builder.getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(builder.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;

            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {

                }
            });

            DisplayMetrics displayMetrics = new DisplayMetrics();
            builder.getWindow().getWindowManager().getDefaultDisplay()
                    .getMetrics(displayMetrics);

            width = displayMetrics.widthPixels;
            Height = displayMetrics.heightPixels;


            final RelativeLayout RL = new RelativeLayout(ads_context);
            RL.setBackgroundColor(Color.BLACK);
            RL.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
            builder.addContentView(RL, new RelativeLayout.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT));


            ImageView imageView = new ImageView(ads_context);
            imageView.setImageResource(icLauncher);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            int image_paadding = ((int) (width / 5));
            imageView.setPadding(image_paadding, image_paadding,
                    image_paadding, image_paadding);
            imageView.setTranslationY(-(int) (Height / 7));
            builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT));


            TextView theText = new TextView(ads_context);
            theText.setText("" + App_Name);
            theText.setTextColor(Text_Color);
            theText.setTypeface(Typeface.DEFAULT_BOLD);
            theText.setTextSize((int) (Height / (width / 9)));
            theText.setTranslationY(-(int) (Height / 5.5));

            theText.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
            builder.addContentView(theText, new RelativeLayout.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT));


            ProgressBar progressBar = new ProgressBar(ads_context);

            progressBar.getIndeterminateDrawable().setColorFilter(Text_Color,
                    android.graphics.PorterDuff.Mode.MULTIPLY);

            int progressBar_padding = ((int) (width / 2.5));
            progressBar.setPadding(progressBar_padding, progressBar_padding,
                    progressBar_padding, progressBar_padding);
            progressBar.setTranslationY((float) ((Height) / 2.5));
            builder.addContentView(progressBar,
                    new RelativeLayout.LayoutParams(
                            WindowManager.LayoutParams.MATCH_PARENT,
                            WindowManager.LayoutParams.MATCH_PARENT));

            builder.show();
            builder.getWindow().setAttributes(lp);
            builder.setCancelable(false);

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (Server_Yes_No == 1 || Server_Yes_No == 0) {

                        Splash_Interstial(builder, ads_context);

                    } else {

                        handler.postDelayed(this, 1000);

                    }

                }

            }, 1000);


        }

    }

    private void Splash_Popup_Dissmiss(final android.app.Dialog builder) {


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                builder.dismiss();

            }

        }, 200);

    }

    public void Splash_Interstial(final android.app.Dialog builder, final Context mContext) {

        if (SharedPreferences.getinter(mContext) == 0) {

            Interstial = SharedPreferences.getinter1(mContext);

            SharedPreferences.setinter(mContext, 1);

        } else {

            Interstial = SharedPreferences.getinter2(mContext);

            SharedPreferences.setinter(mContext, 0);

        }

        try {

            AdRequest adRequest = new AdRequest.Builder().build();
            Splash_InterstialAd = new InterstitialAd(mContext);
            Splash_InterstialAd.setAdUnitId(Interstial);

            Splash_InterstialAd.loadAd(adRequest);

            Splash_InterstialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();

                    Splash_InterstialAd.show();

                    SharedPreferences.setsplashcount(mContext, 0);

                    Splash_Popup_Dissmiss(builder);

                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    super.onAdFailedToLoad(errorCode);

                    Splash_InterstialAd = null;

                    Splash_Tappx_Inter(builder, mContext);

                }

            });
        } catch (Exception e) {

        }

    }

    public void Splash_Tappx_Inter(final android.app.Dialog builder, final Context context) {

        Splash_tappxInterstitial_preload = new TappxInterstitial(context,
                SharedPreferences.gettx(context));
        Splash_tappxInterstitial_preload.loadAd();
        Splash_tappxInterstitial_preload
                .setListener(new TappxInterstitialListener() {
                    @Override
                    public void onInterstitialLoaded(
                            TappxInterstitial tappxInterstitial) {

                        tappxInterstitial.show();

                        Splash_Popup_Dissmiss(builder);

                        SharedPreferences.setsplashcount(context,
                                (SharedPreferences.getsplashcount(context) + 1));
                    }

                    @Override
                    public void onInterstitialLoadFailed(
                            TappxInterstitial tappxInterstitial,
                            TappxAdError tappxAdError) {

                        Splash_Popup_Dissmiss(builder);

                    }

                    @Override
                    public void onInterstitialClicked(TappxInterstitial arg0) {


                    }

                    @Override
                    public void onInterstitialDismissed(TappxInterstitial arg0) {


                    }

                    @Override
                    public void onInterstitialShown(TappxInterstitial arg0) {


                    }

                });

    }

    public void Banner(final RelativeLayout Ad_Layout, final int Banner_Type) {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (Server_Yes_No == 1 || Server_Yes_No == 0) {

                    AdSize Banner_Type_Size = null;

                    if (Banner_Type == 1) {

                        Banner_Type_Size = AdSize.SMART_BANNER;

                    } else if (Banner_Type == 2) {

                        Banner_Type_Size = AdSize.LARGE_BANNER;

                    } else if (Banner_Type == 3) {

                        Banner_Type_Size = AdSize.LARGE_BANNER;

                    } else if (Banner_Type == 4) {

                        Banner_Type_Size = AdSize.MEDIUM_RECTANGLE;

                    } else {

                        Banner_Type_Size = AdSize.SMART_BANNER;

                    }

                    if (SharedPreferences.getbanner(Contextt) == 0) {

                        Banner = SharedPreferences.getbanner1(Contextt);

                        SharedPreferences.setbanner(Contextt, 1);

                    } else {

                        Banner = SharedPreferences.getbanner2(Contextt);

                        SharedPreferences.setbanner(Contextt, 0);

                    }

                    mAdView = new AdView(Contextt);
                    mAdView.setAdSize(Banner_Type_Size);
                    mAdView.setAdUnitId(Banner);
                    AdRequest adore = new AdRequest.Builder().build();
                    mAdView.loadAd(adore);
                    Ad_Layout.addView(mAdView);

                    RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) Ad_Layout
                            .getLayoutParams();
                    relativeParams.setMargins(0, 10, 0, 0);

                    Ad_Layout.setLayoutParams(relativeParams);

                    mAdView.setAdListener(new AdListener() {

                        @Override
                        public void onAdLoaded() {


                            Ad_Layout.setVisibility(View.VISIBLE);
                            SharedPreferences.setsplashcount(Contextt, 0);

                            super.onAdLoaded();

                        }

                        @Override
                        public void onAdFailedToLoad(int errorCode) {

                            super.onAdFailedToLoad(errorCode);

                            mAdView.destroy();

                            com.tappx.sdk.android.TappxBanner.AdSize Banner_Type_Size_Tappx = null;

                            if (Banner_Type == 1) {

                                Banner_Type_Size_Tappx = com.tappx.sdk.android.TappxBanner.AdSize.SMART_BANNER;

                            } else if (Banner_Type == 2) {

                                Banner_Type_Size_Tappx = com.tappx.sdk.android.TappxBanner.AdSize.BANNER_300x250;

                            } else if (Banner_Type == 3) {

                                Banner_Type_Size_Tappx = com.tappx.sdk.android.TappxBanner.AdSize.BANNER_300x250;

                            } else if (Banner_Type == 4) {

                                Banner_Type_Size_Tappx = com.tappx.sdk.android.TappxBanner.AdSize.BANNER_300x250;

                            } else {

                                Banner_Type_Size_Tappx = com.tappx.sdk.android.TappxBanner.AdSize.SMART_BANNER;

                            }

                            Tappxbanner = new TappxBanner(Contextt, SharedPreferences
                                    .gettx(Contextt));
                            Tappxbanner.setAdSize(Banner_Type_Size_Tappx);
                            Ad_Layout.addView(Tappxbanner);
                            Tappxbanner.loadAd();
                            Tappxbanner.setRefreshTimeSeconds(45);

                            Tappxbanner.setListener(new TappxBannerListener() {
                                @Override
                                public void onBannerLoaded(
                                        TappxBanner tappxBanner) {
                                    Ad_Layout.setVisibility(View.VISIBLE);

                                    SharedPreferences.setsplashcount(
                                            Contextt,
                                            (SharedPreferences.getsplashcount(Contextt) + 1));

                                }

                                @Override
                                public void onBannerLoadFailed(
                                        TappxBanner tappxBanner,
                                        TappxAdError tappxAdError) {

                                    Ad_Layout.setVisibility(View.GONE);

                                }

                                @Override
                                public void onBannerClicked(
                                        TappxBanner tappxBanner) {
                                    Ad_Layout.setVisibility(View.GONE);
                                }

                                @Override
                                public void onBannerExpanded(
                                        TappxBanner tappxBanner) {
                                }

                                @Override
                                public void onBannerCollapsed(
                                        TappxBanner tappxBanner) {
                                }
                            });

                        }
                    });

                } else {

                    handler.postDelayed(this, 1000);

                }

            }

        }, 1000);

    }

    public void Banner_Main_Linear(final RelativeLayout Ad_Layout, final int Banner_Type) {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (Server_Yes_No == 1 || Server_Yes_No == 0) {

                    AdSize Banner_Type_Size = null;

                    if (Banner_Type == 1) {

                        Banner_Type_Size = AdSize.SMART_BANNER;

                    } else if (Banner_Type == 2) {

                        Banner_Type_Size = AdSize.LARGE_BANNER;

                    } else if (Banner_Type == 3) {

                        Banner_Type_Size = AdSize.LARGE_BANNER;

                    } else if (Banner_Type == 4) {

                        Banner_Type_Size = AdSize.MEDIUM_RECTANGLE;

                    } else {

                        Banner_Type_Size = AdSize.SMART_BANNER;

                    }

                    if (SharedPreferences.getbanner(Contextt) == 0) {

                        Banner = SharedPreferences.getbanner1(Contextt);

                        SharedPreferences.setbanner(Contextt, 1);

                    } else {

                        Banner = SharedPreferences.getbanner2(Contextt);

                        SharedPreferences.setbanner(Contextt, 0);

                    }

                    mAdView = new AdView(Contextt);
                    mAdView.setAdSize(Banner_Type_Size);
                    mAdView.setAdUnitId(Banner);
                    AdRequest adore = new AdRequest.Builder().build();
                    mAdView.loadAd(adore);
                    Ad_Layout.addView(mAdView);

                    LinearLayout.LayoutParams relativeParams = (LinearLayout.LayoutParams) Ad_Layout
                            .getLayoutParams();
                    relativeParams.setMargins(0, 10, 0, 0);

                    Ad_Layout.setLayoutParams(relativeParams);

                    mAdView.setAdListener(new AdListener() {

                        @Override
                        public void onAdLoaded() {


                            Ad_Layout.setVisibility(View.VISIBLE);
                            SharedPreferences.setsplashcount(Contextt, 0);

                            super.onAdLoaded();

                        }

                        @Override
                        public void onAdFailedToLoad(int errorCode) {

                            super.onAdFailedToLoad(errorCode);

                            mAdView.destroy();

                            com.tappx.sdk.android.TappxBanner.AdSize Banner_Type_Size_Tappx = null;

                            if (Banner_Type == 1) {

                                Banner_Type_Size_Tappx = com.tappx.sdk.android.TappxBanner.AdSize.SMART_BANNER;

                            } else if (Banner_Type == 2) {

                                Banner_Type_Size_Tappx = com.tappx.sdk.android.TappxBanner.AdSize.BANNER_300x250;

                            } else if (Banner_Type == 3) {

                                Banner_Type_Size_Tappx = com.tappx.sdk.android.TappxBanner.AdSize.BANNER_300x250;

                            } else if (Banner_Type == 4) {

                                Banner_Type_Size_Tappx = com.tappx.sdk.android.TappxBanner.AdSize.BANNER_300x250;

                            } else {

                                Banner_Type_Size_Tappx = com.tappx.sdk.android.TappxBanner.AdSize.SMART_BANNER;

                            }

                            Tappxbanner = new TappxBanner(Contextt, SharedPreferences
                                    .gettx(Contextt));
                            Tappxbanner.setAdSize(Banner_Type_Size_Tappx);
                            Ad_Layout.addView(Tappxbanner);
                            Tappxbanner.loadAd();
                            Tappxbanner.setRefreshTimeSeconds(45);

                            Tappxbanner.setListener(new TappxBannerListener() {
                                @Override
                                public void onBannerLoaded(
                                        TappxBanner tappxBanner) {
                                    Ad_Layout.setVisibility(View.VISIBLE);

                                    SharedPreferences.setsplashcount(
                                            Contextt,
                                            (SharedPreferences.getsplashcount(Contextt) + 1));

                                }

                                @Override
                                public void onBannerLoadFailed(
                                        TappxBanner tappxBanner,
                                        TappxAdError tappxAdError) {

                                    Ad_Layout.setVisibility(View.GONE);

                                }

                                @Override
                                public void onBannerClicked(
                                        TappxBanner tappxBanner) {
                                    Ad_Layout.setVisibility(View.GONE);
                                }

                                @Override
                                public void onBannerExpanded(
                                        TappxBanner tappxBanner) {
                                }

                                @Override
                                public void onBannerCollapsed(
                                        TappxBanner tappxBanner) {
                                }
                            });

                        }
                    });

                } else {

                    handler.postDelayed(this, 1000);

                }

            }

        }, 1000);

    }

    public void Splash_Screen(final Context mContext, boolean bool,
                              final int When_App_Open_How_Much_Time_After) {

        if (SharedPreferences.getsplashcount(mContext) >= 10) {

            Load_Splash_Goog(mContext, When_App_Open_How_Much_Time_After);

        } else {

            if (bool == true) {

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {

                        Load_Splash_Goog(mContext,
                                When_App_Open_How_Much_Time_After);


                    }

                }, When_App_Open_How_Much_Time_After);

            } else {

            }

        }

    }

    private void Load_Splash_Goog(final Context mContext, int splash_Screen_Time) {


        Ad_Popup(mContext, "Splash Ad . . .");

        if (SharedPreferences.getinter(mContext) == 0) {

            Interstial = SharedPreferences.getinter1(mContext);

            SharedPreferences.setinter(mContext, 1);

        } else {

            Interstial = SharedPreferences.getinter2(mContext);

            SharedPreferences.setinter(mContext, 0);

        }

        try {

            AdRequest adRequest = new AdRequest.Builder().build();
            InterstialAd = new InterstitialAd(mContext);
            InterstialAd.setAdUnitId(Interstial);

            InterstialAd.loadAd(adRequest);

            InterstialAd.setAdListener(new AdListener() {

                @Override
                public void onAdLoaded() {

                    InterstialAd.show();
                    SharedPreferences.setsplashcount(mContext, 0);
                    Ad_ProgressDialog.dismiss();

                    super.onAdLoaded();

                }

                @Override
                public void onAdFailedToLoad(int errorCode) {

                    SharedPreferences.setsplashcount(mContext,
                            (SharedPreferences.getsplashcount(mContext) + 1));
                    Tappx_Inter(mContext);

                    super.onAdFailedToLoad(errorCode);

                }

            });
        } catch (Exception e) {

        }

    }

    public void Interstial(final Context mContext,
                           final int How_Much_Time_After_Interstial_Milisecond) {


        if (isNetworkConnected(Contextt) == true) {

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {

                    Pre_Interstial_Show(mContext);

                }

            }, How_Much_Time_After_Interstial_Milisecond);

        }

    }

    public void Interstial(final Context mContext) {

        if (SharedPreferences.getinter(mContext) == 0) {

            Interstial = SharedPreferences.getinter1(mContext);

            SharedPreferences.setinter(mContext, 1);

        } else {

            Interstial = SharedPreferences.getinter2(mContext);

            SharedPreferences.setinter(mContext, 0);

        }

        Ad_Popup(mContext, "Ad Loading . . .");

        try {

            AdRequest adRequest = new AdRequest.Builder().build();
            InterstialAd = new InterstitialAd(mContext);
            InterstialAd.setAdUnitId(Interstial);

            InterstialAd.loadAd(adRequest);

            InterstialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {

                    InterstialAd.show();
                    SharedPreferences.setsplashcount(mContext, 0);
                    Ad_ProgressDialog.dismiss();

                    super.onAdLoaded();

                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    super.onAdFailedToLoad(errorCode);

                    Tappx_Inter(mContext);

                    super.onAdFailedToLoad(errorCode);

                }

            });
        } catch (Exception e) {

        }

    }


    public void Pre_Interstial_Load(final Context mContext) {

        if (SharedPreferences.getinter(mContext) == 0) {

            Interstial = SharedPreferences.getinter1(mContext);

            SharedPreferences.setinter(mContext, 1);

        } else {

            Interstial = SharedPreferences.getinter2(mContext);

            SharedPreferences.setinter(mContext, 0);

        }

        try {

            AdRequest adRequest = new AdRequest.Builder().build();
            InterstialAd1 = new InterstitialAd(mContext);
            InterstialAd1.setAdUnitId(Interstial);

            InterstialAd1.loadAd(adRequest);

            InterstialAd1.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {

                    Inter_Failed = 0;
                    super.onAdLoaded();

                }

                @Override
                public void onAdFailedToLoad(int errorCode) {

                    super.onAdFailedToLoad(errorCode);

                    InterstialAd1 = null;

                    tappxInterstitial_preload = new TappxInterstitial(mContext,
                            SharedPreferences.gettx(Contextt));
                    tappxInterstitial_preload.loadAd();
                    tappxInterstitial_preload
                            .setListener(new TappxInterstitialListener() {
                                @Override
                                public void onInterstitialLoaded(
                                        TappxInterstitial tappxInterstitial) {

                                    Inter_Failed = 1;

                                }

                                @Override
                                public void onInterstitialLoadFailed(
                                        TappxInterstitial tappxInterstitial,
                                        TappxAdError tappxAdError) {

                                }

                                @Override
                                public void onInterstitialClicked(
                                        TappxInterstitial arg0) {


                                }

                                @Override
                                public void onInterstitialDismissed(
                                        TappxInterstitial arg0) {


                                }

                                @Override
                                public void onInterstitialShown(
                                        TappxInterstitial arg0) {


                                }

                            });

                }

            });
        } catch (Exception e) {

        }

    }


    public void Pre_Interstial_Show(final Context mContext) {

        if (isNetworkConnected(Contextt) == true) {

            if (Inter_Failed == 1) {

                if (tappxInterstitial_preload != null)
                    tappxInterstitial_preload.show();

                SharedPreferences.setsplashcount(mContext,
                        (SharedPreferences.getsplashcount(mContext) + 1));

            }

            if (Inter_Failed == 0) {

                if (InterstialAd1 != null) {
                    InterstialAd1.show();
                    SharedPreferences.setsplashcount(mContext, 0);
                }

            }

            Pre_Interstial_Load(mContext);

        }
    }

    public void Pre_Interstial_Show_End(final Context mContext) {

        if (isNetworkConnected(Contextt) == true) {

            if (Inter_Failed == 1) {

                if (tappxInterstitial_preload != null)
                    tappxInterstitial_preload.show();

                SharedPreferences.setsplashcount(mContext,
                        (SharedPreferences.getsplashcount(mContext) + 1));

            }

            if (Inter_Failed == 0) {

                if (InterstialAd1 != null) {
                    InterstialAd1.show();
                    SharedPreferences.setsplashcount(mContext, 0);
                }

            }

        }

    }

    public void Interstial_Counted(Context mContext,
                                   int How_Much_Click_After_Interstial) {

        if (isNetworkConnected(Contextt) == true) {

            if (How_Much_Click_After_Interstial == SharedPreferences.getcount(mContext)) {

                Pre_Interstial_Show(mContext);

                SharedPreferences.setcount(mContext, 1);

            } else {

                SharedPreferences.setcount(mContext, (SharedPreferences.getcount(mContext) + 1));

            }

        }

    }

    public void Native(Context nContext, final RelativeLayout Ad_Layout,
                       int Native_Type, int Bottom_Ad_Margin, int Top_Ad_Margin, int Animation) {

        if (isNetworkConnected(Contextt) == true) {

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (Server_Yes_No == 1 || Server_Yes_No == 0) {

                        if (SharedPreferences.getnative(nContext) == 0) {

                            Native_ID = SharedPreferences.getnative1(nContext);

                            SharedPreferences.setnative(nContext, 1);


                        } else {

                            Native_ID = SharedPreferences.getnative2(nContext);

                            SharedPreferences.setnative(nContext, 0);


                        }

                        AdLoader adLoader = new AdLoader.Builder(nContext, Native_ID)
                                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                                    private ColorDrawable background;

                                    @Override
                                    public void onNativeAdLoaded(NativeAd nativeAd) {
                                        NativeStyle styles = new
                                                NativeStyle.Builder().withMainBackgroundColor(background).build();


                                        NativeTemplateView templateView = new NativeTemplateView(nContext, Native_Type);

                                        templateView.setStyles(styles);
                                        templateView.setNativeAd(nativeAd);

                                        Ad_Layout.removeAllViews();
                                        Ad_Layout.addView(templateView);
                                        RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) Ad_Layout
                                                .getLayoutParams();
                                        relativeParams.setMargins(0, Bottom_Ad_Margin, 0, Top_Ad_Margin);

                                        Ad_Layout.setLayoutParams(relativeParams);

                                        templateView.setVisibility(View.VISIBLE);


                                    }

                                })
                                .withAdListener(new AdListener() {
                                    @Override
                                    public void onAdFailedToLoad(LoadAdError adError) {


                                        Ad_Layout.removeAllViews();


                                        if (Native_Type == 2) {

                                            Banner(Ad_Layout, 4);

                                        } else {

                                            Banner(Ad_Layout, 3);
                                        }

                                    }
                                })
                                .withNativeAdOptions(new NativeAdOptions.Builder()

                                        .build())
                                .build();

                        adLoader.loadAd(new AdRequest.Builder().build());


                    } else {

                        handler.postDelayed(this, 1000);

                    }

                }

            }, 1000);

        } else {

            Ad_Layout.setVisibility(View.GONE);

        }
    }

    public void Native_Main_Linear(Context nContext, final RelativeLayout Ad_Layout,
                                   int Native_Type, int Bottom_Ad_Margin, int Top_Ad_Margin, int Animation) {

        if (isNetworkConnected(Contextt) == true) {

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (Server_Yes_No == 1 || Server_Yes_No == 0) {


                        if (SharedPreferences.getnative(nContext) == 0) {

                            Native_ID = SharedPreferences.getnative1(nContext);

                            SharedPreferences.setnative(nContext, 1);

                        } else {

                            Native_ID = SharedPreferences.getnative2(nContext);

                            SharedPreferences.setnative(nContext, 0);

                        }

                        AdLoader adLoader = new AdLoader.Builder(nContext, Native_ID)
                                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                                    private ColorDrawable background;

                                    @Override
                                    public void onNativeAdLoaded(NativeAd nativeAd) {
                                        NativeStyle styles = new
                                                NativeStyle.Builder().withMainBackgroundColor(background).build();


                                        NativeTemplateView templateView = new NativeTemplateView(nContext, Native_Type);


                                        templateView.setStyles(styles);
                                        templateView.setNativeAd(nativeAd);

                                        Ad_Layout.removeAllViews();
                                        Ad_Layout.addView(templateView);
                                        LinearLayout.LayoutParams relativeParams = (LinearLayout.LayoutParams) Ad_Layout
                                                .getLayoutParams();
                                        relativeParams.setMargins(0, Bottom_Ad_Margin, 0, Top_Ad_Margin);

                                        Ad_Layout.setLayoutParams(relativeParams);

                                        templateView.setVisibility(View.VISIBLE);


                                    }

                                })
                                .withAdListener(new AdListener() {
                                    @Override
                                    public void onAdFailedToLoad(LoadAdError adError) {


                                        Ad_Layout.removeAllViews();


                                        if (Native_Type == 2) {

                                            Banner_Main_Linear(Ad_Layout, 4);

                                        } else {

                                            Banner_Main_Linear(Ad_Layout, 3);
                                        }

                                    }
                                })
                                .withNativeAdOptions(new NativeAdOptions.Builder()

                                        .build())
                                .build();

                        adLoader.loadAd(new AdRequest.Builder().build());


                    } else {

                        handler.postDelayed(this, 1000);

                    }

                }

            }, 1000);

        } else {

            Ad_Layout.setVisibility(View.GONE);

        }

    }


    public void Start(Context aContext, String Ad_ID) {

        if (isNetworkConnected(Contextt) == true) {

            int width = 480, Height = 800;

            final android.app.Dialog builder = new android.app.Dialog(aContext);
            builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
            builder.getWindow().setBackgroundDrawable(
                    new ColorDrawable(Color.BLACK));
            builder.getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(builder.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;

            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {

                }
            });

            DisplayMetrics displayMetrics = new DisplayMetrics();
            builder.getWindow().getWindowManager().getDefaultDisplay()
                    .getMetrics(displayMetrics);

            width = displayMetrics.widthPixels;
            Height = displayMetrics.heightPixels;


            final RelativeLayout RL = new RelativeLayout(aContext);
            RL.setBackgroundColor(Color.WHITE);
            RL.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
            builder.addContentView(RL, new RelativeLayout.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT));


            Native_ID = "/6499/example/native";

            AdLoader adLoader = new AdLoader.Builder(aContext, Native_ID)
                    .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                        private ColorDrawable background;

                        @Override
                        public void onNativeAdLoaded(NativeAd nativeAd) {
                            NativeStyle styles = new
                                    NativeStyle.Builder().withMainBackgroundColor(background).build();


                            NativeTemplateView templateView = new NativeTemplateView(aContext, 0);


                            templateView.setStyles(styles);
                            templateView.setNativeAd(nativeAd);

                            RL.removeAllViews();
                            RL.addView(templateView);

                            templateView.setVisibility(View.VISIBLE);


                        }

                    })
                    .withAdListener(new AdListener() {
                        @Override
                        public void onAdFailedToLoad(LoadAdError adError) {


                            RL.removeAllViews();
                            Banner(RL, 4);

                        }
                    })
                    .withNativeAdOptions(new NativeAdOptions.Builder()


                            .build())
                    .build();

            adLoader.loadAd(new AdRequest.Builder().build());

            builder.show();
            builder.getWindow().setAttributes(lp);
            builder.setCancelable(false);


        }

    }

    public void Pre_App_Open_Show(Activity currentActivity) {


        if (!isShowingAd && appOpenAd != null) {


            FullScreenContentCallback fullScreenContentCallback =
                    new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {

                            appOpenAd = null;
                            isShowingAd = false;
                            Pre_App_Open_Load(currentActivity);
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {

                            if (tappxInterstitial_preload != null)
                                tappxInterstitial_preload.show();

                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            isShowingAd = true;
                        }
                    };

            appOpenAd.show(currentActivity, fullScreenContentCallback);

        } else {

            Pre_App_Open_Load(currentActivity);

            if (tappxInterstitial_preload != null)
                tappxInterstitial_preload.show();

        }
    }


    public void Pre_App_Open_Load(Context mContext) {

        if (appOpenAd != null) {
            return;
        }

        loadCallback =
                new AppOpenAd.AppOpenAdLoadCallback() {

                    @Override
                    public void onAppOpenAdLoaded(AppOpenAd ad) {
                        appOpenAd = ad;
                    }


                    @Override
                    public void onAppOpenAdFailedToLoad(LoadAdError loadAdError) {


                        tappxInterstitial_preload = new TappxInterstitial(mContext,
                                SharedPreferences.gettx(Contextt));
                        tappxInterstitial_preload.loadAd();
                        tappxInterstitial_preload
                                .setListener(new TappxInterstitialListener() {
                                    @Override
                                    public void onInterstitialLoaded(
                                            TappxInterstitial tappxInterstitial) {


                                    }

                                    @Override
                                    public void onInterstitialLoadFailed(
                                            TappxInterstitial tappxInterstitial,
                                            TappxAdError tappxAdError) {

                                    }

                                    @Override
                                    public void onInterstitialClicked(
                                            TappxInterstitial arg0) {


                                    }

                                    @Override
                                    public void onInterstitialDismissed(
                                            TappxInterstitial arg0) {


                                    }

                                    @Override
                                    public void onInterstitialShown(
                                            TappxInterstitial arg0) {


                                    }

                                });


                    }

                };
        AdRequest request = new AdRequest.Builder().build();
        AppOpenAd.load(
                mContext, SharedPreferences.getapp_open(mContext), request,
                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);


    }

    public void Reward_Inter_Show(Activity mContext, String title, String description, int Popupmenu_Type) {

        CustomDialogue progressDialog = new CustomDialogue(mContext, Popupmenu_Type, title, description);

        progressDialog.show();

        RewardedInterstitialAd.load(mContext, SharedPreferences.getinter_reward(mContext),
                new AdRequest.Builder().build(), new RewardedInterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(RewardedInterstitialAd ad) {


                        rewardedInterstitialAd = ad;

                        rewardedInterstitialAd.show(mContext, new OnUserEarnedRewardListener() {
                            @Override
                            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {

                            }
                        });


                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {


                                progressDialog.dismiss();


                            }

                        }, 1000);


                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        rewardedInterstitialAd = null;
                        progressDialog.dismiss();
                    }
                });

    }


    public void Reward_Inter_Show_with_Dialog(Activity mContext, OnRewardgetListner onRewardgetListner, String title, String description, int Popupmenu_Type) {


        CustomDialogue progressDialog = new CustomDialogue(mContext, Popupmenu_Type, title, description);


        new Dialog.Builder(mContext)
                .setTitle(title)
                .setMessage(description)
                .setTitleTextColor(R.color.titleText)
                .setDescriptionTextColor(R.color.descriptionText)
                .setNegativeBtnText("Close")
                .setPositiveBtnBackground(R.color.positiveButton)
                .setPositiveBtnText("Watch Now (Ad)")
                .setNegativeBtnBackground(R.color.positiveButton)
                .setGifResource(R.drawable.ad)
                .isCancellable(true)
                .OnPositiveClicked(new DialogListener() {
                    @Override
                    public void OnClick() {


                        progressDialog.show();

                        AdRequest adRequest = new AdRequest.Builder().build();
                        RewardedInterstitialAd.load(mContext, SharedPreferences.getinter_reward(mContext),
                                adRequest, new RewardedInterstitialAdLoadCallback() {
                                    @Override
                                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {

                                        mrewardedInterstitialAd = null;


                                        Log.e("TAG", "onAdFailedToLoad: " + loadAdError.getMessage());
                                        onRewardgetListner.OnReward(false);
                                        progressDialog.dismiss();
                                    }

                                    @Override
                                    public void onAdLoaded(@NonNull RewardedInterstitialAd rewardedInterstitialAd) {
                                        super.onAdLoaded(rewardedInterstitialAd);

                                        mrewardedInterstitialAd = rewardedInterstitialAd;

                                        mrewardedInterstitialAd.show(mContext, new OnUserEarnedRewardListener() {
                                            @Override
                                            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                                onRewardgetListner.OnReward(true);
                                            }
                                        });


                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {


                                                progressDialog.dismiss();


                                            }

                                        }, 1000);
                                    }


                                });

                    }
                })
                .OnNegativeClicked(new DialogListener() {
                    @Override
                    public void OnClick() {


                    }
                })
                .build();


    }


    public void Reward(Activity mContext, OnRewardgetListner onRewardgetListner, String title, String description, int Popupmenu_Type) {


        CustomDialogue progressDialog = new CustomDialogue(mContext, Popupmenu_Type, title, description);


        new Dialog.Builder(mContext)
                .setTitle(title)
                .setMessage(description)
                .setTitleTextColor(R.color.titleText)
                .setDescriptionTextColor(R.color.descriptionText)
                .setNegativeBtnText("Close")
                .setPositiveBtnBackground(R.color.positiveButton)
                .setPositiveBtnText("Watch Now (Ad)")
                .setNegativeBtnBackground(R.color.positiveButton)
                .setGifResource(R.drawable.ad)
                .isCancellable(true)
                .OnPositiveClicked(new DialogListener() {
                    @Override
                    public void OnClick() {


                        progressDialog.show();

                        AdRequest adRequest = new AdRequest.Builder().build();
                        RewardedAd.load(mContext, SharedPreferences.getreward(mContext),
                                adRequest, new RewardedAdLoadCallback() {
                                    @Override
                                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {

                                        mRewardedAd = null;


                                        Log.e("TAG", "onAdFailedToLoad: " + loadAdError.getMessage());
                                        onRewardgetListner.OnReward(false);
                                        progressDialog.dismiss();
                                    }

                                    @Override
                                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                                        mRewardedAd = rewardedAd;


                                        mRewardedAd.show(mContext, new OnUserEarnedRewardListener() {
                                            @Override
                                            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {

                                                onRewardgetListner.OnReward(true);


                                            }
                                        });

                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {


                                                progressDialog.dismiss();


                                            }

                                        }, 1000);


                                    }

                                });

                    }
                })
                .OnNegativeClicked(new DialogListener() {
                    @Override
                    public void OnClick() {


                    }
                })
                .build();


    }

    public void Exit(Context aContext) {

        Exit("Do you want to exit ?", aContext,
                "" + aContext.getString(R.string.app_name));

    }

    @SuppressLint({"InflateParams", "InlinedApi"})
    public void Exit(String Description, final Context context, String App_Name) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);


        alertDialogBuilder.setTitle("" + Description);


        alertDialogBuilder

                .setCancelable(false)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                })

                .setNeutralButton("Rate App",
                        new DialogInterface.OnClickListener() {
                            @SuppressLint("InlinedApi")
                            public void onClick(DialogInterface dialog, int id) {

                                Uri uri = Uri.parse("market://details?id="
                                        + Packages);
                                Intent goToMarket = new Intent(
                                        Intent.ACTION_VIEW, uri);
                                goToMarket
                                        .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY
                                                | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                                try {
                                    context.startActivity(goToMarket);
                                } catch (ActivityNotFoundException e) {
                                    context.startActivity(new Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("http://play.google.com/OneForAll/apps/details?id="
                                                    + Packages)));
                                }
                            }
                        })
                .setPositiveButton("Sure",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                                ((Activity) context).moveTaskToBack(true);
                                ((Activity) context).finish();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();

        alertDialog.setCancelable(true);

    }

    public void Tappx_Inter(final Context context) {

        if (isNetworkConnected(Contextt) == true) {

            tappxInterstitial = new TappxInterstitial(context,
                    SharedPreferences.gettx(Contextt));
            tappxInterstitial.loadAd();
            tappxInterstitial.setListener(new TappxInterstitialListener() {
                @Override
                public void onInterstitialLoaded(
                        TappxInterstitial tappxInterstitial) {

                    tappxInterstitial.show();
                    Ad_ProgressDialog.dismiss();

                    SharedPreferences.setsplashcount(context,
                            (SharedPreferences.getsplashcount(context) + 1));
                }

                @Override
                public void onInterstitialLoadFailed(
                        TappxInterstitial tappxInterstitial,
                        TappxAdError tappxAdError) {

                    Ad_ProgressDialog.dismiss();

                }

                @Override
                public void onInterstitialClicked(TappxInterstitial arg0) {


                }

                @Override
                public void onInterstitialDismissed(TappxInterstitial arg0) {


                }

                @Override
                public void onInterstitialShown(TappxInterstitial arg0) {


                }

            });

        }

    }

    public void Exit_With_Ads(final Context context) {

        final int Banner_Type = 1;

        if (Exit_Menu_Decided == 0) {

            onBackPressed(context);

        } else if (Exit_Menu_Decided == 1) {

            Exit("Do you want to exit ?", context,
                    "" + context.getString(R.string.app_name));

        } else if (Exit_Menu_Decided == 2) {

            Exit_Popup_With_Ads(context, Banner_Type);

        } else if (Exit_Menu_Decided == 3) {

            Exit("Do you want to exit ?", context,
                    "" + context.getString(R.string.app_name));

        } else if (Exit_Menu_Decided == 4) {

            Exit_Popup_With_Ads(context, Banner_Type);

        } else {

            onBackPressed(context);

        }

    }

    public void Exit_Popup_With_Ads(final Context context, final int Banner_Type) {

        String Description = "Do you want to exit ?";

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);


        alertDialogBuilder.setTitle("" + Description);


        TextView Exit_Title = new TextView(context);
        Exit_Title.setText("" + Description);
        Exit_Title.setTextSize(25);
        Exit_Title.setGravity(Gravity.CENTER_HORIZONTAL);
        alertDialogBuilder.setView(Exit_Title);


        if (SharedPreferences.getbanner(Contextt) == 0) {

            Banner = SharedPreferences.getbanner1(Contextt);

            SharedPreferences.setbanner(Contextt, 1);

        } else {

            Banner = SharedPreferences.getbanner2(Contextt);

            SharedPreferences.setbanner(Contextt, 0);

        }

        AdSize Banner_Type_Size = null;

        if (Banner_Type == 1) {

            Banner_Type_Size = AdSize.MEDIUM_RECTANGLE;

        } else if (Banner_Type == 2) {

            Banner_Type_Size = AdSize.LARGE_BANNER;

        } else if (Banner_Type == 3) {

            Banner_Type_Size = AdSize.LARGE_BANNER;

        } else {

            Banner_Type_Size = AdSize.MEDIUM_RECTANGLE;

        }

        final RelativeLayout Exit_Ads = new RelativeLayout(context);
        Exit_Ads.setGravity(Gravity.CENTER_HORIZONTAL);

        mAdView_exit = new AdView(context);
        mAdView_exit.setAdSize(Banner_Type_Size);
        mAdView_exit.setAdUnitId(Banner);
        AdRequest adre = new AdRequest.Builder().build();
        mAdView_exit.loadAd(adre);
        Exit_Ads.addView(mAdView_exit);
        alertDialogBuilder.setView(Exit_Ads);

        mAdView_exit.setAdListener(new AdListener() {

            @Override
            public void onAdLoaded() {


                SharedPreferences.setsplashcount(context, 0);

                super.onAdLoaded();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {


                super.onAdFailedToLoad(errorCode);

                com.tappx.sdk.android.TappxBanner.AdSize Banner_Type_Size_Tappx = null;

                if (Banner_Type == 1) {

                    Banner_Type_Size_Tappx = com.tappx.sdk.android.TappxBanner.AdSize.BANNER_300x250;

                } else if (Banner_Type == 2) {

                    Banner_Type_Size_Tappx = com.tappx.sdk.android.TappxBanner.AdSize.BANNER_300x250;

                } else if (Banner_Type == 3) {

                    Banner_Type_Size_Tappx = com.tappx.sdk.android.TappxBanner.AdSize.BANNER_300x250;

                } else if (Banner_Type == 4) {

                    Banner_Type_Size_Tappx = com.tappx.sdk.android.TappxBanner.AdSize.BANNER_300x250;

                } else {

                    Banner_Type_Size_Tappx = com.tappx.sdk.android.TappxBanner.AdSize.BANNER_300x250;

                }

                Tappxbanner = new TappxBanner(context, SharedPreferences.gettx(context));
                Tappxbanner.setAdSize(Banner_Type_Size_Tappx);
                Exit_Ads.addView(Tappxbanner);
                Tappxbanner.loadAd();
                Tappxbanner.setRefreshTimeSeconds(45);

                Tappxbanner.setListener(new TappxBannerListener() {
                    @Override
                    public void onBannerLoaded(TappxBanner tappxBanner) {
                        Exit_Ads.setVisibility(View.VISIBLE);

                        SharedPreferences.setsplashcount(context,
                                (SharedPreferences.getsplashcount(context) + 1));

                    }

                    @Override
                    public void onBannerLoadFailed(TappxBanner tappxBanner,
                                                   TappxAdError tappxAdError) {

                        Exit_Ads.setVisibility(View.GONE);

                    }

                    @Override
                    public void onBannerClicked(TappxBanner tappxBanner) {
                        Exit_Ads.setVisibility(View.GONE);
                    }

                    @Override
                    public void onBannerExpanded(TappxBanner tappxBanner) {
                    }

                    @Override
                    public void onBannerCollapsed(TappxBanner tappxBanner) {
                    }
                });

            }
        });


        alertDialogBuilder

                .setCancelable(false)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                })

                .setNeutralButton("Rate App",
                        new DialogInterface.OnClickListener() {
                            @SuppressLint("InlinedApi")
                            public void onClick(DialogInterface dialog, int id) {

                                Uri uri = Uri.parse("market://details?id="
                                        + Packages);
                                Intent goToMarket = new Intent(
                                        Intent.ACTION_VIEW, uri);

                                goToMarket
                                        .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY
                                                | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                                try {
                                    context.startActivity(goToMarket);
                                } catch (ActivityNotFoundException e) {
                                    context.startActivity(new Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("http://play.google.com/OneForAll/apps/details?id="
                                                    + Packages)));
                                }
                            }
                        })
                .setPositiveButton("Sure",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                                ((Activity) context).moveTaskToBack(true);
                                ((Activity) context).finish();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();

        alertDialog.setCancelable(true);

    }


    public void Exit_With_Ads_Native(final Context context) {

        if (Exit_Menu_Decided == 0) {

            onBackPressed(context);

        } else if (Exit_Menu_Decided == 1) {

            Exit("Do you want to exit ?", context,
                    "" + context.getString(R.string.app_name));

        } else if (Exit_Menu_Decided == 2) {

            Exit_Popup_With_Ads_Native(context);

        } else if (Exit_Menu_Decided == 3) {

            Exit("Do you want to exit ?", context,
                    "" + context.getString(R.string.app_name));

        } else if (Exit_Menu_Decided == 4) {

            Exit_Popup_With_Ads_Native(context);

        } else {

            onBackPressed(context);

        }

    }


    public void Exit_Popup_With_Ads_Native(final Context context) {

        String Description = "Do you want to exit ?";

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);


        alertDialogBuilder.setTitle("" + Description);


        TextView Exit_Title = new TextView(context);
        Exit_Title.setText("" + Description);
        Exit_Title.setTextSize(25);
        Exit_Title.setGravity(Gravity.CENTER_HORIZONTAL);
        alertDialogBuilder.setView(Exit_Title);

        final RelativeLayout Exit_Ads = new RelativeLayout(context);
        Exit_Ads.setGravity(Gravity.CENTER_HORIZONTAL);
        alertDialogBuilder.setView(Exit_Ads);

        Native_Back(context, Exit_Ads, 2, 0, 0, 0);


        alertDialogBuilder

                .setCancelable(false)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                })

                .setNeutralButton("Rate App",
                        new DialogInterface.OnClickListener() {
                            @SuppressLint("InlinedApi")
                            public void onClick(DialogInterface dialog, int id) {

                                Uri uri = Uri.parse("market://details?id="
                                        + Packages);
                                Intent goToMarket = new Intent(
                                        Intent.ACTION_VIEW, uri);

                                goToMarket
                                        .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY
                                                | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                                try {
                                    context.startActivity(goToMarket);
                                } catch (ActivityNotFoundException e) {
                                    context.startActivity(new Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("http://play.google.com/OneForAll/apps/details?id="
                                                    + Packages)));
                                }
                            }
                        })
                .setPositiveButton("Sure",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                                ((Activity) context).moveTaskToBack(true);
                                ((Activity) context).finish();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();

        alertDialog.setCancelable(true);

    }

    public void Native_Back(Context nContext, final RelativeLayout Ad_Layout, int Native_Type, int Bottom_Ad_Margin, int Top_Ad_Margin, int Animation) {


        if (isNetworkConnected(Contextt) == true) {

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (Server_Yes_No == 1 || Server_Yes_No == 0) {


                        if (SharedPreferences.getnative(nContext) == 0) {

                            Native_ID = SharedPreferences.getnative1(nContext);

                            SharedPreferences.setnative(nContext, 1);


                        } else {

                            Native_ID = SharedPreferences.getnative2(nContext);

                            SharedPreferences.setnative(nContext, 0);


                        }

                        AdLoader adLoader = new AdLoader.Builder(nContext, Native_ID)
                                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                                    private ColorDrawable background;

                                    @Override
                                    public void onNativeAdLoaded(NativeAd nativeAd) {
                                        NativeStyle styles = new
                                                NativeStyle.Builder().withMainBackgroundColor(background).build();


                                        NativeTemplateView templateView = new NativeTemplateView(nContext, 2);


                                        templateView.setStyles(styles);
                                        templateView.setNativeAd(nativeAd);

                                        Ad_Layout.removeAllViews();
                                        Ad_Layout.addView(templateView);

                                        templateView.setVisibility(View.VISIBLE);


                                    }

                                })
                                .withAdListener(new AdListener() {
                                    @Override
                                    public void onAdFailedToLoad(LoadAdError adError) {


                                        Ad_Layout.removeAllViews();


                                        Banner_Back(Ad_Layout, 4);


                                    }
                                })
                                .withNativeAdOptions(new NativeAdOptions.Builder()


                                        .build())
                                .build();

                        adLoader.loadAd(new AdRequest.Builder().build());


                    } else {

                        handler.postDelayed(this, 1000);

                    }

                }

            }, 1000);

        } else {

            Ad_Layout.setVisibility(View.GONE);

        }

    }

    public void Banner_Back(final RelativeLayout Ad_Layout, final int Banner_Type) {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (Server_Yes_No == 1 || Server_Yes_No == 0) {

                    AdSize Banner_Type_Size = null;

                    if (Banner_Type == 1) {

                        Banner_Type_Size = AdSize.SMART_BANNER;

                    } else if (Banner_Type == 2) {

                        Banner_Type_Size = AdSize.LARGE_BANNER;

                    } else if (Banner_Type == 3) {

                        Banner_Type_Size = AdSize.LARGE_BANNER;

                    } else if (Banner_Type == 4) {

                        Banner_Type_Size = AdSize.MEDIUM_RECTANGLE;

                    } else {

                        Banner_Type_Size = AdSize.SMART_BANNER;

                    }

                    if (SharedPreferences.getbanner(Contextt) == 0) {

                        Banner = SharedPreferences.getbanner1(Contextt);

                        SharedPreferences.setbanner(Contextt, 1);

                    } else {

                        Banner = SharedPreferences.getbanner2(Contextt);

                        SharedPreferences.setbanner(Contextt, 0);

                    }

                    mAdView = new AdView(Contextt);
                    mAdView.setAdSize(Banner_Type_Size);
                    mAdView.setAdUnitId(Banner);
                    AdRequest adore = new AdRequest.Builder().build();
                    mAdView.loadAd(adore);
                    Ad_Layout.addView(mAdView);

                    mAdView.setAdListener(new AdListener() {

                        @Override
                        public void onAdLoaded() {


                            Ad_Layout.setVisibility(View.VISIBLE);
                            SharedPreferences.setsplashcount(Contextt, 0);

                            super.onAdLoaded();

                        }

                        @Override
                        public void onAdFailedToLoad(int errorCode) {

                            super.onAdFailedToLoad(errorCode);

                            mAdView.destroy();

                            com.tappx.sdk.android.TappxBanner.AdSize Banner_Type_Size_Tappx = null;

                            if (Banner_Type == 1) {

                                Banner_Type_Size_Tappx = com.tappx.sdk.android.TappxBanner.AdSize.SMART_BANNER;

                            } else if (Banner_Type == 2) {

                                Banner_Type_Size_Tappx = com.tappx.sdk.android.TappxBanner.AdSize.BANNER_300x250;

                            } else if (Banner_Type == 3) {

                                Banner_Type_Size_Tappx = com.tappx.sdk.android.TappxBanner.AdSize.BANNER_300x250;

                            } else if (Banner_Type == 4) {

                                Banner_Type_Size_Tappx = com.tappx.sdk.android.TappxBanner.AdSize.BANNER_300x250;

                            } else {

                                Banner_Type_Size_Tappx = com.tappx.sdk.android.TappxBanner.AdSize.SMART_BANNER;

                            }

                            Tappxbanner = new TappxBanner(Contextt, SharedPreferences
                                    .gettx(Contextt));
                            Tappxbanner.setAdSize(Banner_Type_Size_Tappx);
                            Ad_Layout.addView(Tappxbanner);
                            Tappxbanner.loadAd();
                            Tappxbanner.setRefreshTimeSeconds(45);

                            Tappxbanner.setListener(new TappxBannerListener() {
                                @Override
                                public void onBannerLoaded(
                                        TappxBanner tappxBanner) {
                                    Ad_Layout.setVisibility(View.VISIBLE);

                                    SharedPreferences.setsplashcount(
                                            Contextt,
                                            (SharedPreferences.getsplashcount(Contextt) + 1));

                                }

                                @Override
                                public void onBannerLoadFailed(
                                        TappxBanner tappxBanner,
                                        TappxAdError tappxAdError) {

                                    Ad_Layout.setVisibility(View.GONE);

                                }

                                @Override
                                public void onBannerClicked(
                                        TappxBanner tappxBanner) {
                                    Ad_Layout.setVisibility(View.GONE);
                                }

                                @Override
                                public void onBannerExpanded(
                                        TappxBanner tappxBanner) {
                                }

                                @Override
                                public void onBannerCollapsed(
                                        TappxBanner tappxBanner) {
                                }
                            });

                        }
                    });

                } else {

                    handler.postDelayed(this, 1000);

                }

            }

        }, 1000);

    }


    private void Ad_Popup(Context mContext, String Title_Text_Of_Popup) {


        Ad_ProgressDialog = ProgressDialog.show(mContext, "", ""
                + Title_Text_Of_Popup, true);

        Ad_ProgressDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        Ad_ProgressDialog.setCancelable(true);
        Ad_ProgressDialog.show();

    }

    public void Rate_App_Randomly(Context mContext) {


        if ((new Random().nextInt((20 - 1) + 1) + 1) == 10) {
            Rate_App(mContext);
        }

    }

    public void Rate_App(final Context mContext) {


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mContext);


        alertDialogBuilder.setTitle("" + mContext.getString(R.string.app_name));


        alertDialogBuilder
                .setMessage("Please Rate Our Application")
                .setCancelable(true)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @SuppressLint("InlinedApi")
                            public void onClick(DialogInterface dialog, int id) {

                                Uri uri = Uri.parse("market://details?id="
                                        + mContext.getPackageName());
                                Intent goToMarket = new Intent(
                                        Intent.ACTION_VIEW, uri);

                                goToMarket
                                        .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY
                                                | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                                try {
                                    mContext.startActivity(goToMarket);
                                } catch (ActivityNotFoundException e) {
                                    mContext.startActivity(new Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("http://play.google.com/OneForAll/apps/details?id="
                                                    + mContext.getPackageName())));
                                }
                            }
                        })

                .setNeutralButton("Remind Me Later",
                        new DialogInterface.OnClickListener() {
                            @SuppressLint("InlinedApi")
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();

                            }
                        })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();

    }

    private class GetData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();


            String jsonStr = sh.makeServiceCall("" + Server);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);


                    JSONArray contacts = jsonObj.getJSONArray("" + Packages);


                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String id = c.getString("exit_popup");
                        String tx = c.getString("tx");
                        String b1 = c.getString("b1");
                        String b2 = c.getString("b2");
                        String i1 = c.getString("i1");
                        String i2 = c.getString("i2");
                        String na1 = c.getString("na1");
                        String na2 = c.getString("na2");
                        String aid = c.getString("aid");
                        String ao = c.getString("ao");
                        String rd = c.getString("rd");
                        String ird = c.getString("ird");
                        String extra1 = c.getString("extra1");
                        String extra2 = c.getString("extra2");


                        HashMap<String, String> contact = new HashMap<String, String>();


                        contact.put("exit_popup", id);
                        contact.put("tx", tx);
                        contact.put("b1", b1);
                        contact.put("b2", b2);
                        contact.put("i1", i1);
                        contact.put("i2", i2);
                        contact.put("na1", na1);
                        contact.put("na2", na2);
                        contact.put("aid", aid);
                        contact.put("ao", ao);
                        contact.put("rd", rd);
                        contact.put("ird", ird);
                        contact.put("extra1", extra1);
                        contact.put("extra2", extra2);


                        contactList.add(contact);

                    }
                } catch (final JSONException e) {

                }
            } else {

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);


            if (contactList.size() > 0) {

                Exit_Menu_Decided = Integer.parseInt(contactList.get(0).get("exit_popup"));

                SharedPreferences.setincreseeee(Contextt, Exit_Menu_Decided);

                TX = "" + contactList.get(0).get("tx");
                BR1 = "" + contactList.get(0).get("b1");
                BR2 = "" + contactList.get(0).get("b2");
                IN1 = "" + contactList.get(0).get("i1");
                IN2 = "" + contactList.get(0).get("i2");
                NA1 = "" + contactList.get(0).get("na1");
                NA2 = "" + contactList.get(0).get("na2");
                AID = "" + contactList.get(0).get("aid");
                AO = "" + contactList.get(0).get("ao");
                RD = "" + contactList.get(0).get("rd");
                IRD = "" + contactList.get(0).get("ird");
                EX1 = "" + contactList.get(0).get("extra1");
                EX2 = "" + contactList.get(0).get("extra2");

                SharedPreferences.settx(Contextt, "" + TX);
                SharedPreferences.setbanner1(Contextt, "" + BR1);
                SharedPreferences.setbanner2(Contextt, "" + BR2);
                SharedPreferences.setinter1(Contextt, "" + IN1);
                SharedPreferences.setinter2(Contextt, "" + IN2);
                SharedPreferences.setnative1(Contextt, "" + NA1);
                SharedPreferences.setnative2(Contextt, "" + NA2);
                SharedPreferences.setapp_id(Contextt, "" + AID);
                SharedPreferences.setapp_open(Contextt, "" + AO);
                SharedPreferences.setreward(Contextt, "" + RD);
                SharedPreferences.setinter_reward(Contextt, "" + IRD);
                SharedPreferences.setextra1(Contextt, "" + EX1);
                SharedPreferences.setextra2(Contextt, "" + EX2);


                if ((SharedPreferences.getapp_id(Contextt)).equals("ca-app-pub-3940256099942544~3347511713")) {

                } else {

                    MobileAds.initialize(Contextt, SharedPreferences.getapp_id(Contextt));

                }

                Server_Yes_No = 1;

                if ((SharedPreferences.getextra1(Contextt)).equals("1")) {

                    Pre_Interstial_Load(Contextt);

                } else if ((SharedPreferences.getextra1(Contextt)).equals("2")) {

                    Pre_App_Open_Load(Contextt);

                } else if ((SharedPreferences.getextra1(Contextt)).equals("3")) {

                    Pre_Interstial_Load(Contextt);

                    Pre_App_Open_Load(Contextt);

                } else {

                    Pre_Interstial_Load(Contextt);

                    Pre_App_Open_Load(Contextt);

                }


            }

            if (contactList.size() == 0) {

                SharedPreferences.settx(Contextt, "" + Tx_ID);
                SharedPreferences.setbanner1(Contextt, "" + Banner1);
                SharedPreferences.setbanner2(Contextt, "" + Banner2);
                SharedPreferences.setinter1(Contextt, "" + Interstial1);
                SharedPreferences.setinter2(Contextt, "" + Interstial2);
                SharedPreferences.setnative1(Contextt, "" + Native_ID1);
                SharedPreferences.setnative2(Contextt, "" + Native_ID2);
                SharedPreferences.setapp_id(Contextt, "" + APP_ID);
                SharedPreferences.setapp_open(Contextt, "" + APP_OPEN);
                SharedPreferences.setreward(Contextt, "" + REWARD);
                SharedPreferences.setinter_reward(Contextt, "" + INTER_REWARD);
                SharedPreferences.setextra1(Contextt, "" + EXTRA1);
                SharedPreferences.setextra2(Contextt, "" + EXTRA2);

                if ((SharedPreferences.getapp_id(Contextt)).equals("ca-app-pub-3940256099942544~3347511713")) {

                } else {

                    MobileAds.initialize(Contextt, SharedPreferences.getapp_id(Contextt));

                }

                Exit_Menu_Decided = 0;

                Server_Yes_No = 0;

                if ((SharedPreferences.getextra1(Contextt)).equals("1")) {

                    Pre_Interstial_Load(Contextt);

                } else if ((SharedPreferences.getextra1(Contextt)).equals("2")) {

                    Pre_App_Open_Load(Contextt);

                } else if ((SharedPreferences.getextra1(Contextt)).equals("3")) {

                    Pre_Interstial_Load(Contextt);

                    Pre_App_Open_Load(Contextt);

                } else {

                    Pre_Interstial_Load(Contextt);

                    Pre_App_Open_Load(Contextt);

                }

            }

        }

    }

    public class HttpHandler {

        public HttpHandler() {
        }

        public String makeServiceCall(String reqUrl) {
            String response = null;
            try {
                URL url = new URL(reqUrl);
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                conn.setRequestMethod("GET");

                InputStream in = new BufferedInputStream(conn.getInputStream());
                response = convertStreamToString(in);
            } catch (MalformedURLException e) {

            } catch (ProtocolException e) {

            } catch (IOException e) {

            } catch (Exception e) {

            }
            return response;
        }

        private String convertStreamToString(InputStream is) {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return sb.toString();
        }
    }

    private boolean isNetworkConnected(Context aContext) {
        ConnectivityManager cm = (ConnectivityManager) aContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public void onBackPressed(Context aContext) {

        if (doubleBackToExitPressedOnce) {
            ((Activity) aContext).moveTaskToBack(true);
            ((Activity) aContext).finish();
        } else {

            Toast.makeText(aContext, "Press Back Again to Exit",
                    Toast.LENGTH_SHORT).show();

            doubleBackToExitPressedOnce = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);

        }

    }

    public void After_2_3(Context aContext) {

        if (Exit_Menu_Decided == 3) {

            Pre_Interstial_Show(aContext);

        }

        if (Exit_Menu_Decided == 4) {

            Pre_Interstial_Show(aContext);

        }

    }

    public void onDestroy(Context aContext) {

        if (Splash_tappxInterstitial_preload != null) {
            Splash_tappxInterstitial_preload.destroy();
        }
        if (tappxInterstitial != null) {
            tappxInterstitial.destroy();
        }
        if (Tappxbanner != null) {
            Tappxbanner.destroy();
        }
        if (tappxInterstitial_preload != null) {
            tappxInterstitial_preload.destroy();
        }
        if (Splash_InterstialAd != null) {
        }
        if (InterstialAd != null) {
        }
        if (InterstialAd1 != null) {
        }
        if (mAdView != null) {
            mAdView.destroy();
        }
        if (mAdView_exit != null) {
            mAdView_exit.destroy();
        }
    }

}