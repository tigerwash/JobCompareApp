# Test Plan
Version 1.0 - 3/13 update initial unit test result

**Author**: Team115

## 1 Testing Strategy

### 1.1 Overall strategy

From a high-level, our Testing Strategy may be broken into the following phases:

Developers’ Testing > Alpha/Beta Testing > Production Ready Testing > Release

**Developers' Testing**

We’ll begin with a phase of developers’ testing where we will perform unit, integration, and system testing. For this project, our user-acceptance testing (UAT)criteria will be pass/fail based on the aforementioned phases. Regression testing will be run, in full, for any code changes.

**Alpha/Beta Testing** 

During this testing phase, we'll look to follow a more manual testing technique. We will test the app ourselves and ask classmates, friends, and family to test the application. Testers of this phase will be provided with generic test plans based on software features of the app.

This process will help us document bugs and issues that are found through their manual testing. Feedback from this phase will aid in progressing to become production ready.

**Production Ready Testing**

To validate the software, we will confirm that the app is passing UAT and goes through another phase of manual testing with testers. Issues reported during this phase will be documented and evaluated for priority level. If this testing is completed with no High Priority issues, then we will consider this version ready for production. 


### 1.2 Test Selection

### Whitebox Testing

We will follow a whitebox branch & condition coverage method for unit, integration, and system testing of our classes and system. 

#### **Unit Testing** [JUnit]
- mainMenu
- jobOfferService
- comparisonService

#### **System Testing** [Automated Testing]
- mainMenu
- guiSystem

### Blackbox testing

#### **Alpha/Beta Testing**

As we move towards producing an alpha/beta application, testing will move to a blackbox focus where we’ll use the category partition method to produce test cases that will be manually executed. 

Additionally, we will also create a simple functional testing suite to allow for general tests to be run and tracked.

#### **User Testing**

Lastly, we will also allow for public testers to use the application with just the application summary and without test cases. This will allow for us to track feedback regarding failures, faults, and errors. 


### 1.3 Adequacy Criterion

As described throughout our test plan, we plan to implement a whitebox testing by developers where we implement unit tests following a branch & condition coverage method with JUnit. This unit testing will also be used for regression testing updates. For our blackbox testing, we have planned out a functional testing plan based on a category partition method and potentially a combination of automated and manual testing. 

### 1.4 Bug Tracking

Bugs will be documented and evaluated by our team in a shared Google Sheet. We will use the following schema for bug reports:
* ID
* Status {none, in progress, closed}
* Report Type {Bug, Wishlist Feedback}
* Description
* Reproduction Steps
* Priority {low, med, high}
* Comments

### 1.5 Technology

**TSLgenerator** to help with producing cases for blackbox testing based on the category partition method.
**Esspresso** for help with UI testing
**JUnit** to focus on Unit Testing

## 2 Test Cases

### Unit Testing

case  | input  | expected result | actual result | pass/fail
------------ | ------------- | ------------- | ------------- | -------------
testSaveJobOfferNegativeTeleworkDays|valid inputs with negative teleworkDays|IllegalArgumentException|IllegalArgumentException|pass
testGetCostOfLivingSuccess|valid input| successfully get the cost of living index|successfully get the cost of living index|pass
testGetAllJobsDbAccessError|error when accessing dbhandler|SQLDataException|SQLDataException|pass 
testSaveJobOfferNegativeRetirement|valid inputs with negative retirement match| IllegalArgumentException |IllegalArgumentException |pass
testSaveJobOfferSuccess|full valid input| successfully save the job offer |successfully save the job offer |pass
testIsAbleToCompareSuccessTrue|two jobs exists in db| successfully return true |successfully return true |pass
testSaveJobOfferNullPointer|null input| NullPointerException|NullPointerException|pass 
testSaveJobOfferInvalidRetirement|more than 100 retirement match|IllegalArgumentException |IllegalArgumentException |pass
testSaveJobOfferCurrentSuccess|valid input| successfully save current job|successfully save current job|pass
testGetAllJobsNullPointerException|no job exist in db| NullPointerException|NullPointerException|pass
testGetCostOfLivingDbAccessError|error when accessing dbhandler|SQLDataException|SQLDataException|pass
testSaveJobOfferInvalidLeaveTime|valid inputs with 366 leave time| IllegalArgumentException |IllegalArgumentException |pass
testGetCostOfLivingNoResult|cannot find the CostOfLiving|returns default 100|returns default 100|pass
testSaveJobOfferNegativeLeaveTime|valid inputs with negative leave time| IllegalArgumentException |IllegalArgumentException |pass
testSaveJobOfferNegativeSalary|valid inputs with negative salary| IllegalArgumentException |IllegalArgumentException |pass
testGetAllJobsSuccess|valid input| successfully get all jobs from db|successfully get all jobs from db|pass
testIsAbleToCompareSuccessFalse|only one job exists in db| successfully return false|successfully return false|pass
testSaveJobOfferInvalidTeleworkDays|valid inputs with 8 telework Days| IllegalArgumentException |IllegalArgumentException |pass
testSaveJobOfferNegativeBonus|valid inputs with negative bonus| IllegalArgumentException |IllegalArgumentException |pass
testIsAbleToCompareDbAccessError |error when accessing dbhandler|SQLDataException|SQLDataException|pass
saveSettings|full valid input| success save the settings | | 
getAllOffersRanking|list of jobs| success rank the jobs | | 
getAllOffersRanking|list of a single job| success return job list | | 
getAllOffersRanking|null list| throws null exception| |
comparison table with empty table | | 



