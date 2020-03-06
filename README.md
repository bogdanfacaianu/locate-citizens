# Locate Citizens

 #### An API application to find people for a given location and within specified radius using the following as a data source:
 `https://bpdts-test-app.herokuapp.com/`
 
## Setup

The application was developed in <b>Kotlin</b> and built with <b>Kotlin Gradle</b>. 

## Build
`gradlew clean bootJar`

## Test
`gradlew clean test`

## Run
The code is packaged already in `locate-citizens-0.1.0.jar` and is enough to to be ran under the command:

`java -Dgeolocator.api.key=<YOUR_API_KEY> -jar locate-citizens-0.1.0.jar`

Or from the freshly build directory:

`java -Dgeolocator.api.key=<YOUR_API_KEY> -jar build/libs/locate-citizens-0.1.0.jar`

The above <b><YOUR_API_KEY></b> required ties this application to a GeoLocator dependency used for finding the coordinates for the location argument given.

You can provide yourself with an api key from <b>[Open Cage Geocoder](https://opencagedata.com/api)</b>

## Running Examples

#### Once the application is started up using the aforementioned command the Locate Citizens api works as follows:

The api takes 3 arguments:
- Location (default = London)
- Distance (default = 50)
- County Code (default = UK)

#### The country code parameter is used for aiding the geolocator precission to avoid conflicts such as Manchester <b>UK</b> vs Manchester <b>USA</b>

1. With no given arguments the request will default for location: London, distance: 50 miles and a countryCode: UK

`curl http://localhost:4852/getAllUsersInLocation`

2. With location parameter given the request will default for distance: 50 miles and a countryCode: UK

`curl http://localhost:4852/getAllUsersInLocation&location=manchester` (auto capitalization included for the api)

3. With location and distance parameters given the request will default for countryCode: UK

`curl localhost:4852/getAllUsersInLocation?location=beijing&distance=20`

4. With all the parameters given the request will be as follows:

`curl localhost:4852/getAllUsersInLocation?location=beijing&distance=20&countryCode=CN`
