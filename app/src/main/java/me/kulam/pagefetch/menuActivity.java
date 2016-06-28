package me.kulam.pagefetch;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class menuActivity extends AppCompatActivity implements AddItemDialogFrag.OnFragmentInteractionListener
{
    private RecyclerView listView;
    private RecyclerView.Adapter menuAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static ArrayList<String> categories;
    private FloatingActionButton fButton;

    private SharedPreferences categoryPref;
    private final String CAT_PREF = "categoryPref";

    public void initAll(){
        setContentView(R.layout.activity_recyclerlist);
        listView = (RecyclerView) findViewById(R.id.list_view);

        fButton = (FloatingActionButton) findViewById(R.id.fab_add_category);
        fButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });
        listView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                    fButton.show();
                else if (newState == RecyclerView.SCROLL_STATE_DRAGGING)
                    fButton.hide();
                else {
                    fButton.show();
                }

            }
        });

        categories = new ArrayList<>();
        menuAdapter = new menuAdapter(categories);
        listView.setAdapter(menuAdapter);

        mLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(mLayoutManager);
        listView.setHasFixedSize(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryPref = getSharedPreferences(CAT_PREF,MODE_PRIVATE);

        initAll();


        //TODO: NETWORK

        //TODO: Store data SQL or sharedpreferences

        //TODO: Load from Local storage
        //TODO: Add a loader while scrolling

    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = categoryPref.edit();

        editor.putInt("totalCategories", getCategories().size());

        for(int i = 0; i < getCategories().size(); i++){
            editor.putString("" + i, me.kulam.pagefetch.menuAdapter.getCategory(i));
            editor.apply();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        int totalCategories = categoryPref.getInt("totalCategories",-1);
        if(totalCategories == -1){
            //TODO: Optional view
        }
        else{
            for(int i = 0; i < totalCategories; i++){
                String category = categoryPref.getString("" + i, null);

                if(category != null && !me.kulam.pagefetch.menuAdapter.getList().contains(category)){
                    categories.add(category);
                    menuAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        /*Want to search for item?*/
        if (item.getItemId() == R.id.search_list){
            searchForItem();
            return true;
        }
        else{
            return super.onOptionsItemSelected(item);
        }
    }

    public void addItem(){
        FragmentManager fm = getFragmentManager();
        AddItemDialogFrag df = AddItemDialogFrag.newInstance("New Category",null);
        df.show(fm, "add_item_frag");
    }

    //TODO: Continue later. less priority
    public void searchForItem() {
        /*
        Intent searchIntent = new Intent(this,SearchActivity.class);
        searchIntent.setAction(Intent.ACTION_SEARCH);
        searchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        searchIntent.putExtra(SearchManager.QUERY, searchView.getQuery().toString());

        Bundle extras = new Bundle();
        extras.putStringArrayList("searchCategories", categories);
        searchIntent.putExtras(extras);
        startActivity(searchIntent) ;
        */
    }

    @Override
    public int handleUserInput(String input, String inputCategory, String inputUrl, String inputDesc) {

        if (categories.contains(StringUsage.stdFormat(input))){
            return -1;
        }
        else if(input.length() == 0){
            return 0;
        } else {
            categories.add(StringUsage.stdFormat(input));
            Toast.makeText(this,"Category added",Toast.LENGTH_SHORT).show();
            menuAdapter.notifyDataSetChanged();
            return 1;
        }
    }

    public static ArrayList<String> getCategories(){return categories;}

}

