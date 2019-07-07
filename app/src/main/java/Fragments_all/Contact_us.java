package Fragments_all;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ShrinkMyIssues.App.R;
import Controllers.Controller;

public class Contact_us extends Fragment implements View.OnClickListener, Animation.AnimationListener {

    private View rootView;
    private Context context;
    private LinearLayout first_layout, hide_layout;
    private EditText title_et, write_problem_issue_et, message_et;
    private Spinner problem_issue_spinner;
    private TextView problem_issue_default, thanks_sending_txt, another_txt, another_press, go_back_txt, back_press;
    private Button send_btn;
    private ImageView send_image;
    private String title, problem, message, problem_issue_main,anim_check;
    private Controller controller;
    private String[] problems;
    private boolean check_select = false;
    private Animation mAnim = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parentViewGroup, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.contact_us, parentViewGroup, false);
        context = rootView.getContext();

        controller = new Controller();

        initialise(rootView);
        return rootView;
    }

    private void initialise(View rootView) {
        first_layout = (LinearLayout) rootView.findViewById(R.id.first_layout);
        hide_layout = (LinearLayout) rootView.findViewById(R.id.hide_layout);

        title_et = (EditText) rootView.findViewById(R.id.title_et);
        write_problem_issue_et = (EditText) rootView.findViewById(R.id.write_problem_issue_et);
        message_et = (EditText) rootView.findViewById(R.id.message_et);

        problem_issue_spinner = (Spinner) rootView.findViewById(R.id.problem_issue_spinner);

        problem_issue_default = (TextView) rootView.findViewById(R.id.problem_issue_default);
        thanks_sending_txt = (TextView) rootView.findViewById(R.id.thanks_sending_txt);
        another_txt = (TextView) rootView.findViewById(R.id.another_txt);
        another_press = (TextView) rootView.findViewById(R.id.another_press);
        go_back_txt = (TextView) rootView.findViewById(R.id.go_back_txt);
        back_press = (TextView) rootView.findViewById(R.id.back_press);

        send_btn = (Button) rootView.findViewById(R.id.send_btn);

        send_image = (ImageView) rootView.findViewById(R.id.send_image);

        send_btn.setOnClickListener(this);
        another_press.setOnClickListener(this);
        back_press.setOnClickListener(this);

        problems = getResources().getStringArray(R.array.problem_issue_select);

        spinner_int();
    }

    private void spinner_int() {
        ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(context, R.layout.spinner_text, problems);
        problem_issue_spinner.setAdapter(langAdapter);
        langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);

        problem_issue_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int position, long arg3) {

                problem = parent.getItemAtPosition(position).toString();
                if (problem.equals(getString(R.string.others))) {
                    problem_issue_default.setVisibility(View.GONE);
                    write_problem_issue_et.setVisibility(View.VISIBLE);
                } else {
                    write_problem_issue_et.setVisibility(View.GONE);
                    if (problem.equals(getString(R.string.select_problem_issue))) {
                        problem_issue_default.setVisibility(View.VISIBLE);
                    } else {
                        problem_issue_default.setVisibility(View.GONE);
                    }
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
            case R.id.send_btn:
                if (controller.Check_all_empty(title_et)) {
                    title_et.setError(getString(R.string.empty_title));
                } else if (problem.equals(getString(R.string.select_problem_issue))) {
                    controller.Toast_show(context, getString(R.string.empty_problem_issue));
                } else {
                    if (check_select) {
                        if (controller.Check_all_empty(write_problem_issue_et)) {
                            write_problem_issue_et.setError(getString(R.string.empty_problem_issue_heading));
                        } else {
                            problem_issue_main = write_problem_issue_et.getText().toString();
                        }
                    } else {
                        problem_issue_main = problem;
                        if (!controller.Check_all_empty(message_et)) {
                            message = message_et.getText().toString();
                        }

                        if (!controller.InternetCheck(context)) {
                            controller.Toast_show(context, getString(R.string.enable_internet));
                        } else {
                            title = title_et.getText().toString();
                            first_layout.setVisibility(View.GONE);
                            hide_layout.setVisibility(View.VISIBLE);
                            thanks_sending_txt.setVisibility(View.INVISIBLE);
                            another_txt.setVisibility(View.INVISIBLE);
                            another_press.setVisibility(View.INVISIBLE);
                            go_back_txt.setVisibility(View.INVISIBLE);
                            back_press.setVisibility(View.INVISIBLE);
                            anim_check = "first";
//                            send_image.setBackgroundResource(R.drawable.process_send);
                            mAnim = AnimationUtils.loadAnimation(context, R.anim.rotate_once);
                            mAnim.setAnimationListener(this);
                            send_image.clearAnimation();
                            send_image.setAnimation(mAnim);
                        }
                    }
                }
                break;

            case R.id.another_press:
                first_layout.setVisibility(View.VISIBLE);
                hide_layout.setVisibility(View.GONE);
                break;

            case R.id.back_press:
                getActivity().onBackPressed();
                break;
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {
        switch (anim_check) {
            case "first":
                send_image.setBackgroundResource(R.drawable.send_email);
                mAnim = AnimationUtils.loadAnimation(context, R.anim.fade_in);
                mAnim.setAnimationListener(this);
                send_image.clearAnimation();
                send_image.setAnimation(mAnim);
                anim_check = "second";
                break;

            case "second":
                thanks_sending_txt.setVisibility(View.VISIBLE);
                another_txt.setVisibility(View.VISIBLE);
                another_press.setVisibility(View.VISIBLE);
                go_back_txt.setVisibility(View.VISIBLE);
                back_press.setVisibility(View.VISIBLE);

                another_press.setPaintFlags(another_press.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                back_press.setPaintFlags(back_press.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

                mAnim = AnimationUtils.loadAnimation(context, R.anim.fade_in);
                mAnim.setAnimationListener(this);
                thanks_sending_txt.clearAnimation();
                thanks_sending_txt.setAnimation(mAnim);

                another_txt.clearAnimation();
                another_txt.setAnimation(mAnim);

                another_press.clearAnimation();
                another_press.setAnimation(mAnim);

                go_back_txt.clearAnimation();
                go_back_txt.setAnimation(mAnim);

                back_press.clearAnimation();
                back_press.setAnimation(mAnim);

                anim_check = "third";
                break;

            case "third":
                anim_check = "first";
                break;
        }
    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}