package plataformesenxarxa.marcclua.joelmonne.festespopulars.fragments;

import android.app.ListFragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
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

public class ResultSearchFragment extends ListFragment {

    private Context context;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getActivity();
        String search = getActivity().getIntent().getStringExtra("search");
        final ListView listView = getListView();

        new AsyncTask<String, Void, List<EventBean>>() {
            @Override
            protected List<EventBean> doInBackground(String... params) {
                FestespopularsAPI.Builder builder = new FestespopularsAPI.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null);
                FestespopularsAPI api = builder.build();
                try {
                    return api.getEventByPlace(params[0].toLowerCase()).execute().getItems();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<EventBean> eventBeen) {
                setListShown(true);
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
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
