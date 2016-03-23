package me.kulam.pagefetch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class menuActivity extends AppCompatActivity
{
    private RecyclerView listView;
    private RecyclerView.Adapter menuAdapter;

    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerlist);

        listView = (RecyclerView) findViewById(R.id.list_view);

        categories = new ArrayList<>();

        //TODO: Load from Local storage
        //TODO: Add a loader while scrolling

        mLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(mLayoutManager);

        listView.setHasFixedSize(true);

        menuAdapter = new menuAdapter(categories);

        listView.setAdapter(menuAdapter);
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

