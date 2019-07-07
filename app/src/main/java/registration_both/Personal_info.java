package registration_both;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ShrinkMyIssues.App.Address_location_get;
import com.ShrinkMyIssues.App.Privacy_Policy;
import com.ShrinkMyIssues.App.R;
import com.ShrinkMyIssues.App.Terms_Conditions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.io.ByteArrayOutputStream;
import java.io.File;

import Controllers.Controller;

public class Personal_info extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private Context context;
    private Intent intent;
    private Controller controller;
    private String type, gender, address, name,picturePath,base64;
    private String[] genders;
    private Button client_info_next;
    private TextView address_info, gender_default, terms_conditions, profile_text,terms_conditions_text,privacy_policy_text,and_text;
    private EditText name_info;
    private CheckBox terms_conditions_agree;
    private boolean checkBox_check = false;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private int address_code = 20,RESULT_LOAD_IMAGE = 30;
    private ImageView profile_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presonal_info);

        context = Personal_info.this;

        type = getIntent().getExtras().getString("type");
        controller = new Controller();

        genders = getResources().getStringArray(R.array.select_gender);

        initialise();
    }

    private void initialise() {
        profile_image = (ImageView) findViewById(R.id.profile_image);
        profile_text = (TextView) findViewById(R.id.profile_text);
        client_info_next = (Button) findViewById(R.id.client_info_next);
        address_info = (TextView) findViewById(R.id.address_info);
        name_info = (EditText) findViewById(R.id.name_info);
        gender_default = (TextView) findViewById(R.id.gender_default);
        terms_conditions_agree = (CheckBox) findViewById(R.id.terms_conditions_agree);
        terms_conditions = (TextView) findViewById(R.id.terms_conditions);
        terms_conditions_text = (TextView)findViewById(R.id.terms_conditions_text);
        and_text = (TextView)findViewById(R.id.and_text);
        privacy_policy_text = (TextView)findViewById(R.id.privacy_policy_text);

        terms_conditions_text.setPaintFlags(terms_conditions_text.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        privacy_policy_text.setPaintFlags(privacy_policy_text.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

        terms_conditions_text.setOnClickListener(this);
        privacy_policy_text.setOnClickListener(this);
        and_text.setOnClickListener(this);
        client_info_next.setOnClickListener(this);
        terms_conditions_agree.setOnCheckedChangeListener(this);
        terms_conditions.setOnClickListener(this);
        address_info.setOnClickListener(this);
        profile_image.setOnClickListener(this);

        Glide.with(context).load(R.drawable.profile_default).asBitmap().centerCrop().into(new BitmapImageViewTarget(profile_image) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                profile_image.setImageDrawable(circularBitmapDrawable);
                profile_image.buildDrawingCache();
            }
        });
        spinner_int();
    }

    private void spinner_int() {
        Spinner gender_spinner = (Spinner) findViewById(R.id.gender_spinner);
        ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(context, R.layout.spinner_text, genders);
        gender_spinner.setAdapter(langAdapter);
        langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);

        gender_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int position, long arg3) {

                gender = parent.getItemAtPosition(position).toString();
                if (gender.equals(getString(R.string.select_gender))) {
                    gender_default.setVisibility(View.VISIBLE);
                } else {
                    gender_default.setVisibility(View.GONE);
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
            case R.id.client_info_next:

                address = address_info.getText().toString();
                if (controller.Check_all_empty(name_info)) {
                    name_info.setError(getString(R.string.enter_name));
                } else if (gender.equals(getString(R.string.select_gender))) {
                    controller.Toast_show(context, getString(R.string.empty_gender));
                } else if (controller.Matches_textview(address, getString(R.string.address))) {
                    controller.Toast_show(context, getString(R.string.empty_address));
                } else if (!checkBox_check) {
                    controller.Toast_show(context, getString(R.string.empty_terms_condition));
                } else {
                    if (!controller.InternetCheck(context)) {
                        controller.Toast_show(context, getString(R.string.enable_internet));
                    } else {
                        name = name_info.getText().toString();
                        intent_type();
                    }
                }
                break;

            case R.id.terms_conditions:
                intent = new Intent(context, Terms_Conditions.class);
                startActivity(intent);
                controller.Animation_up(context);
                break;

            case R.id.address_info:

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkPermission()) {
                        call_pick_location();
                    } else {
                        requestPermission();
                    }
                } else {
                    call_pick_location();
                }

                break;

            case R.id.profile_image:

                intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
                break;

            case R.id.and_text:
                if (!checkBox_check){
                    terms_conditions_agree.setChecked(true);
                    checkBox_check = true;
                }else {
                    terms_conditions_agree.setChecked(false);
                    checkBox_check = false;
                }
                break;

            case R.id.terms_conditions_text:
                intent = new Intent(context, Terms_Conditions.class);
                startActivity(intent);
                controller.Animation_up(context);

                break;
            case R.id.privacy_policy_text:
                intent = new Intent(context, Privacy_Policy.class);
                startActivity(intent);
                controller.Animation_up(context);
                break;
        }
    }

    private void call_pick_location() {
        if (!controller.InternetCheck(context)) {
            controller.Toast_show(context, getString(R.string.enable_internet));
        } else {
            intent = new Intent(context, Address_location_get.class);
            startActivityForResult(intent, address_code);
            controller.Animation_forward(context);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        checkBox_check = isChecked;
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
    }

    private void intent_type() {
        if(type.equals(getString(R.string.client))){
            intent = new Intent(context, Card_details.class);
            intent.putExtra("type", type);
            startActivity(intent);
            controller.Animation_forward(context);
        }else {
            intent = new Intent(context, Pro_personal_details.class);
            intent.putExtra("type", type);
            startActivity(intent);
            controller.Animation_forward(context);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && cameraAccepted) {
                        call_pick_location();
                    } else {
                        controller.Toast_show(context, getString(R.string.please_grant_permission));
                    }
                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == address_code && resultCode == RESULT_OK && null != data) {
            address_info.setText(data.getExtras().getString("Address"));
        }else if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {


            Uri selectedImage = data.getData();
//            String[] filePathColumn = { MediaStore.Images.Media.DATA };
//
//            Cursor cursor = getContentResolver().query(selectedImage,
//                    filePathColumn, null, null, null);
//            cursor.moveToFirst();


//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

//            File file = new File(cursor.getString(columnIndex));
//            double fileSizeInBytes = file.length();
//            double fileSizeInKB = fileSizeInBytes / 1024;


//                picturePath = cursor.getString(columnIndex);
//                cursor.close();

            Glide.with(context).load(selectedImage).asBitmap().centerCrop().into(new BitmapImageViewTarget(profile_image) {

                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    profile_image.setImageDrawable(circularBitmapDrawable);
//                    profile_image.buildDrawingCache();
//
//                    Bitmap bitmap = profile_image.getDrawingCache();
//
//                    ByteArrayOutputStream stream=new ByteArrayOutputStream();
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
//                    byte[] image=stream.toByteArray();
//                    System.out.println("byte array:"+image);
//
//                    base64 = Base64.encodeToString(image, 0);
                    profile_text.setVisibility(View.GONE);
                }
            });
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        controller.Animation_backward(context);
    }
}