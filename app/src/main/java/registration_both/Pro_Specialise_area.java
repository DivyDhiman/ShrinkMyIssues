package registration_both;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ShrinkMyIssues.App.R;
import com.ShrinkMyIssues.App.Terms_Conditions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import Adapter_all.Card_img_adapter;
import Adapter_all.Speciality_list_adapter;
import Call_Back.Register_data_callback;
import Controllers.Controller;

public class Pro_Specialise_area extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private Intent intent;
    private TextView terms_conditions, next_click;
    private Controller controller;
    private String type;
    private RecyclerView list_item;
    private ArrayList<String> list, selected_list_pass;
    private Register_data_callback register_data_callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pro_specialise_area);

        context = Pro_Specialise_area.this;
        type = getIntent().getExtras().getString("type");

        controller = new Controller();
        selected_list_pass = new ArrayList<>();

        register_data_callback = new Register_data_callback() {
            @Override
            public void list_data(ArrayList<String> list, int type) {
                selected_list_pass = list;
            }
        };
        initialise();
    }

    private void initialise() {
        list_item = (RecyclerView) findViewById(R.id.list_item);
        next_click = (TextView) findViewById(R.id.next_click);
        terms_conditions = (TextView) findViewById(R.id.terms_conditions);
        next_click.setOnClickListener(this);
        terms_conditions.setOnClickListener(this);

        list = new ArrayList<>();
        String[] get_list = getResources().getStringArray(R.array.speciality_list);
        Collections.addAll(list, get_list);

        call_adapter();
    }

    private void call_adapter() {
        Speciality_list_adapter speciality_list_adapter = new Speciality_list_adapter(list_item.getContext(), list, register_data_callback,0);
        StaggeredGridLayoutManager stagger_layout_manager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        list_item.setLayoutManager(stagger_layout_manager);
        list_item.setAdapter(speciality_list_adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_click:
                if (!controller.InternetCheck(context)) {
                    controller.Toast_show(context, getString(R.string.enable_internet));
                } else {
                    if (selected_list_pass.size() > 0) {
                        intent_type();
                    } else {
                        controller.Toast_show(context, getString(R.string.empty_specialise_list));
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
        intent = new Intent(context, Pro_Specialise_other.class);
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