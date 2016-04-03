package me.kulam.pagefetch;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

//picasso and jsoup
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
    private ArrayList<Page> validPages;
    private Context context;
    private String pressedCategory;

    public void removeCard(int position){
        validPages.remove(position);
        notifyItemRemoved(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //For small card
        protected CardView cardView;
        protected TextView cardTitle;
        protected TextView cardDesc;
        protected ImageView cardImg;
        protected Button open_page;
        protected String pageUrl;

        //for big card
        protected CardView bigCard;
        protected ImageView bigCardImg;
        protected TextView bigCardTitle;
        protected Button shareBtn;
        protected Button visitBtn;

        public ViewHolder(View v) {
            super(v);

            //For initial card view design
            cardView = (CardView) v.findViewById(R.id.card_view);
            cardTitle = (TextView) v.findViewById(R.id.card_title);
            cardDesc = (TextView) v.findViewById(R.id.card_desc);
            cardImg = (ImageView) v.findViewById(R.id.card_img);
            open_page = (Button) v.findViewById(R.id.card_open_page);
            pageUrl = "";

            open_page.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //TODO: ANimate card click elevation

                    Intent intent = new Intent(v.getContext(), WebActivity.class);
                    intent.putExtra("url", pageUrl);
                    intent.putExtra("title",cardTitle.getText().toString());
                    v.getContext().startActivity(intent);

                    //viewWebPage(v);//TODO: Start fragment here
                    //TODO Next step in fragments view
                }
            });

            /*//for big cardview design testing
            bigCard = (CardView) v.findViewById(R.id.bigcard_view);
            bigCardTitle = (TextView) v.findViewById(R.id.bigcard_title);
            bigCardImg = (ImageView) v.findViewById(R.id.bigcard_img);
            shareBtn = (Button) v.findViewById(R.id.bigcard_share);
            visitBtn = (Button) v.findViewById(R.id.bigcard_visit);
            pageUrl = "";

            visitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //TODO: ANimate card click elevation

                    Intent intent = new Intent(v.getContext(), WebActivity.class);
                    intent.putExtra("url", pageUrl);
                    intent.putExtra("title",bigCardTitle.getText().toString());
                    v.getContext().startActivity(intent);

                    //viewWebPage(v);//TODO: Start fragment here
                    //TODO Next step in fragments view
                }
            });*/

            //TODO: Import for image view
        }

        public void setUrl(String url) {
            pageUrl = url;
        }

        public void setImgRes(){

        }
    }

    public cardAdapter(ArrayList<Page> validPages, Context context) {
        this.validPages = validPages;
        this.context = context; //FOR PICASSSO
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


        //TODO: Works, export to get from database
        Resources resources = context.getResources();
        int resourceId = getResId(resources,page);
        holder.cardImg.setImageResource(resourceId);



        /*//For big card
        holder.bigCardTitle.setText(page.getTitle());
        holder.setUrl(page.getUrl());
        */
        //See for bigcard img below with picasso

        //FOR HTML PARSING in ASYNCTASK
        //String pageUrl = holder.pageUrl;
        //String testURL = "http://www.";
        //new RunTask().execute(testURL);


        //TODO: Picasso. Resize to cardImg first also
        //TODO: Or load from external database

        //PICASSO
        //String testImgUrl = "https://cdn1.iconfinder.com/data/icons/logotypes/32/square-facebook-128.png";
        //Picasso.with(context).load(testImgUrl).resize(75,75).centerCrop().into(holder.cardImg);


        //holder.cardImg.setImageResource(R.drawable.card_img_fashion);


    }

    public int getResId(Resources resources, Page page){
        String category = page.getCategory().toLowerCase();
        String title = page.getTitle().toLowerCase();
        String [] checkSize = title.split(" ");
        String finite = "";
        String s;

        if (checkSize.length > 1){
            for(String word : checkSize){
                finite += word + "_";
            }
            s = finite.substring(0,finite.length()-1); //Removing redundant last underscore
        }
        else{
            s = title;
        }

        int resourceId = resources.getIdentifier(s, "drawable", context.getPackageName());
        int catRes; //redundant
        int notFound; //redundant

        if(resourceId == 0){
            catRes = resources.getIdentifier(category, "drawable", context.getPackageName());
            if (catRes == 0) {
                notFound = resources.getIdentifier("icon_not_found", "drawable", context.getPackageName());
                return notFound; //holder.cardImg.setImageResource(notFound);
            }
            else{
                return catRes; //holder.cardImg.setImageResource(catRes);
            }
        }
        else{
            return resourceId; //holder.cardImg.setImageResource(resourceId);
        }
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return validPages.size();
    }

    private class RunTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... urls) {
            Document doc = null;
            Elements pageLogo = null;
            Element getElement = null;
            String absoluteUrl;
            String srcValue;
            String checkID;
            String checkClass;



            /*
             //TODO: FIX this FIX FUCKING ISSUE FIX
            try{
                doc = Jsoup.connect(urls[0]).get();
                for(int i = 0; i < 50; i++){
                    getElement = doc.select("img").get(i); //Image element i
                    System.out.println("Total <IMG>: " + getElement.toString()); //img tag nr i

                    absoluteUrl = getElement.absUrl("src"); //Get src url of img element
                    System.out.println("Abs url: " + absoluteUrl); //Print out absolute src url

                    checkID = getElement.id(); //TODO: Check how to get an <IMG>-tag id
                    System.out.println("ID: " + checkID);

                    checkClass = getElement.className(); //Gets a <IMG>-tag classname.
                    System.out.println("Class name: " + checkClass);


                    switch(checkID){
                        case "LogoImage": System.out.println("LogoImage");
                        case "logo-image": System.out.println("logo-image");
                        case "logoImage": System.out.println("logoImage");
                    }
                }
                //srcValue = getPic.attr("LogoImage");
                //System.out.println("SrcValue: " + srcValue);// exact content value of the attribute

                throw new IOException();
            }catch(IOException e){
                    e.getMessage();
            }*/
            return null;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}