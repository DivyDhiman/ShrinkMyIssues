package registration_both;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.ShrinkMyIssues.App.R;
import com.ShrinkMyIssues.App.Terms_Conditions;

import java.util.ArrayList;
import java.util.Collections;

import Adapter_all.Speciality_list_adapter;
import Call_Back.Register_data_callback;
import Controllers.Controller;

public class Pro_Specialise_other extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private Intent intent;
    private TextView terms_conditions, next_click;
    private Controller controller;
    private String type;
    private RecyclerView age_speciality_list, session_formats_list, treatment_approaches_list;
    private String[] area_speciality, session_formats, treatment_approaches;
    private ArrayList<String> area_list, session_list, treatment_list, select_area_list, select_session_list, select_treatment_list;
    private Register_data_callback register_data_callback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pro_specialise_other);

        context = Pro_Specialise_other.this;
        type = getIntent().getExtras().getString("type");

        controller = new Controller();
        select_area_list = new ArrayList<>();
        select_session_list = new ArrayList<>();
        select_treatment_list = new ArrayList<>();

        register_data_callback = new Register_data_callback() {
            @Override
            public void list_data(ArrayList<String> list, int type) {
                if (type == 0) {
                    select_area_list = list;
                } else if (type == 1) {
                    select_session_list = list;
                } else {
                    select_treatment_list = list;
                }
            }
        };

        initialise();
    }

    private void initialise() {
        age_speciality_list = (RecyclerView) findViewById(R.id.age_speciality_list);
        age_speciality_list.setNestedScrollingEnabled(false);
        session_formats_list = (RecyclerView) findViewById(R.id.session_formats_list);
        session_formats_list.setNestedScrollingEnabled(false);
        treatment_approaches_list = (RecyclerView) findViewById(R.id.treatment_approaches_list);
        treatment_approaches_list.setNestedScrollingEnabled(false);

        next_click = (TextView) findViewById(R.id.next_click);
        terms_conditions = (TextView) findViewById(R.id.terms_conditions);
        next_click.setOnClickListener(this);
        terms_conditions.setOnClickListener(this);

        area_list = new ArrayList<>();
        session_list = new ArrayList<>();
        treatment_list = new ArrayList<>();

        area_speciality = getResources().getStringArray(R.array.area_speciality_list);
        session_formats = getResources().getStringArray(R.array.session_formats_list);
        treatment_approaches = getResources().getStringArray(R.array.treatment_approaches_list);

        Collections.addAll(area_list, area_speciality);
        Collections.addAll(session_list, session_formats);
        Collections.addAll(treatment_list, treatment_approaches);

        call_adapter();
    }

    private void call_adapter() {
        Speciality_list_adapter area_list_adapter = new Speciality_list_adapter(age_speciality_list.getContext(), area_list, register_data_callback, 0);
        Speciality_list_adapter speciality_list_adapter = new Speciality_list_adapter(session_formats_list.getContext(), session_list, register_data_callback, 1);
        Speciality_list_adapter treatment_list_adapter = new Speciality_list_adapter(treatment_approaches_list.getContext(), treatment_list, register_data_callback, 2);

        StaggeredGridLayoutManager stagger_layout_manager_age = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        age_speciality_list.setLayoutManager(stagger_layout_manager_age);
        age_speciality_list.setAdapter(area_list_adapter);

        StaggeredGridLayoutManager stagger_layout_manager_session = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        session_formats_list.setLayoutManager(stagger_layout_manager_session);
        session_formats_list.setAdapter(speciality_list_adapter);

        StaggeredGridLayoutManager stagger_layout_manager_treatment = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        treatment_approaches_list.setLayoutManager(stagger_layout_manager_treatment);
        treatment_approaches_list.setAdapter(treatment_list_adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_click:
                if (!controller.InternetCheck(context)) {
                    controller.Toast_show(context, getString(R.string.enable_internet));
                } else {
                    if (select_area_list.size() == 0) {
                        controller.Toast_show(context, getString(R.string.empty_area_list));
                    }else if (select_session_list.size() == 0) {
                        controller.Toast_show(context, getString(R.string.empty_session_list));
                    }else if (select_treatment_list.size() ==0){
                        controller.Toast_show(context, getString(R.string.empty_treatment_list));
                    }else {
                        intent_type();
                    }
                }
                break;
            case R.id.terms_conditions:
                intent = new Intent(context, Terms_Conditions.class);
                startActivity(intent);
                controller.Animation_up(context);
                break;
        }
    }

    private void intent_type() {
        intent = new Intent(context, Pro_insurance_plan.class);
        intent.putExtra("type", type);
        startActivity(intent);
        controller.Animation_forward(context);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        controller.Animation_backward(context);
    }
}