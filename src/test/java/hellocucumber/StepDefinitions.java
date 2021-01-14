package hellocucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;


class client {
    static void call(String url, String attribute, String expectation) {
        given().when().get(url).then().statusCode(200).body(attribute, equalTo(expectation));
    }

    static void call(String url) {
        given().when().get(url).then().statusCode(200);
    }
}

public class StepDefinitions {
	private String baseurl = "";
	private String date = "";
	private String base = "";
	private String symbols = "";

    @Given("Exchange Rate API {string}")
    public void exchange_rate(String url) {
        this.baseurl = url;
    }
	
	@Given("Exchange Rate API {string} and Base {string}")
	public void exchange_rate_with_base(String url, String base){
        this.baseurl = url;
        this.base = base;
	}
	
	@Given("Exchange Rate API {string}, Symbols {string} and Base {string}")
	public void exchange_rate_for_symbols_with_base(String url, String base, String symbols){
        this.baseurl = url;
        this.base = base;
        this.symbols = symbols;
	}
	
	@Given("Exchange Rate API {string} and Symbols {string}")
	public void exchange_rate_for_symbols(String url, String base, String symbols){
        this.baseurl = url;
        this.symbols = symbols;
	}

    @When("Make a query of exchange rate")
    public void make_query_for_exchange() {
        if( !this.base.equals("") && this.symbols.equals(""))
            this.baseurl = this.baseurl + "?base=" + this.base;
        if(!this.symbols.equals("") && this.base.equals(""))
            this.baseurl = this.baseurl + "?symbols=" + this.symbols;
        if(!this.symbols.equals("") && !this.base.equals(""))
            this.baseurl = this.baseurl + "?symbols=" + this.symbols + "&base=" + this.base;
    }

    @Then("Response ok")
    public void response_ok() {
        client.call(baseurl);
    }
}
