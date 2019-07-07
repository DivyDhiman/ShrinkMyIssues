package com.ShrinkMyIssues.App;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import Circular_animation.Circular_animation_ViewAnimationUtils;
import Controllers.Controller;
import Fragments_all.Contact_us;
import Fragments_all.Find_a_Shrink;
import Fragments_all.Issues_fragment;
import Fragments_all.Peer_group_fragment;
import Fragments_all.Problem_cue;
import Fragments_all.Professional_corner;

public class Dashboard_all_functionality extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    private Context context;
    private Controller controller;
    private String type;
    private int fragment_count;
    private ImageView back_press, menu_click;
    private LinearLayout parent_layout,menu_sub_layout;
    private RelativeLayout menu_layout;
    private int reverse_startradius, reverse_endradius, cx, cy, startradius, endradius;
    private Animator animate;
    private Boolean menu_check = false;
    private ImageView profile_menu,messages_menu,rate_us_menu,contact_us_menu,logout_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_all_functionality);

        context = Dashboard_all_functionality.this;
        controller = new Controller();

        type = getIntent().getExtras().getString("type");
        fragment_count = getIntent().getExtras().getInt("fragment_count");

        check_type_replace_fragment();
        initialise();
    }

    private void check_type_replace_fragment() {
        if (type.equals(getString(R.string.client))) {
            if (fragment_count == 1) {
                Issues_fragment issues_fragment = new Issues_fragment();
                replace_fragment(issues_fragment);
            } else if (fragment_count == 2) {
                Find_a_Shrink find_a_shrink = new Find_a_Shrink();
                replace_fragment(find_a_shrink);
            } else if (fragment_count == 4) {
                Peer_group_fragment peer_group_fragment = new Peer_group_fragment();
                replace_fragment(peer_group_fragment);
            } else if (fragment_count == 6) {
                Contact_us contact_us = new Contact_us();
                replace_fragment(contact_us);
            }
        } else {
            if (fragment_count == 1) {
                Problem_cue problem_cue = new Problem_cue();
                replace_fragment(problem_cue);
            } else if (fragment_count == 3) {
                Professional_corner professional_corner = new Professional_corner();
                replace_fragment(professional_corner);
            } else if (fragment_count == 6) {
                Contact_us contact_us = new Contact_us();
                replace_fragment(contact_us);
            }
        }
    }

    private void replace_fragment(Fragment fragment) {
        FragmentManager fragmnet_manager = getSupportFragmentManager();
        FragmentTransaction frgament_transaction = fragmnet_manager.beginTransaction();
        frgament_transaction.replace(R.id.container_replace, fragment);
        frgament_transaction.addToBackStack(null);
        frgament_transaction.commit();
    }

    private void initialise() {
        parent_layout = (LinearLayout) findViewById(R.id.parent_layout);
        back_press = (ImageView) findViewById(R.id.back_press);
        menu_layout = (RelativeLayout) findViewById(R.id.menu_layout);
        menu_sub_layout = (LinearLayout) findViewById(R.id.menu_sub_layout);
        menu_click = (ImageView) findViewById(R.id.menu_click);
        profile_menu = (ImageView) findViewById(R.id.profile_menu);
        messages_menu = (ImageView) findViewById(R.id.messages_menu);
        rate_us_menu = (ImageView) findViewById(R.id.rate_us_menu);
        contact_us_menu = (ImageView) findViewById(R.id.contact_us_menu);
        logout_menu = (ImageView) findViewById(R.id.logout_menu);

        back_press.setOnClickListener(this);
        menu_click.setOnClickListener(this);
        parent_layout.setOnTouchListener(this);
        profile_menu.setOnTouchListener(this);
        messages_menu.setOnTouchListener(this);
        rate_us_menu.setOnTouchListener(this);
        contact_us_menu.setOnTouchListener(this);
        logout_menu.setOnTouchListener(this);
    }

    @Override
    public void onBackPressed() {
        if (fragment_count == 6) {
            finish();
            controller.Animation_down(context);
        } else {
            finish();
            controller.Animation_backward(context);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_press:
                onBackPressed();
                break;
            case R.id.menu_click:
                if (!menu_check) {
                    cx = (menu_layout.getLeft() + menu_layout.getRight());
                    cy = (menu_layout.getTop());

                    startradius = 0;
                    endradius = Math.max(menu_layout.getWidth(), menu_layout.getHeight());

                    animate = Circular_animation_ViewAnimationUtils.createCircularReveal(menu_layout, cx, cy, startradius, endradius);
                    animate.setInterpolator(new AccelerateDecelerateInterpolator());
                    animate.setDuration(400);

                    menu_layout.setVisibility(View.VISIBLE);
                    menu_sub_layout.setVisibility(View.VISIBLE);
                    animate.start();
                    menu_check = true;
                } else {
                    call_reverse_menu();
                }
                break;
            case R.id.profile_menu:
                controller.Toast_show(context, getString(R.string.update_soon));
                break;
            case R.id.messages_menu:
                controller.Toast_show(context, getString(R.string.update_soon));
                break;
            case R.id.rate_us_menu:
                controller.Toast_show(context, getString(R.string.update_soon));
                break;
            case R.id.contact_us_menu:
                controller.Toast_show(context, getString(R.string.update_soon));
                break;
            case R.id.logout_menu:
                controller.Toast_show(context, getString(R.string.update_soon));
                break;
        }
    }

    private void call_reverse_menu() {
        cx = (menu_layout.getLeft() + menu_layout.getRight());
        cy = (menu_layout.getTop());

        reverse_startradius = Math.max(menu_layout.getWidth(), menu_layout.getHeight());
        reverse_endradius = 0;

        animate = Circular_animation_ViewAnimationUtils.createCircularReveal(menu_layout, cx, cy, reverse_startradius, reverse_endradius);
        animate.setInterpolator(new AccelerateDecelerateInterpolator());
        animate.setDuration(400);

        menu_layout.setVisibility(View.VISIBLE);
        animate.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                menu_layout.setVisibility(View.INVISIBLE);
                menu_sub_layout.setVisibility(View.GONE);
                menu_check = false;
            }
        });
        animate.start();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if (menu_check) {
                call_reverse_menu();
                return true;
            }
        }
        return false;
    }
}