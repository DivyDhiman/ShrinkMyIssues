package com.ShrinkMyIssues.App;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import Controllers.Controller;

public class Client_dash_board extends AppCompatActivity implements View.OnClickListener {

    private Button my_issue, find_shrink,messages,peer_group, settings;
    private TextView contact_us;
    private Context context;
    private Controller controller;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_dash_board);

        context = Client_dash_board.this;
        controller = new Controller();

        initialise();
    }

    private void initialise() {
        my_issue = (Button) findViewById(R.id.my_issue);
        find_shrink = (Button) findViewById(R.id.find_shrink);
        messages = (Button) findViewById(R.id.messages);
        peer_group = (Button) findViewById(R.id.peer_group);
        settings = (Button) findViewById(R.id.settings);
        contact_us = (TextView) findViewById(R.id.contact_us);

        my_issue.setOnClickListener(this);
        find_shrink.setOnClickListener(this);
        messages.setOnClickListener(this);
        peer_group.setOnClickListener(this);
        settings.setOnClickListener(this);
        contact_us.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_issue:
                call_intent(1);
                break;
            case R.id.find_shrink:
                call_intent(2);
                break;
            case R.id.messages:
                controller.Toast_show(context, getString(R.string.update_soon));
                break;
            case R.id.peer_group:
                call_intent(4);
                break;
            case R.id.settings:
                controller.Toast_show(context, getString(R.string.update_soon));
                break;
            case R.id.contact_us:
                call_intent(6);
                break;
        }
    }

    private void call_intent(int fragment_count) {
        intent = new Intent(context,Dashboard_all_functionality.class);
        intent.putExtra("type",getString(R.string.client));
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