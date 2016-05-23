package plataformesenxarxa.marcclua.joelmonne.festespopulars.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.festespopulars.backend.festespopularsAPI.FestespopularsAPI;
import com.example.festespopulars.backend.festespopularsAPI.model.EventBean;
import com.example.festespopulars.backend.messaging.Messaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.Calendar;

import plataformesenxarxa.marcclua.joelmonne.festespopulars.R;
import plataformesenxarxa.marcclua.joelmonne.festespopulars.activities.MapsActivity;
import plataformesenxarxa.marcclua.joelmonne.festespopulars.activities.ResultSearchActivity;
import plataformesenxarxa.marcclua.joelmonne.festespopulars.models.Event;

public class SearchFragment extends Fragment {

    private Context context;

    public static SearchFragment getInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        final Button button_search = (Button) getView().findViewById(R.id.boto_cerca);
        button_search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText text = (EditText) getView().findViewById(R.id.search_text);
                String searchText = text.getText().toString();
                if (!searchText.equals("")) {
                    Intent intent = new Intent(context, ResultSearchActivity.class);
                    intent.putExtra("search", searchText);
                    startActivity(intent);
                } else {
                    showToast("Camp de busqueda buit");
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.add_event_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_create_event, null);
                final AlertDialog dialog = new AlertDialog.Builder(context)
                        .setView(dialogView).create();
                dialogView.findViewById(R.id.date).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        final Calendar c = Calendar.getInstance();
                        int year = c.get(Calendar.YEAR);
                        int month = c.get(Calendar.MONTH);
                        int day = c.get(Calendar.DAY_OF_MONTH);
                        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                ((TextView) v).setText(dayOfMonth + "/" + monthOfYear + "/" + year);
                            }
                        }, year, month, day).show();
                    }
                });
                dialogView.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (goMap(dialogView)) {
                            dialog.dismiss();
                        }
                    }
                });
                dialogView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        getView().findViewById(R.id.test_gcm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        Messaging.Builder builder = new Messaging.Builder(AndroidHttp.newCompatibleTransport(),
                                new AndroidJsonFactory(), null);
                        Messaging messaging = builder.build();
                        try {
                            messaging.messagingEndpoint().sendMessage("Ja funcionaaaaaa!!!").execute();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute();
            }
        });
    }

    private boolean goMap(View eventView) {
        Event event = checkFields(eventView);
        if (event != null) {
            Intent intent = new Intent(getActivity(), MapsActivity.class);
            intent.putExtra(Event.event_key, event);
            startActivityForResult(intent, 1);
            return true;
        } else {
            showToast("Check empty fields");
        }
        return false;
    }

    private Event checkFields(View eventView) {
        Event event = new Event();

        String name = ((TextView) eventView.findViewById(R.id.name)).getText().toString();
        if (name.equals("")) return null;
        event.setName(name);

        String description = ((TextView) eventView.findViewById(R.id.description)).getText().toString();
        if (description.equals("")) event.setDescription("No hi ha descripció per aquest event");
        else event.setDescription(description);

        String place = ((TextView) eventView.findViewById(R.id.place)).getText().toString();
        if (place.equals("")) return null;
        event.setPlace(place);

        String date = ((TextView) eventView.findViewById(R.id.date)).getText().toString();
        if (date.equals("")) return null;
        event.setDate(date);

        return event;
    }

    private void showToast(String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            Event event = (Event) data.getSerializableExtra(Event.event_key);
            saveEvent(event);
        }
    }

    private void saveEvent(Event event) {
        new AsyncTask<EventBean, Void, Void>() {
            @Override
            protected Void doInBackground(EventBean... params) {
                FestespopularsAPI.Builder builder = new FestespopularsAPI.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null);
                FestespopularsAPI api = builder.build();
                try {
                    api.storeEvent(params[0]).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Snackbar.make(getView(), getString(R.string.event_saved), Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new AsyncTask<Void, Void, Void>() {
                                    @Override
                                    protected Void doInBackground(Void... params) {
                                        Messaging.Builder builder = new Messaging.Builder(AndroidHttp.newCompatibleTransport(),
                                                new AndroidJsonFactory(), null);
                                        Messaging messaging = builder.build();
                                        try {
                                            messaging.messagingEndpoint().sendMessage("Ja funcionaaaaaa!!!").execute();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        return null;
                                    }
                                }.execute();
                            }
                        }).show();
            }
        }.execute(Event.eventToEventBean(event));
    }
}
