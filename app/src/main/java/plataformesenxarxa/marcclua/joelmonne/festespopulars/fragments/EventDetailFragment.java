package plataformesenxarxa.marcclua.joelmonne.festespopulars.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import plataformesenxarxa.marcclua.joelmonne.festespopulars.R;
import plataformesenxarxa.marcclua.joelmonne.festespopulars.models.Event;

public class EventDetailFragment extends Fragment {

    private TextView title;
    private TextView date;
    private TextView description;
    private TextView place;
    private Event event;

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
        FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.add_favourites);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, getString(R.string.create_button_click), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        if (event != null) update(event);
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void update(Event event) {

        title.setText(event.getName());
        date.setText(event.getDate());
        description.setText(event.getDescription());
        place.setText(event.getPlace());
    }
}
