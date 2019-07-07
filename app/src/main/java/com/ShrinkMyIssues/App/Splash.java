package com.ShrinkMyIssues.App;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Abhay dhiman
 */

//Splash Activity
public class Splash extends Activity implements Animation.AnimationListener {

    private Context context;
    private Animation mAnim = null;
    private ImageView name_img, logo_img;
    private boolean checksplash;

    //Create view for Splash activity , bind their xml splash
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        context = Splash.this;
        checksplash = false;

        initialise();
    }

    private void initialise() {
        name_img = (ImageView) findViewById(R.id.name_img);
        logo_img = (ImageView) findViewById(R.id.logo_img);

        logo_img.setVisibility(View.GONE);
        mAnim = AnimationUtils.loadAnimation(context, R.anim.translate);
        mAnim.setAnimationListener(this);
        name_img.clearAnimation();
        name_img.setAnimation(mAnim);
    }


    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

        if (!checksplash) {
            logo_img.setVisibility(View.VISIBLE);

            mAnim = AnimationUtils.loadAnimation(context, R.anim.fade_in);
            mAnim.setAnimationListener(this);

            logo_img.startAnimation(mAnim);
            checksplash = true;

        } else {
            Intent intent = new Intent(context, Login_activity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}