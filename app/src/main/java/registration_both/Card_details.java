package registration_both;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ShrinkMyIssues.App.Client_dash_board;
import com.ShrinkMyIssues.App.Pro_dash_board;
import com.ShrinkMyIssues.App.R;
import com.ShrinkMyIssues.App.Terms_Conditions;

import java.lang.reflect.Field;
import java.util.ArrayList;

import Adapter_all.Card_img_adapter;
import Controllers.Controller;
import Functionality_Class.CustomDatePickerDialog;

public class Card_details extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private String[] member_plan_list;
    private String selected_plan, card_month_year, card_holder_name, card_holder_number, card_holder_cvv, type;
    private TextView member_plan_default, month_year_selection, terms_conditions;
    private Intent intent;
    private Controller controller;
    private Button confirm_card_info;
    private EditText name_on_card, card_number, card_cvv;
    private DatePickerDialog.OnDateSetListener datePickerListener;
    private int year, month;
    private RecyclerView card_img;
    private ArrayList<Integer> card_images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_details);

        context = Card_details.this;
        controller = new Controller();
        type = getIntent().getExtras().getString("type");

        if (type.equals(getString(R.string.client))) {
            member_plan_list = getResources().getStringArray(R.array.client_member_ship_plan);
        } else {
            member_plan_list = getResources().getStringArray(R.array.pro_member_ship_plan);
        }

        initialise();
    }

    private void initialise() {
        member_plan_default = (TextView) findViewById(R.id.member_plan_default);
        name_on_card = (EditText) findViewById(R.id.name_on_card);
        card_number = (EditText) findViewById(R.id.card_number);
        month_year_selection = (TextView) findViewById(R.id.month_year_selection);
        card_cvv = (EditText) findViewById(R.id.card_cvv);
        confirm_card_info = (Button) findViewById(R.id.confirm_card_info);
        terms_conditions = (TextView) findViewById(R.id.terms_conditions);

        confirm_card_info.setOnClickListener(this);
        month_year_selection.setOnClickListener(this);
        terms_conditions.setOnClickListener(this);

        card_month_year = getString(R.string.mm);
        spinner_int();

        //Date picker listener callback
        datePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                month_year_selection.setText(monthOfYear + 1 + "/" + year);
                card_month_year = month_year_selection.getText().toString();
            }
        };

        card_logo();
    }

    private void card_logo() {
        card_images = new ArrayList<>();
        card_images.add(R.drawable.cards_master);
        card_images.add(R.drawable.cards_visa);
        card_images.add(R.drawable.cards_amex);

        card_img = (RecyclerView) findViewById(R.id.card_img);
        Card_img_adapter card_img_adapter = new Card_img_adapter(card_img.getContext(), card_images);
        LinearLayoutManager linear_layout_manager = new LinearLayoutManager(card_img.getContext(), LinearLayoutManager.HORIZONTAL, false);
        card_img.setLayoutManager(linear_layout_manager);
        card_img.setAdapter(card_img_adapter);

    }

    private void spinner_int() {
        Spinner member_ship_plan = (Spinner) findViewById(R.id.member_ship_plan);
        ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(context, R.layout.spinner_text, member_plan_list);
        member_ship_plan.setAdapter(langAdapter);
        langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);

        member_ship_plan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1, int position, long arg3) {
                selected_plan = parent.getItemAtPosition(position).toString();
                if (selected_plan.equals(getString(R.string.select_membership_plan))) {
                    member_plan_default.setVisibility(View.VISIBLE);
                } else {
                    member_plan_default.setVisibility(View.GONE);
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
            case R.id.confirm_card_info:
                if (selected_plan.equals(getString(R.string.select_membership_plan))) {
                    controller.Toast_show(context, getString(R.string.empty_plan));
                } else if (controller.Check_all_empty(name_on_card)) {
                    name_on_card.setError(getString(R.string.empty_card_holder_name));
                } else if (controller.Check_all_empty(card_number)) {
                    card_number.setError(getString(R.string.empty_card_number));
                } else if (card_month_year.equals(getString(R.string.mm))) {
                    controller.Toast_show(context, getString(R.string.empty_month_year));
                } else if (controller.Check_all_empty(card_cvv)) {
                    card_cvv.setError(getString(R.string.empty_card_cvv));
                } else if (!controller.Password_length(card_number, 15, 16)) {
                    card_number.setError(getString(R.string.invalid_card_number));
                } else if (!controller.Password_length(card_cvv, 3, 5)) {
                    card_cvv.setError(getString(R.string.invalid_cvv));
                } else {
                    if (!controller.InternetCheck(context)) {
                        controller.Toast_show(context, getString(R.string.enable_internet));
                    } else {
                        card_holder_name = name_on_card.getText().toString();
                        card_holder_number = card_number.getText().toString();
                        card_holder_cvv = card_cvv.getText().toString();

                        //API Hit
                        call_intent();
                    }
                }

                break;

            case R.id.month_year_selection:
                Card_datepick();
                break;

            case R.id.terms_conditions:
                intent = new Intent(context, Terms_Conditions.class);
                startActivity(intent);
                controller.Animation_up(context);
                break;
        }
    }

    private void call_intent() {
        if (type.equals(getString(R.string.client))) {
            intent = new Intent(context, Client_dash_board.class);
            startActivity(intent);
            controller.Animation_forward(context);
        } else {
            intent = new Intent(context, Pro_dash_board.class);
            startActivity(intent);
            controller.Animation_forward(context);
        }
    }

    private void intent_type() {
        intent = new Intent(context, Card_details.class);
        startActivity(intent);
        controller.Animation_forward(context);
    }

    //Method for date pick operation from date picker dialog
    private void Card_datepick() {
        CustomDatePickerDialog dp = new CustomDatePickerDialog(this, R.style.Date_picker, datePickerListener, year, month, 1);
        DatePickerDialog obj = dp.getPicker();
        obj.show();
        obj.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        try {

            //Check for higher and lower sdk versions and call method according to them
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                int daySpinnerId = Resources.getSystem().getIdentifier("day", "id", "android");
                if (daySpinnerId != 0) {
                    View daySpinner = obj.findViewById(daySpinnerId);
                    if (daySpinner != null) {
                        daySpinner.setVisibility(View.GONE);
                    }
                }
            } else {
                Field[] datePickerDialogFields = obj.getClass().getDeclaredFields();
                for (Field datePickerDialogField : datePickerDialogFields) {
                    if (datePickerDialogField.getName().equals("mDatePicker")) {
                        datePickerDialogField.setAccessible(true);
                        DatePicker datePicker = (DatePicker) datePickerDialogField.get(obj);
                        Field datePickerFields[] = datePickerDialogField.getType().getDeclaredFields();
                        for (Field datePickerField : datePickerFields) {
                            if ("mDayPicker".equals(datePickerField.getName()) || "mDaySpinner".equals(datePickerField
                                    .getName())) {
                                datePickerField.setAccessible(true);
                                Object dayPicker = new Object();
                                dayPicker = datePickerField.get(datePicker);
                                ((View) dayPicker).setVisibility(View.GONE);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        controller.Animation_backward(context);
    }
}