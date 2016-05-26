package me.kulam.pagefetch;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
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
    private static ArrayList<Page> categoryPages;
    private ArrayList<Page> validPages;

    private String category;
    private FloatingActionButton fButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerlist);
        listView = (RecyclerView) findViewById(R.id.list_view);

        categoryPages = new ArrayList<>();
        validPages = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            category = extras.getString("category");
        }

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
        this.setTitle(category);

        cardAdapter = new cardAdapter(validPages,this);
        listView.setAdapter(cardAdapter);

        layoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(layoutManager);
        listView.setHasFixedSize(true);

        //TODO: Give option to get back deleted card, timeout for restoration
        ItemTouchHelper.Callback swipeCards = new SwipeCards(cardAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(swipeCards);
        helper.attachToRecyclerView(listView);



        //TODO: Description max size = 150 characters
        categoryPages.add(new Page("Facebook","Social","facebook.com","It was popularised"));
        categoryPages.add(new Page("Twitter", "Social", "twitter.com", "This is a social page."));
        categoryPages.add(new Page("Telenor","Social","telenor.com","It was popularised"));
        categoryPages.add(new Page("Google","Social","google.com","This is a social page."));
        categoryPages.add(new Page("Techcrunch","Social", "techcrunch.com","This is a tech page."));
        categoryPages.add(new Page("Bekk","Social", "bekk.no","This is a tech page."));

        categoryPages.add(new Page("VG", "News", "vg.no", "This is a News page."));
        categoryPages.add(new Page("Aftenposten","News", "aftenposten.no","This is a News page."));
        categoryPages.add(new Page("Dagbladet","News", "dagbladet.no","This is a News page."));
        categoryPages.add(new Page("Nettavisen","News", "nettavisen.no","This is a News page."));

        categoryPages.add(new Page("Techcrunch","Tech", "techcrunch.com","This is a tech page."));
        categoryPages.add(new Page("Bekk","Tech", "bekk.no","This is a tech page."));

        for(Page page : categoryPages){
            if (page.getCategory().trim().equalsIgnoreCase(category)){
                validPages.add(page);
            }
        }
        cardAdapter.notifyDataSetChanged();





        //cardAdapter = new RecyclerAdapter(this);

        //TODO: Load pages from local storage.

        //TODO: Check if specified category. Add to card list if so.
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
        //TODO:
    }

    public void exitDialog(View v){
        AddItemDialogFrag runningFragment = (AddItemDialogFrag)getFragmentManager().findFragmentByTag("add_page_frag");

        if(v.getId() == R.id.add_page_exitbtn && runningFragment.isVisible()){
            runningFragment.dismiss();
        }
    }
    public static ArrayList<Page> getCategoryPages(){
        return categoryPages;
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
}
