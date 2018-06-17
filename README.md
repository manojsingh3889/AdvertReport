# Advert Report

Advert Report is a web services for reporting on Ad categories. It provide reports generated from static csv data by.


> ###### [Java Documentation](https://manojsingh3889.github.io/JavaDocs/AdvertReport-docs/)

>## Technology stack
- `Java 8` 
- `Maven` 
- `Spring-boot`  
- `Spring-rest` 
- `Spring-data-jpa`
- `Hibernate-JPA-annotations`
- `h2-database`
- `Spring-boot-test` 
- `Spring-boot-Log4j`
- `Junit`
- `Mockito`

>## Instructions

**Note :** If your system doesn't have maven installed then you can use **./mvnw**(Linux) or **./mvnw.cmd**(windows) as a replacement for **mvn** for e.g. ***mvn clean install*** can be written as ***./mvnw clean install*** or ***./mvnw.cmd clean install*** . Only per-requite in this case is that your system should contain **java 8** installed and has **$JAVA_HOME** variable pointing to java home directory.

#### Build
1. Go to home directory,  run **mvn clean install**
2. find artifact in target
	1.  ***target/AdvertReport-1.0-SNAPSHOT.war*** standalone version which can be run without any server
	2.  ***target/AdvertReport-1.0-SNAPSHOT.war*** standalone version which can be run without any server

#### Testing
Although test case get executed in build as well,But this step is recommended if you want to get run only test.
Contains more than 70 test cases.

1. Go to home directory,  run any one mentioned below:
	1. `mvn test` (fast but no consolidate report rather you can find individuals reports in target/surefire-reports )
	2. `mvn test surefire-report:report-only` (Fast but generates basic HTML in target/site/surefire-report.html)
	2. `mvn test site` (Slowest but generates advance HTML in target/site/surefire-report.html)
2. find test report target folder accordingly.

#### Run

There are several way to execute the artifact:

	

1. Run using maven spring-boot plugin ` mvn spring-boot:run [Options]` For e.g.
	- `mvn spring-boot:run -Dserver.port=8580` (**Recommended**)
	- `mvn spring-boot:run`
	- `mvn spring-boot:run -Dlogging.level.root=DEBUG -Dserver.port=8580 `
2. Run war file directly using `java -jar [options] target/AdvertReport-1.0-SNAPSHOT.war`. e.g.
	- `java -jar -Dserver.port=8580 AdvertReport-1.0-SNAPSHOT.war` (**Recommended**)
	- `java -jar AdvertReport-1.0-SNAPSHOT.war`
	- `java -jar -Dlogging.level.root=DEBUG -Dserver.port=8580 AdvertReport-1.0-SNAPSHOT.war`
3. Drop file in application server such as Jboss or tomcat.

		
		

**Options**

Option can be any spring level or java level command line VM argument, some example are
			
1. `-Dlogging.level.root=DEBUG` to provide more verbose logs
2. `-Dserver.port=8580` (**recommended**), otherwise application will take random port. 
	    
#### RestApi

- **Mandatory API**
	1. Month-Site wise report `GET http://localhost:8080/reports?month=<month>&site=<site>`
  
- **Bonus API**
	1. Month wise aggregated report `GET http://localhost:8080/reports?month=<month>`
	

- **Extra API - List version of above APIs**
	1. Month-Site wise report into list `GET http://localhost:8080/reports/list?month=<month>&site=<site>`
	2. Month wise aggregated report into list `GET http://localhost:8080/reports/list?month=<month>`
		 

># Requirement

To create a Restful web service that can offer reporting advertising data, part of which
is extracted from a csv file and the rest being calculated as additional metrics.

>#### Scope
- Implement only the backend and leave the creation of a colourful and flashy frontend over to the frontend developers.
- Your web service should use Java SDK 8+, Spring and Maven since we use them heavily.
  You can alternatively use Gradle instead of Maven if you feel more comfortable.
- Since we put quite some emphasis on __tests__, we would also like to see how you verify the functionality
  of your service by writing some tests.

>#### Guidance
- Together with the implementation, provide a short description for other developers on how to run and use your application.
  Run and build __instructions__ as well as general __documentation__ are evaluated in a positive manner,
  as it indicates you know how to work in that environment (spoiler alert: we love documentation!).
- We also like to see your 'long division' i.e. the working out of the problem in code, so please use a public
  git repository and add commits for significant working steps.
- Usage of standard JDK libraries is encouraged, though external libraries are not forbidden to use.
- Create a private git repository for your solution and send us the git bundle once done.

