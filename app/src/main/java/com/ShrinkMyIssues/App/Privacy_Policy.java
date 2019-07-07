package com.ShrinkMyIssues.App;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import Controllers.Controller;

public class Privacy_Policy extends AppCompatActivity {

    private Controller controller;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacy_policy);

        context = Privacy_Policy.this;
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
