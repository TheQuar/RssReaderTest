package uz.quar.rssreadertest.adapter;

import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.ListFragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

import uz.quar.rssreadertest.ui.fragments.FragmentMyFeeds;
import uz.quar.rssreadertest.ui.fragments.FragmentNews;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private List<Fragment> allTabs = new ArrayList<>();

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        allTabs.add(new FragmentNews());
        allTabs.add(new FragmentMyFeeds());
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return allTabs.get(position);
    }

    @Override
    public int getItemCount() {
        return allTabs.size();
    }

}
