package plataformesenxarxa.marcclua.joelmonne.festespopulars.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import plataformesenxarxa.marcclua.joelmonne.festespopulars.R;
import plataformesenxarxa.marcclua.joelmonne.festespopulars.models.Event;

public class ResultSearchDetailFragment extends Fragment {

    private TextView title;
    private TextView date;
    private TextView description;
    private TextView place;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_result_search_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title = (TextView) getView().findViewById(R.id.title);
        date = (TextView) getView().findViewById(R.id.date);
        description = (TextView) getView().findViewById(R.id.description);
        place = (TextView) getView().findViewById(R.id.place);
    }

    public void update(Event event) {
        title.setText(event.getName());
        date.setText(event.getDate());
        description.setText(event.getDescription());
        place.setText(event.getPlace());
    }
}
