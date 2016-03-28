package me.kulam.pagefetch;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class cardActivity extends AppCompatActivity implements AddItemDialogFrag.OnFragmentInteractionListener
{
    private RecyclerView listView;
    private RecyclerView.Adapter cardAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private static ArrayList<Page> categoryPages;
    private ArrayList<Page> validPages;

    private String category;

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

        cardAdapter = new cardAdapter(this,category,validPages);
        listView.setAdapter(cardAdapter);

        layoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(layoutManager);
        listView.setHasFixedSize(true);

        categoryPages.add(new Page("Facebook","This is a social page.","Social" ,"http://facebook.com"));
        categoryPages.add(new Page("Twitter","This is a social page.","Social" ,"http://twitter.com"));

        categoryPages.add(new Page("Amazon","This is a OS page.","Online shopping" ,"http://amazon.com"));

        categoryPages.add(new Page("VG", "This is a News page.", "News", "http://vg.no"));
        categoryPages.add(new Page("Aftenposten", "This is a News page.", "News", "http://aftenposten.no"));
        categoryPages.add(new Page("DB", "This is a News page.", "News", "http://dagbladet.no"));
        categoryPages.add(new Page("Nettavisen", "This is a News page.", "News", "http://nettavisen.no"));

        for(Page page : categoryPages){
            if (page.getCategory().trim().equalsIgnoreCase(category)){
                validPages.add(page);
                System.out.println(page.getTitle());
            }
        }
        cardAdapter.notifyDataSetChanged();

        /*Test data*/
        //TODO: Description max size = 170 characters. FIX AGAIN

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

        /*Want to add item? */
        if (item.getItemId() == R.id.add_item){
            addItem();
            return true;
        }
        /*Want to search for item?*/
        else if (item.getItemId() == R.id.search_list){
            //searchForItem();
            return true;
        }
        else{
            return super.onOptionsItemSelected(item);
        }
    }

    public void addItem(){
        FragmentManager fm = getFragmentManager();
        AddItemDialogFrag df = AddItemDialogFrag.newInstance("New Page");
        df.show(fm, "add_page_frag");

        //TODO: Add item, remember multiple constructors
        //TODO: Check validity of URL
        //TODO: Fetch picture from page
        //TODO:
    }

    public static ArrayList<Page> getCategoryPages(){
        return categoryPages;
    }

    @Override
    public int handleUserInput(String inputTitle, String inputCategory, String inputUrl, String inputDesc) {

        Page page;
        if(inputDesc == null || inputDesc.length() == 0){
            page = new Page(inputTitle,inputCategory,inputUrl);
        }else{
            page = new Page(inputTitle,inputCategory,inputUrl,inputDesc);
        }
        validPages.add(page);
        cardAdapter.notifyDataSetChanged();
        Toast.makeText(this,"Page added",Toast.LENGTH_SHORT).show();
        return 1;
    }
}
