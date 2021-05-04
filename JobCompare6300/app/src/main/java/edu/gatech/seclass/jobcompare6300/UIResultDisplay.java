package edu.gatech.seclass.jobcompare6300;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UIResultDisplay extends AppCompatActivity {

    Button resultReturn;
    Button anotherCompare;
    TextView resultsTextView;
    TextView resultsTextView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_result_display);

        String sessionJob1 = getIntent().hasExtra("job1")?getIntent().getStringExtra("job1"):"";
        String sessionJob2 = getIntent().hasExtra("job2")?getIntent().getStringExtra("job2"):"";
        resultsTextView = findViewById(R.id.resultsTextView);
        resultsTextView2 = findViewById(R.id.resultsTextView2);
        if(getIntent().hasExtra("comparison")) {
            String comparison = getIntent().getStringExtra("comparison");
            resultsTextView.setText(comparison);
        }else{
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    // get last data and draw here.
                    Job j1 = CompareService.jobHashtable.get(sessionJob1);
                    Job j2 = CompareService.jobHashtable.get(sessionJob2);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            String fieldTitle =
//                                    "\n" + "\n" +
//                                            "Title:"  + "\n" +
//                                            "Current Job:"  + "\n" +
//                                            "Company:"  + "\n" +
//                                            "City:"  + "\n" +
//                                            "State:"  + "\n" +
//                                            "Yearly Salary:"  + "\n" +
//                                            "Yearly Bonus:"  + "\n" +
//                                            "Telework Days:"  + "\n" +
//                                            "Retirement %:"  + "\n" +
//                                            "Leave Days:" ;
                            String formattedComp =
                                    "Job1" + "\n" +
                                    "=======" + "\n" +
                                            j1.title  + "\n" +
                                            j1.currentJob  + "\n" +
                                            j1.company  + "\n" +
                                            j1.city  + "\n" +
                                            j1.state  + "\n" +
                                            j1.yearlySalary  + "\n" +
                                            j1.yearlyBonus  + "\n" +
                                            j1.teleworkDaysPerWeek  + "\n" +
                                            j1.retirementBenefit  + "\n" +
                                            j1.leaveTime ;
                            String formattedComp2 =
                                    "Job2" + "\n" +
                                    "=======" + "\n" +
                                            j2.title + "\n" +
                                            j2.currentJob + "\n" +
                                            j2.company + "\n" +
                                            j2.city + "\n" +
                                            j2.state + "\n" +
                                            j2.yearlySalary + "\n" +
                                            j2.yearlyBonus + "\n" +
                                            j2.teleworkDaysPerWeek + "\n" +
                                            j2.retirementBenefit + "\n" +
                                            j2.leaveTime;
                            resultsTextView.setText(formattedComp);
                            resultsTextView2.setText(formattedComp2);
                        }
                    });
                }
            });
        }

        //switch the page to main menu when click "return"
        //find return button
        resultReturn = findViewById(R.id.resultReturn);
        //switch viewer
        resultReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                intent = new Intent(UIResultDisplay.this, UIMainMenu.class);
                startActivity(intent);
            }
        });



        anotherCompare = findViewById(R.id.anotherCompare);
        anotherCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                intent = new Intent(UIResultDisplay.this, UIJobsToCompare.class);
                startActivity(intent);
            }
        });
    }
}