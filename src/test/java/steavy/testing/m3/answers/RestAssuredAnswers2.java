package steavy.testing.m3.answers;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RestAssuredAnswers2 {

	@BeforeClass
	public void initPath() {

		RestAssured.baseURI = "http://localhost:8080";
	}

	/*******************************************************
	 * Create a DataProvider that specifies in which country
	 * a specific circuit can be found (specify that Monza
	 * is in Italy, for example)
	 ******************************************************/

	@DataProvider(name = "circuits")
	public Object[][] createCircuitData() {
		return new Object[][]{
			{"monza", "Italy"},
			{"spa", "Belgium"},
			{"sepang", "Malaysia"},
			{"nurburgring", "Germany"}
		};
	}

	@DataProvider(name = "circuitsLocality")
	public Object[][] createCircuitLocalityData() {
		return new Object[][]{
			{"monza", "Monza"},
			{"spa", "Spa"},
			{"sepang", "Kuala Lumpur"},
			{"nurburgring", "Nürburgring"}
		};
	}

	/*******************************************************
	 * Create a DataProvider that specifies for all races
	 * (adding the first four suffices) in 2015 how many
	 * pit stops Max Verstappen made
	 * (race 1 = 1 pitstop, 2 = 3, 3 = 2, 4 = 2)
	 ******************************************************/

	@DataProvider(name = "pitstops")
	public Object[][] createPitstopData() {
		return new Object[][]{
			{"1", 1},
			{"2", 3},
			{"3", 2},
			{"4", 2}
		};
	}

	/*******************************************************
	 * Request data for a specific circuit (for Monza this
	 * is /api/f1/circuits/monza.json)
	 * and check the country this circuit can be found in
	 ******************************************************/

	@Test(dataProvider = "circuits")
	public void checkCountryForCircuit(String circuitName, String circuitCountry) {

		given().
			pathParam("circuitName", circuitName).
			when().
			get("/api/f1/circuits/{circuitName}.json").
			then().
			assertThat().
			body("MRData.CircuitTable.Circuits.Location[0].country", equalTo(circuitCountry));
	}

	/*******************************************************
	 * Request the locality of each track
	 ******************************************************/

	@Test(dataProvider = "circuitsLocality")
	public void checkTheLocalityOfEachTrack(String circuitName, String locality) {

		given().
			pathParam("circuitName", circuitName).
			when().
			get("/api/f1/circuits/{circuitName}.json").
			then().
			assertThat().
			body("MRData.CircuitTable.Circuits.Location[0].locality", equalTo(locality));
	}

	/*******************************************************
	 * Request the pitstop data for the first four races in
	 * 2015 for Max Verstappen (for race 1 this is
	 * /api/f1/2015/1/drivers/max_verstappen/pitstops.json)
	 * and verify the number of pit stops made
	 ******************************************************/

	@Test(dataProvider = "pitstops")
	public void checkNumberOfPitstopsForMaxVerstappenIn2015(String raceNumber, int numberOfPitstops) {

		given().
			pathParam("raceNumber", raceNumber).
			when().
			get("/api/f1/2015/{raceNumber}/drivers/max_verstappen/pitstops.json").
			then().
			assertThat().
			body("MRData.RaceTable.Races[0].PitStops", hasSize(numberOfPitstops));
	}


}
