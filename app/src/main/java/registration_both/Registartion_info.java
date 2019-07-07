package registration_both;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ShrinkMyIssues.App.R;
import com.ShrinkMyIssues.App.Terms_Conditions;

import Controllers.Controller;

public class Registartion_info extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private Intent intent;
    private EditText registration_username, registration_password, registration_confirm_password, registration_email, registration_confirm_email;
    private Button registration_btn;
    private Controller controller;
    private String user_name, email_id, password, type;
    private TextView terms_conditions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_info);

        context = Registartion_info.this;

        type = getIntent().getExtras().getString("type");

        controller = new Controller();
        initialise();
    }

    private void initialise() {
        registration_username = (EditText) findViewById(R.id.registration_username);
        registration_password = (EditText) findViewById(R.id.registration_password);
        registration_confirm_password = (EditText) findViewById(R.id.registration_confirm_password);
        registration_email = (EditText) findViewById(R.id.registration_email);
        registration_confirm_email = (EditText) findViewById(R.id.registration_confirm_email);
        registration_btn = (Button) findViewById(R.id.registration_btn);
        terms_conditions = (TextView) findViewById(R.id.terms_conditions);

        terms_conditions.setOnClickListener(this);

        registration_btn.setOnClickListener(this);
    }

    private void check_all() {
        if (controller.Check_all_empty(registration_username)) {
            registration_username.setError(getString(R.string.empty_username));
        } else if (controller.Check_all_empty(registration_password)) {
            registration_password.setError(getString(R.string.empty_password));
        } else if (controller.Check_all_empty(registration_confirm_password)) {
            registration_confirm_password.setError(getString(R.string.empty_confirm_password));
        } else if (controller.Check_all_empty(registration_email)) {
            registration_email.setError(getString(R.string.empty_email));
        } else if (controller.Check_all_empty(registration_confirm_email)) {
            registration_confirm_email.setError(getString(R.string.empty_confirm_email));
        } else if (!controller.Check_all_email(registration_email)) {
            registration_email.setError(getString(R.string.valid_email));
        } else if (!controller.Password_length(registration_password, 6, 20)) {
            registration_password.setError(getString(R.string.password_length));
        } else {
            user_name = registration_username.getText().toString();
            email_id = registration_email.getText().toString();
            password = registration_password.getText().toString();

            if (!controller.Check_all_matches(registration_confirm_password, password)) {
                registration_confirm_password.setError(getString(R.string.matches_password));
            } else if (!controller.Check_all_matches(registration_confirm_email, email_id)) {
                registration_email.setError(getString(R.string.matches_email));
            } else {
                if (!controller.InternetCheck(context)) {
                    controller.Toast_show(context, getString(R.string.enable_internet));
                } else {

                    intent_type();
                    //API hit
                }
            }
        }
    }

    private void intent_type() {
        intent = new Intent(context, Personal_info.class);
        intent.putExtra("type", type);
        startActivity(intent);
        controller.Animation_forward(context);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        controller.Animation_backward(context);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.registration_btn:
                check_all();
                break;

            case R.id.terms_conditions:
                intent = new Intent(context, Terms_Conditions.class);
                startActivity(intent);
                controller.Animation_up(context);
                break;
        }
    }
}