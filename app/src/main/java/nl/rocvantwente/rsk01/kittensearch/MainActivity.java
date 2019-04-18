package nl.rocvantwente.rsk01.kittensearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements BookAdapter.OnItemClickListener {

    public static final String EXTRA_URL = "imageUrl";
    public static final String EXTRA_CREATOR = "creatorName";
    public static final String EXTRA_LIKES = "likesCount";

    private RequestQueue mRequestQueue;
    private ArrayList<Book> mBooks;
    private BookAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private EditText mSearch;
    private ImageButton mSearchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSearch = findViewById(R.id.editTextSearch);
        mSearchButton = findViewById(R.id.imageSearchButton);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parseJson(mSearch.getText().toString());

            }
        });
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Remove the previous search
                ((EditText) view).setText("");
            }
        });
        mRecyclerView = findViewById(R.id.recycler_view);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mBooks = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);

        parseJson("android");
    }

    private void parseJson(String search) {
        String url = "https://www.googleapis.com/books/v1/volumes?q=" + search + "&maxResults=40";
//        String url = "https://pixabay.com/api/?key=5303976-fd6581ad4ac165d1b75cc15b3&q=dogs&image_type=photo&pretty=true";
        mBooks.clear();
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("items");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonVolumeInfo = jsonArray.getJSONObject(i).getJSONObject("volumeInfo");
                        JSONObject jsonSaleInfo = jsonArray.getJSONObject(i).getJSONObject("saleInfo");
                        Book book = new Book();
                        VolumeInfo volumeInfo = new VolumeInfo();
                        SaleInfo saleInfo = new SaleInfo();

                        // get the title
                        volumeInfo.setmTitle(jsonVolumeInfo.getString("title"));

                        // Get the subtitle
                        if (jsonVolumeInfo.has("subtitle"))
                            volumeInfo.setmSubtitle(jsonVolumeInfo.getString("subtitle"));
                        else
                            volumeInfo.setmSubtitle("");

                        // Get the publisher
                        if (jsonVolumeInfo.has("publisher"))
                            volumeInfo.setmPublisher(jsonVolumeInfo.getString("publisher"));
                        else
                            volumeInfo.setmPublisher("");

                        // Get the number of pages
                        if (jsonVolumeInfo.has("pageCount"))
                            volumeInfo.setmPageCount(jsonVolumeInfo.getInt("pageCount"));

                        // Get the published date
                        if (jsonVolumeInfo.has("publishedDate")) {
                            try {
                                DateFormat df = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
                                volumeInfo.setmPublishedDate(df.parse(jsonVolumeInfo.getString("publishedDate")));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }

                        // Get the authors
                        if(jsonVolumeInfo.has("authors")){
                            JSONArray authors = jsonVolumeInfo.getJSONArray("authors");
                            for(int a = 0 ; a < authors.length() ; a++){
                                volumeInfo.addAuthor(authors.get(a).toString());
                            }
                        }

                        // Get the buy link
                        if (jsonSaleInfo.has("buyLink"))
                            saleInfo.setmBuyLink(jsonSaleInfo.getString("buyLink"));
                        else
                            saleInfo.setmBuyLink("");

                        // Get the image links
                        if(jsonVolumeInfo.has("imageLinks")){
                            if(jsonVolumeInfo.getJSONObject("imageLinks").has("smallThumbnail")){
                                volumeInfo.setmSmallThumbnail(jsonVolumeInfo.getJSONObject("imageLinks").getString("smallThumbnail"));
                            } else{
                                volumeInfo.setmSmallThumbnail("");
                            }

                            if(jsonVolumeInfo.getJSONObject("imageLinks").has("thumbnail")){
                                volumeInfo.setmThumbnail(jsonVolumeInfo.getJSONObject("imageLinks").getString("thumbnail"));
                            } else {
                                volumeInfo.setmThumbnail("");
                            }
                        }

                        // Get the ISBN codes
                        if(jsonVolumeInfo.has("industryIdentifiers")){
                            JSONArray isbn = null;
                            try {
                                isbn = jsonVolumeInfo.getJSONArray("industryIdentifiers");
                                for(int n = 0 ; n < isbn.length() ; n++){
                                    JSONObject obj = isbn.getJSONObject(n);
                                    if(obj.get("type").toString().equals("ISBN_13")){
                                        volumeInfo.setmIsbn13(obj.getString("identifier"));
                                    } else if(obj.get("type").toString().equals("ISBN_10")){
                                        volumeInfo.setmIsbn10(obj.getString("identifier"));
                                    }
                                }
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }

                        }

                        book.setVolumeInfo(volumeInfo);
                        book.setSaleInfo(saleInfo);

//                        Book item = new Book(
//                                hit.getString("webformatURL"), hit.getString("user"),
//                                hit.getInt("likes"));

                        mBooks.add(book);

                    }
                    mAdapter = new BookAdapter(getApplicationContext(), mBooks);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.setOnItemClickListener(MainActivity.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }
        );
        mRequestQueue.add((request));
    }

    @Override
    public void OnItemClick(int position) {
        Intent detailIntent = new Intent(this, DetailActivity.class);
        Book book = mBooks.get(position);
        detailIntent.putExtra(EXTRA_URL, book.getVolumeInfo().getmTitle());
        detailIntent.putExtra(EXTRA_CREATOR, book.getVolumeInfo().getmDescription());
        detailIntent.putExtra(EXTRA_LIKES, book.getVolumeInfo().getmPageCount());
        startActivity(detailIntent);
    }
}
