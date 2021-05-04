package edu.gatech.seclass.jobcompare6300;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    //    JobDatabase jobDatabase;
    DataHandler dataHandler;

    //UI button
    private Button StartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataHandler = new DataHandler(getApplicationContext());

        // Load CSV Data
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                dataHandler.TryLoadCostOfLivingTable();
            };
        });

//        final ComparisionSettings comparisionSettings = dataHandler.CreateNewComparisionSettings();
//        comparisionSettings.bonusWeight = 1;
//        comparisionSettings.leaveTimeWeight = 1;
//        comparisionSettings.retirementBenefitWeight = 1;
//        comparisionSettings.salaryWeight = 1;
//        comparisionSettings.teleworkDaysWeight = 1;


        //switch the page to main menu when click "Start"
        //find start button
        StartButton = findViewById(R.id.StartButton);
        //switch viewer
        StartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                intent = new Intent(MainActivity.this, UIMainMenu.class);
                startActivity(intent);
            }
        });
    }
}