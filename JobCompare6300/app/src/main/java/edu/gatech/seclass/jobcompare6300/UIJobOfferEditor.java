package edu.gatech.seclass.jobcompare6300;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class UIJobOfferEditor extends AppCompatActivity {

    Button cancel;
    Button saveJobOffer;
    Button enterAnotherJob;
    Button compareCurrent;
    EditText titleInput;
    EditText companyInput;
    EditText cityInput;
    EditText stateInput;
    EditText salaryInput;
    EditText bonusInput;
    EditText teldaysInput;
    EditText retireInput;
    EditText leavetimeInput;
    static Job JOB;
    Job current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_job_offer_editor);

        titleInput     = findViewById(R.id.titleInput);
        companyInput   = findViewById(R.id.companyInput);
        cityInput      = findViewById(R.id.cityInput);
        stateInput     = findViewById(R.id.stateInput);
        salaryInput    = findViewById(R.id.salaryInput);
        bonusInput     = findViewById(R.id.bonusInput);
        teldaysInput   = findViewById(R.id.teldaysInput);
        retireInput    = findViewById(R.id.retireInput);
        leavetimeInput = findViewById(R.id.leavetimeInput);

        ClearInputTextFields();

        // Init buttions
        saveJobOffer = findViewById(R.id.saveJobOffer);
        saveJobOffer.setEnabled(true);
        enterAnotherJob = findViewById(R.id.enterAnotherJob);
        enterAnotherJob.setEnabled(false);
        compareCurrent = findViewById(R.id.compareCurrent);
        cancel = findViewById(R.id.resultReturn);
        cancel.setEnabled(true);
        compareCurrent.setEnabled(false);

        saveJobOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pass data over to JobService to handle
                try {
                    JOB = GetLayoutFieldDataAsJob();
                } catch(Exception e){
                    System.out.println("Error reading input.");
                }
                if(JOB != null) {
                    final boolean[] enabled = new boolean[1];
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                // set currentJob flag to false
                                JobService.saveJob(JOB);
                                if(DataHandler.GetDataHandlerInstance().GetCurrentJob()!=null){
                                    enabled[0] = true;
                                }
                            } catch (Exception e) {
                                System.out.println("Error saving the job offer:" + e.getMessage());

                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    compareCurrent.setEnabled(enabled[0]);
                                }
                            });
                        }

                    });
                    // enable post-save button options
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            final isAbleToCompare task = new isAbleToCompare();
//                            task.execute();
//                        }
//                    }).start();
                    saveJobOffer.setEnabled(false);
                    enterAnotherJob.setEnabled(true);
                }
            }
        });

        enterAnotherJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save the job offer to database
                //and then clean the current data and open a new interface?
                //Question: do we need to save then we can enter another offer?
                ClearInputTextFields();
                enterAnotherJob.setEnabled(false);
                compareCurrent.setEnabled(false);
                saveJobOffer.setEnabled(true);
                cancel.setEnabled(true);
            }
        });

        compareCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //go to the ranking display interfase
//            startActivity(new Intent(UIJobOfferEditor.this, UIResultDisplay.class));
                Intent intent = new Intent(UIJobOfferEditor.this, UIResultDisplay.class);
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        // Make sure hashtable gets filled
                        CompareService.getRankedJobStrings();

                        // get current job
                        current = DataHandler.GetDataHandlerInstance().GetCurrentJob();
                        String formattedComp =
                                JOB.title + " | " + current.title + "\n" +
                                        JOB.currentJob + " | " + current.currentJob + "\n" +
                                        JOB.company + " | " + current.company + "\n" +
                                        JOB.city + " | " + current.city + "\n" +
                                        JOB.state + " | " + current.state + "\n" +
                                        JOB.yearlySalary + " | " + current.yearlySalary + "\n" +
                                        JOB.yearlyBonus + " | " + current.yearlyBonus + "\n" +
                                        JOB.teleworkDaysPerWeek + " | " + current.teleworkDaysPerWeek + "\n" +
                                        JOB.retirementBenefit + " | " + current.retirementBenefit + "\n" +
                                        JOB.leaveTime + " | " + current.leaveTime;
                        intent.putExtra("job1", JOB.title + ", " + JOB.company);
                        intent.putExtra("job2", current.title + ", " + current.company);
                        startActivity(intent);
                    }
                });
            }
        });


        //switch the page to main menu when click "cancel"
        //find cancel button
        //switch viewer
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(UIJobOfferEditor.this, UIMainMenu.class));
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
            newCurrentJob.currentJob = false;
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
            throw new IllegalArgumentException("Please fix all the error on input.");
        }
    }

    private void ClearInputTextFields() {
        titleInput    .getText().clear();
        companyInput  .getText().clear();
        cityInput     .getText().clear();
        stateInput    .getText().clear();
        salaryInput   .getText().clear();
        bonusInput    .getText().clear();
        teldaysInput  .getText().clear();
        retireInput   .getText().clear();
        leavetimeInput.getText().clear();
    }

}


