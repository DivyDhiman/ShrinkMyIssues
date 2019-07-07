package Fragments_all;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ShrinkMyIssues.App.R;

public class Professional_corner extends Fragment {
    private View rootView;
    private RecyclerView professional_corner_message__list;
    private TextView post_message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parentViewGroup, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.professional_corner__layout, parentViewGroup, false);

        professional_corner_message__list = (RecyclerView) rootView.findViewById(R.id.professional_corner_message__list);
        post_message = (TextView) rootView.findViewById(R.id.post_message);
        return rootView;
    }
}