package hellocucumber;

import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;


class client {
    static void call(String url, String attribute, String expectation) {
        given().when().get(url).then().statusCode(200).body(attribute, equalTo(expectation));
    }

    static void call(String url, String attribute, List<String> expectation) {
        Response response = given().when().get(url);
        expectation.stream().forEach(key -> response.then().body(attribute, hasKey(key)));
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
	private static Logger log;

	@Before
    public void setup(){
        log = Logger.getLogger("Exchange rate test logger");
    }

    @Given("Exchange Rate API {string}")
    public void exchange_rate(String url) {
        log.info("Initialize url: " + url);
        this.baseurl = url;
    }
	
	@Given("Exchange Rate API {string} and Base {string}")
	public void exchange_rate_with_base(String url, String base){
        log.info("Initialize url: " + url + " base: " + base);
        this.baseurl = url;
        this.base = base;
	}
	
	@Given("Exchange Rate API {string}, Symbols {string} and Base {string}")
	public void exchange_rate_for_symbols_with_base(String url, String base, String symbols){
        log.info("Initialize url: " + url + " base: " + base + " symbols: " + symbols);
        this.baseurl = url;
        this.base = base;
        this.symbols = symbols;
	}
	
	@Given("Exchange Rate API {string} and Symbols {string}")
	public void exchange_rate_for_symbols(String url, String symbols){
        log.info("Initialize url: " + url + " symbols: " + symbols);
        this.baseurl = url;
        this.symbols = symbols;
	}
	
	@Given("Date is {string}")
	public void date_is(String date){
        log.info("Initialize date: " + date);
        this.date = date;
	}

    @When("Make a query of exchange rate")
    public void make_query_for_exchange() {
        log.info("Creating base url");
        if(!this.date.equals(""))
            this.baseurl = this.baseurl + "/" + this.date;
        else
            this.baseurl = this.baseurl + "/latest";
        if( !this.base.equals("") && this.symbols.equals(""))
            this.baseurl = this.baseurl + "?base=" + this.base;
        if(!this.symbols.equals("") && this.base.equals(""))
            this.baseurl = this.baseurl + "?symbols=" + this.symbols;
        if(!this.symbols.equals("") && !this.base.equals(""))
            this.baseurl = this.baseurl + "?symbols=" + this.symbols + "&base=" + this.base;
        log.info("Created exchange rate api: " + this.baseurl);
    }

    @Then("Response ok")
    public void response_ok() {
        client.call(baseurl);
    }

    @Then("Response have base {string}")
    public void has_response_base(String base){
        client.call(this.baseurl, "base", base);
    }

    @Then("Response have symbols {string}")
    public void has_response_symbols(String symbols){
	    List<String> _symbol = Arrays.asList(symbols.split(","));
        client.call(this.baseurl, "rates", _symbol);
    }
}
