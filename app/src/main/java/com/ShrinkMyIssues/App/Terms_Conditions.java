package com.ShrinkMyIssues.App;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import Controllers.Controller;

public class Terms_Conditions extends AppCompatActivity {

    private Controller controller;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms_conditions);

        context = Terms_Conditions.this;
        controller = new Controller();

        initialise();
    }

    private void initialise() {

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        controller.Animation_down(context);
    }
}