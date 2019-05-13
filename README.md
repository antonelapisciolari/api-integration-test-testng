Java test - API Integration tests  

## Description
Here I implemented 2 API tests: 

 
#### Test Scenarios: 
1. 'tenFirstPhotosFromSolAndEarthCuriosityAreTheSameTest':  
    Get first 10 Mars photos made by "Curiosity" on 1000 sol.
    Get the same 10 Mars photos made by "Curiosity" on earth date that is equals 1000 Mars sol.
    Compare downloaded images and metadata from API. Test fails in case of any difference.
    
2. 'noCameraTook10TimesMorePhotosThanOtherCuriosityTest':  
    Using NASA’s API determine how many pictures were made by each camera (by Curiosity on 1000 sol.). 
    If any camera made 10 times more images than any other - test fails


## Requirement:
- Java 1.8 (1.8.0_102)
- Gradle (4.10)

## Main Usages
- **Rest Assured** - for writing powerful, maintainable tests for RESTful APIs
- **TestNG** - testing framework 
- **Slf4j** - Simple Logging Facade for Java 
 
## How to Execute the tests
1. Unzip file in desired folder
2. Open command line from folder
3. Run command ./gradlew integration 


## Report
Once you've run the tests, in the cmd line execution you'll see the link to the report generated (if it failed) if not the report can be found in SDET-test/build/reports/tests/integration/index.html


