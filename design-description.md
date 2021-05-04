# Design Description

1. When the app is started, the user is presented with the main menu, which allows the user to (1) enter or edit current job details, (2) enter job offers, (3) adjust the comparison settings, or (4) compare job offers (disabled if no job offers were entered yet).  

	 **The mainMenu class in my design initiates the app and offers four methods for each required operations. when the app is started, jobOfferService and comparisonService is initialized to be used for tracking job offers and comparison. When the app is started, the compare job offer is disabled. Whenever we return to the main menu from other views, the retunToMenu method calls the isAbleToCompare method in jobOfferService to check if we should enable the compre job offer option. The underlying code for isAbleToCompare checks if numberOfJobs is greater than 2.**


2. When choosing to enter current job details, a user will:
Be shown a user interface to enter (if it is the first time) or edit all of the details of their current job, which consist of:
	* Title
	* Company
	* Location (entered as city and state)
	* Cost of living in the location (expressed as an index)
	* Yearly salary
	* Yearly bonus
	* Allowed weekly telework days (expressed as the number of days per week allowed for remote work, inclusively between 0 and 5)
	* Retirement benefits (as percentage matched)
	* Leave time (vacation days and holiday and/or sick leave, as a single overall number of days)
	Be able to either save the job details or cancel and exit without saving, returning in both cases to the main menu.

	 **When entering the current job details for the first time, the jobOfferService will have a null currentJob attribute. After the user hit save, a new currentJob instance is being created through saveCurrentJob() method and save to currentJob attribute. numberOfJobs will also increase one. Cost of living index is calculated by the getCostOfLiving() method which uses the user entered city and state to get the corresponding index. If the curretJob attribute already contains the current job details, populateCurrentJobDetail method will pull the current job detail from the object and populate the data for user to edit. And in this case, updateCurrentJob is called to save the changes to the current job. Whenever the save data methods are called, we also connects to IOSerializer to serialize the current job data and save it to the file system for data recording. If the user hit exit, then the returnToMenu is called without other methods.**


3. When choosing to enter job offers, a user will:
	* Be shown a user interface to enter all of the details of the offer, which are the same ones listed above for the current job.
	* Be able to either save the job offer details or cancel.
	* Be able to (1) enter another offer, (2) return to the main menu, or (3) compare the offer with the current job details (if present).

	 **When the user decided to enter job offers, enterJobOffer in mainMenu is called and the view for entering job details should show up for user to enter the information. saveJobOffer is called when the user choose to save the offer and it creates a jobDetail object and append it to the offers list. IOSerializer also saves the list of job offers to the file system. After successfully save the job offer, all details on the view should be cleared. If currentJob attribute is not null, the option to compare offer with the current job is enabled. When the user choose to compare offer with current job then the displayTable is called with the newly entered job offer and the current job and shows the comparision result. Cancel or return to the main menu calles the returnToMenu method that opens the menu view.**

4. When adjusting the comparison settings, the user can assign integer weights to:
	* Yearly salary
	* Yearly bonus
	* Allowed weekly telework days
	* Retirement benefits
	* Leave time
	If no weights are assigned, all factors are considered equal.

	 **To set the comparision setting, saveSettings simply updates the weights using the user's input from the GUI. When the weights are updated, IOSerializer also save the data for future sessions.**

5. When choosing to compare job offers, a user will:
	* Be shown a list of job offers, displayed as Title and Company, ranked from best to worst (see below for details), and including the current job (if present), clearly indicated.
	* Select two jobs to compare and trigger the comparison.
	* Be shown a table comparing the two jobs, displaying, for each job:
		** Title
		** Company
		** Location
		** Yearly salary adjusted for cost of living
		** Yearly bonus adjusted for cost of living
		** Allowed weekly telework days
		** Retirement benefits (as percentage matched)
		** Leave time
	* Be offered to perform another comparison or go back to the main menu.

	 **When the user choose to compare job offers, the getAllOffersRanking is called which takes input of all jobs(including current job if present)objects and returns all the jobs after ranking them with the scores. After user choose two jobs and trigger the comparison, displayTable method is called and passed with the two selected jobs. Then the table should be displayed with the job details and we can either call compareOffers again or returnToMenu.**

6. When ranking jobs, a job’s score is computed as the weighted sum of:

	AYS + AYB + (RBP * AYS) + (LT * AYS / 260) - ((260 - 52 * RWT) * (AYS / 260) / 8)

	where:
	AYS = yearly salary adjusted for cost of living
	AYB = yearly bonus adjusted for cost of living
	RBP = retirement benefits percentage
	LT = leave time
	RWT = telework days per week
	The rationale for the RWT subformula is:
	value of an employee hour = (AYS / 260) / 8
	commute hours per year (assuming a 1-hour/day commute) =
	1 * (260 - 52 * RWT)
	therefore travel-time cost = (260 - 52 * RWT) * (AYS / 260) / 8

	For example, if the weights are 2 for the yearly salary, 2 for the retirement benefits, and 1 for all other factors, the score would be computed as:


	2/7 * AYS + 1/7 * AYB + 2/7 * (RBP * AYS) + 1/7 * (LT * AYS / 260) - 1/7 * ((260 - 52 * RWT) * (AYS / 260) / 8)

	 **The calculation of the scores are calculated when getAllOfferRanking in comparisonService is called. getAllOfferRanking takes input of all job detail objects and return a sorted list of jobDetail sorted with the job scores.**

7. The user interface must be intuitive and responsive.

	 **This will be handled entirely within the GUI implementation.**


8. For simplicity, you may assume there is a single system running the app (no communication or saving between devices is necessary).

	 **The design does not consider database or web communication. Datas are only saved on the single device.**
