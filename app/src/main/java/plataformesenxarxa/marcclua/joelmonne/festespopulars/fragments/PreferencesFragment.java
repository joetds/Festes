package plataformesenxarxa.marcclua.joelmonne.festespopulars.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import plataformesenxarxa.marcclua.joelmonne.festespopulars.R;

public class PreferencesFragment extends PreferenceFragment {
    public static PreferencesFragment getInstance() {
        return new PreferencesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
