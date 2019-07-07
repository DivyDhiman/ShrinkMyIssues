package Fragments_all;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ShrinkMyIssues.App.R;

public class Issues_fragment extends Fragment implements View.OnClickListener {

    private View rootView;
    private TextView past_issues,current_issues,submit_issue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parentViewGroup, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.my_issues_layout, parentViewGroup, false);

        past_issues = (TextView)rootView.findViewById(R.id.past_issues);
        current_issues = (TextView)rootView.findViewById(R.id.current_issues);
        submit_issue = (TextView)rootView.findViewById(R.id.submit_issue);

        past_issues.setOnClickListener(this);
        current_issues.setOnClickListener(this);
        submit_issue.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.past_issues:
                past_issues.setBackgroundResource(R.color.issue_selected);
                current_issues.setBackgroundResource(R.color.issue_not_selected);

                break;
            case R.id.current_issues:
                past_issues.setBackgroundResource(R.color.issue_not_selected);
                current_issues.setBackgroundResource(R.color.issue_selected);

                break;
            case R.id.submit_issue:

                break;
        }
    }
}
