package edu.gatech.seclass.jobcompare6300;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.wx.wheelview.widget.WheelViewDialog;

import java.util.ArrayList;
import java.util.List;

public class UIJobsToCompare extends AppCompatActivity {

    Button firstChosen;
    Button secondChosen;
    Button compareChosen;
    Button compareReturn;
    List<String> jobList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_jobs_to_compare);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if(DataHandler.GetDataHandlerInstance().GetJobsCount() < 2) return;
                jobList = CompareService.getRankedJobStrings();
            }
        });
        // assign chosen job
        firstChosen = findViewById(R.id.firstCompare);
        secondChosen = findViewById(R.id.secondCompare);

        firstChosen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWheelViewFirst();
            }
        });

        secondChosen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWheelViewSecond();
            }
        });

        compareChosen = findViewById(R.id.compareChosen);
        compareChosen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do ranking activity


                //go to the ranking display interfase
                Intent intent = null;
                intent = new Intent(UIJobsToCompare.this, UIResultDisplay.class);
                intent.putExtra("job1", firstChosen.getText());
                intent.putExtra("job2", secondChosen.getText());
                startActivity(intent);
            }
        });

        //switch the page to main menu when click "return"
        //find return button
        compareReturn = findViewById(R.id.compareReturn);
        //switch viewer
        compareReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                intent = new Intent(UIJobsToCompare.this, UIMainMenu.class);
                startActivity(intent);
            }
        });

    }


    // ref:  https://www.youtube.com/watch?v=E50yhR2A2jE
    // first job chosen dialog
    private void showWheelViewFirst(){
        WheelViewDialog dialog = new WheelViewDialog(this);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if(DataHandler.GetDataHandlerInstance().GetJobsCount() < 2) return;
//                List<String> jobList = CompareService.getRankedJobStrings();
                if(jobList != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.setTitle("Choose one job offer")
                                    .setItems(jobList)   // this is should be retrieved from database in the future
                                    .setLoop(true)
                                    .setButtonText("OK")
                                    .setOnDialogItemClickListener(new WheelViewDialog.OnDialogItemClickListener() {
                                        @Override
                                        public void onItemClick(int position, String s) {
                                            Toast.makeText(UIJobsToCompare.this, "You choose " + s + "to compare", Toast.LENGTH_SHORT).show();
                                            firstChosen.setText(s); // change the chosen one to first chosen
                                        }
                                    })
                                    .show();
                        }
                    });
                }
            }
        });

    }
    // second job chosen dialog
    private void showWheelViewSecond(){
        WheelViewDialog dialog = new WheelViewDialog(this);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if(DataHandler.GetDataHandlerInstance().GetJobsCount() < 2) return;
//                List<String> jobList = CompareService.getRankedJobStrings();
                if (jobList != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.setTitle("Choose one job offer")
                                    .setItems(jobList)   // this is should be retrieved from database in the future
                                    .setLoop(true)
                                    .setButtonText("OK")
                                    .setOnDialogItemClickListener(new WheelViewDialog.OnDialogItemClickListener() {
                                        @Override
                                        public void onItemClick(int position, String s) {
                                            Toast.makeText(UIJobsToCompare.this, "You choose " + s + " to compare", Toast.LENGTH_SHORT).show();
                                            secondChosen.setText(s); // change the chosen one to first chosen
                                        }
                                    })
                                    .show();
                        }
                    });
                }
            }
        });
    }



    private ArrayList<String> makePlaceHolder(){
        List<String> placeHolder = new ArrayList<>();
        placeHolder.add("offer1");
        placeHolder.add("offer2");
        placeHolder.add("offer3");
        placeHolder.add("offer4");
        placeHolder.add("offer5");
        placeHolder.add("offer6");
        placeHolder.add("offer7");
        return (ArrayList<String>) placeHolder;
    }
}