package mpdproject.gcu.me.org.assignmenttest1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

// Student Name: Lewis Duncan
// Student ID: S1630772

public class ItemListActivity extends AppCompatActivity {

    ListView listView;
    SearchView searchView;


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listView = (ListView) findViewById(R.id.item_list_view);

        Bundle bundle = getIntent().getExtras();
        ArrayList<Item> itemCollection = (ArrayList<Item>) bundle.getSerializable("itemCollection");

        // Temporarily declared as 'final'
        final ItemArrayAdapter adapter = new ItemArrayAdapter(this, itemCollection);
        listView.setAdapter(adapter);

        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                adapter.getFilter().filter(text);
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ItemListActivity.this, ItemActivity.class);
                Item item = (Item) adapterView.getAdapter().getItem(i);
                intent.putExtra("item", item);
                startActivity(intent);
            }
        });
    }

    private class ItemArrayAdapter extends ArrayAdapter<Item> {

        Context context;
        ArrayList<Item> items;

        public ItemArrayAdapter(Context context, ArrayList<Item> items) {
            super(context, 0, items);
            this.context = context;
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View listItem = convertView;
            if (listItem == null) {
                listItem = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
            }

            Item item = getItem(position);

            TextView title = (TextView) listItem.findViewById(R.id.list_item_title);
            title.setText(item.getTitle());

            TextView description = (TextView) listItem.findViewById(R.id.list_item_description);
            description.setText(item.getDescription());

            return listItem;
        }
    }

}
