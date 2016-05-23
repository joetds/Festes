package plataformesenxarxa.marcclua.joelmonne.festespopulars.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import plataformesenxarxa.marcclua.joelmonne.festespopulars.R;
import plataformesenxarxa.marcclua.joelmonne.festespopulars.fragments.ResultSearchDetailFragment;
import plataformesenxarxa.marcclua.joelmonne.festespopulars.fragments.ResultSearchFragment;
import plataformesenxarxa.marcclua.joelmonne.festespopulars.models.Event;

public class ResultSearchActivity extends AppCompatActivity implements ResultSearchFragment.OnEventClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onEventClick(Event event) {
        ResultSearchDetailFragment fragment = (ResultSearchDetailFragment) getSupportFragmentManager().findFragmentById(R.id.detail);
        if (fragment != null && fragment.isInLayout()) {
            fragment.setEvent(event);
        } else {
            Intent intent = new Intent(ResultSearchActivity.this, ResultSearchDetailActivity.class);
            intent.putExtra(Event.event_key, event);
            startActivity(intent);
        }
    }
}
