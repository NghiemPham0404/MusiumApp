package com.example.musiumproject.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.view.View;
import android.widget.Toast;

import com.example.musiumproject.R;
import com.example.musiumproject.View.mainActivityFragments.HomeFragment;
import com.example.musiumproject.View.mainActivityFragments.LibraryFragment;
import com.example.musiumproject.View.mainActivityFragments.SearchFragment;
import com.example.musiumproject.viewmodels.TrackViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Timer;

public class MainWindow extends AppCompatActivity {

    HomeFragment homeFragment;
    LibraryFragment libraryFragment;
    SearchFragment searchFragment;

    ViewPager viewPager;

    private int alert_out = 1;
    private static Handler handler;
    private static Runnable runnable;
    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);

        TrackViewModel trackViewModel = new ViewModelProvider(this).get(TrackViewModel.class);
        initializeFragments();
        initComponents();
    }

    public void initComponents(){
        navigationView = findViewById(R.id.bottomNavigationBar);
        viewPager = findViewById(R.id.viewPager);
        navigationView.setItemIconTintList(null);

        viewPager.setAdapter(new HomePagerAdapter(getSupportFragmentManager(), homeFragment, libraryFragment, searchFragment));

        navigationView.setOnNavigationItemSelectedListener( item -> {
            if (item.getItemId() == R.id.home_nav) {
                viewPager.setCurrentItem(0);
            } else if(item.getItemId() == R.id.explore_nav) {
                viewPager.setCurrentItem(1);
            } else if (item.getItemId() == R.id.library_nav) {
                viewPager.setCurrentItem(2);
            }
            return true;
        });

        navigationView.setOnNavigationItemReselectedListener(item -> {
            if (item.getItemId() == R.id.home_nav) {
                viewPager.setCurrentItem(0);
            } else if(item.getItemId() == R.id.explore_nav) {
                viewPager.setCurrentItem(1);
            } else if (item.getItemId() == R.id.library_nav) {
                viewPager.setCurrentItem(2);
            }
        });
    }

    public void initializeFragments(){
        homeFragment =  HomeFragment.newInstance();
        libraryFragment = LibraryFragment.newInstance();
        searchFragment = SearchFragment.newInstance();
    }

    @Override
    public void onBackPressed() {
        if(alert_out == 1){
            Toast.makeText(this, "press again to exit app", Toast.LENGTH_SHORT).show();
            alert_out--;
            handler = new Handler();
            runnable = new Runnable() {
                @Override
                public void run() {
                    alert_out ++;
                }
            };
            handler.postDelayed(runnable, 3000);
        }else{
            super.onBackPressed();
            moveTaskToBack(true);
            Process.killProcess(Process.myPid());
            System.exit(1);
        }
    }
    private static class HomePagerAdapter extends FragmentPagerAdapter {
        HomeFragment homeFragment;
        LibraryFragment libraryFragment;
        SearchFragment searchFragment;

        public HomePagerAdapter(@NonNull FragmentManager fm, HomeFragment homeFragment, LibraryFragment libraryFragment, SearchFragment searchFragment) {
            super(fm);
            this.homeFragment = homeFragment;
            this.libraryFragment = libraryFragment;
            this.searchFragment = searchFragment;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0 : return homeFragment;
                case 1  : return  libraryFragment;
                case 2 : return  searchFragment;
                default : return  null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}