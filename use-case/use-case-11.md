# USE CASE: 11 Produce a Report on City Populations by District

## CHARACTERISTIC INFORMATION

### Goal in Context
As an Admin, I want a report of all the cities in each district organized by population so that I can analyze district-level urban population.

### Scope
System

### Level
Primary task

### Preconditions
* Admin has accessed to the system.
* World Population data is already stored in the database.

### Success End Condition
A correctly formatted report is generated, with cities grouped by district, sorted by population in descending order, and is displayed to the Admin.

### Failed End Condition
No report is produced.

### Primary Actor
Admin

### Trigger
Admin requests a report on the population of all cities with their related district.

## MAIN SUCCESS SCENARIO
1.	All the cities data in each district organized by population is requested by Admin
2.	A complete report of all cities’ population data in each district is generated and sorted from largest to smallest.
3.	A report is displayed to the admin.

## EXTENSIONS
None

## SUB-VARIATIONS
None

## SCHEDULE

**DUE DATE**: Release 0.1.0.1