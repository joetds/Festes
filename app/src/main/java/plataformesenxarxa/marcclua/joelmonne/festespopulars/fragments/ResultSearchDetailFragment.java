package plataformesenxarxa.marcclua.joelmonne.festespopulars.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;

import plataformesenxarxa.marcclua.joelmonne.festespopulars.R;
import plataformesenxarxa.marcclua.joelmonne.festespopulars.models.Event;

public class ResultSearchDetailFragment extends Fragment {
    private Event event;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_result_search_detail, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MyAdapter myAdapter = new MyAdapter(getActivity().getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        ViewPager mViewPager = (ViewPager) getView().findViewById(R.id.container);
        mViewPager.setAdapter(myAdapter);

        TabLayout tabLayout = (TabLayout) getView().findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    EventDetailFragment fragment = new EventDetailFragment();
                    fragment.setEvent(event);
                    return fragment;
                case 1:
                    SupportMapFragment mapFragment = SupportMapFragment.newInstance();
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            googleMap.addMarker(new MarkerOptions()
                                    .position(event.getLocationAsLatlng())
                                    .title(event.getName())
                                    .snippet(event.getDate()));
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(event.getLocationAsLatlng(), 7));
                        }
                    });
                    return mapFragment;
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.event_tittle);
                case 1:
                    return getString(R.string.map_tittle);
            }
            return "";
        }
    }
}
