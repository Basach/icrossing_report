# Automation UI flow with Selenium, Java and TestNG.

Two scenarios were worked:
- Straightforward flow replicating user behavior (clicks and browsing) and collecting the expected information for the report.
- A flow where tire size and diameter hyperlinks were stored in a List in order to hit urls directly to collect the expected information for the report.

This implementation is based in **Page Object Model (POM).**

## Dependencies
- Java
- Selenium
- TestNG
- OpenCSV
- Tinylog
- Maven
- Commons

## Run a Test
To run a test use the following command in the terminal
```agsl
mvn test
```
Or go to `resources/suites/test_suite.xml`, right click on the xml file and select `Run...`

## CSV Report
The CSV report is created during the test run with the specified file name plus currentTimeMillis in order to avoid updating the same report over and over.
The CSV file contains three column titles tire_size, tire_count and url. Each test will collect such information and add it to the existing CSV file.
At the end of the test run, the reporter will add the TEST RUN TIME at the bottom of the file before saving it.

## Notes
As I was not able to find a scenario where the tire stock is empty (edge case) I took the approach I though fit better in the method ```ResultPage.getTireAmount()```. 