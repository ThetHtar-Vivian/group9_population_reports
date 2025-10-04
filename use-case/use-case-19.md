# USE CASE: 19 Produce a Report on Capital City Populations by Region

## CHARACTERISTIC INFORMATION

### Goal in Context
As an Admin, I want a report of all capital cities in each region organized by population so that I can understand the relative population sizes of regional capitals.

### Scope
System

### Level
Primary task

### Preconditions
* Admin has accessed to the system.
* World Population data is already stored in the database.

### Success End Condition
A correctly formatted report is generated, with capital cities grouped by region, sorted by population in descending order, and is displayed to the Admin.

### Failed End Condition
No report is produced.

### Primary Actor
Admin

### Trigger
Admin requests a report on capital city populations by region.

## MAIN SUCCESS SCENARIO
1.	All the capital cities data in each region organized by population is requested by Admin.
2.	A complete report of all capital cities’ population data in each region is generated and sorted from largest to smallest.
3.	A report is displayed to the admin.

## EXTENSIONS
None

## SUB-VARIATIONS
None

## SCHEDULE

**DUE DATE**: Release 0.1.0.2