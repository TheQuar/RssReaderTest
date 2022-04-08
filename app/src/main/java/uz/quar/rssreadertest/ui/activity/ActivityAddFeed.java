package uz.quar.rssreadertest.ui.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.prof.rssparser.Channel;
import com.prof.rssparser.Image;
import com.prof.rssparser.OnTaskCompleted;
import com.prof.rssparser.Parser;

import uz.quar.rssreadertest.R;
import uz.quar.rssreadertest.db.AppDatabase;
import uz.quar.rssreadertest.db.feeds.FeedArticles;
import uz.quar.rssreadertest.db.feeds.FeedListDao;
import uz.quar.rssreadertest.utils.FeedHelper;
import uz.quar.rssreadertest.utils.MyDialog;
import uz.quar.rssreadertest.viewmodel.NewsListViewModel;

public class ActivityAddFeed extends AppCompatActivity {
    private EditText editText;
    private ProgressDialog progressDialog;
    private FeedListDao feedListDao;
    NewsListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feed);

        feedListDao = AppDatabase.getInstance(getBaseContext()).feedListDao();
        viewModel = new ViewModelProvider(this).get(NewsListViewModel.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        editText = findViewById(R.id.rss_url_txt);
        findViewById(R.id.add_feed_btn).setOnClickListener(v -> {
            if (!editText.getText().toString().isEmpty()) {
                progressDialog.show();
                checkFeed(FeedHelper.convertUrlToFormat(editText.getText().toString()));
            } else MyDialog.show(ActivityAddFeed.this, "Error", "Please enter feed url");
        });
    }


    private synchronized void checkFeed(String url) {
        Parser parser = new Parser.Builder()
//                .charset(Charset.forName("ISO-8859-7"))
                // .cacheExpirationMillis() and .context() not called because on Java side, caching is NOT supported
                .build();

        parser.onFinish(new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(Channel channel) {
                addFeed(url, channel);
                progressDialog.dismiss();
            }

            @Override
            public void onError(Exception e) {
                progressDialog.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        editText.getText().clear();
                        MyDialog.show(ActivityAddFeed.this, "Error", "Please enter valid feed url");
                    }
                });
            }
        });
        parser.execute(url);
    }

    private void addFeed(String feedUrl, Channel channel) {
        if (!isHave(feedUrl)) {
            feedListDao.insert(new FeedArticles(
                    channel.getTitle(),
                    feedUrl,
                    channel.getLink(),
                    checkImageUrl(channel.getImage()),
                    channel.getDescription()));
            viewModel.refresh();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showDialog("Success", "Feed added successfully");
                }
            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    editText.getText().clear();
                    MyDialog.show(ActivityAddFeed.this, "Error", "This feed is already exist");
                }
            });

        }
    }

    private boolean isHave(String feedUrl) {
        boolean isHave = false;
        for (FeedArticles feedArticles : feedListDao.getAllFeedsNow()) {
            if (feedArticles.getFeedUrl().equals(feedUrl)) {
                isHave = true;
                break;
            }
        }
        return isHave;
    }

    private String checkImageUrl(Image image) {
        if (image != null)
            if (image.getLink() != null)
                return image.getLink();
            else if (image.getUrl() != null)
                return image.getUrl();
            else return "";
        else return "";
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void showDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", (dialog, which) -> {
            dialog.dismiss();
            finish();
        });
        builder.show();
    }
}