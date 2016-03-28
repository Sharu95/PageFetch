package me.kulam.pagefetch;

import android.content.ClipData;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by Sharu on 28/03/2016.
 */

//Callback is not manually implemented. Using superclass callback
public class SwipeCards extends ItemTouchHelper.Callback{


    private final RecyclerView.Adapter mAdapter;

    public SwipeCards(RecyclerView.Adapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT; //ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(0,swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        if(direction == ItemTouchHelper.LEFT){
            ((cardAdapter) mAdapter).removeCard(direction);
        }

    }

    public interface ItemCardSwipe {
        void removeCard(int position);
    }


}
