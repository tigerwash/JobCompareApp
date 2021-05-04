package edu.gatech.seclass.jobcompare6300;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class UIMainMenu extends AppCompatActivity {
//    boolean isAbleToCompare;
    //initialize buttons
    Button CurrentJob;
    Button AddOffer;
    Button Settings;
    Button Compare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
//        isAbleToCompare = false;




        //find buttons
        CurrentJob = findViewById(R.id.CurrentJob);
        AddOffer = findViewById(R.id.AddOffer);
        Settings = findViewById(R.id.Settings);
        Compare = findViewById(R.id.Compare);
        final boolean[] enabled = new boolean[1];
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if(DataHandler.GetDataHandlerInstance().GetJobsCount()>1){
                    enabled[0] = true;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Compare.setEnabled(enabled[0]);
                    }
                });
            }

        });
//        setAbleToCompare(Compare);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                final UIMainMenu.isAbleToCompare task = new UIMainMenu.isAbleToCompare();
//                task.execute();
//            }
//        }).start();
        System.out.println("compare is:"+Compare.isEnabled());
        // Load CSV Data

        //switch viewer
        CurrentJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                setAbleToCompare(Compare);
                Intent intent = null;
                intent = new Intent(UIMainMenu.this, UICurrentJobEditor.class);
                startActivity(intent);
            }
        });

        AddOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                setAbleToCompare(Compare);
                Intent intent = null;
                intent = new Intent(UIMainMenu.this, UIJobOfferEditor.class);
                startActivity(intent);
            }
        });

        Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                setAbleToCompare(Compare);
                Intent intent = null;
                intent = new Intent(UIMainMenu.this, UICompareSettings.class);
                startActivity(intent);
            }
        });

        Compare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                setAbleToCompare(Compare);
                Intent intent = null;
                intent = new Intent(UIMainMenu.this, UIJobsToCompare.class);
                startActivity(intent);
            }
        });
    }
//    private void setAbleToCompare(Button compare){
//        final boolean[] enabled = new boolean[1];
//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//
//                // set currentJob flag to false
//                if(JobService.isAbleToCompare()){
//                    System.out.println("setting compare to true");
//                    enabled[0] = true;
//                    System.out.println("setting compare to:"+ enabled[0]);
//                    compare.setEnabled(enabled[0]);
//                }
//            }
//        });
//
//    }
//    private class isAbleToCompare extends AsyncTask<Void, String, Void> {
//
//        final boolean[] enabled = new boolean[1];
//        @Override
//        protected void onPostExecute(Void result) {
//            System.out.println("setting compare to:"+ enabled[0]);
//            Compare.setEnabled(enabled[0]);
//
//        }
//        @Override
//        protected void onPreExecute() {
//            return;
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//
//            if(DataHandler.GetDataHandlerInstance().GetCurrentJob()!=null){
//                enabled[0] = true;
//            }
//            return null;
//        }
//
//
//    }
}