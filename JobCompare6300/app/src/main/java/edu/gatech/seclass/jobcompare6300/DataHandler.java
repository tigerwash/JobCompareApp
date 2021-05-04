package edu.gatech.seclass.jobcompare6300;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Update;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataHandler {

    private static DataHandler _instance;
    private static JobDatabase _dbInstance;
    private CSVReader _csvReader;

    DataHandler(android.content.Context context) {
        _instance = this;
        JobDatabase jobDatabase = CreateNewDatabase(context);
        _csvReader = new CSVReader(context, R.raw.col);
    }

    public static DataHandler GetDataHandlerInstance() {
        return _instance;
    }

    // only setup once
    public static JobDatabase CreateNewDatabase(android.content.Context context) {
        if (_dbInstance == null)
            _dbInstance = Room.databaseBuilder(context, JobDatabase.class, JobDatabase.NAME)
                    .fallbackToDestructiveMigration()
                    .build();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                ComparisionSettings cSetting = CompareService.GetLastWeightSettings();

                if(cSetting == null) {
                    // Init weight settings to 1
                    ComparisionSettings newSetting = new ComparisionSettings(1,1,1,1,1,5);
                    _dbInstance.comparisionSettingsDao().insertSettings(cSetting);
                }
            };
        });



        return _dbInstance;
    }

    public static void SetDataHandlerInstance(DataHandler dataHandler) {
        _instance = dataHandler;
    }

    public void ArchiveJob(Job job){
        job.isArchived = true;
        _dbInstance.jobDao().updateJob(job);
    }

    public Job CreateNewJobObject() {
        return new Job();
    }

//    public ComparisionSettings CreateNewComparisionSettings() {
//        return new ComparisionSettings();
//    }

    public ComparisionSettings getComparisionSettings() {
        return _dbInstance.comparisionSettingsDao().getCurrentSettings();
    }

    public void SaveComparisionSettings(ComparisionSettings settings) {
        _dbInstance.comparisionSettingsDao().insertSettings(settings);
    }

    public Job GetCurrentJob(){
        return _dbInstance.jobDao().getCurrentJob();
    }

    public List<Job> GetAllJobs(){
        return _dbInstance.jobDao().getAll();
    }

    public int GetJobsCount() {
        return _dbInstance.jobDao().getAll().size();
    }

    public void SaveJob(Job job) {
        // normal jobs can't be a current job, so force false to eliminate accidental bool input
        job.currentJob = false;
        _dbInstance.jobDao().insertJob(job);
    }

    public void TryLoadCostOfLivingTable() {
        List<CostOfLiving> col_data = _dbInstance.costOfLivingDao().getAll();
        if(col_data.size() == 0){
            // load data
            List<String[]> data = _csvReader.LoadCostOfLivingData();
            for (String[] col : data) {
                CostOfLiving newData = new CostOfLiving();
                newData.rank = col[0];
                newData.city = col[1];
                newData.index = Integer.parseInt(col[2]);

                _dbInstance.costOfLivingDao().insertRecord(newData);
            }
        }
    }

    public List<CostOfLiving> GetCostOfLivingData(){
        // TODO: we shouldn't query this everytime. only do it on load of app. then cache it locally
        return _dbInstance.costOfLivingDao().getAll();
    }

    public void UpdateOrSaveNewCurrentJob(Job job) {
        job.currentJob = true;

        Job curJob = GetCurrentJob();
        if(curJob != null)
            _dbInstance.jobDao().deleteJob(curJob);
        _dbInstance.jobDao().insertJob(job);

    }
}

// bump version number if your schema changes
@Database(entities={Job.class, ComparisionSettings.class, CostOfLiving.class}, version=9)
abstract class JobDatabase extends RoomDatabase {
    // Declare your data access objects as abstract
    public abstract JobDao jobDao();
    public abstract ComparisionSettingsDao comparisionSettingsDao();
    public abstract CostOfLivingDao costOfLivingDao();

    // Database name to be used
    public static final String NAME = "jobDatabase";

}

@Dao
interface JobDao {
    @Query("Select * FROM job")
    public List<Job> getAll();

    @Query("Select * FROM job WHERE currentJob = 1")
    public Job getCurrentJob();

    @Insert
    public void insertJob(Job job);

