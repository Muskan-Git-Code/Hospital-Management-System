## Environment:
- Java version: 1.8
- Maven version: 3.*
- Spring Boot version: 2.2.1.RELEASE

## Read-Only Files:
- src/test/*

## Data:
Sample example of JSON data object:
```json
{
  "id": 7,
  "name": "pqr7",
  "age": 6,
  "email": "pqr7@gmail.com",
  "phone": "1247785675",
  "room": "A4",
  "doctorName": "abc1",
  "doctorEmail": "abc1@gmail.com",
  "admitDate": "2023-08-05T15:32:44.320",
  "expenses": 8000,
  "status": "Discharged"
}
```

## Requirements:
There is a tiny `Hospital Management System` and for this problem its scope is limited to just maintaining hospital staff and patient details into database.

Following architectural components for this application have already been provide as a standard Java classes:
* `Controller`: controller class where you have to define REST endpoints for POST and GET methods
* `Service`: service class expected to be used by controller class to save/retrieve details to/from repository
* `Repository`: repository class expected to be used by service class to save/retrieve details to/from database
* `Entity`: Model class to hold the detail information

## System: 
Hospital staff can login into the system from their staff Id, and can see the patient details

## The task is:
1. Fetch all patients admitted in hospital.
2. Fetch all patients discharged from hospital.

## Assumption:
1. All doctors available in Staff System is available.
2. A patient can be admitted under one doctor at a time, but a doctor can treat many patients at the same time i.e. One to Many relationship mapping.

## Corner Cases handled:
1. Doctor appointed to a patient should be a registered doctor into our system.
2. Discharge is possible for patients already in Admitted State.
3. Duplicate records and null records not allowed. 
4. Patient already present in database in discharged state, can get admitted again by updating the existing record, no need to register again.
5. Mark room available, only if any already admitted patient is not there. 


The project by default supports the use of the H2 database. So that you can directly run the testcases and test the result.





