package registration_both;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ShrinkMyIssues.App.R;
import com.ShrinkMyIssues.App.Terms_Conditions;

import java.util.ArrayList;
import java.util.Collections;

import Adapter_all.Speciality_list_adapter;
import Call_Back.Register_data_callback;
import Controllers.Controller;

public class Pro_insurance_plan extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private Intent intent;
    private TextView terms_conditions, insurance_default;
    private Controller controller;
    private Button next_click;
    private String type, insurance_plan,additional_language_data;
    private RecyclerView demographic_list;
    private EditText additional_language;
    private ArrayList<String> demographic_expertise_list, selected_demographic_expertise_list;
    private String[] demographic_expertise, insurance_plan_list;
    private Register_data_callback register_data_callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pro_insurance_plan);

        context = Pro_insurance_plan.this;
        type = getIntent().getExtras().getString("type");
        controller = new Controller();

        insurance_plan_list = getResources().getStringArray(R.array.select_insurance_plan);
        ;
        selected_demographic_expertise_list = new ArrayList<>();
        register_data_callback = new Register_data_callback() {
            @Override
            public void list_data(ArrayList<String> list, int type) {
                selected_demographic_expertise_list = list;
            }
        };

        initialise();
    }

    private void initialise() {
        demographic_list = (RecyclerView) findViewById(R.id.demographic_list);
        additional_language = (EditText) findViewById(R.id.additional_language);
        insurance_default = (TextView) findViewById(R.id.insurance_default);
        next_click = (Button) findViewById(R.id.next_click);
        terms_conditions = (TextView) findViewById(R.id.terms_conditions);

        next_click.setOnClickListener(this);
        terms_conditions.setOnClickListener(this);

        demographic_expertise_list = new ArrayList<>();
        demographic_expertise = getResources().getStringArray(R.array.demographic_expertise_list);
        Collections.addAll(demographic_expertise_list, demographic_expertise);

        call_adapter();
    }

    private void call_adapter() {
        Speciality_list_adapter demographic_expertise_adapter = new Speciality_list_adapter(demographic_list.getContext(), demographic_expertise_list, register_data_callback, 0);
        StaggeredGridLayoutManager stagger_layout_manager_treatment = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        demographic_list.setLayoutManager(stagger_layout_manager_treatment);
        demographic_list.setAdapter(demographic_expertise_adapter);

        spinner_int();
    }

    private void spinner_int() {
        Spinner insurance_spinner = (Spinner) findViewById(R.id.insurance_spinner);
        ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(context, R.layout.spinner_text, insurance_plan_list);
        insurance_spinner.setAdapter(langAdapter);
        langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);

        insurance_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int position, long arg3) {

                insurance_plan = parent.getItemAtPosition(position).toString();
                if (insurance_plan.equals(getString(R.string.select_gender))) {
                    insurance_default.setVisibility(View.VISIBLE);
                } else {
                    insurance_default.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_click:
                if (selected_demographic_expertise_list.size() == 0) {
                    controller.Toast_show(context, getString(R.string.empty_demographic_list));
                } else if (insurance_plan.equals(getString(R.string.select))) {
                    controller.Toast_show(context, getString(R.string.empty_insurance_plan));
                } else {
                    if (!controller.InternetCheck(context)) {
                        controller.Toast_show(context, getString(R.string.enable_internet));
                    } else {
                        if (!controller.Check_all_empty(additional_language)) {
                            additional_language_data = additional_language.getText().toString();
                        }
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
        intent = new Intent(context, Card_details.class);
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
