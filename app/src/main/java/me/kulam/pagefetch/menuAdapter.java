package me.kulam.pagefetch;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Sharu95 on 04/03/2016.
 */
public class menuAdapter extends RecyclerView.Adapter<menuAdapter.MenuViewHolder>
{
    private final ArrayList<String> categories;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
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
                    Intent intent = new Intent(v.getContext(), cardActivity.class);
                    intent.putExtra("category",categoryName.getText());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public menuAdapter(ArrayList<String> categories){
        this.categories = categories;

        /*Test data*/
        categories.add("School");
        categories.add("Fashion");
        categories.add("News");
        categories.add("Tech");
        categories.add("School");
        categories.add("Fashion");
        categories.add("News");
        categories.add("Tech");
        categories.add("School");
        categories.add("Fashion");
        categories.add("News");
        categories.add("Tech");
        categories.add("School");
        categories.add("Fashion");
        categories.add("News");
        categories.add("Tech");
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

        holder.categoryName.setText(categories.get(position));
        //TODO Find out about null issue
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return categories.size();
    }

}
