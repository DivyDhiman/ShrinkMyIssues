package Adapter_all;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.ShrinkMyIssues.App.R;

import java.util.ArrayList;

public class Peer_support_adapter extends RecyclerView.Adapter<Peer_support_adapter.ViewHolder> {

    private View view;
    private Context resultScreen;
    private ArrayList<String> list = new ArrayList<>();

    // ViewHolder parent class for all views
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private Button peers_support_text;

        //All views connect through their id's from R file
        public ViewHolder(View view) {
            super(view);
            peers_support_text = (Button) view.findViewById(R.id.peers_support_text);
        }
    }

    public Peer_support_adapter(Context context, ArrayList<String> list) {
        resultScreen = context;
        this.list = list;
    }

    //Creating view holder bind xml file with that
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.peer_group_list_adapter, parent, false);

        return new ViewHolder(view);
    }

    //Bind all views and performing action at every position
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        holder.peers_support_text.setText(list.get(position));

        holder.peers_support_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
