package plataformesenxarxa.marcclua.joelmonne.festespopulars.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.festespopulars.backend.festespopularsAPI.FestespopularsAPI;
import com.example.festespopulars.backend.festespopularsAPI.model.EventBean;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

import plataformesenxarxa.marcclua.joelmonne.festespopulars.R;
import plataformesenxarxa.marcclua.joelmonne.festespopulars.models.Event;

public class EventDetailFragment extends Fragment {

    private TextView title;
    private TextView date;
    private TextView description;
    private TextView place;
    private Event event;
    private TextView favourite;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title = (TextView) getView().findViewById(R.id.title);
        date = (TextView) getView().findViewById(R.id.date);
        description = (TextView) getView().findViewById(R.id.description);
        place = (TextView) getView().findViewById(R.id.place);
        favourite = (TextView) getView().findViewById(R.id.favourite);
        FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.add_favourites);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (event != null) {
                    event.setFavourite(!event.getFavourite());
                    updateEventInCloud(event);
                }
            }
        });
        if (event != null) update(event);
    }

    private void updateEventInCloud(final Event event) {
        new AsyncTask<Event, Void, Void>() {
            @Override
            protected Void doInBackground(Event... params) {
                FestespopularsAPI.Builder builder = new FestespopularsAPI.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null);
                FestespopularsAPI api = builder.build();
                try {
                    EventBean eventBean = Event.eventToEventBean(event);
                    api.storeEvent(eventBean).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                update(event);
            }
        }.execute(event);
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void update(Event event) {

        title.setText(event.getName());
        date.setText(event.getDate());
        description.setText(event.getDescription());
        place.setText(event.getPlace());
        favourite.setText(getString(R.string.its_favourite)+ " " + event.getFavourite());
    }
}