### Blackbox Testing
| case           | input                 | expected result                | actual result | pass/fail |
| -------------- | --------------------- | ------------------------------ | ------------- | --------- |
|	Test Case 1 (Key = 1.1.1.1.0.0.1.1.1.1.)	|	"CurrentJob : empty,JobCount : GreaterThanTwoJobs,MainMenu : Enter Current Job,CurrentJobOptions : Save,NewJobOfferOptions : <n/a>,NewJobOfferAdditionalOptions : <n/a>,SelectJobOne : SelectJob1,SelectJobTwo : SelectJob2,DoCompare : RunCompare,ComparisonDisplay : CompareAgain"	|	Run Compare Again	|	|	|										
|	Test Case 2 (Key = 1.1.1.1.0.0.1.1.1.2.)	|	"CurrentJob : empty,JobCount : GreaterThanTwoJobs,MainMenu : Enter Current Job,CurrentJobOptions : Save,NewJobOfferOptions : <n/a>,NewJobOfferAdditionalOptions : <n/a>,SelectJobOne : SelectJob1,SelectJobTwo : SelectJob2,DoCompare : RunCompare,ComparisonDisplay : MainMenu"	|	"Compare, Go back to main menu"	|	|	|										
|	Test Case 3 (Key = 1.1.1.2.0.0.1.1.1.1.)	|	"CurrentJob : empty,JobCount : GreaterThanTwoJobs,MainMenu : Enter Current Job,CurrentJobOptions : CancelExitWithoutSaving,NewJobOfferOptions : <n/a>,NewJobOfferAdditionalOptions : <n/a>,SelectJobOne : SelectJob1,SelectJobTwo : SelectJob2,DoCompare : RunCompare,ComparisonDisplay : CompareAgain"	|	Run Compare Again	|	|	|										
|	Test Case 4 (Key = 1.1.1.2.0.0.1.1.1.2.)	|	"CurrentJob : empty,JobCount : GreaterThanTwoJobs,MainMenu : Enter Current Job,CurrentJobOptions : CancelExitWithoutSaving,NewJobOfferOptions : <n/a>,NewJobOfferAdditionalOptions : <n/a>,SelectJobOne : SelectJob1,SelectJobTwo : SelectJob2,DoCompare : RunCompare,ComparisonDisplay : MainMenu"	|	"Compare, Go back to main menu"	|	|	|										
|	Test Case 5 (Key = 1.1.2.0.1.1.1.1.1.1.)	|	"CurrentJob : empty,JobCount : GreaterThanTwoJobs,MainMenu : Enter Job Offer,CurrentJobOptions : <n/a>,NewJobOfferOptions : Save,NewJobOfferAdditionalOptions : EnterAnotherOffer,SelectJobOne : SelectJob1,SelectJobTwo : SelectJob2,DoCompare : RunCompare,ComparisonDisplay : CompareAgain"	|	Run Compare Again	|	|	|										
|	Test Case 6 (Key = 1.1.2.0.1.1.1.1.1.2.)	|	"CurrentJob : empty,JobCount : GreaterThanTwoJobs,MainMenu : Enter Job Offer,CurrentJobOptions : <n/a>,NewJobOfferOptions : Save,NewJobOfferAdditionalOptions : EnterAnotherOffer,SelectJobOne : SelectJob1,SelectJobTwo : SelectJob2,DoCompare : RunCompare,ComparisonDisplay : MainMenu"	|	"Compare, Go back to main menu"	|	|	|										
|	Test Case 7 (Key = 1.1.2.0.1.2.1.1.1.1.)	|	"CurrentJob : empty,JobCount : GreaterThanTwoJobs,MainMenu : Enter Job Offer,CurrentJobOptions : <n/a>,NewJobOfferOptions : Save,NewJobOfferAdditionalOptions : ReturnToMainMenu,SelectJobOne : SelectJob1,SelectJobTwo : SelectJob2,DoCompare : RunCompare,ComparisonDisplay : CompareAgain"	|	Run Compare Again	|	|	|										
|	Test Case 8 (Key = 1.1.2.0.1.2.1.1.1.2.)	|	"CurrentJob : empty,JobCount : GreaterThanTwoJobs,MainMenu : Enter Job Offer,CurrentJobOptions : <n/a>,NewJobOfferOptions : Save,NewJobOfferAdditionalOptions : ReturnToMainMenu,SelectJobOne : SelectJob1,SelectJobTwo : SelectJob2,DoCompare : RunCompare,ComparisonDisplay : MainMenu"	|	"Compare, Go back to main menu"	|	|	|										
|	Test Case 9 (Key = 1.1.2.0.2.0.1.1.1.1.)	|	"CurrentJob : empty,JobCount : GreaterThanTwoJobs,MainMenu : Enter Job Offer,CurrentJobOptions : <n/a>,NewJobOfferOptions : CancelExitWithoutSaving,NewJobOfferAdditionalOptions : <n/a>,SelectJobOne : SelectJob1,SelectJobTwo : SelectJob2,DoCompare : RunCompare,ComparisonDisplay : CompareAgain"	|	Run Compare Again	|	|	|										
|	Test Case 10 (Key = 1.1.2.0.2.0.1.1.1.2.)	|	"CurrentJob : empty,JobCount : GreaterThanTwoJobs,MainMenu : Enter Job Offer,CurrentJobOptions : <n/a>,NewJobOfferOptions : CancelExitWithoutSaving,NewJobOfferAdditionalOptions : <n/a>,SelectJobOne : SelectJob1,SelectJobTwo : SelectJob2,DoCompare : RunCompare,ComparisonDisplay : MainMenu"	|	"Compare, Go back to main menu"	|	|	|										
|	Test Case 11 (Key = 1.1.3.0.0.0.1.1.1.1.)	|	"CurrentJob : empty,JobCount : GreaterThanTwoJobs,MainMenu : Change Comparison Settings,CurrentJobOptions : <n/a>,NewJobOfferOptions : <n/a>,NewJobOfferAdditionalOptions : <n/a>,SelectJobOne : SelectJob1,SelectJobTwo : SelectJob2,DoCompare : RunCompare,ComparisonDisplay : CompareAgain"	|	Run Compare Again	|	|	|										
|	Test Case 12 (Key = 1.1.3.0.0.0.1.1.1.2.)	|	"CurrentJob : empty,JobCount : GreaterThanTwoJobs,MainMenu : Change Comparison Settings,CurrentJobOptions : <n/a>,NewJobOfferOptions : <n/a>,NewJobOfferAdditionalOptions : <n/a>,SelectJobOne : SelectJob1,SelectJobTwo : SelectJob2,DoCompare : RunCompare,ComparisonDisplay : MainMenu"	|	"Compare, Go back to main menu"	|	|	|										
|	Test Case 13 (Key = 1.1.4.0.0.0.1.1.1.1.)	|	"CurrentJob : empty,JobCount : GreaterThanTwoJobs,MainMenu : Compare Job Offers,CurrentJobOptions : <n/a>,NewJobOfferOptions : <n/a>,NewJobOfferAdditionalOptions : <n/a>,SelectJobOne : SelectJob1,SelectJobTwo : SelectJob2,DoCompare : RunCompare,ComparisonDisplay : CompareAgain"	|	Run Compare Again	|	|	|										
|	Test Case 14 (Key = 1.1.4.0.0.0.1.1.1.2.)	|	"CurrentJob : empty,JobCount : GreaterThanTwoJobs,MainMenu : Compare Job Offers,CurrentJobOptions : <n/a>,NewJobOfferOptions : <n/a>,NewJobOfferAdditionalOptions : <n/a>,SelectJobOne : SelectJob1,SelectJobTwo : SelectJob2,DoCompare : RunCompare,ComparisonDisplay : MainMenu"	|	"Compare, Go back to main menu"	|	|	|										
|	Test Case 15 (Key = 1.2.1.1.0.0.0.0.0.0.)	|	"CurrentJob : empty,JobCount : LessThanTwoJobs,MainMenu : Enter Current Job,CurrentJobOptions : Save,NewJobOfferOptions : <n/a>,NewJobOfferAdditionalOptions : <n/a>,SelectJobOne : <n/a>,SelectJobTwo : <n/a>,DoCompare : <n/a>,ComparisonDisplay : <n/a>"	|	"Save Current Job, Back To Main Menu"	|	|	|										
|	Test Case 16 (Key = 1.2.1.2.0.0.0.0.0.0.)	|	"CurrentJob : empty,JobCount : LessThanTwoJobs,MainMenu : Enter Current Job,CurrentJobOptions : CancelExitWithoutSaving,NewJobOfferOptions : <n/a>,NewJobOfferAdditionalOptions : <n/a>,SelectJobOne : <n/a>,SelectJobTwo : <n/a>,DoCompare : <n/a>,ComparisonDisplay : <n/a>"	|	"Cancel Changes to Current Job, Back to Main Menu"	|	|	|										
|	Test Case 17 (Key = 1.2.2.0.1.1.0.0.0.0.)	|	"CurrentJob : empty,JobCount : LessThanTwoJobs,MainMenu : Enter Job Offer,CurrentJobOptions : <n/a>,NewJobOfferOptions : Save,NewJobOfferAdditionalOptions : EnterAnotherOffer,SelectJobOne : <n/a>,SelectJobTwo : <n/a>,DoCompare : <n/a>,ComparisonDisplay : <n/a>"	|	"Save Current Offer, Enter Another"	|	|	|										
|	Test Case 18 (Key = 1.2.2.0.1.2.0.0.0.0.)	|	"CurrentJob : empty,JobCount : LessThanTwoJobs,MainMenu : Enter Job Offer,CurrentJobOptions : <n/a>,NewJobOfferOptions : Save,NewJobOfferAdditionalOptions : ReturnToMainMenu,SelectJobOne : <n/a>,SelectJobTwo : <n/a>,DoCompare : <n/a>,ComparisonDisplay : <n/a>"	|	"Save Current Offer, Back to Main Menu"	|	|	|										
|	Test Case 19 (Key = 1.2.2.0.2.0.0.0.0.0.)	|	"CurrentJob : empty,JobCount : LessThanTwoJobs,MainMenu : Enter Job Offer,CurrentJobOptions : <n/a>,NewJobOfferOptions : CancelExitWithoutSaving,NewJobOfferAdditionalOptions : <n/a>,SelectJobOne : <n/a>,SelectJobTwo : <n/a>,DoCompare : <n/a>,ComparisonDisplay : <n/a>"	|	"Cancel Changes to New Job Offer, Back to Main Menu"	|	|	|										
|	Test Case 20 (Key = 1.2.3.0.0.0.0.0.0.0.)	|	"CurrentJob : empty,JobCount : LessThanTwoJobs,MainMenu : Change Comparison Settings,CurrentJobOptions : <n/a>,NewJobOfferOptions : <n/a>,NewJobOfferAdditionalOptions : <n/a>,SelectJobOne : <n/a>,SelectJobTwo : <n/a>,DoCompare : <n/a>,ComparisonDisplay : <n/a>"	|	"Change Comparison Settings, Back To Main Menu"	|	|	|										
|	Test Case 21 (Key = 2.1.1.1.0.0.1.1.1.1.)	|	"CurrentJob : notEmpty,JobCount : GreaterThanTwoJobs,MainMenu : Enter Current Job,CurrentJobOptions : Save,NewJobOfferOptions : <n/a>,NewJobOfferAdditionalOptions : <n/a>,SelectJobOne : SelectJob1,SelectJobTwo : SelectJob2,DoCompare : RunCompare,ComparisonDisplay : CompareAgain"	|	Run Compare Again	|	|	|										
|	Test Case 22 (Key = 2.1.1.1.0.0.1.1.1.2.)	|	"CurrentJob : notEmpty,JobCount : GreaterThanTwoJobs,MainMenu : Enter Current Job,CurrentJobOptions : Save,NewJobOfferOptions : <n/a>,NewJobOfferAdditionalOptions : <n/a>,SelectJobOne : SelectJob1,SelectJobTwo : SelectJob2,DoCompare : RunCompare,ComparisonDisplay : MainMenu"	|	"Compare, Go back to main menu"	|	|	|										
|	Test Case 23 (Key = 2.1.1.2.0.0.1.1.1.1.)	|	"CurrentJob : notEmpty,JobCount : GreaterThanTwoJobs,MainMenu : Enter Current Job,CurrentJobOptions : CancelExitWithoutSaving,NewJobOfferOptions : <n/a>,NewJobOfferAdditionalOptions : <n/a>,SelectJobOne : SelectJob1,SelectJobTwo : SelectJob2,DoCompare : RunCompare,ComparisonDisplay : CompareAgain"	|	Run Compare Again	|	|	|										
|	Test Case 24 (Key = 2.1.1.2.0.0.1.1.1.2.)	|	"CurrentJob : notEmpty,JobCount : GreaterThanTwoJobs,MainMenu : Enter Current Job,CurrentJobOptions : CancelExitWithoutSaving,NewJobOfferOptions : <n/a>,NewJobOfferAdditionalOptions : <n/a>,SelectJobOne : SelectJob1,SelectJobTwo : SelectJob2,DoCompare : RunCompare,ComparisonDisplay : MainMenu"	|	"Compare, Go back to main menu"	|	|	|										
|	Test Case 25 (Key = 2.1.2.0.1.1.1.1.1.1.)	|	"CurrentJob : notEmpty,JobCount : GreaterThanTwoJobs,MainMenu : Enter Job Offer,CurrentJobOptions : <n/a>,NewJobOfferOptions : Save,NewJobOfferAdditionalOptions : EnterAnotherOffer,SelectJobOne : SelectJob1,SelectJobTwo : SelectJob2,DoCompare : RunCompare,ComparisonDisplay : CompareAgain"	|	Run Compare Again	|	|	|										
|	Test Case 26 (Key = 2.1.2.0.1.1.1.1.1.2.)	|	"CurrentJob : notEmpty,JobCount : GreaterThanTwoJobs,MainMenu : Enter Job Offer,CurrentJobOptions : <n/a>,NewJobOfferOptions : Save,NewJobOfferAdditionalOptions : EnterAnotherOffer,SelectJobOne : SelectJob1,SelectJobTwo : SelectJob2,DoCompare : RunCompare,ComparisonDisplay : MainMenu"	|	"Compare, Go back to main menu"	|	|	|										
|	Test Case 27 (Key = 2.1.2.0.1.2.1.1.1.1.)	|	"CurrentJob : notEmpty,JobCount : GreaterThanTwoJobs,MainMenu : Enter Job Offer,CurrentJobOptions : <n/a>,NewJobOfferOptions : Save,NewJobOfferAdditionalOptions : ReturnToMainMenu,SelectJobOne : SelectJob1,SelectJobTwo : SelectJob2,DoCompare : RunCompare,ComparisonDisplay : CompareAgain"	|	Run Compare Again	|	|	|										
|	Test Case 28 (Key = 2.1.2.0.1.2.1.1.1.2.)	|	"CurrentJob : notEmpty,JobCount : GreaterThanTwoJobs,MainMenu : Enter Job Offer,CurrentJobOptions : <n/a>,NewJobOfferOptions : Save,NewJobOfferAdditionalOptions : ReturnToMainMenu,SelectJobOne : SelectJob1,SelectJobTwo : SelectJob2,DoCompare : RunCompare,ComparisonDisplay : MainMenu"	|	"Compare, Go back to main menu"	|	|	|										
|	Test Case 29 (Key = 2.1.2.0.1.3.1.1.1.1.)	|	"CurrentJob : notEmpty,JobCount : GreaterThanTwoJobs,MainMenu : Enter Job Offer,CurrentJobOptions : <n/a>,NewJobOfferOptions : Save,NewJobOfferAdditionalOptions : CompareToCurrentJob,SelectJobOne : SelectJob1,SelectJobTwo : SelectJob2,DoCompare : RunCompare,ComparisonDisplay : CompareAgain"	|	Run Compare Again	|	|	|										
|	Test Case 30 (Key = 2.1.2.0.1.3.1.1.1.2.)	|	"CurrentJob : notEmpty,JobCount : GreaterThanTwoJobs,MainMenu : Enter Job Offer,CurrentJobOptions : <n/a>,NewJobOfferOptions : Save,NewJobOfferAdditionalOptions : CompareToCurrentJob,SelectJobOne : SelectJob1,SelectJobTwo : SelectJob2,DoCompare : RunCompare,ComparisonDisplay : MainMenu"	|	"Compare, Go back to main menu"	|	|	|										
|	Test Case 31 (Key = 2.1.2.0.2.0.1.1.1.1.)	|	"CurrentJob : notEmpty,JobCount : GreaterThanTwoJobs,MainMenu : Enter Job Offer,CurrentJobOptions : <n/a>,NewJobOfferOptions : CancelExitWithoutSaving,NewJobOfferAdditionalOptions : <n/a>,SelectJobOne : SelectJob1,SelectJobTwo : SelectJob2,DoCompare : RunCompare,ComparisonDisplay : CompareAgain"	|	Run Compare Again	|	|	|										
|	Test Case 32 (Key = 2.1.2.0.2.0.1.1.1.2.)	|	"CurrentJob : notEmpty,JobCount : GreaterThanTwoJobs,MainMenu : Enter Job Offer,CurrentJobOptions : <n/a>,NewJobOfferOptions : CancelExitWithoutSaving,NewJobOfferAdditionalOptions : <n/a>,SelectJobOne : SelectJob1,SelectJobTwo : SelectJob2,DoCompare : RunCompare,ComparisonDisplay : MainMenu"	|	"Compare, Go back to main menu"	|	|	|										
|	Test Case 33 (Key = 2.1.3.0.0.0.1.1.1.1.)	|	"CurrentJob : notEmpty,JobCount : GreaterThanTwoJobs,MainMenu : Change Comparison Settings,CurrentJobOptions : <n/a>,NewJobOfferOptions : <n/a>,NewJobOfferAdditionalOptions : <n/a>,SelectJobOne : SelectJob1,SelectJobTwo : SelectJob2,DoCompare : RunCompare,ComparisonDisplay : CompareAgain"	|	Run Compare Again	|	|	|										
|	Test Case 34 (Key = 2.1.3.0.0.0.1.1.1.2.)	|	"CurrentJob : notEmpty,JobCount : GreaterThanTwoJobs,MainMenu : Change Comparison Settings,CurrentJobOptions : <n/a>,NewJobOfferOptions : <n/a>,NewJobOfferAdditionalOptions : <n/a>,SelectJobOne : SelectJob1,SelectJobTwo : SelectJob2,DoCompare : RunCompare,ComparisonDisplay : MainMenu"	|	"Compare, Go back to main menu"	|	|	|										
|	Test Case 35 (Key = 2.1.4.0.0.0.1.1.1.1.)	|	"CurrentJob : notEmpty,JobCount : GreaterThanTwoJobs,MainMenu : Compare Job Offers,CurrentJobOptions : <n/a>,NewJobOfferOptions : <n/a>,NewJobOfferAdditionalOptions : <n/a>,SelectJobOne : SelectJob1,SelectJobTwo : SelectJob2,DoCompare : RunCompare,ComparisonDisplay : CompareAgain"	|	Run Compare Again	|	|	|										
|	Test Case 36 (Key = 2.1.4.0.0.0.1.1.1.2.)	|	"CurrentJob : notEmpty,JobCount : GreaterThanTwoJobs,MainMenu : Compare Job Offers,CurrentJobOptions : <n/a>,NewJobOfferOptions : <n/a>,NewJobOfferAdditionalOptions : <n/a>,SelectJobOne : SelectJob1,SelectJobTwo : SelectJob2,DoCompare : RunCompare,ComparisonDisplay : MainMenu"	|	"Compare, Go back to main menu"	|	|	|										
|	Test Case 37 (Key = 2.2.1.1.0.0.0.0.0.0.)	|	"CurrentJob : notEmpty,JobCount : LessThanTwoJobs,MainMenu : Enter Current Job,CurrentJobOptions : Save,NewJobOfferOptions : <n/a>,NewJobOfferAdditionalOptions : <n/a>,SelectJobOne : <n/a>,SelectJobTwo : <n/a>,DoCompare : <n/a>,ComparisonDisplay : <n/a>"	|	"Save Current Job, Back To Main Menu"	|	|	|										
|	Test Case 38 (Key = 2.2.1.2.0.0.0.0.0.0.)	|	"CurrentJob : notEmpty,JobCount : LessThanTwoJobs,MainMenu : Enter Current Job,CurrentJobOptions : CancelExitWithoutSaving,NewJobOfferOptions : <n/a>,NewJobOfferAdditionalOptions : <n/a>,SelectJobOne : <n/a>,SelectJobTwo : <n/a>,DoCompare : <n/a>,ComparisonDisplay : <n/a>"	|	"Cancel Changes to Current Job, Back to Main Menu"	|	|	|										
|	Test Case 39 (Key = 2.2.2.0.1.1.0.0.0.0.)	|	"CurrentJob : notEmpty,JobCount : LessThanTwoJobs,MainMenu : Enter Job Offer,CurrentJobOptions : <n/a>,NewJobOfferOptions : Save,NewJobOfferAdditionalOptions : EnterAnotherOffer,SelectJobOne : <n/a>,SelectJobTwo : <n/a>,DoCompare : <n/a>,ComparisonDisplay : <n/a>"	|	"Save New Job Offer, Enter Another Offer"	|	|	|										
|	Test Case 40 (Key = 2.2.2.0.1.2.0.0.0.0.)	|	"CurrentJob : notEmpty,JobCount : LessThanTwoJobs,MainMenu : Enter Job Offer,CurrentJobOptions : <n/a>,NewJobOfferOptions : Save,NewJobOfferAdditionalOptions : ReturnToMainMenu,SelectJobOne : <n/a>,SelectJobTwo : <n/a>,DoCompare : <n/a>,ComparisonDisplay : <n/a>"	|	"Save New Job Offer, Back to Main Menu"	|	|	|										
|	Test Case 41 (Key = 2.2.2.0.1.3.1.1.1.1.)	|	"CurrentJob : notEmpty,JobCount : LessThanTwoJobs,MainMenu : Enter Job Offer,CurrentJobOptions : <n/a>,NewJobOfferOptions : Save,NewJobOfferAdditionalOptions : CompareToCurrentJob,SelectJobOne : SelectJob1,SelectJobTwo : SelectJob2,DoCompare : RunCompare,ComparisonDisplay : CompareAgain"	|	"Save New Job Offer, Compare to Current Job, Compare Again"	|	|	|										
|	Test Case 42 (Key = 2.2.2.0.1.3.1.1.1.2.)	|	"CurrentJob : notEmpty,JobCount : LessThanTwoJobs,MainMenu : Enter Job Offer,CurrentJobOptions : <n/a>,NewJobOfferOptions : Save,NewJobOfferAdditionalOptions : CompareToCurrentJob,SelectJobOne : SelectJob1,SelectJobTwo : SelectJob2,DoCompare : RunCompare,ComparisonDisplay : MainMenu"	|	"Save New Job Offer, Compare to Current Job, Back to Main Menu"	|	|	|										
|	Test Case 43 (Key = 2.2.2.0.2.0.0.0.0.0.)	|	"CurrentJob : notEmpty,JobCount : LessThanTwoJobs,MainMenu : Enter Job Offer,CurrentJobOptions : <n/a>,NewJobOfferOptions : CancelExitWithoutSaving,NewJobOfferAdditionalOptions : <n/a>,SelectJobOne : <n/a>,SelectJobTwo : <n/a>,DoCompare : <n/a>,ComparisonDisplay : <n/a>"	|	"Cancel new Job Offer, Back to Main Menu"	|	|	|										
|	Test Case 44 (Key = 2.2.3.0.0.0.0.0.0.0.)	|	"CurrentJob : notEmpty,JobCount : LessThanTwoJobs,MainMenu : Change Comparison Settings,CurrentJobOptions : <n/a>,NewJobOfferOptions : <n/a>,NewJobOfferAdditionalOptions : <n/a>,SelectJobOne : <n/a>,SelectJobTwo : <n/a>,DoCompare : <n/a>,ComparisonDisplay : <n/a>"	|	"Change Comparison Settings, Back To Main Menu"	|	|	|		

