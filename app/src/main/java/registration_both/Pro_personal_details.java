package registration_both;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ShrinkMyIssues.App.R;
import com.ShrinkMyIssues.App.Terms_Conditions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;

import Controllers.Controller;
import Functionality_Class.CustomDatePickerDialog;

public class Pro_personal_details extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private Context context;
    private Controller controller;
    private Intent intent;
    private String[] speciality;
    private TextView type_speciality_default, license_year_selection, upload_letter_txt, btn_login, terms_conditions;
    private CheckBox intern_coach;
    private EditText license_certification, license_number, supervisor_license, license_or_certification;
    private LinearLayout license_click, license_insurance_click, proof_supervision_click;
    private ImageView license_image, license_insurance_image, proof_supervision_image;
    private boolean check_box = false;
    private String select_speciality_type, license_month_year, license_img, license_insurance_img, supervision_proof_img, type;
    private DatePickerDialog.OnDateSetListener datePickerListener;
    private int year, month, RESULT_License_IMAGE = 10, RESULT_insurance_IMAGE = 20, RESULT_supervision_IMAGE = 30;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.pro_presonal_details);

        context = Pro_personal_details.this;
        controller = new Controller();

        type = getIntent().getExtras().getString("type");
        speciality = getResources().getStringArray(R.array.type_of_speciality);

        license_img = null;
        license_insurance_img = null;
        supervision_proof_img = null;
        initialise();
    }

    private void initialise() {
        type_speciality_default = (TextView) findViewById(R.id.type_speciality_default);
        license_year_selection = (TextView) findViewById(R.id.license_year_selection);
        upload_letter_txt = (TextView) findViewById(R.id.upload_letter_txt);
        btn_login = (TextView) findViewById(R.id.btn_login);
        terms_conditions = (TextView) findViewById(R.id.terms_conditions);

        intern_coach = (CheckBox) findViewById(R.id.intern_coach);

        license_certification = (EditText) findViewById(R.id.license_certification);
        license_number = (EditText) findViewById(R.id.license_number);
        supervisor_license = (EditText) findViewById(R.id.supervisor_license);
        license_or_certification = (EditText) findViewById(R.id.license_or_certification);

        license_click = (LinearLayout) findViewById(R.id.license_click);
        license_insurance_click = (LinearLayout) findViewById(R.id.license_insurance_click);
        proof_supervision_click = (LinearLayout) findViewById(R.id.proof_supervision_click);

        license_image = (ImageView) findViewById(R.id.license_image);
        license_insurance_image = (ImageView) findViewById(R.id.license_insurance_image);
        proof_supervision_image = (ImageView) findViewById(R.id.proof_supervision_image);

        btn_login.setOnClickListener(this);

        license_month_year = getString(R.string.mm);

        intern_coach.setOnCheckedChangeListener(this);
        license_click.setOnClickListener(this);
        license_insurance_click.setOnClickListener(this);
        proof_supervision_click.setOnClickListener(this);
        license_year_selection.setOnClickListener(this);
        terms_conditions.setOnClickListener(this);

        license_image.setVisibility(View.GONE);
        license_insurance_image.setVisibility(View.GONE);
        proof_supervision_image.setVisibility(View.GONE);

        spinner_int();

        visible_views();

        //Date picker listener callback
        datePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                license_year_selection.setText(monthOfYear + 1 + "/" + year);
                license_month_year = license_year_selection.getText().toString();
            }
        };

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (select_speciality_type.equals(getString(R.string.type_speciality))) {
                    controller.Toast_show(context, getString(R.string.empty_speciality));
                } else {
                    if (check_box) {
                        if (controller.Check_all_empty(license_or_certification)) {
                            license_certification.setError(getString(R.string.empty_license_or_certification));
                        } else if (license_img == null) {
                            controller.Toast_show(context, getString(R.string.empty_license_image));
                        } else if (license_insurance_img == null) {
                            controller.Toast_show(context, getString(R.string.empty_license_insurance_image));
                        } else if (controller.Check_all_empty(supervisor_license)) {
                            license_number.setError(getString(R.string.empty_supervisor_license));
                        } else if (supervision_proof_img == null) {
                            controller.Toast_show(context, getString(R.string.empty_supervision_proof_img));
                        } else {
                            if (!controller.InternetCheck(context)) {
                                controller.Toast_show(context, getString(R.string.enable_internet));
                            } else {
                                intent_type();
                            }
                        }

                    } else {
                        if (controller.Check_all_empty(license_certification)) {
                            license_certification.setError(getString(R.string.empty_license_certification_number));
                        } else if (controller.Check_all_empty(license_number)) {
                            license_number.setError(getString(R.string.empty_license_number));
                        } else if (license_month_year.equals(getString(R.string.mm))) {
                            controller.Toast_show(context, getString(R.string.empty_license_month_year));
                        } else if (license_img == null) {
                            controller.Toast_show(context, getString(R.string.empty_license_image));
                        } else if (license_insurance_img == null) {
                            controller.Toast_show(context, getString(R.string.empty_license_insurance_image));
                        } else {
                            if (!controller.InternetCheck(context)) {
                                controller.Toast_show(context, getString(R.string.enable_internet));
                            } else {
                                intent_type();
                            }
                        }
                    }
                }
                break;

            case R.id.license_year_selection:
                Card_datepick();
                break;

            case R.id.license_click:
                intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_License_IMAGE);

                break;

            case R.id.license_insurance_click:
                intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_insurance_IMAGE);

                break;
            case R.id.proof_supervision_click:
                intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_supervision_IMAGE);

                break;

            case R.id.terms_conditions:
                intent = new Intent(context, Terms_Conditions.class);
                startActivity(intent);
                controller.Animation_up(context);
                break;

        }
    }


    private void intent_type() {
        intent = new Intent(context, Pro_Specialise_area.class);
        intent.putExtra("type", type);
        startActivity(intent);
        controller.Animation_forward(context);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_License_IMAGE && resultCode == RESULT_OK && null != data) {
            license_image.setVisibility(View.VISIBLE);
            Uri selectedImage = data.getData();
            Glide.with(context).load(selectedImage).asBitmap().centerCrop().into(new BitmapImageViewTarget(license_image) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    license_image.setImageDrawable(circularBitmapDrawable);
                    license_image.buildDrawingCache();

                    Bitmap bitmap = license_image.getDrawingCache();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] image = stream.toByteArray();
                    license_img = Base64.encodeToString(image, 0);
                }
            });
        } else if (requestCode == RESULT_insurance_IMAGE && resultCode == RESULT_OK && null != data) {
            license_insurance_image.setVisibility(View.VISIBLE);
            Uri selectedImage = data.getData();
            Glide.with(context).load(selectedImage).asBitmap().centerCrop().into(new BitmapImageViewTarget(license_insurance_image) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    license_insurance_image.setImageDrawable(circularBitmapDrawable);
                    license_insurance_image.buildDrawingCache();

                    Bitmap bitmap = license_insurance_image.getDrawingCache();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] image = stream.toByteArray();
                    license_insurance_img = Base64.encodeToString(image, 0);
                }
            });
        } else if (requestCode == RESULT_supervision_IMAGE && resultCode == RESULT_OK && null != data) {
            proof_supervision_image.setVisibility(View.VISIBLE);
            Uri selectedImage = data.getData();
            Glide.with(context).load(selectedImage).asBitmap().centerCrop().into(new BitmapImageViewTarget(proof_supervision_image) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    proof_supervision_image.setImageDrawable(circularBitmapDrawable);
                    proof_supervision_image.buildDrawingCache();

                    Bitmap bitmap = proof_supervision_image.getDrawingCache();

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] image = stream.toByteArray();
                    supervision_proof_img = Base64.encodeToString(image, 0);
                }
            });
        }
    }


    private void spinner_int() {
        Spinner type_speciality = (Spinner) findViewById(R.id.type_speciality);
        ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(context, R.layout.spinner_text, speciality);
        type_speciality.setAdapter(langAdapter);
        langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);

        type_speciality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1, int position, long arg3) {
                select_speciality_type = parent.getItemAtPosition(position).toString();
                if (select_speciality_type.equals(getString(R.string.type_speciality))) {
                    type_speciality_default.setVisibility(View.VISIBLE);
                } else {
                    type_speciality_default.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
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
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        check_box = isChecked;
        if (check_box) {
            hide_views();
        } else {
            visible_views();
        }
    }

    private void hide_views() {
        license_or_certification.setVisibility(View.VISIBLE);
        supervisor_license.setVisibility(View.VISIBLE);
        upload_letter_txt.setVisibility(View.VISIBLE);
        proof_supervision_click.setVisibility(View.VISIBLE);
        license_certification.setVisibility(View.GONE);
        license_number.setVisibility(View.GONE);
        license_year_selection.setVisibility(View.GONE);
    }

    private void visible_views() {
        license_or_certification.setVisibility(View.GONE);
        supervisor_license.setVisibility(View.GONE);
        upload_letter_txt.setVisibility(View.GONE);
        proof_supervision_click.setVisibility(View.GONE);
        license_certification.setVisibility(View.VISIBLE);
        license_number.setVisibility(View.VISIBLE);
        license_year_selection.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        controller.Animation_backward(context);
    }
}