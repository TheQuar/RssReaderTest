package uz.quar.rssreadertest.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.xml.sax.XMLReader;

import uz.quar.rssreadertest.R;
import uz.quar.rssreadertest.db.news.NewsArticles;

public class ActivityDetails extends AppCompatActivity {

    NewsArticles newsArticles = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        newsArticles = (NewsArticles) getIntent().getSerializableExtra("data");

        ImageView imageView = findViewById(R.id.imageView);
        TextView txt_FeedName = findViewById(R.id.txt_FeedName);
        TextView txt_FeedTitle = findViewById(R.id.txt_ReadTitle);
        TextView txt_ReadBody = findViewById(R.id.txt_ReadBody);

        if (newsArticles != null) {
            Picasso.get().load(newsArticles.getImage()).error(R.drawable.placeholder).into(imageView);
            txt_FeedName.setText(newsArticles.getSourceName());
            txt_FeedTitle.setText(newsArticles.getTitle());
            txt_ReadBody.setText(HtmlCompat.fromHtml(
                    newsArticles.getDescription(),
                    HtmlCompat.FROM_HTML_MODE_LEGACY,
                    new MyImageGetter(getBaseContext()),
                    new MyHTMLTagHandler()));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    class MyImageGetter implements Html.ImageGetter {
        Context context;

        public MyImageGetter(Context context) {
            this.context = context;
        }

        @Override
        public Drawable getDrawable(String s) {
            return ContextCompat.getDrawable(context, android.R.color.transparent);
        }
    }

    class MyHTMLTagHandler implements Html.TagHandler {
        @Override
        public void handleTag(boolean b, String s, Editable editable, XMLReader xmlReader) {

        }
    }
}