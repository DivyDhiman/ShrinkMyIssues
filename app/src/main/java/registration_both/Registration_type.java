package registration_both;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ShrinkMyIssues.App.R;
import com.ShrinkMyIssues.App.Terms_Conditions;

import Controllers.Controller;

public class Registration_type extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private Button client_type,pro_type;
    private Intent intent;
    private Controller controller;
    private TextView terms_conditions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_type);

        context = Registration_type.this;
        controller = new Controller();
        initialise();
    }

    private void initialise() {
        client_type = (Button) findViewById(R.id.client_type);
        pro_type = (Button) findViewById(R.id.pro_type);
        terms_conditions = (TextView) findViewById(R.id.terms_conditions);

        client_type.setOnClickListener(this);
        pro_type.setOnClickListener(this);
        terms_conditions.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.client_type:
                intent_type(getString(R.string.client));
                break;
            case R.id.pro_type:
                intent_type(getString(R.string.pro));
//                controller.Toast_show(context, getString(R.string.update_soon));
                break;

            case R.id.terms_conditions:
                intent = new Intent(context, Terms_Conditions.class);
                startActivity(intent);
                controller.Animation_up(context);
                break;
        }
    }

    private void intent_type(String type) {
        intent = new Intent(context, Registartion_info.class);
        intent.putExtra("type",type);
        startActivity(intent);
        controller.Animation_forward(context);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        controller.Animation_backward(context);
    }
}