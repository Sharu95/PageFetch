package me.kulam.pagefetch;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.HashSet;
import java.util.Set;

public class menuActivity extends AppCompatActivity implements AddItemDialogFrag.OnFragmentInteractionListener
{
    private RecyclerView listView;
    private RecyclerView.Adapter menuAdapter;

    private RecyclerView.LayoutManager mLayoutManager;
    private static ArrayList<String> categories;
    private static Set<String> categoriesSet;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerlist);

        listView = (RecyclerView) findViewById(R.id.list_view);

        categories = new ArrayList<>();
        menuAdapter = new menuAdapter(categories);
        listView.setAdapter(menuAdapter);

        mLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(mLayoutManager);
        listView.setHasFixedSize(true);

        /*Test data*/
        categories.add(StringUsage.stdFormat("EDUCATION"));
        categories.add(StringUsage.stdFormat("FASHION"));
        categories.add(StringUsage.stdFormat("NewS"));
        categories.add(StringUsage.stdFormat("TECh"));
        categories.add(StringUsage.stdFormat("SocIAl"));
        categories.add(StringUsage.stdFormat("OnliNE ShOppIng"));

        menuAdapter.notifyDataSetChanged();

        categoriesSet = new HashSet<>();
        sharedPreferences = getSharedPreferences("categories", Context.MODE_PRIVATE);

        //TODO: Store data SQL or sharedpreferences

        //TODO: Load from Local storage
        //TODO: Add a loader while scrolling

        //menuAdapter = new RecyclerAdapter(this);

    }

    /*
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        saveData();
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        sharedPreferences =
                getSharedPreferences("categories", Context.MODE_PRIVATE);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        /*Want to add item? */
        if (item.getItemId() == R.id.add_item){
            addItem();
            return true;
        }
        /*Want to search for item?*/
        else if (item.getItemId() == R.id.search_list){
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
        }
        else{
            categories.add(input);

            saveData();

            Toast.makeText(this,"Category added",Toast.LENGTH_SHORT).show();
            menuAdapter.notifyDataSetChanged();
            return 1;
        }
    }

    public void saveData(){
        for(String s : categories){
            categoriesSet.add(s);
        }
        editor = sharedPreferences.edit();
        editor.putStringSet("categories", categoriesSet);
        editor.commit();
    }

    public static ArrayList<String> getCategories(){return categories;}

    //TODO: REMOve later
    public Activity getActivity(){return this;}
}

