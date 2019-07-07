package com.ShrinkMyIssues.App;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import Controllers.Controller;
import registration_both.Registration_type;

public class Login_activity extends AppCompatActivity implements View.OnClickListener, Animation.AnimationListener {

    private EditText et_username, et_password, dialog_email_id;
    private Button btn_login, btn_register;
    private TextView forgot_password, dialog_password_reset_steps, dialog_ok;
    private Context context;
    private Controller controller;
    private String username, password, forgot_email, anim_check = "first";
    private Intent intent;
    private Dialog forgot_pass_dialog;
    private ImageView send_image;
    private LinearLayout send_image_text_parent, cancel_submit_parent;
    private Animation mAnim = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        context = Login_activity.this;
        controller = new Controller();

        initialise();
    }

    private void initialise() {

        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        forgot_password = (TextView) findViewById(R.id.forgot_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);

        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        forgot_password.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:

                if (controller.Check_all_empty(et_username)) {
                    et_username.setError(getString(R.string.enter_username));
                } else if (controller.Check_all_empty(et_password)) {
                    et_password.setError(getString(R.string.enter_password));
                } else {
                    if (controller.InternetCheck(context)) {
                        username = et_username.getText().toString();
                        password = et_password.getText().toString();

                        if(username.equals("pro")){
                            intent = new Intent(context, Pro_dash_board.class);
                            startActivity(intent);
                            controller.Animation_forward(context);
                        }else {
                            intent = new Intent(context, Client_dash_board.class);
                            startActivity(intent);
                            controller.Animation_forward(context);
                        }

                    } else {
                        controller.Toast_show(context, getString(R.string.enable_internet));
                    }
                }

                break;

            case R.id.btn_register:
                intent = new Intent(context, Registration_type.class);
                startActivity(intent);
                controller.Animation_forward(context);
                break;

            case R.id.forgot_password:
                dialog_forgot_password();
                break;

            case R.id.dialog_ok:
                forgot_pass_dialog.dismiss();
                break;
            case R.id.dialog_cancel:
                forgot_pass_dialog.dismiss();
                break;
            case R.id.dialog_submit:

                if (controller.Check_all_empty(dialog_email_id)) {
                    dialog_email_id.setError(getString(R.string.empty_email));
                } else if (!controller.Check_all_email(dialog_email_id)) {
                    dialog_email_id.setError(getString(R.string.valid_email));
                } else {
                    if (controller.InternetCheck(context)) {
                        forgot_email = dialog_email_id.getText().toString();
                        send_image_text_parent.setVisibility(View.VISIBLE);
                        dialog_ok.setVisibility(View.VISIBLE);
                        cancel_submit_parent.setVisibility(View.GONE);
                        dialog_email_id.setVisibility(View.GONE);
                        dialog_password_reset_steps.setText("");

                        mAnim = AnimationUtils.loadAnimation(context, R.anim.rotate_once);
                        mAnim.setAnimationListener(this);
                        send_image.clearAnimation();
                        send_image.setAnimation(mAnim);

                    } else {
                        controller.Toast_show(context, getString(R.string.enable_internet));
                    }
                }
                break;

        }
    }

    private void dialog_forgot_password() {
        forgot_pass_dialog = new Dialog(context);
        forgot_pass_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        forgot_pass_dialog.setContentView(R.layout.custom_dialog_pass);
        forgot_pass_dialog.setCanceledOnTouchOutside(false);
        forgot_pass_dialog.setCancelable(false);
        send_image_text_parent = (LinearLayout) forgot_pass_dialog.findViewById(R.id.send_image_text_parent);
        send_image = (ImageView) forgot_pass_dialog.findViewById(R.id.send_image);
        send_image.setBackgroundResource(R.drawable.process_send);

        dialog_password_reset_steps = (TextView) forgot_pass_dialog.findViewById(R.id.dialog_password_reset_steps);
        cancel_submit_parent = (LinearLayout) forgot_pass_dialog.findViewById(R.id.cancel_submit_parent);
        dialog_ok = (TextView) forgot_pass_dialog.findViewById(R.id.dialog_ok);
        TextView dialog_cancel = (TextView) forgot_pass_dialog.findViewById(R.id.dialog_cancel);
        TextView dialog_submit = (TextView) forgot_pass_dialog.findViewById(R.id.dialog_submit);
        dialog_email_id = (EditText) forgot_pass_dialog.findViewById(R.id.dialog_email_id);
        dialog_ok.setOnClickListener(this);
        dialog_cancel.setOnClickListener(this);
        dialog_submit.setOnClickListener(this);

        send_image_text_parent.setVisibility(View.GONE);
        dialog_ok.setVisibility(View.GONE);

        forgot_pass_dialog.show();
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        switch (anim_check) {
            case "first":
                send_image.setBackgroundResource(R.drawable.send_email);
                mAnim = AnimationUtils.loadAnimation(context, R.anim.fade_in);
                mAnim.setAnimationListener(this);
                send_image.clearAnimation();
                send_image.setAnimation(mAnim);
                anim_check = "second";
                break;

            case "second":
                dialog_password_reset_steps.setText(getString(R.string.password_reset_steps));
                mAnim = AnimationUtils.loadAnimation(context, R.anim.fade_in);
                mAnim.setAnimationListener(this);
                dialog_password_reset_steps.clearAnimation();
                dialog_password_reset_steps.setAnimation(mAnim);
                anim_check = "third";
                break;

            case "third":
                anim_check = "first";
                break;
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}