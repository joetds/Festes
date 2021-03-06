package plataformesenxarxa.marcclua.joelmonne.festespopulars.fragments;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.festespopulars.backend.festespopularsAPI.FestespopularsAPI;
import com.example.festespopulars.backend.festespopularsAPI.model.EventBean;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.List;

import plataformesenxarxa.marcclua.joelmonne.festespopulars.R;
import plataformesenxarxa.marcclua.joelmonne.festespopulars.models.Event;

public class MapsFragment extends Fragment {
    private MapFragment map;
    private boolean allEvents;

    public static MapsFragment getInstance() {
        return getInstance(true);
    }

    public static MapsFragment getInstance(boolean allEvents) {
        MapsFragment fragment = new MapsFragment();
        fragment.allEvents = allEvents;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourites, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        map = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        putFavouritesEvents();
    }

    private void putFavouritesEvents() {
        new AsyncTask<Void, Void, List<EventBean>>() {
            @Override
            protected List<EventBean> doInBackground(Void... params) {
                FestespopularsAPI.Builder builder = new FestespopularsAPI.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null);
                FestespopularsAPI api = builder.build();
                try {
                    return api.getAllEvents().execute().getItems();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(final List<EventBean> eventBeen) {
                super.onPostExecute(eventBeen);
                if (eventBeen != null)
                    map.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            for (EventBean eventBean : eventBeen) {
                                if (allEvents || eventBean.getFavourite())
                                    googleMap.addMarker(new MarkerOptions()
                                            .position(Event.getLocationAsLatlng(eventBean.getLocation()))
                                            .title(eventBean.getName() + "(" + eventBean.getPlace() + ")")
                                            .snippet(eventBean.getDate()));
                            }
                        }
                    });

            }
        }.execute();
    }
}
