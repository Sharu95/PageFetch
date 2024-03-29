package me.kulam.pagefetch;

import android.app.Activity;
import android.bluetooth.le.AdvertiseData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sharu95 on 04/03/2016.
 */
public class menuAdapter extends RecyclerView.Adapter<menuAdapter.MenuViewHolder>
{
    private static ArrayList<String> categories;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    private int lastPos = -1;

    public static class MenuViewHolder extends RecyclerView.ViewHolder
    {
        protected TextView categoryName;

        public MenuViewHolder(View v)
        {
            super(v);
            categoryName = (TextView) v.findViewById(R.id.list_item);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int cName = R.id.list_item;
                    if (cName == categoryName.getId()) {
                        Intent intent = new Intent(v.getContext(), cardActivity.class);
                        intent.putExtra("category", categoryName.getText().toString());
                        v.getContext().startActivity(intent);
                    }
                }
            });
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "font/Roboto-Light.ttf"); //TODO: Light preferred
            categoryName.setTypeface(typeface);//TODO: For Roboto

        }
    }
    protected static Context context;
    // Provide a suitable constructor (depends on the kind of dataset)
    public menuAdapter(ArrayList<String> categories, Context activity){
        this.categories = categories;
        context = activity; //TODO: For roboto
    }

    // Create new views (invoked by the layout manager)
    @Override
    public menuAdapter.MenuViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new MenuViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.categoryName.setText(StringUsage.stdFormat(categories.get(position)));

        //TODO: Sequental animation for each time items are loaded after onStop. Now it is all at the same time
        final Animation anim = AnimationUtils.loadAnimation(context, R.anim.slide_up);
        if(position > lastPos){
            holder.categoryName.startAnimation(anim);
            lastPos = position;
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static String getCategory(int i){
        return categories.get(i);
    }

    public static ArrayList<String> getList(){
        return categories;
    }
}
