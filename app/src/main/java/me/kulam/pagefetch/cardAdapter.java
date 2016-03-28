package me.kulam.pagefetch;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sharu on 14/03/2016.
 */
public class cardAdapter extends RecyclerView.Adapter<cardAdapter.ViewHolder> {
    private ArrayList<Page> validPages;
    private Context context;
    private String pressedCategory;


    public void removeCard(int position){
        validPages.remove(position);
        notifyItemRemoved(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected CardView cardView;
        protected TextView cardTitle;
        protected TextView cardDesc;
        protected ImageView cardImg;
        protected ImageView open_page;
        protected String pageUrl;

        public ViewHolder(View v) {
            super(v);
            cardView = (CardView) v.findViewById(R.id.card_view);
            cardTitle = (TextView) v.findViewById(R.id.card_title);
            cardDesc = (TextView) v.findViewById(R.id.card_desc);
            cardImg = (ImageView) v.findViewById(R.id.card_img);
            open_page = (ImageView) v.findViewById(R.id.card_open_page);
            pageUrl = "";

            cardImg.setImageResource(R.drawable.card_img_fashion);

            open_page.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(v.getContext(), WebActivity.class);
                    intent.putExtra("url", pageUrl);
                    System.out.println(pageUrl);
                    v.getContext().startActivity(intent);

                    //viewWebPage(v);//TODO: Start fragment here
                    //TODO Next step in fragments view
                }
            });
            //TODO: Import for image view
        }

        public void setUrl(String url) {
            pageUrl = url;
        }
    }

    public cardAdapter(Context context, String pressedCategory, ArrayList<Page> validPages) {
        this.validPages = validPages;
        this.context = context;
        this.pressedCategory = pressedCategory.toLowerCase();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public cardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_cardview, parent,false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);

        //TODO: Remove Animation class, remove AnimationUtils
        //TODO: FInd out about animation in viewholder
        //if(flag == false) {
            Animation animation = AnimationUtils.loadAnimation(this.context, R.anim.slide_in);
            v.startAnimation(animation);//}
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Page page = validPages.get(position);
        holder.cardTitle.setText(page.getTitle());
        holder.cardDesc.setText(page.getDescription());
        holder.setUrl(page.getUrl());
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return validPages.size();
    }

}