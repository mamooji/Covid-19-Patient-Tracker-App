package com.example.dbmarch11;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/*  FILE        : CoronaNews.java
 *  PROJECT     : Mobile A2
 *  DEVELOPERS  : Mohamed Benzreba, Muhammad Mamooji, Ethan Hoekstra, Jacob Nelson
 *  DUE DATE    : 14 March 2020
 *  DESCRIPTION :
 */
public class CoronaNews extends AppCompatActivity
{

    // Private widget access
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView lvRSS;

    // Internal abstract modelling
    private ArrayList<RSSFeedModel> RSSFeedModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corona_news);

        // Set widget & layout access
        this.swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        this.lvRSS = (ListView) findViewById(R.id.lv_rss);

        // Setting the refresh listener
        this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                // Call the AsyncTask to fetch the RSS feed for us
                // The AsyncTask will be a private class defined within this activity
                new GetFeedAsyncTask().execute((Void) null);
            }
        });

        // Execute the refresh for the first time
        new GetFeedAsyncTask().execute((Void) null);


        // Set click listener for each news story in the ListView
        this.lvRSS.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                String link = RSSFeedModelList.get(position).link;
                Uri viewUri = Uri.parse(link);

                Intent networkIntent = new Intent(Intent.ACTION_VIEW, viewUri);
                startActivity(networkIntent);
            }
        });
    }


    public ArrayList<RSSFeedModel> parseFeed(String entireXmlString)
    {
        String title = null;
        String link = null;
        String description = null;
        boolean isItem = false;
        ArrayList<RSSFeedModel> items = new ArrayList<>();

        try {
            XmlPullParser xmlPullParser = Xml.newPullParser();
            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);

            // New way!!! Pass in a string instead...
            xmlPullParser.setInput(new StringReader(entireXmlString));


            xmlPullParser.nextTag();

            // XML pull parser workload:
            // Logic to parse each item in the XML document
            while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT)
            {
                int eventType = xmlPullParser.getEventType();

                String name = xmlPullParser.getName();
                if(name == null)
                    continue;

                if(eventType == XmlPullParser.END_TAG) {
                    if(name.equalsIgnoreCase("item"))
                    {
                        isItem = false;
                    }
                    continue;
                }

                if (eventType == XmlPullParser.START_TAG)
                {
                    if(name.equalsIgnoreCase("item"))
                    {
                        isItem = true;
                        continue;
                    }
                }

                Log.d("MyXmlParser", "Parsing name ==> " + name);
                String result = "";
                if (xmlPullParser.next() == XmlPullParser.TEXT)
                {
                    result = xmlPullParser.getText();
                    xmlPullParser.nextTag();
                }

                if (name.equalsIgnoreCase("title"))
                {
                    title = result;
                }
                else if (name.equalsIgnoreCase("link"))
                {
                    link = result;
                }
                else if (name.equalsIgnoreCase("description"))
                {
                    description = result;
                }

                if (title != null && link != null && description != null)
                {
                    if(isItem)
                    {
                        RSSFeedModel item = new RSSFeedModel(title, link, description);
                        items.add(item);
                    }

                    title = null;
                    link = null;
                    description = null;
                    isItem = false;
                }
            }

        }
        catch (XmlPullParserException e)
        {
            Log.d("Error: ", e.getMessage());
        }
        catch (IOException e)
        {
            Log.d("Error: ", e.getMessage());
        }

        return items;
    }




    /*  CLASS   : GetFeedAsyncTask
     *  PURPOSE : AsyncTask to pull RSS Feed from RSS_URL.
     */
    private class GetFeedAsyncTask extends AsyncTask<Void, Void, Boolean>
    {

        // URL to pull RSS feed from
        private static final String RSS_URL = "https://rss.cbc.ca/lineup/health.xml";


        @Override
        protected void onPreExecute()
        {
            swipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected Boolean doInBackground(Void... voids)
        {
            URL url = null;

            try {
                url = new URL(GetFeedAsyncTask.RSS_URL);
                InputStream inputStream = url.openConnection().getInputStream();

                byte[] data = new byte[256];
                int endCheck = inputStream.read(data);
                String xmlString = new String();
                while(endCheck != -1)
                {
                    //do something with data...
                    String tmpByteStr = new String(data);

                    //xmlString.concat(tmpByteStr);
                    xmlString += tmpByteStr;

                    endCheck = inputStream.read(data);
                }
                inputStream.close();

                RSSFeedModelList = parseFeed(xmlString);
            }
            catch (MalformedURLException e)
            {
                Log.e("MalformedURLException ", e.getMessage());
                return false;
            }
            catch (IOException e)
            {
                Log.e("IOException ", e.getMessage());
                return false;
            }

            return true;
        }


        @Override
        protected void onPostExecute(Boolean success)
        {
            swipeRefreshLayout.setRefreshing(false);

            if (success)
            {
                // Fill RecyclerView
                //rvRSS.setAdapter(new RSSFeedListAdapter(RSSFeedModelList));
                lvRSS.setAdapter(new ArrayAdapter<RSSFeedModel>(getApplicationContext(),
                        android.R.layout.simple_list_item_1,RSSFeedModelList));
            }
            else
            {
                Toast.makeText(CoronaNews.this,
                        "Retrieval Error: COVID-19 has destroyed CBC News",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
