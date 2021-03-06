package org.usfirst.frc.team25.scouting.ui.preferences;


import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import org.usfirst.frc.team25.scouting.Constants;
import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.FileManager;
import org.usfirst.frc.team25.scouting.data.Settings;
import org.usfirst.frc.team25.scouting.data.thebluealliance.DataDownloader;
import org.usfirst.frc.team25.scouting.ui.views.DecimalPickerPreference;
import org.usfirst.frc.team25.scouting.ui.views.NumberPickerPreference;

/** List of preferences, as defined in res/xml/preferences.xml
 *
 */

public class SettingsFragment extends PreferenceFragment {

    private ListPreference matchType;
    private ListPreference event;
    private ListPreference leftStation;
    private Preference deleteFiles;
    private Preference changePass;
    private Preference year;
    private Preference downloadSchedule;
    private Preference game;
    private Preference version; // Buttons that hold a value, but do not prompt a dialogue
    private NumberPickerPreference matchNum;
    private NumberPickerPreference shiftDur;
    private DecimalPickerPreference timerManualInc;
    private EditTextPreference scoutNameInput;
    CheckBoxPreference useTeamList;
    private Settings set;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        set = Settings.newInstance(getActivity());

        shiftDur = (NumberPickerPreference) findPreference("shift_dur");
        scoutNameInput = (EditTextPreference) findPreference("scout_name");
        matchType = (ListPreference) findPreference("match_type");
        matchNum = (NumberPickerPreference) findPreference("match_num");
        event = (ListPreference) findPreference("event");
        deleteFiles = findPreference("delete_data");
        changePass =  findPreference("change_pass");
        year = findPreference("year");
        downloadSchedule = findPreference("download_schedule");
        game = findPreference("game");
        version = findPreference("version");
        leftStation = (ListPreference) findPreference("leftStation");
        timerManualInc = (DecimalPickerPreference) findPreference("timer_manual_inc");

        updateSummary();

        game.setSummary(Constants.GAME_NAME);
        version.setSummary("v"+Constants.VERSION_NUMBER);

        matchNum.setMaxValue(Settings.newInstance(getActivity()).getMaxMatchNum());
        shiftDur.setMaxValue(25);


        event.setOnPreferenceChangeListener((preference, o) -> {
            matchNum = (NumberPickerPreference) findPreference("match_num");
            matchNum.setValue(1); //Resets match number

            return true;// A false value means the preference change is not saved
        });



        matchType.setOnPreferenceChangeListener((preference, o) -> {
            matchNum = (NumberPickerPreference) findPreference("match_num");
            matchNum.setValue(1);
            return true;
        });


        deleteFiles.setOnPreferenceClickListener(preference -> {
            if(set.getHashedPass().equals("DEFAULT"))
                Toast.makeText(getActivity(), "Password needs to be set before deleting data", Toast.LENGTH_SHORT ).show();

            else {
                Intent i = new Intent(getActivity(), EnterPasswordActivity.class);
                startActivity(i);
            }

            return true;
        });


        changePass.setOnPreferenceClickListener(preference -> {
            Intent i;
            if(set.getHashedPass().equals("DEFAULT"))
                i = new Intent(getActivity(), SetPasswordActivity.class);

            else i = new Intent(getActivity(), ConfirmPasswordActivity.class);

            startActivity(i);

            return true;
        });

        year.setOnPreferenceClickListener(preference -> {
            Toast.makeText(getActivity(), "Year automatically updated", Toast.LENGTH_SHORT).show();
            return true;
        });




        downloadSchedule.setOnPreferenceClickListener(preference -> {
            new DataDownloader(getActivity()).execute();

            return true;
        });

    }



    /**
     * Updates preference summary text with their values
     */
    void updateSummary(){
        try {
            Settings.newInstance(getActivity()).setMaxMatchNum(FileManager.getMaxMatchNum(getActivity())); //Automates maximum match number based on current event
            shiftDur.setSummary(String.valueOf(set.getShiftDur()) + " matches");
            scoutNameInput.setSummary(set.getScoutName());
            matchNum.setSummary(String.valueOf(set.getMatchNum()));
            year.setSummary(set.getYear());
            set.setYear();
            leftStation.setSummary(leftStation.getValue());
            timerManualInc.setSummary(set.getTimerManualInc() + " sec");
        }catch(NullPointerException e){
            e.printStackTrace();
        }

    }


}
