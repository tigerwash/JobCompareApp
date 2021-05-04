package edu.gatech.seclass.jobcompare6300;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JobServiceTest {

    @InjectMocks
    public JobService jobService;

    @Mock
    DataHandler dataHandler;

    @Before
    public void setup(){
        DataHandler.SetDataHandlerInstance(dataHandler);
    }

    @Test
    public void testSaveJobOfferCurrentSuccess() {
        JobService.saveJob(new Job(true, "company", "title", "city", "state", 1000, 1000, 0, 0, 0));
        verify(dataHandler, times(1)).UpdateOrSaveNewCurrentJob((Job) any());
        verify(dataHandler, times(0)).SaveJob((Job) any());
    }
    @Test
    public void testSaveJobOfferSuccess() {
        JobService.saveJob(new Job(false, "company", "title", "city", "state", 1000, 1000, 0, 0, 0));
        verify(dataHandler, times(1)).SaveJob((Job) any());
        verify(dataHandler, times(0)).UpdateOrSaveNewCurrentJob((Job) any());
    }

    @Test(expected = NullPointerException.class)
    public void testSaveJobOfferNullPointer() {
        JobService.saveJob(new Job(false, null, null, null, null, 0, 0, 0, 0, 0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveJobOfferNegativeSalary() {
        JobService.saveJob(new Job(false, "company", "title", "city", "state", -1, 0, 0, 0, 0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveJobOfferNegativeBonus() {
        JobService.saveJob(new Job(false, "company", "title", "city", "state", 1000, -1, 0, 0, 0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveJobOfferInvalidRetirement() {
        JobService.saveJob(new Job(false, "company", "title", "city", "state", 1000, 1000, 0, 101, 0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveJobOfferNegativeRetirement() {
        JobService.saveJob(new Job(false, "company", "title", "city", "state", 1000, 1000, 0, -1, 0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveJobOfferInvalidTeleworkDays() {
        JobService.saveJob(new Job(false, "company", "title", "city", "state", 1000, 1000, 8, 0, 0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveJobOfferNegativeTeleworkDays() {
        JobService.saveJob(new Job(false, "company", "title", "city", "state", 1000, 1000, -1, 0, 0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveJobOfferInvalidLeaveTime() {
        JobService.saveJob(new Job(false, "company", "title", "city", "state", 1000, 1000, 0, 0, 366));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveJobOfferNegativeLeaveTime() {
        JobService.saveJob(new Job(false, "company", "title", "city", "state", 1000, 1000, 0, 0, -1));
    }

    @Test
    public void testIsAbleToCompareSuccessTrue() {
        when(dataHandler.GetJobsCount()).thenReturn(3);
        assertTrue(JobService.isAbleToCompare());
        verify(dataHandler, times(1)).GetJobsCount();
    }

    @Test
    public void testIsAbleToCompareSuccessFalse() {
        when(dataHandler.GetJobsCount()).thenReturn(1);
        assertFalse(JobService.isAbleToCompare());
        verify(dataHandler, times(1)).GetJobsCount();
    }

    @Test(expected = SQLDataException.class)
    public void testIsAbleToCompareDbAccessError() {
        when(dataHandler.GetJobsCount()).thenThrow(SQLDataException.class);
        JobService.isAbleToCompare();
    }

    @Test
    public void testGetAllJobsSuccess() {
        List<Job> jobs = new ArrayList<>();
        jobs.add(getTestCurrentJob());
        when(dataHandler.GetAllJobs()).thenReturn(jobs);
        assertEquals(jobs, JobService.getAllJobs());
        verify(dataHandler, times(1)).GetAllJobs();
    }

    @Test(expected = NullPointerException.class)
    public void testGetAllJobsNullPointerException() {
        List<Job> jobs = new ArrayList<>();
        when(dataHandler.GetAllJobs()).thenReturn(jobs);
        JobService.getAllJobs();
    }

    @Test(expected = SQLDataException.class)
    public void testGetAllJobsDbAccessError() {
        when(dataHandler.GetAllJobs()).thenThrow(SQLDataException.class);
        JobService.getAllJobs();
    }

    @Test
    public void testGetCostOfLivingSuccess() {
        when(dataHandler.GetCostOfLivingData()).thenReturn(getTestCostOfLiving());
        assertEquals(200, JobService.getCostOfLiving("city"));
        verify(dataHandler, times(1)).GetCostOfLivingData();
    }

    @Test
    public void testGetCostOfLivingNoResult() {
        when(dataHandler.GetCostOfLivingData()).thenThrow(NullPointerException.class);
        assertEquals(100, JobService.getCostOfLiving("city"));
    }

    @Test
    public void testGetCostOfLivingDbAccessError() {
        when(dataHandler.GetCostOfLivingData()).thenThrow(SQLDataException.class);
        assertEquals(100, JobService.getCostOfLiving("city"));
        List<CostOfLiving> col = new ArrayList<>();
    }


    /**
     * Helper method for creating a test current job object.
     *
     * @return Job current job object with dummy data.
     */
    public Job getTestCurrentJob() {
        Job currentJob = new Job();
        currentJob.currentJob = true;
        currentJob.company = "Disneyland";
        currentJob.title = "Mickey Mouse";
        currentJob.city = "Anaheim";
        currentJob.state = "California";
        currentJob.yearlySalary = 1337337;
        currentJob.yearlyBonus = 31337;
        currentJob.teleworkDaysPerWeek = 0;
        currentJob.retirementBenefit = 4;
        currentJob.leaveTime = 15;
        return currentJob;
    }

    /**
     * Helper method for creating a test CostOfLiving object.
     *
     * @return ArrayList<CostOfLiving> CostOfLiving object with dummy data.
     */
    public ArrayList<CostOfLiving> getTestCostOfLiving() {
        ArrayList<CostOfLiving> colData = new ArrayList<>();
        CostOfLiving costOfLiving = new CostOfLiving();
        costOfLiving.city = "city";
        costOfLiving.index = 200;
        colData.add(costOfLiving);
        return colData;
    }


}
