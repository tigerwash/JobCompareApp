package edu.gatech.seclass.jobcompare6300;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

public class UICompareSettings extends AppCompatActivity {

    Button saveJobOffer;
    Button settingReturn;
    SeekBar salaryWeight;
    SeekBar bonusWeight;
    SeekBar teleworkDaysWeight;
    SeekBar retirementBenefitWeight;
    SeekBar leaveTimeWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_compare_settings);

        salaryWeight                = findViewById(R.id.salaryWeightInput);
        bonusWeight                 = findViewById(R.id.bonusWeightInput);
        teleworkDaysWeight          = findViewById(R.id.teldaysWeightInout);
        retirementBenefitWeight     = findViewById(R.id.retireWeightInput);
        leaveTimeWeight             = findViewById(R.id.leavetimeWeightInput);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // get last data and draw here.
                ComparisionSettings weightData = CompareService.GetLastWeightSettings();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(weightData != null) {
                            salaryWeight.setProgress((int)weightData.salaryWeight);
                            bonusWeight.setProgress((int)weightData.bonusWeight);
                            teleworkDaysWeight.setProgress((int)weightData.teleworkDaysWeight);
                            retirementBenefitWeight.setProgress((int)weightData.retirementBenefitWeight);
                            leaveTimeWeight.setProgress((int)weightData.leaveTimeWeight);
                        }
                    }
                });
            }
        });

        //save the current setting to database
        saveJobOffer = findViewById(R.id.saveJobOffer);
        saveJobOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save the settings to database

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        CompareService.saveSettings(
                                bonusWeight.getProgress(),
                                leaveTimeWeight.getProgress(),
                                retirementBenefitWeight.getProgress(),
                                salaryWeight.getProgress(),
                                teleworkDaysWeight.getProgress()
                        );
                    }
                });

                // navigate back home
                startActivity(new Intent(UICompareSettings.this, UIMainMenu.class));
            }
        });

        //switch the page to main menu when click "cancel"
        //find cancel button
        settingReturn = findViewById(R.id.settingReturn);
        settingReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UICompareSettings.this, UIMainMenu.class));
            }
        });

    }
}