package com.company.android.languageapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.settings));
        setContentView(R.layout.activity_settings);

        final Spinner langSpinner = (Spinner) findViewById(R.id.lang_spinner);

        // Apply the adapter to the spinner
        langSpinner.setSelection(getIndexOfLang());
        langSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            boolean start = false;
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (start) {
                    String item = langSpinner.getItemAtPosition(i).toString().toLowerCase();
                    setNewLocale(item);

                }
                start = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
    }

    private int getIndexOfLang() {
        String locale = LocaleManager.getLocale(getResources()).getLanguage().toUpperCase();
        String [] allLocales = getResources().getStringArray(R.array.lang_options);
        int index = 0;
        for (int i = 0; i < allLocales.length; i++) {
            if (allLocales[i].equals(locale)) {
                index = i;
                break;
            }
        }
        return index;
    }

    private void setNewLocale(String language) {
        LocaleManager.setNewLocale(this, language);
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
