package com.androidstrike.quizease.fragments;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.androidstrike.quizease.R;
import com.androidstrike.quizease.SubHomeFragment;
import com.androidstrike.quizease.TestPendingFragment;
import com.androidstrike.quizease.ui.HomeActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    ViewPager viewPager = null;
    HomeActivity homeActivity;
    Toolbar toolbar;
    ActionBar actionBar;

    public Home() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        viewPager = view.findViewById(R.id.pager);
//        toolbar = view.findViewById(R.id.tool_bar);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setCustomView(toolbar);
        TabLayout tabLayout = view.findViewById(R.id.tab_title);

        tabLayout.addTab(tabLayout.newTab().setText("Overview"));
        tabLayout.addTab(tabLayout.newTab().setText("Pending Tests"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(getFragmentManager(),tabLayout.getTabCount());
//        FragmentManager fragmentManager = getFragmentManager();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;

    }
}

class MyViewPagerAdapter extends FragmentStatePagerAdapter {

    int mNumofTabs;
    public MyViewPagerAdapter(@NonNull FragmentManager fm, int NoofTabs) {
        super(fm);
        this.mNumofTabs = NoofTabs;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                SubHomeFragment subHomeFragment = new SubHomeFragment();
                return subHomeFragment;
            case 1:
                TestPendingFragment testPendingFragment = new TestPendingFragment();

                return testPendingFragment;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return mNumofTabs;
    }

}
