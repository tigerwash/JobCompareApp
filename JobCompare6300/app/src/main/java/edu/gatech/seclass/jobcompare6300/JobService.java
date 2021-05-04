package edu.gatech.seclass.jobcompare6300;

//import lombok.NonNull;
import java.util.List;

public class JobService {

    private static JobService _instance;

    public JobService() {
        _instance = this;
    }

    public static JobService GetJobServiceInstance() {
        return _instance;
    }
    public static Job populateCurrentJobDetail(){
        Job currentJob = DataHandler.GetDataHandlerInstance().GetCurrentJob();
        return currentJob;
    }


    public static void saveJob(Job job) {
        if(job.company==null || job.title ==null || job.city == null|| job.state==null) throw new NullPointerException("Invalid null paremeter.");
        if(job.company.length()==0 || job.title.length()==0 || job.city.length()==0|| job.state.length()==0) throw new NullPointerException("Invalid null paremeter.");
        if(job.yearlySalary<0) throw new IllegalArgumentException("Invalid salary.");
        if(job.yearlyBonus<0) throw new IllegalArgumentException("Invalid bonus.");
        if(job.retirementBenefit>100 || job.retirementBenefit<0) throw new IllegalArgumentException("Invalid retirement match.");
        if(job.teleworkDaysPerWeek>7 || job.teleworkDaysPerWeek<0) throw new IllegalArgumentException("Invalid telework days.");
        if(job.leaveTime>365 || job.leaveTime<0) throw new IllegalArgumentException("Invalid leave time.");

        // if this is a CurrentJob
        if(job.currentJob) {
            // save the currentJob
            DataHandler.GetDataHandlerInstance().UpdateOrSaveNewCurrentJob(job);
        }
        else
        {
            DataHandler.GetDataHandlerInstance().SaveJob(job);
        }
    }

    public static boolean isAbleToCompare(){
        try{
            return DataHandler.GetDataHandlerInstance().GetJobsCount()>1;
        } catch (Exception ex){
            System.out.println("Issue with getting job counts from database...");
            throw ex;
        }
    }
    public static List<Job> getAllJobs(){
        List<Job> jobs;
        try{
            jobs = DataHandler.GetDataHandlerInstance().GetAllJobs();
        } catch (Exception ex){
            System.out.println("Issue with getting all jobs from database...");
            throw ex;
        }
        if(jobs==null || jobs.size()==0) throw new NullPointerException("No job in the database...");
        return jobs;
    }
//    public static List<String> GetCompareList(){
//
//    }
    public static int getCostOfLiving(String city){
        int index = 100;
        try{
            List<CostOfLiving> allCOL = DataHandler.GetDataHandlerInstance().GetCostOfLivingData();
            for (CostOfLiving col : allCOL) {
                String colListCity = col.city.toLowerCase();
                if (colListCity.contains(city.toLowerCase())) {
                    index = col.index;
                    break;
                }
            }
        } catch (Exception ex){
            System.out.println("Issue with getting index from database...");
        }
        return index;
    }
}
