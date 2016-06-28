package me.kulam.pagefetch;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

//picasso
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Sharu on 14/03/2016.
 */
public class cardAdapter extends RecyclerView.Adapter<cardAdapter.ViewHolder>{
    private static ArrayList<Page> validPages;
    private Context context;

    public void removeCard(int position){
        final Page removedPage = validPages.get(position);
        final int removedPos = position;
        validPages.remove(removedPage);
        cardActivity.getAllpages().remove(removedPage);
        notifyItemRemoved(position);

        Snackbar undoBar = Snackbar
                .make((((cardActivity) context).findViewById(R.id.crd_layout)), "Page was removed", Snackbar.LENGTH_LONG);

                undoBar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        validPages.add(removedPos, removedPage);
                        notifyItemInserted(removedPos);
                        cardActivity.getAllpages().add(removedPage);
                    }
                });
        undoBar.setActionTextColor(Color.YELLOW);

        // Changing action button text color
       // View sbView = undoBar.getView();
        //TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        //textView.setTextColor(Color.WHITE);
        undoBar.show();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //For small card
        protected RelativeLayout relativeLayout;
        protected CardView cardView;
        protected TextView cardTitle;
        protected TextView cardDesc;
        protected ImageView cardImg;
        protected Button open_page;
        protected String pageUrl;

        public ViewHolder(View v) {
            super(v);

            //For initial card view design
            cardView = (CardView) v.findViewById(R.id.card_view);
            cardTitle = (TextView) v.findViewById(R.id.card_title);
            cardDesc = (TextView) v.findViewById(R.id.card_desc);
            cardImg = (ImageView) v.findViewById(R.id.card_img);
            open_page = (Button) v.findViewById(R.id.card_open_page);
            relativeLayout = (RelativeLayout) v.findViewById(R.id.card_open_page_holder);
            pageUrl = "";


            open_page.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), WebActivity.class);
                    intent.putExtra("url", pageUrl);
                    intent.putExtra("title",cardTitle.getText().toString());
                    v.getContext().startActivity(intent);
                }
            });
        }

        public void setUrl(String url) {
            pageUrl = url;
        }
    }

    public cardAdapter(ArrayList<Page> validPages, Context context) {
        this.validPages = validPages;
        this.context = context;
        //this.pressedCategory = pressedCategory.toLowerCase();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public cardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_cardview, parent,false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Page page = validPages.get(position);

        //For small card
        holder.cardTitle.setText(page.getTitle());
        holder.cardDesc.setText(page.getDescription());
        holder.setUrl(page.getUrl());

        //TODO: Firebase integration for img picture
        //Picasso.with(context).load("http://www.kulam.me/applications/pagefetch/pageicons/telenor.png").into(holder.cardImg);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return validPages.size();
    }

    public static ArrayList<Page> getValidPages(){
        return validPages;
    }


}