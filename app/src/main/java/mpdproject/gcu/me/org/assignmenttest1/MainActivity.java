package mpdproject.gcu.me.org.assignmenttest1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

// Student Name: Lewis Duncan
// Student ID: S1630772

public class MainActivity extends AppCompatActivity {
    final String currentIncidentsURL = "http://trafficscotland.org/rss/feeds/currentincidents.aspx";
    final String plannedRoadworksURL = "http://trafficscotland.org/rss/feeds/plannedroadworks.aspx";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button currentIncidentsBtn = (Button) findViewById(R.id.currentIncidentsBtn);
        currentIncidentsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View aview) {
                try {
                    new GetFeedTask().execute(new URL(currentIncidentsURL));
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }
        });

        Button plannedRoadworksBtn = (Button) findViewById(R.id.plannedRoadworksBtn);
        plannedRoadworksBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View aview) {
                try {
                    new GetFeedTask().execute(new URL(plannedRoadworksURL));
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }
        });

    } // End of onCreate

    // Asynchronous tasks execution
    private class GetFeedTask extends AsyncTask<URL, Void, ArrayList<Item>> {
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("Fetching feed...");
            progressDialog.show();
        }

        @Override
        protected ArrayList<Item> doInBackground(URL... params) {
            // Set up DocumentBuilder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder;

            try {
                builder = factory.newDocumentBuilder();
            } catch (Exception e) {
                Log.e("Exception", e.getMessage());
                return new ArrayList<>();
            }

            // Parse XML document from URL passed in
            Document doc = null;
            try {
                URLConnection yc = params[0].openConnection();
                doc = builder.parse(yc.getInputStream());
            } catch (Exception e) {
                Log.e("Exception", e.getMessage());
            }

            // Extract relevant fields from document and insert into Item model
            ArrayList<Item> itemCollection = new ArrayList<>();
            NodeList nodeList = doc.getElementsByTagName("item");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                NodeList itemNodeList = node.getChildNodes();
                Item item = new Item();

                for (int j = 0; j < itemNodeList.getLength(); j++) {
                    Node itemNode = itemNodeList.item(j);
                    String itemNodeName = itemNode.getNodeName();
                    String itemNodeValue = itemNode.getTextContent();

                    switch (itemNodeName) {
                        case "title":
                            item.setTitle(itemNodeValue);
                            break;
                        case "description":
                            itemNodeValue = itemNodeValue.replace("<br />", "\n");
                            item.setDescription(itemNodeValue);
                            break;
                        case "link":
                            item.setLink(itemNodeValue);
                            break;
                        case "georss:point":
                            item.setGeo(itemNodeValue);
                            break;
                        case "author":
                            item.setAuthor(itemNodeValue);
                            break;
                        case "comments":
                            item.setComments(itemNodeValue);
                            break;
                        case "pubDate":
                            item.setPubDate(itemNodeValue);
                            break;
                    }
                }
                itemCollection.add(item);
            }
            return itemCollection;
        }

        @Override
        protected void onPostExecute(ArrayList<Item> result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Intent intent = new Intent(MainActivity.this, ItemListActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("itemCollection", result);
            intent.putExtras(bundle);
            startActivity(intent);

        }
    }

}
