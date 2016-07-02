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


import java.util.ArrayList;

public class cardActivity extends AppCompatActivity implements AddItemDialogFrag.OnFragmentInteractionListener
{
    private RecyclerView listView;
    private RecyclerView.Adapter cardAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static final int MAX_DESC_LENGTH = 150;
    private static ArrayList<Page> allPages;
    private static ArrayList<Page> validPages;

    private String category;
    private FloatingActionButton fButton;

    private SharedPreferences sPref;

    /*Add Floating Action Button*/
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
        sPref = getPreferences(MODE_PRIVATE);

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
    }


    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences.Editor editor = sPref.edit();
        editor.putInt("totalPages", allPages.size());

        String encode = "Page";
        int ID = 0;

        for(int i = 0; i < allPages.size(); i++){
            editor.putString(encode+ID,allPages.get(i).getTitle());
            ID++;
            editor.putString(encode+ID,allPages.get(i).getDescription());
            ID++;
            editor.putString(encode+ID,allPages.get(i).getUrl());
            ID++;
            editor.putString(encode+ID,allPages.get(i).getCategory());
            ID++;
        }
        editor.apply();

       // System.out.println("Prior onStop - AllPages - " + allPages.size());
       // System.out.println("Prior onStop - ValidPages - " + validPages.size());
        allPages.clear();
        me.kulam.pagefetch.cardAdapter.getValidPages().clear();
       // System.out.println("After onStop - AllPages - " + allPages.size());
        //System.out.println("After onStop - ValidPages - " + validPages.size());
        //TODO: Null out all structures to free memory if potentially onDestroy
    }


    @Override
    protected void onStart() {
        super.onStart();

        int totalPages = sPref.getInt("totalPages",-1);
        String title, desc, url, category;

        int ID = 0;
        String encode = "Page";

        if(totalPages != -1){
            for(int i = 0; i < totalPages; i++){
                //Baaaaaaad
                title = sPref.getString(encode+ID,"noTitle");
                ID++;
                desc = sPref.getString(encode+ID, null);
                ID++;
                url = sPref.getString(encode+ID,"noUrl");
                ID++;
                category = sPref.getString(encode+ID,"noCat");
                ID++;

                Page newPage = new Page(title,category,url,desc);
                allPages.add(newPage);
            }
        }
        else{
            //TODO: Alternative view
        }

        //System.out.println("Prior onStart - AllPages - " + allPages.size());
        //System.out.println("Prior onStart - ValidPages - " + validPages.size());

        for (Page page : allPages) {
            if (page.getCategory().trim().equalsIgnoreCase(this.category)) {
                validPages.add(page);
            }
        }
        //System.out.println("After onStart - AllPages - " + allPages.size());
        //System.out.println("After onStart - ValidPages - " + validPages.size());
        cardAdapter.notifyDataSetChanged();
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

    /*Add item dialog initiation*/
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

        Page page = null;
        if(inputDesc == null || inputDesc.length() == 0){
            //System.out.println("handle input no desc");
            page = new Page(inputTitle,inputCategory,inputUrl,null);
        }
        else{
            //System.out.println("handle input");
            page = new Page(inputTitle,inputCategory,inputUrl,inputDesc);
        }

        allPages.add(page);
        validPages.add(page);
        cardAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Page added", Toast.LENGTH_SHORT).show();
        return 1;
    }

    public int getMaxDescSize(){
        return MAX_DESC_LENGTH;
    }
}
