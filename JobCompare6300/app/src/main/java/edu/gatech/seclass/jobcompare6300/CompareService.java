package edu.gatech.seclass.jobcompare6300;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.PriorityQueue;

public class CompareService {

    public static List<Job> rankedJobList;
    public static Hashtable<String,Job> jobHashtable;

    public static void saveSettings(double bonusWeight, double leaveTimeWeight, double retirementBenefitWeight, double salaryWeight, double teleworkDaysWeight){
        //update the comparison settings in database
        ComparisionSettings currentSetting = new ComparisionSettings(salaryWeight,bonusWeight,teleworkDaysWeight,retirementBenefitWeight,leaveTimeWeight,bonusWeight+leaveTimeWeight+retirementBenefitWeight+salaryWeight+teleworkDaysWeight);
        DataHandler.GetDataHandlerInstance().SaveComparisionSettings(currentSetting);
    }

    public static ComparisionSettings GetLastWeightSettings() {
        return DataHandler.GetDataHandlerInstance().getComparisionSettings();
    }

    public static List<Job> getAllOffersRanking(){
        List<Job> jobs = JobService.getAllJobs();
        ComparisionSettings settings = DataHandler.GetDataHandlerInstance().getComparisionSettings();
        PriorityQueue<Job> rankedJobs = new PriorityQueue<>((j1,j2)-> Double.compare(calculateScore(j2,settings),calculateScore(j1,settings)));
        rankedJobs.addAll(jobs);
        List<Job> rankedJobList  = new ArrayList<>();
        while(!rankedJobs.isEmpty()){
            rankedJobList.add(rankedJobs.poll());
        }
        return rankedJobList;
    }

    public static List<String> getRankedJobStrings() {
        List<Job> rankedJobs = getAllOffersRanking();
        List<String> formattedRankedStrings = new ArrayList<String>();
        Hashtable<String, Job> map = new Hashtable<String, Job>();

        for (Job job : rankedJobs) {
            String formattedOutput = job.title + ", " + job.company;
            map.put(formattedOutput, job);
            formattedRankedStrings.add(formattedOutput);
        }

        jobHashtable = map;
        return formattedRankedStrings;
    }

    /**
     * Helper method for calculating the rank score for a job.
     * @param job       job to be evaluated
     * @param settings  comparision settings to be used in the calculation
     * @return int      job score to be used for ranking
     */
    private static double calculateScore(Job job, ComparisionSettings settings){
        if(settings.weightSum !=settings.salaryWeight+settings.bonusWeight+settings.retirementBenefitWeight+settings.leaveTimeWeight+settings.teleworkDaysWeight){
            System.out.println("Weight Settings are incorrect...");
            throw new IllegalArgumentException("Current weight settings are incorrect, check the database for current settings...");
        }
        double coostOfLiving = JobService.getCostOfLiving(job.city);
        double adjSalary = job.yearlySalary * 100.0 / coostOfLiving;
        double adjBonus = job.yearlyBonus * 100.0 / coostOfLiving;
        double benefit = adjSalary * job.retirementBenefit / 100.0;
        double leave = job.leaveTime * adjSalary / 260.0;
        double telework = (260.0 - 52.0 * job.teleworkDaysPerWeek) * (adjSalary / 260.0) / 8.0;
        double score = (settings.salaryWeight / settings.weightSum) * adjSalary +
                (settings.bonusWeight / settings.weightSum) * adjBonus +
                (settings.retirementBenefitWeight / settings.weightSum) * benefit +
                (settings.leaveTimeWeight / settings.weightSum) * leave +
                (settings.teleworkDaysWeight / settings.weightSum) * telework;
        if(Double.isNaN(score)||Double.isInfinite(score)){
            System.out.println("Getting divided by zero...");
            throw new IllegalArgumentException("One of the divisor is zero, check the weights or cost of living index...");
        }
        return score;

    }

}
