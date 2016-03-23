package me.kulam.pagefetch;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class cardActivity extends AppCompatActivity
{
    private RecyclerView listView;
    private RecyclerView.Adapter cardAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<Page> categoryPages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerlist);
        listView = (RecyclerView) findViewById(R.id.list_view);

        categoryPages = new ArrayList<>();

        layoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(layoutManager);
        listView.setHasFixedSize(true);

        cardAdapter = new cardAdapter(categoryPages);
        listView.setAdapter(cardAdapter);


        //TODO: Load pages from local storage.
        Bundle extras = getIntent().getExtras();
        String category = extras.getString("category");
        //TODO: Check if specified category. Add to card list if so.


    }


}
