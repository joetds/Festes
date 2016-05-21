package plataformesenxarxa.marcclua.joelmonne.festespopulars.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.festespopulars.backend.festespopularsAPI.FestespopularsAPI;
import com.example.festespopulars.backend.festespopularsAPI.model.EventBean;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import plataformesenxarxa.marcclua.joelmonne.festespopulars.R;

public class SearchActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        String search = getIntent().getStringExtra("search");
        final ListView listView = (ListView) findViewById(R.id.list);

        new AsyncTask<String, Void, List<EventBean>>() {
            @Override
            protected List<EventBean> doInBackground(String... params) {
                FestespopularsAPI.Builder builder = new FestespopularsAPI.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null);
                FestespopularsAPI api = builder.build();
                try {
                    return api.getEventByPlace(params[0]).execute().getItems();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<EventBean> eventBeen) {
                if (eventBeen != null) {
                    List<String> list = new ArrayList<>();
                    for (EventBean bean : eventBeen) {
                        list.add(bean.getName());
                    }
                    listView.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, list));
                }
            }
        }.execute(search);
    }

    private void showToast(String text) {
        Toast.makeText(SearchActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}