    @Update
    public void updateJob(Job job);

    @Delete
    public void deleteJob(Job job);
}

@Entity(tableName = "job")
class Job {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "currentJob")
    public boolean currentJob;
    @ColumnInfo(name = "archived")
    public boolean isArchived;
    @ColumnInfo(name = "title")
    public String title;
    @ColumnInfo(name = "company")
    public String company;
    @ColumnInfo(name = "city")
    public String city;
    @ColumnInfo(name = "state")
    public String state;
    @ColumnInfo(name = "yearlySalary")
    public int yearlySalary;
    @ColumnInfo(name = "yearlyBonus")
    public int yearlyBonus;
    @ColumnInfo(name = "teleworkDaysPerWeek")
    public int teleworkDaysPerWeek;
    @ColumnInfo(name = "retirementBenefit")
    public int retirementBenefit;
    @ColumnInfo(name = "leaveTime")
    public int leaveTime;

    Job(){}
    Job(boolean isCurrentJob, String newCompany, String newTitle, String newCity,
        String newState, int salary, int bonus, int teleworkDays,
        int retirementMatch, int newLeaveTime) {
        currentJob = isCurrentJob;
        company = newCompany;
        title = newTitle;
        city = newCity;
        state = newState;
        yearlySalary = salary;
        yearlyBonus = bonus;
        teleworkDaysPerWeek = teleworkDays;
        retirementBenefit = retirementMatch;
        leaveTime = newLeaveTime;
    }
}


@Dao
interface ComparisionSettingsDao {

    @Query("Select * FROM comparision_settings WHERE id=(SELECT max(id) FROM comparision_settings)")
    public ComparisionSettings getCurrentSettings();

    @Insert
    public void insertSettings(ComparisionSettings comparisionSettings);

    @Update
    public void updateSettings(ComparisionSettings comparisionSettings);

    @Delete
    public void deleteSettings(ComparisionSettings comparisionSettings);
}

@Entity(tableName = "comparision_settings")
class ComparisionSettings {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "salaryWeight")
    public double salaryWeight;
    @ColumnInfo(name = "bonusWeight")
    public double bonusWeight;
    @ColumnInfo(name = "teleworkDaysWeight")
    public double teleworkDaysWeight;
    @ColumnInfo(name = "retirementBenefitWeight")
    public double retirementBenefitWeight;
    @ColumnInfo(name = "leaveTimeWeight")
    public double leaveTimeWeight;
    @ColumnInfo(name = "weightSum")
    public double weightSum;

    public ComparisionSettings(double salaryWeight, double bonusWeight, double teleworkDaysWeight, double retirementBenefitWeight, double leaveTimeWeight, double weightSum) {
        this.salaryWeight = salaryWeight;
        this.bonusWeight = bonusWeight;
        this.teleworkDaysWeight = teleworkDaysWeight;
        this.retirementBenefitWeight = retirementBenefitWeight;
        this.leaveTimeWeight = leaveTimeWeight;
        this.weightSum = weightSum;
    }
}

@Dao
interface CostOfLivingDao {

    @Query("Select * FROM cost_of_living")
    public List<CostOfLiving> getAll();

    @Query("Select * FROM cost_of_living WHERE city = :city")
    public CostOfLiving GetCostOfLivingRecord(String city);

    @Insert
    public void insertRecord(CostOfLiving col);

    @Update
    public void updateRecord(CostOfLiving col);

    @Delete
    public void deleteRecord(CostOfLiving col);
}

@Entity(tableName = "cost_of_living")
class CostOfLiving {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "rank")
    public String rank;
    @ColumnInfo(name = "city")
    public String city;
    @ColumnInfo(name = "index")
    public int index;
}

class CSVReader {
    InputStream _is;
    CSVReader(Context context, int raw_resource_id) {
        _is = context.getResources().openRawResource(raw_resource_id);
    }

    public List<String[]> LoadCostOfLivingData() {
        List resultList = new ArrayList();
        BufferedReader reader = new BufferedReader(new InputStreamReader(_is));
        try {
            String line;
            while ((line = reader.readLine()) != null) {

                String[] row = line.split(",");
                resultList.add(row);
            }
        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }
        finally {
            try {
                _is.close();
            }
            catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: "+e);
            }
        }
        return resultList;
    }
}