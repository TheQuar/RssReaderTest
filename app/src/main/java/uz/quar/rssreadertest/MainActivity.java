package uz.quar.rssreadertest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;


import java.util.ArrayList;
import java.util.List;

import uz.quar.rssreadertest.adapter.ViewPagerAdapter;
import uz.quar.rssreadertest.db.feeds.FeedArticles;
import uz.quar.rssreadertest.ui.activity.ActivityAddFeed;
import uz.quar.rssreadertest.utils.Utils;
import uz.quar.rssreadertest.viewmodel.FeedsListViewModel;
import uz.quar.rssreadertest.viewmodel.NewsListViewModel;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationView;
    private ViewPager2 viewPager;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setUserInputEnabled(false);


        if (!Utils.isFirstOpen(this)) {
            List<FeedArticles> all_feeds = new ArrayList<>();
            all_feeds.add(new FeedArticles("NYT > Top Stories", "http://feeds.nytimes.com/nyt/rss/homepage", "https://www.nytimes.com", "https://www.nytimes.com", ""));
            all_feeds.add(new FeedArticles("Android Authority", "http://androidauthority.com/feed", "https://www.androidauthority.com", "", "Android News, Reviews, How To"));
            all_feeds.add(new FeedArticles("CNN.com - RSS Channel - HP Hero", "http://rss.cnn.com/rss/cnn_topstories.rss", "https://www.cnn.com/index.html", "https://www.cnn.com/index.html", "CNN.com delivers up-to-the-minute news and information on the latest top stories, weather, entertainment, politics and more."));

            FeedsListViewModel viewModel = new ViewModelProvider(this).get(FeedsListViewModel.class);
            viewModel.insert(all_feeds);
            Utils.writeFirstOpen(this);

        }

        setUpViewPager();


    }

    private void setUpViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.news:
                viewPager.setCurrentItem(0);
                toolbar.setTitle(getResources().getString(R.string.menu1));
                toolbar.getMenu().getItem(0).setVisible(false);
                return true;
            case R.id.my_feeds:
                viewPager.setCurrentItem(1);
                toolbar.setTitle(getResources().getString(R.string.menu2));
                toolbar.getMenu().getItem(0).setVisible(true);
                return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.getItem(0).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_feeds:
                startActivity(new Intent(this, ActivityAddFeed.class));
                break;
        }
        return super.onOptionsItemSelected(item);

    }
}