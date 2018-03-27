package mpdproject.gcu.me.org.assignmenttest1;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;

import android.net.Uri;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;


// Student Name: Lewis Duncan
// Student ID: S1630772

public class ItemActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        final Intent intent = getIntent();
        Item item = (Item) intent.getExtras().getSerializable("item");

        TextView title = (TextView) findViewById(R.id.item_value_title);
        title.setText(item.getTitle());

        TextView description = (TextView) findViewById(R.id.item_value_description);
        description.setText(item.getDescription());

        TextView link = (TextView) findViewById(R.id.item_value_link);
        link.setText(item.getLink());

//        TextView geo = (TextView) findViewById(R.id.item_value_geo);
//        geo.setText(item.getGeo());

        TextView geo = (TextView)findViewById(R.id.item_value_geo);
        geo.setTextColor(Color.parseColor("#ff4182"));
        geo.setPaintFlags(geo.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        geo.setText(item.getGeo());
        geo.setOnClickListener(new View.OnClickListener() {
            Item item = (Item) intent.getExtras().getSerializable("item");

            @Override
            public void onClick(View v) {
                try {
                    Uri mapUri = Uri.parse("geo:0,0?q="+(item.getGeo()));
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }
        } );


        TextView author = (TextView) findViewById(R.id.item_value_author);
        author.setText(item.getAuthor());

        TextView comments = (TextView) findViewById(R.id.item_value_comments);
        comments.setText(item.getComments());

        TextView pubDate = (TextView) findViewById(R.id.item_value_pubDate);
        pubDate.setText(item.getPubDate());

    }
}
