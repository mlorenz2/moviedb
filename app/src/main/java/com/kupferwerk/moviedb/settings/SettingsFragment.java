package com.kupferwerk.moviedb.settings;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.kupferwerk.moviedb.R;

public class SettingsFragment extends PreferenceFragment
      implements Preference.OnPreferenceChangeListener {

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      addPreferencesFromResource(R.xml.pref_general);
      // For all preferences, attach an OnPreferenceChangeListener so the UI summary can be
      // updated when the preference changes.
      bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_endpoint_key)));
   }

   @Override
   public boolean onPreferenceChange(Preference preference, Object o) {
      String stringValue = o.toString();

      if (preference instanceof ListPreference) {
         // For list preferences, look up the correct display value in
         // the preference's 'entries' list (since they have separate labels/values).
         ListPreference listPreference = (ListPreference) preference;
         int prefIndex = listPreference.findIndexOfValue(stringValue);
         if (prefIndex >= 0) {
            CharSequence prefValue = listPreference.getEntries()[prefIndex];
            preference.setSummary(prefValue);
         }
      } else {
         // For other preferences, set the summary to the value's simple string representation.
         preference.setSummary(stringValue);
      }
      return true;
   }

   private void bindPreferenceSummaryToValue(Preference preference) {
      // Set the listener to watch for value changes.
      preference.setOnPreferenceChangeListener(this);

      // Trigger the listener immediately with the preference's
      // current value.
      onPreferenceChange(preference,
            PreferenceManager.getDefaultSharedPreferences(preference.getContext())
                  .getString(preference.getKey(), ""));
   }
}
