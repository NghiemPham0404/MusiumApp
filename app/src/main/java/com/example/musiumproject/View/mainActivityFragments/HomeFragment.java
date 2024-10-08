package com.example.musiumproject.View.mainActivityFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musiumproject.R;
import com.example.musiumproject.View.interfaceForView.ViewInit;
import com.example.musiumproject.adapters.AlbumAdapter;
import com.example.musiumproject.adapters.TrackAdapter;
import com.example.musiumproject.models.Album;
import com.example.musiumproject.models.Track;
import com.example.musiumproject.util.TrackPlayingType;
import com.example.musiumproject.viewmodels.AlbumViewModel;
import com.example.musiumproject.viewmodels.TrackViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    RecyclerView trackRecyclerView, albumRecyclerView;
    private TrackViewModel trackViewModel;
    private AlbumViewModel albumViewModel;
    private TrackAdapter trackAdapter;
    private AlbumAdapter albumAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        trackViewModel = new ViewModelProvider(this).get(TrackViewModel.class);
        albumViewModel = new ViewModelProvider(this).get(AlbumViewModel.class);
        observeAnyChanges();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        initComponents(view);
        initFeatures();
        return view;
    }

    public void observeAnyChanges(){
        if(trackViewModel!=null){
           trackViewModel.getNewestTracks().observe(this, tracks -> {
               if(tracks!= null){
                   trackAdapter.setTracks(tracks);
                   trackAdapter.notifyDataSetChanged();
               }
           });
        }
        if(albumViewModel!=null){
            albumViewModel.getLiveNewestAlbums().observe(this, albums -> {
                if(albums!=null){
                    albumAdapter.setAlbums(albums);
                    albumAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    public void initComponents(View view){
        trackRecyclerView = view.findViewById(R.id.trackRecyclerView);
        albumRecyclerView = view.findViewById(R.id.albumRecyclerView);
        configureRecyclerViews();
    }

    public void initFeatures(){
        trackViewModel.listNewestTracks(1);
        albumViewModel.listNewestAlbums(1);
    }
    public void configureRecyclerViews(){
        trackRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        trackAdapter = new TrackAdapter(getContext(), TrackPlayingType.newest);
        trackRecyclerView.setAdapter(trackAdapter);

        trackRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!trackRecyclerView.canScrollHorizontally(1)) {
                    trackViewModel.listNewestTracksNext();
                }
            }
        });

        albumRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        albumAdapter = new AlbumAdapter(getContext());
        albumRecyclerView.setAdapter(albumAdapter);
    }
}