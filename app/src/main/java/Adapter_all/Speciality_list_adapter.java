package Adapter_all;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ShrinkMyIssues.App.R;

import java.util.ArrayList;

import Call_Back.Register_data_callback;

public class Speciality_list_adapter extends RecyclerView.Adapter<Speciality_list_adapter.ViewHolder> {

    private View view;
    private Context resultScreen;
    private ArrayList<String> list, select_list = new ArrayList<>();
    private Register_data_callback register_data_callback;
    private int type;

    // ViewHolder parent class for all views
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView un_sel_text, sel_text;
        private RelativeLayout hide_view, click_parent;

        //All views connect through their id's from R file
        public ViewHolder(View view) {
            super(view);
            click_parent = (RelativeLayout) view.findViewById(R.id.click_parent);
            un_sel_text = (TextView) view.findViewById(R.id.un_sel_text);
            sel_text = (TextView) view.findViewById(R.id.sel_text);
            hide_view = (RelativeLayout) view.findViewById(R.id.hide_view);
        }
    }

    public Speciality_list_adapter(Context context, ArrayList<String> list, Register_data_callback register_data_callback, int type) {
        resultScreen = context;
        this.list = list;
        this.register_data_callback = register_data_callback;
        this.type = type;
    }

    //Creating view holder bind xml file with that
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.speciality_list_adapter, parent, false);

        return new ViewHolder(view);
    }

    //Bind all views and performing action at every position
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (select_list != null) {
            if (select_list.size() > 0) {
                if (select_list.contains(list.get(position))) {
                    select_view_visible(holder, position);
                }else {
                    unselected_view_visible(holder, position);
                }
            } else {
                unselected_view_visible(holder, position);
            }
        } else {
            unselected_view_visible(holder, position);
        }

        holder.click_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String position_data = list.get(position);
                if (select_list != null) {
                    if (select_list.size() > 0) {
                        if (select_list.contains(position_data)) {
                            for (int i = 0; i < select_list.size(); i++) {
                                if (position_data.equals(select_list.get(i))) {
                                    select_list.remove(i);
                                    unselected_view_visible(holder, position);
                                    break;
                                }
                            }
                        } else {
                            select_list.add(position_data);
                            select_view_visible(holder, position);
                        }
                    } else {
                        select_list.add(position_data);
                        select_view_visible(holder, position);
                    }
                } else {
                    select_list.add(position_data);
                    select_view_visible(holder, position);
                }
                if (type == 0){
                    register_data_callback.list_data(select_list,0);
                }else  if(type == 1){
                    register_data_callback.list_data(select_list,1);
                }else {
                    register_data_callback.list_data(select_list,2);
                }
            }
        });
    }

    private void unselected_view_visible(ViewHolder holder, int position) {
        holder.un_sel_text.setVisibility(View.VISIBLE);
        holder.hide_view.setVisibility(View.GONE);
        holder.un_sel_text.setText(list.get(position));
    }

    private void select_view_visible(ViewHolder holder, int position) {
        holder.un_sel_text.setVisibility(View.GONE);
        holder.hide_view.setVisibility(View.VISIBLE);
        holder.sel_text.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}