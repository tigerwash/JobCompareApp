package edu.gatech.seclass.jobcompare6300;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CompareServiceTest {

    @Mock
    DataHandler dataHandler;

    @Before
    public void setup(){
        DataHandler.SetDataHandlerInstance(dataHandler);
    }

    @Captor
    ArgumentCaptor<ComparisionSettings> comparisonSettingsCaptor;

    @Test
    public void testSaveSettingsSuccess() {
//        when(dataHandler.CreateNewComparisionSettings()).thenReturn(getTestComparisionSettings());
        CompareService.saveSettings(1,1,1,1,1);
        verify(dataHandler, times(1)).SaveComparisionSettings(comparisonSettingsCaptor.capture());
        assertEquals(1, comparisonSettingsCaptor.getValue().teleworkDaysWeight, 0.0f);
        assertEquals(1, comparisonSettingsCaptor.getValue().bonusWeight, 0.0f);
        assertEquals(1, comparisonSettingsCaptor.getValue().leaveTimeWeight, 0.0f);
        assertEquals(1, comparisonSettingsCaptor.getValue().retirementBenefitWeight, 0.0f);
        assertEquals(1, comparisonSettingsCaptor.getValue().salaryWeight, 0.0f);
        assertEquals(5, comparisonSettingsCaptor.getValue().weightSum, 0.0f);
    }

    @Test
    public void testGetCurrentSettingsSuccess() {
        ComparisionSettings expected = getTestComparisionSettings();
        when(dataHandler.getComparisionSettings()).thenReturn(expected);
        ComparisionSettings returned = CompareService.GetLastWeightSettings();
        verify(dataHandler, times(1)).getComparisionSettings();
        assertEquals(expected, returned);
    }

    @Test
    public void testGetAllOffersRankingSuccess() {
        List<Job> expected = getTestJobList();
        when(dataHandler.GetAllJobs()).thenReturn(expected);
        when(dataHandler.getComparisionSettings()).thenReturn(getTestComparisionSettings());
        List<Job> returned = CompareService.getAllOffersRanking();
        Collections.reverse(expected);
        verify(dataHandler, times(1)).getComparisionSettings();
        assertEquals(expected, returned);
    }

    @Test
    public void testGetAllOffersRankingDifferentWeightsSuccess() {
        List<Job> expected = getTestJobList();
        when(dataHandler.GetAllJobs()).thenReturn(expected);
        when(dataHandler.getComparisionSettings()).thenReturn(new ComparisionSettings(1,10,1,1,1,14));
        List<Job> returned = CompareService.getAllOffersRanking();
        verify(dataHandler, times(1)).getComparisionSettings();
        assertEquals(expected, returned);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetAllOffersRankingWithIncorrectWeights() {
        List<Job> expected = getTestJobList();
        when(dataHandler.GetAllJobs()).thenReturn(expected);
        when(dataHandler.getComparisionSettings()).thenReturn(new ComparisionSettings(1,1,1,1,1,1));
        List<Job> returned = CompareService.getAllOffersRanking();

    }
    @Test(expected = IllegalArgumentException.class)
    public void testGetAllOffersRankingWithZeroDivisor() {
        List<Job> expected = getTestJobList();
        when(dataHandler.GetAllJobs()).thenReturn(expected);
        when(dataHandler.getComparisionSettings()).thenReturn(new ComparisionSettings(0,0,0,0,0,0));
        List<Job> returned = CompareService.getAllOffersRanking();

    }

    @Test
    public void testGetRankedJobStringsSuccess() {
        List<Job> jobs = new ArrayList<>();
        jobs.add(new Job(false, "company1", "title1", "city1", "state1", 10000, 1080, 2, 2, 2));
        Hashtable<String, Job> expectedMap = new Hashtable<String, Job>();
        expectedMap.put("title1, company1",jobs.get(0));
        when(dataHandler.GetAllJobs()).thenReturn(jobs);
        when(dataHandler.getComparisionSettings()).thenReturn(getTestComparisionSettings());
        String returned = CompareService.getRankedJobStrings().get(0);
        verify(dataHandler, times(1)).getComparisionSettings();
        assertEquals("title1, company1", returned);
        assertEquals(expectedMap, CompareService.jobHashtable);
    }

    /**
     * Helper method for creating a test comparision settings object.
     * @return ComparisionSettings comparision settings with dummy data.
     */
    public ComparisionSettings getTestComparisionSettings(){
        return new ComparisionSettings(1,1,1,1,1,5);
    }

    /**
     * Helper method for creating a test job list.
     * @return List<Job> list of test job objects
     */
    public List<Job> getTestJobList(){
        List<Job> jobs = new ArrayList<>();
        jobs.add(new Job(false, "company1", "title1", "city1", "state1", 10000, 1080, 2, 2, 2));
        jobs.add(new Job(false, "company2", "title2", "city2", "state2", 10020, 1060, 2, 2, 2));
        jobs.add(new Job(false, "company3", "title3", "city3", "state3", 10040, 1040, 2, 2, 2));
        jobs.add(new Job(false, "company4", "title4", "city4", "state4", 10060, 1020, 2, 2, 2));
        jobs.add(new Job(true, "company5", "title5", "city5", "state5", 10080, 1000, 2, 2, 2));
        return jobs;
    }


}
