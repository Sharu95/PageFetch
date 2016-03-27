package me.kulam.pagefetch;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sharu on 23/03/2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    private static ArrayList<Page> categoryPages;
    private static ArrayList<String> categories;

    public RecyclerAdapter(Activity host) {

        if(host.getClass() == cardActivity.class){
            System.out.println("CARDAC");
            categoryPages = cardActivity.getCategoryPages();

            //TODO: Description max size = 170 characters. FIX AGAIN
            categoryPages.add(new Page("Facebook","This is a social page.","Social" ,"http://facebook.com"));
            categoryPages.add(new Page("Amazon","This is a social page.","Online shopping" ,"http://amazon.com"));
            categoryPages.add(new Page("Twitter","This is a social page.","Social" ,"http://twitter.com"));
            categoryPages.add(new Page("VG", "This is a social page.", "News", "http://vg.no"));
            categoryPages.add(new Page("Aftenposten","This is a social page.","News", "http://aftenposten.no"));
        }
        else if (host.getClass() == menuActivity.class){
            System.out.println("MENUAC");
            categories = menuActivity.getCategories();

            /*Test data*/
            categories.add("School");
            categories.add("Fashion");
            categories.add("News");
            categories.add("Tech");
            categories.add("School");
            categories.add("Fashion");
            categories.add("News");
            categories.add("Tech");
        }
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = null;
        int listViewType = getItemViewType(viewType);

        if(listViewType == 0){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cardview, parent,false);
        }
        else{
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        }
        return new RecyclerViewHolder(v,viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        int viewType = getItemViewType(position);
        if (viewType == 0){
            holder.cardTitle.setText(categoryPages.get(position).getTitle());
            holder.cardDesc.setText(categoryPages.get(position).getDescription());
            holder.setUrl(categoryPages.get(position).getUrl());
        }
        else if (viewType == 2){
            holder.categoryName.setText(categories.get(position));
        }


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        //For card
        protected CardView cardView;
        protected TextView cardTitle;
        protected TextView cardDesc;
        protected ImageView cardImg;
        protected ImageView open_page;
        protected String pageUrl;

        //For primary list
        protected TextView categoryName;

        public RecyclerViewHolder (View v, int viewType){
            super(v);

            if (viewType == 0){
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
                        intent.putExtra("url",pageUrl);
                        v.getContext().startActivity(intent);

                        //viewWebPage(v);//TODO: Start fragment here
                        //TODO Next step in fragments view
                    }
                });
            }
            else if(viewType == 2){
                categoryName = (TextView) v.findViewById(R.id.list_item);

                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), cardActivity.class);
                        intent.putExtra("category",categoryName.getText());
                        v.getContext().startActivity(intent);
                    }
                });
            }
        }

        public void setUrl(String url){
            pageUrl = url;
        }
    }
}
