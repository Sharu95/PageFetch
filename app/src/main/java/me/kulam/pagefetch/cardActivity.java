package me.kulam.pagefetch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

        //TODO: Description max size = 170 characters
        categoryPages.add(new Page("Volt", "00s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic TTT"));
        categoryPages.add(new Page("Swag", "hen an unknown printer took a galley of type and scrambled it to make a type specimen book. It has sur"));
        categoryPages.add(new Page("FFY", "wn printer took a galley of type and scrambled it to make a type specimen book. It ha"));
        categoryPages.add(new Page("WAKKAMAKKA","00s, when an unake a type specimen book. It has survived It was popularised in the 1960s with the release of Letraset sheets containing "));
        categoryPages.add(new Page("BOLT", "hen an unknown printer took a galley of type and scrambled it to make a type specimen book. It has sur"));
        categoryPages.add(new Page("XXL", "wn printer took a galley of type and scrambled it to make a type specimen book. It ha"));


        layoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(layoutManager);
        listView.setHasFixedSize(true);

        cardAdapter = new cardAdapter(categoryPages);
        listView.setAdapter(cardAdapter);
    }
}
