package org.autodatacorp.fcaus.steps;

import cucumber.api.Pending;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.serenitybdd.screenplay.Actor;
import io.cucumber.datatable.DataTable;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.autodatacorp.fcaus.Api.RequestParams;
import org.autodatacorp.fcaus.tasks.GetRequest;
import org.autodatacorp.fcaus.tasks.patchRequest;
import org.autodatacorp.fcaus.tasks.postCompareRequest;
import org.autodatacorp.fcaus.tasks.putRequest;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;

public class model_failure_steps {

    private Actor actor;
    private RequestParams requestParam;
    private JSONObject jObj;
    private String theRestApiBaseUrl = "https://www.chrysler.com/hostd/api";

    public model_failure_steps()
    {
        jObj = new JSONObject();
        this.requestParam = new RequestParams();
    }

    @Given("{word} has a list of vehicles to be Compared:")
    public void mathew_has_a_list_of_vehicles_to_be_Compared(String name, DataTable dt) {
        List<Map<String, String>> list = dt.asMaps(String.class, String.class);
        this.jObj.put("models", list);
        actor =  Actor.named(name).whoCan(CallAnApi.at(theRestApiBaseUrl));
        requestParam.setBody(jObj);
    }

    @Given("has a stateCode for the Comparison")
    public void has_a_stateCode_for_the_Comparison(DataTable dt) {
        List<Map<String, String>> list = dt.asMaps(String.class, String.class);
        this.jObj.put("stateCode", list.get(0).get("stateCode"));
        requestParam.setBody(jObj);
    }

    @When("The user makes a post request to the Configuration-Service")
    public void the_user_makes_a_post_request_with_three_valid_CCODES_Llp_s() {
        actor.attemptsTo(postCompareRequest.Execute(this.requestParam));
    }

    @Then("he should get a {int} response with compare data.")
    public void should_get_a_response_with_compare_data(Integer int1) {
        actor.should(seeThatResponse(response -> response.statusCode(int1)));
    }

    @Then("Should get a {int} response with the Error Message.")
    public void shouldGetAResponseWithTheErrorMessage(int int1) {
        actor.should(seeThatResponse(response -> response.statusCode(int1)));
    }

    @When("The user makes a PUT request to the Configuration-Service")
    public void theUserMakesAPUTRequestToTheConfigurationService() {
        actor.attemptsTo(putRequest.Execute(this.requestParam));
    }

    @When("The user makes a GET request to the Configuration-Service")
    public void theUserMakesAGETRequestToTheConfigurationService() {
        actor.attemptsTo(GetRequest.Execute(theRestApiBaseUrl + "/configuration-compare/compare"));
    }

    @When("The user makes a PATCH request to the Configuration-Service")
    public void theUserMakesAPATCHRequestToTheConfigurationService() {
        actor.attemptsTo(patchRequest.Execute(this.requestParam));
    }
}
