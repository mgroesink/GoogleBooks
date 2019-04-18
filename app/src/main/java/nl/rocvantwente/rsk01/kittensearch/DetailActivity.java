package nl.rocvantwente.rsk01.kittensearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static nl.rocvantwente.rsk01.kittensearch.MainActivity.EXTRA_CREATOR;
import static nl.rocvantwente.rsk01.kittensearch.MainActivity.EXTRA_LIKES;
import static nl.rocvantwente.rsk01.kittensearch.MainActivity.EXTRA_URL;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_details);
        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_URL);
        String creatorName = intent.getStringExtra(EXTRA_CREATOR);
        int likesCount = intent.getIntExtra(EXTRA_LIKES , 0);

        ImageView imageView = (ImageView)findViewById(R.id.cover_view);
        TextView textViewCreator = (TextView)findViewById(R.id.text_view_title);
        TextView textViewLikes = (TextView)findViewById(R.id.text_view_pages);

        textViewCreator.setText(creatorName);
        textViewLikes.setText("Likes: " + likesCount);
        Picasso.with(this).load(imageUrl).fit()
                .centerInside().into(imageView);
    }
}
