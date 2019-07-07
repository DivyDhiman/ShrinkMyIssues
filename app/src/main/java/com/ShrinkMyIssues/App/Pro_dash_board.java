package com.ShrinkMyIssues.App;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import Controllers.Controller;

public class Pro_dash_board extends AppCompatActivity implements View.OnClickListener {
    private Button problem_cue,messages,professional_corner,settings;
    private Context context;
    private Controller controller;
    private TextView contact_us;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pro_dash_board);

        context = Pro_dash_board.this;
        controller = new Controller();

        initialise();
    }

    private void initialise() {
        problem_cue = (Button) findViewById(R.id.problem_cue);
        messages = (Button) findViewById(R.id.messages);
        professional_corner = (Button) findViewById(R.id.professional_corner);
        settings = (Button) findViewById(R.id.settings);
        contact_us = (TextView) findViewById(R.id.contact_us);

        problem_cue.setOnClickListener(this);
        messages.setOnClickListener(this);
        professional_corner.setOnClickListener(this);
        settings.setOnClickListener(this);
        contact_us.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.problem_cue:
                call_intent(1);
                break;
            case R.id.messages:

                break;
            case R.id.professional_corner:
                call_intent(3);
                break;
            case R.id.settings:

                break;

            case R.id.contact_us:
                call_intent(6);
                break;
        }
    }

    private void call_intent(int fragment_count) {
        intent = new Intent(context,Dashboard_all_functionality.class);
        intent.putExtra("type",getString(R.string.pro));
        intent.putExtra("fragment_count",fragment_count);
        startActivity(intent);
        if(fragment_count ==6){
            controller.Animation_up(context);
        }else {
            controller.Animation_forward(context);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        controller.Animation_backward(context);
    }
}