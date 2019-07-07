package Fragments_all;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ShrinkMyIssues.App.R;

public class Find_a_Shrink extends Fragment {

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parentViewGroup, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.find_a_shrink, parentViewGroup, false);
        return rootView;
    }
}