##### Definitions
Find below some definitions that will allow you to better understand the business scope of the coding challenge.
They are largely extracted from [Appnexus wiki](https://wiki.appnexus.com/display/industry/Online+Advertising+and+Ad+Tech+Glossary#OnlineAdvertisingandAdTechGlossary-publisher).

- **Advertiser**: An entity that pays money to get his advertisement shown.
- **Publisher**: The one who gets money for showing the ads on his site.
- **Creative**: The actual graphical advertisement itself.
- **Request**: In Ad Tech, it refers to a request for a creative or ad tag.
- **Demand**: Advertising demand, or the desire to buy ad space and display creatives.
- **Impression**: A creative served to a single user at a single point in time.
- **Revenue**: The amount of money a publisher earns from ads showing.
- **Conversion**: When a user makes a purchase, or performs some other desired action in response to an ad.
- **CPC**: Cost per click. A payment model in which advertisers pay each time a user clicks on their advertisement.
- **CPM**: Cost per mille or thousand (mille = thousand in Latin). A pricing model in which advertisers pay
  for every 1000 impressions of their advertisement served. This is the standard basic pricing model for online advertising.
- **CTR (%)**: Click-through rate.  Expressed as a percentage. Literally, the ratio of users who click on a specific
  link to the number of total users who view an advertisement.

  **CTR = (clicks ÷ impressions) × 100%**

- **CR (%)**: Conversion rate. The ratio of conversions to the number of impressions

  **CR = (conversions ÷ impressions) × 100%**

- **Fill Rate**: The ratio of impressions to the number of requests. It varies by inventory.

  **Fill Rate = (impressions ÷ requests) × 100%**

- **ecpm**: Effective Cost Per Thousand. A translation from CPM, expressed as such from a publisher's point of view.

  **ecpm = (revenue × 1000) ÷ impressions**

>#### Task Description
In the root folder you will find two csv files which contain publisher advertising reporting data for the months of
January [2018_01_report.csv](2018_01_report.csv) and February [2018_02_report.csv](2018_02_report.csv).
Specifically, it lists the 4 available sites of the publisher (desktop and mobile web plus android and iOS apps)
broken down into five dimensions (requests, impressions, clicks, conversions and revenue (USD)).

Your task is to
1. Parse the file and store it in-memory.
   Kudos and additional points earned for using JPA together with any kind of underlying database technology
   for data persistence (mysql, derby, H2, HSQLDB etc).
2. Calculate the following additional metrics (as explained above) and also store them along with the parsed data
   from the CSV file:
   * CTR
   * CR
   * Fill Rate
   * ecpm

   How you store them is completely up to you.
3. Design and expose a RESTful API that can read the reporting data which was parsed and generated in the previous step
   using the site and month as input arguments. For simplicity, we assume no reporting exists for previous years
   so you don't have to take the year 2018 into account.
4. Use the following keys as the input argument to map to the actual sites:
   * desktop_web: "desktop web"
   * mobile_web: "mobile web"
   * android: "android"
   * iOS: "iOS"
5. Regarding months you must provide a flexible API to accept all of the following options:
   * numeric (ranging from 1-12) that map to the corresponding months (`1` for `January`, `2` for `February` etc)
   * first 3 letters of the month (`Jan` for `January`, `Feb` for `February` etc)
   * full name of the month (case insensitive)
6.  The API must then return a JSON object with all the following metrics defined inside the object itself:
   * month
   * site
   * requests
   * impressions
   * clicks
   * conversions
   * revenue
   * CTR
   * CR
   * fill_rate
   * ecpm
7. Assume a 2-digit precision for all double values.
8. The design of the API is completely up to you. For example, you can either use PathVariable or
   RequestParam or a combination of the two.
   You can find some examples in the following section.

###### Examples
* GET http://localhost:8080/reports?month=jan&site=desktop_mobile

  or

  GET http://localhost:8080/reports/jan/desktop_mobile

  or

  GET http://localhost:8080/reports/jan?site=desktop_mobile

  JSON Response:
  ``` json
      {
        "month" : "January",
        "site" : "desktop web",
        "requests" : "some_value",
        "impressions" : "some_value",
        "clicks" : "some_value",
        "conversions" : "some_value",
        "revenue" : "some_value",
        "CTR" : "some_value",
        "CR" : "some_value",
        "fill_rate" : "some_value",
        "ecpm" : "some_value"
      }
      ```

* GET http://localhost:8080/reports?month=2&site=ios
  JSON Response:
  ``` json
      {
        "month" : "February",
        "site" : "iOS",
        "requests" : "some_value",
        "impressions" : "some_value",
        "clicks" : "some_value",
        "conversions" : "some_value",
        "revenue" : "some_value",
        "CTR" : "some_value",
        "CR" : "some_value",
        "fill_rate" : "some_value",
        "ecpm" : "some_value"
      }
      ```

* Extra points (not mandatory) if you support aggregate reports like
  - GET http://localhost:8080/reports?month=january
  The JSON Response will contain the metrics for January for __all sites__ (notice the absence of "site" attribute):
  ``` json
      {
        "month" : "January",
        "requests" : "some_value",
        "impressions" : "some_value",
        "clicks" : "some_value",
        "conversions" : "some_value",
        "revenue" : "some_value",
        "CTR" : "some_value",
        "CR" : "some_value",
        "fill_rate" : "some_value",
        "ecpm" : "some_value"
      }
  ```
  - GET http://localhost:8080/reports?site=android
  The JSON response will contain the metrics for the android site for __all months__ (notice the absence of "month" attribute):
  ``` json
      {
        "site" : "android",
        "requests" : "some_value",
        "impressions" : "some_value",
        "clicks" : "some_value",
        "conversions" : "some_value",
        "revenue" : "some_value",
        "CTR" : "some_value",
        "CR" : "some_value",
        "fill_rate" : "some_value",
        "ecpm" : "some_value"
      }
  ```
 The above examples are just for guidance, feel free to follow them but don't hesitate to come up with your own approach.

 Good luck!
