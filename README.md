# AdvertiseCore

AdvertReport is a webservices for reporting on Ad categories.<a href="https://manojsingh3889.github.io/AdvertReport/Doc/">Javadocs</a>

>## Requirement

To create a RESTful web service that can offer reporting advertising data, part of which
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
