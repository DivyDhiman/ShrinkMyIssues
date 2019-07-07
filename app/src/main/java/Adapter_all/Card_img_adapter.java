package Adapter_all;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.ShrinkMyIssues.App.R;

import java.util.ArrayList;

public class Card_img_adapter extends RecyclerView.Adapter<Card_img_adapter.ViewHolder> {

    private View view;
    private Context resultScreen;
    private ArrayList<Integer> card_images;

    // ViewHolder parent class for all views
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView card_img;

        //All views connect through their id's from R file
        public ViewHolder(View view) {
            super(view);
            card_img = (ImageView) view.findViewById(R.id.card_img);
        }
    }

    //Constructor of MyCard for all values and context passing from parent fragment
    public Card_img_adapter(Context context, ArrayList<Integer> card_images) {
        resultScreen = context;
        this.card_images = card_images;
    }

    //Creating view holder bind xml file with that
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_img_adapter, parent, false);

        return new ViewHolder(view);
    }

    //Bind all views and performing action at every position
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.card_img.setImageResource(card_images.get(position));
    }

    @Override
    public int getItemCount() {
        return card_images.size();
    }
}