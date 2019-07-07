package Fragments_all;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ShrinkMyIssues.App.R;

import java.util.ArrayList;
import java.util.Collections;

import Adapter_all.Peer_support_adapter;

public class Peer_group_fragment extends Fragment {

    private View rootView;
    private RecyclerView peer_group_list;
    private String[] get_peer_support_list;
    private ArrayList<String> peer_support_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parentViewGroup, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.peer_group_layout, parentViewGroup, false);

        peer_group_list = (RecyclerView) rootView.findViewById(R.id.peer_group_list);

        peer_support_list = new ArrayList<>();
        get_peer_support_list = getResources().getStringArray(R.array.peer_support_group);
        Collections.addAll(peer_support_list, get_peer_support_list);

        call_adapter();

        return rootView;
    }

    private void call_adapter() {
        Peer_support_adapter peer_support_adapter = new Peer_support_adapter(peer_group_list.getContext(), peer_support_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(peer_group_list.getContext(),1,false);
        peer_group_list.setLayoutManager(linearLayoutManager);
        peer_group_list.setAdapter(peer_support_adapter);
    }
}
