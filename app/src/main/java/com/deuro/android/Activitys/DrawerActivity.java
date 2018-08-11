package com.deuro.android.Activitys;

import android.animation.Animator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import yalantis.com.sidemenu.util.ViewAnimator;

import com.deuro.android.Fragments.Home_Fragment;
import com.deuro.android.Fragments.Webview_Fragment;
import com.deuro.android.R;
import java.util.ArrayList;
import java.util.List;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;

public class DrawerActivity extends AppCompatActivity implements ViewAnimator.ViewAnimatorListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
    private ViewAnimator viewAnimator;
    private LinearLayout linearLayout;
    private ImageView telegram_imageview;
    String ai = "http://18.221.131.198:19999";
    String sys = "http://18.221.131.198:19999";
    String dapp = "http://18.221.131.198:19999";
    private Context mContext;
    public static final String CLOSE = "Close";
    public static final String HOME = "Home";
    public static final String AI = "Ai";
    public static final String SYS = "Sys";
    public static final String dAPP = "dApp";
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        mContext = this;
        Home_Fragment home_fragment = new Home_Fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, home_fragment).commit();
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        linearLayout = findViewById(R.id.left_drawer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorBlack));
        setActionBar();
        createMenuList();
        viewAnimator = new ViewAnimator<>(this, list, home_fragment, drawerLayout, this);
    }

    private void createMenuList() {
        SlideMenuItem menuItem0 = new SlideMenuItem(CLOSE, R.drawable.icn_close);
        list.add(menuItem0);
        SlideMenuItem menuItem = new SlideMenuItem(HOME, R.drawable.home);
        list.add(menuItem);
        SlideMenuItem menuItem2 = new SlideMenuItem(AI, R.drawable.ai);
        list.add(menuItem2);
        SlideMenuItem menuItem3 = new SlideMenuItem(SYS, R.drawable.sys_icon);
        list.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem(dAPP, R.drawable.dapp_icon);
        list.add(menuItem4);
    }
    private void setActionBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        telegram_imageview = findViewById(R.id.telegram_imageview);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                linearLayout.removeAllViews();
                linearLayout.invalidate();
            }
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && linearLayout.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
        telegram_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telegram();
            }
        });
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        switch (slideMenuItem.getName()) {
            case CLOSE:
                return screenShotable;
            case HOME:
                return showHome(screenShotable, position);
            case AI:
                if (!isNetworkConnected(mContext)) {
                    showNetworkDialog(mContext);
                    return showHome(screenShotable, position);
                } else {
                    return showAL(screenShotable, position);
                }
            case SYS:
                if (!isNetworkConnected(mContext)) {
                    showNetworkDialog(mContext);
                    return showHome(screenShotable, position);
                } else {
                    return showSYS(screenShotable, position);
                }
            case dAPP:
                if (!isNetworkConnected(mContext)) {
                    showNetworkDialog(mContext);
                    return showHome(screenShotable, position);
                } else {
                    return showdAPP(screenShotable, position);
                }
            default:
                return showHome(screenShotable, position);
        }
    }

    private void showNetworkDialog(Context mContext) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.network_title);
        builder.setMessage(R.string.network_error_message);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void animationShow(ScreenShotable screenShotable, int position) {
        View view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        Animator animator = ViewAnimationUtils.createCircularReveal(view, 0, position, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);
        findViewById(R.id.content_overlay).setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private ScreenShotable showdAPP(ScreenShotable screenShotable, int position) {
        animationShow(screenShotable, position);
        Webview_Fragment contentFragment = new Webview_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", dapp);
        contentFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment).commit();
        return contentFragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private ScreenShotable showSYS(ScreenShotable screenShotable, int position) {
        animationShow(screenShotable, position);
        Webview_Fragment contentFragment = new Webview_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", sys);
        contentFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment).commit();
        return contentFragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private ScreenShotable showAL(ScreenShotable screenShotable, int position) {
        animationShow(screenShotable, position);
        Webview_Fragment contentFragment = new Webview_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", ai);
        contentFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment).commit();
        return contentFragment;
    }

    private void telegram() {
        String url_Telegram = "http://t.me/deuroio_english";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url_Telegram));
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private ScreenShotable showHome(ScreenShotable screenShotable, int position) {
        animationShow(screenShotable, position);
        Home_Fragment contentFragment = new Home_Fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment).commit();
        return contentFragment;
    }

    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();

    }

    @Override
    public void addViewToContainer(View view) {
        linearLayout.addView(view);
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
