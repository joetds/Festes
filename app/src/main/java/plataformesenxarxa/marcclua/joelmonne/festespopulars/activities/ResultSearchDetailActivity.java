package plataformesenxarxa.marcclua.joelmonne.festespopulars.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import plataformesenxarxa.marcclua.joelmonne.festespopulars.R;
import plataformesenxarxa.marcclua.joelmonne.festespopulars.fragments.ResultSearchDetailFragment;
import plataformesenxarxa.marcclua.joelmonne.festespopulars.models.Event;

public class ResultSearchDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_search_detail);
        Event event = (Event) getIntent().getSerializableExtra(Event.event_key);
        ResultSearchDetailFragment fragment = (ResultSearchDetailFragment) getSupportFragmentManager().findFragmentById(R.id.detail);
        fragment.setEvent(event);
    }
}
