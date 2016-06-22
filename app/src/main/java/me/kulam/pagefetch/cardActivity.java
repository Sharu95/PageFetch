package me.kulam.pagefetch;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.PersistableBundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class cardActivity extends AppCompatActivity implements AddItemDialogFrag.OnFragmentInteractionListener
{
    private RecyclerView listView;
    private RecyclerView.Adapter cardAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static final int MAX_DESC_LENGTH = 150;
    private static ArrayList<Page> allPages;
    private ArrayList<Page> validPages;
    private SharedPreferences preferences;


    private String category;
    private FloatingActionButton fButton;

    public void addFAB(){
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
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING)
                    fButton.hide();
                else if (newState == RecyclerView.SCROLL_STATE_IDLE)
                    fButton.show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerlist);
        preferences = this.getPreferences(MODE_PRIVATE);

        if(savedInstanceState != null){
            for(Page page : allPages){
                Gson gson = new Gson();
                String json = preferences.getString(page.getTitle(), "");
                Page p = gson.fromJson(json, Page.class);
            }
        }else {

            listView = (RecyclerView) findViewById(R.id.list_view);
            allPages = new ArrayList<>();
            validPages = new ArrayList<>();

            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                category = extras.getString("category");
            }

            addFAB();

            this.setTitle(category);
            cardAdapter = new cardAdapter(validPages, this);
            listView.setAdapter(cardAdapter);

            layoutManager = new LinearLayoutManager(this);
            listView.setLayoutManager(layoutManager);
            listView.setHasFixedSize(true);

            ItemTouchHelper.Callback swipeCards = new SwipeCards(cardAdapter);
            ItemTouchHelper helper = new ItemTouchHelper(swipeCards);
            helper.attachToRecyclerView(listView);

            //TODO: Description max size = 150 characters
            allPages.add(new Page("Facebook", "Social", "facebook.com", "It was popularised"));
            allPages.add(new Page("Twitter", "Social", "twitter.com", "This is a social page."));
            allPages.add(new Page("Telenor", "Social", "telenor.com", "It was popularised"));
            allPages.add(new Page("Google", "Social", "google.com", "This is a social page."));
            allPages.add(new Page("Techcrunch", "Social", "techcrunch.com", "This is a tech page."));
            allPages.add(new Page("Bekk", "Social", "bekk.no", "This is a tech page."));

            allPages.add(new Page("VG", "News", "vg.no", "This is a News page."));
            allPages.add(new Page("Aftenposten", "News", "aftenposten.no", "This is a News page."));
            allPages.add(new Page("Dagbladet", "News", "dagbladet.no", "This is a News page."));
            allPages.add(new Page("Nettavisen", "News", "nettavisen.no", "This is a News page."));

            allPages.add(new Page("Techcrunch", "Tech", "techcrunch.com", "This is a tech page."));
            allPages.add(new Page("Bekk", "Tech", "bekk.no", "This is a tech page."));

            for (Page page : allPages) {
                if (page.getCategory().trim().equalsIgnoreCase(category)) {
                    validPages.add(page);
                }
            }
            cardAdapter.notifyDataSetChanged();
        }
        //TODO: Load pages from local storage.
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
            //searchForItem();
            return true;
        }
        else{
            return super.onOptionsItemSelected(item);
        }
    }

    public void addItem(){
        FragmentManager fm = getFragmentManager();
        AddItemDialogFrag df = AddItemDialogFrag.newInstance("New Page",category);
        df.show(fm, "add_page_frag");

        //TODO: Add item, remember multiple constructors
        //TODO: Check validity of URL
        //TODO: Fetch picture from page
    }

    public void exitDialog(View v){
        AddItemDialogFrag runningFragment = (AddItemDialogFrag)getFragmentManager().findFragmentByTag("add_page_frag");

        if(v.getId() == R.id.add_page_exitbtn && runningFragment.isVisible()){
            runningFragment.dismiss();
        }
    }
    public static ArrayList<Page> getAllpages(){
        return allPages;
    }

    @Override
    public int handleUserInput(String inputTitle, String inputCategory, String inputUrl, String inputDesc) {

        Page page;
        if(inputDesc == null || inputDesc.length() == 0){
            page = new Page(inputTitle,inputCategory,inputUrl);
        }else{ //Possibly redundant, but for the sake of controlled environment
            page = new Page(inputTitle,inputCategory,inputUrl,inputDesc);
        }
        validPages.add(page);
        cardAdapter.notifyDataSetChanged();
        Toast.makeText(this,"Page added",Toast.LENGTH_SHORT).show();
        return 1;
    }

    public int getMaxDescSize(){
        return MAX_DESC_LENGTH;
    }

    /*States*/
    @Override
    protected void onSaveInstanceState(Bundle outState) {

        SharedPreferences.Editor prefsEditor;
        Gson gson;
        String json;

        for(Page page : allPages){
            prefsEditor = preferences.edit();
            gson = new Gson();
            json = gson.toJson(page);
            prefsEditor.putString(page.getTitle(), json);
            prefsEditor.apply();
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {

        for(Page page : allPages){
            Gson gson = new Gson();
            String json = preferences.getString(page.getTitle(), "");
            Page p = gson.fromJson(json, Page.class);
        }

        super.onRestoreInstanceState(savedInstanceState, persistentState);

    }
}
