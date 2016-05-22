package plataformesenxarxa.marcclua.joelmonne.festespopulars.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import plataformesenxarxa.marcclua.joelmonne.festespopulars.R;
import plataformesenxarxa.marcclua.joelmonne.festespopulars.models.Event;

public class ResultSearchDetailFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_result_search_detail, container, false);
    }

    public void update(Event event) {
        Snackbar.make(getView(),event.getName(),Snackbar.LENGTH_LONG).show();
    }
}
