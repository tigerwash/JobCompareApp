package edu.gatech.seclass.jobcompare6300;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class UICurrentJobEditor extends AppCompatActivity {

    Button cancel;
    Button saveCurrent;
    EditText titleInput;
    EditText companyInput;
    EditText cityInput;
    EditText stateInput;
    EditText salaryInput;
    EditText bonusInput;
    EditText teldaysInput;
    EditText retireInput;
    EditText leavetimeInput;
    static Job current;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_current_job_editor);

        titleInput     = findViewById(R.id.titleInput);
        companyInput   = findViewById(R.id.companyInput);
        cityInput      = findViewById(R.id.cityInput);
        stateInput     = findViewById(R.id.stateInput);
        salaryInput    = findViewById(R.id.salaryInput);
        bonusInput     = findViewById(R.id.bonusInput);
        teldaysInput   = findViewById(R.id.teldaysInput);
        retireInput    = findViewById(R.id.retireInput);
        leavetimeInput = findViewById(R.id.leavetimeInput);

        // load prev current data if it exists
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // try to add another current job, should update the current one
                Job tmp = JobService.populateCurrentJobDetail();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if ((tmp != null)) {
                            titleInput.setText(tmp.title);
                            companyInput.setText(tmp.company);
                            cityInput.setText(tmp.city);
                            stateInput.setText(tmp.state);
                            salaryInput.setText(String.valueOf(tmp.yearlySalary));
                            bonusInput.setText(String.valueOf(tmp.yearlyBonus));
                            teldaysInput.setText(String.valueOf(tmp.teleworkDaysPerWeek));
                            retireInput.setText(String.valueOf(tmp.retirementBenefit));
                            leavetimeInput.setText(String.valueOf(tmp.leaveTime));
                        }
                    }
                });
//                if ((tmp != null)) {
//                    titleInput.setText(tmp.title);
//                    companyInput.setText(tmp.company);
//                    cityInput.setText(tmp.city);
//                    stateInput.setText(tmp.state);
//                    salaryInput.setText(String.valueOf(tmp.yearlySalary));
//                    bonusInput.setText(String.valueOf(tmp.yearlyBonus));
//                    teldaysInput.setText(String.valueOf(tmp.teleworkDaysPerWeek));
//                    retireInput.setText(String.valueOf(tmp.retirementBenefit));
//                    leavetimeInput.setText(String.valueOf(tmp.leaveTime));
//                }
            }
        });

        // Handle Cancel Event
        cancel = findViewById(R.id.cancelCurrentJob);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UICurrentJobEditor.this, UIMainMenu.class));
            }
        });

        // Handle Save Event
        saveCurrent = findViewById(R.id.SaveCurrentJob);
        saveCurrent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                current = GetLayoutFieldDataAsJob();
                if(current!=null) {
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JobService.saveJob(current);
                                // back to main screen
                                startActivity(new Intent(UICurrentJobEditor.this, UIMainMenu.class));
                            } catch (Exception e) {
                                System.out.println("Error reading input.");
                            }

                        }
                    });
                }


//                AsyncTask.execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        // pass data over to JobService to handle
//                        try {
//                            // set currentJob flag to false
////                            Job job = GetLayoutFieldDataAsJob();
//                            JobService.saveJob(finalJob);
//                            // back to main screen
//                            startActivity(new Intent(UICurrentJobEditor.this, UIMainMenu.class));
//                        } catch (Exception e) {
//                            System.out.println("Error saving the current job:" + e.getMessage());
//
//                        }
//
//                    }
//                  });

            }
        });
    }

    private Job GetLayoutFieldDataAsJob() {
        final Job newCurrentJob = DataHandler.GetDataHandlerInstance().CreateNewJobObject();
        try {
            if(titleInput.getText().length()==0) titleInput.setError("Title can not be empty.");
            if(companyInput.getText().length()==0) companyInput.setError("Company can not be empty.");
            if(cityInput.getText().length()==0) cityInput.setError("City can not be empty.");
            if(stateInput.getText().length()==0) stateInput.setError("State can not be empty.");
            if(salaryInput.getText().length()==0) salaryInput.setError("Salary can not be empty.");
            if(bonusInput.getText().length()==0) bonusInput.setError("Bonus can not be empty.");
            if(teldaysInput.getText().length()==0) teldaysInput.setError("Telework day can not be empty.");
            if(retireInput.getText().length()==0) retireInput.setError("Retirement benefit match percent can not be empty.");
            if(leavetimeInput.getText().length()==0) leavetimeInput.setError("Leave time days per year can not be empty.");
            newCurrentJob.currentJob = true;
            newCurrentJob.title = titleInput.getText().toString();
            newCurrentJob.company = companyInput.getText().toString();
            newCurrentJob.city = cityInput.getText().toString();
            newCurrentJob.state = stateInput.getText().toString();
            newCurrentJob.yearlySalary = Integer.parseInt(salaryInput.getText().toString());
            if(newCurrentJob.yearlySalary<0) {
                salaryInput.setError("Salary can not be negative.");
                throw new IllegalArgumentException();
            }
            newCurrentJob.yearlyBonus = Integer.parseInt(bonusInput.getText().toString());
            if(newCurrentJob.yearlyBonus<0) {bonusInput.setError("Bonus can not be negative.");throw new IllegalArgumentException(); }
            newCurrentJob.teleworkDaysPerWeek = Integer.parseInt(teldaysInput.getText().toString());
            if(newCurrentJob.teleworkDaysPerWeek<0 || newCurrentJob.teleworkDaysPerWeek>5) {teldaysInput.setError("Telework day has to be between 0 and 5 inclusive.");throw new IllegalArgumentException(); }
            newCurrentJob.retirementBenefit = Integer.parseInt(retireInput.getText().toString());
            if(newCurrentJob.retirementBenefit<0 || newCurrentJob.retirementBenefit>100) {retireInput.setError("Retirement benefit match percent has to be between 0 and 100 inclusive.");throw new IllegalArgumentException(); }
            newCurrentJob.leaveTime = Integer.parseInt(leavetimeInput.getText().toString());
            if(newCurrentJob.leaveTime<0 || newCurrentJob.leaveTime>365) {leavetimeInput.setError("Leave time days per year has to be between 0 and 365 inclusive.");throw new IllegalArgumentException(); }
            return newCurrentJob;
        }catch(Exception ex){
            return null;
        }
    }


}