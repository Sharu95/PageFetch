package me.kulam.pagefetch;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/*Could've extended with ListActivity*/
public class CategoryList extends AppCompatActivity
{
    private RecyclerView listView;
    private RecyclerView.Adapter listAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    //REMOVED TEST COMMENT FOR GIT TESTING

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorylist);

        listView = (RecyclerView) findViewById(R.id.list_view);

        ArrayList<String> categories = new ArrayList<>();

        //TODO: Load from Local storage
        //TODO: Add a loader while scrolling

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


        mLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(mLayoutManager);

        listView.setHasFixedSize(true);

        listAdapter = new CategoryListAdapter(categories);

        listView.setAdapter(listAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

