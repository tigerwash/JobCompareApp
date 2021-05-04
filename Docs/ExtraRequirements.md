# Extra Requirement

Version 1.0 - 3/20 updated cost of living index description

**Author**: Team115

## Formulas to calculate the scores for ranking:


AYS + AYB + (RBP * AYS) + (LT * AYS / 260) - ((260 - 52 * RWT) * (AYS / 260) / 8)


** AYS = yearly salary adjusted for cost of living
** AYB = yearly bonus adjusted for cost of living
** RBP = retirement benefits percentage
** LT = leave time
** RWT = telework days per week

## Cost of living in the location is sourced from ![this page](https://www.expatistan.com/cost-of-living/index/north-america).

To simplify the process, we loaded the cost of living index from top 102 cities to the database. We will look up for the index when calculating the scores.
