package me.kulam.pagefetch;

import android.app.Fragment;
import android.media.Image;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Sharu on 14/03/2016.
 */
public class cardAdapter extends RecyclerView.Adapter<cardAdapter.ViewHolder>
{
    private ArrayList<Page> categoryPages;

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        protected CardView cardView;
        protected TextView cardTitle;
        protected TextView cardDesc;
        protected ImageView cardImg;
        protected ImageView open_page; //TODO inflate/populate this one

        public ViewHolder(View v)
        {
            super(v);
            cardView = (CardView) v.findViewById(R.id.card_view);
            cardTitle = (TextView) v.findViewById(R.id.card_title);
            cardDesc = (TextView) v.findViewById(R.id.card_desc);
            cardImg = (ImageView) v.findViewById(R.id.card_img);
            open_page = (ImageView) v.findViewById(R.id.card_open_page);
            cardImg.setImageResource(R.drawable.card_img_fashion);

            open_page.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new Fragment();
                    //TODO Next step in fragments view
                }
            });
            //TODO: Import for image view
        }
    }

    public cardAdapter(ArrayList<Page> categories){
        categoryPages = categories;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public cardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        //holder.mainTextView.setText(categories.get(position));
        holder.cardTitle.setText(categoryPages.get(position).getTitle());
        holder.cardDesc.setText(categoryPages.get(position).getDescription());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return categoryPages.size();
    }
}
