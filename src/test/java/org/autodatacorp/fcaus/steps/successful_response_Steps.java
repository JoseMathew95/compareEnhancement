package org.autodatacorp.fcaus.steps;

    import cucumber.api.java.en.And;
    import cucumber.api.java.en.Given;
    import cucumber.api.java.en.Then;
    import cucumber.api.java.en.When;
    import io.cucumber.datatable.DataTable;
    import net.serenitybdd.rest.SerenityRest;
    import net.serenitybdd.screenplay.Actor;
    import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
    import org.autodatacorp.fcaus.Api.ConfigCompareUtils;
    import org.autodatacorp.fcaus.Api.RequestParams;
    import org.autodatacorp.fcaus.Schema.Option;
    import org.autodatacorp.fcaus.questions.ValidateOptions;
    import org.autodatacorp.fcaus.questions.validateEquipCat;
    import org.autodatacorp.fcaus.questions.validateSchema;
    import org.autodatacorp.fcaus.tasks.postCompareRequest;
    import org.json.JSONArray;
    import org.json.JSONObject;
    import org.junit.Assert;

    import java.util.List;
    import java.util.Map;

    import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;

    public class successful_response_Steps {

        private Actor actor;
        private RequestParams requestParam;
        private JSONObject jObj, compareResp;
        private String theRestApiBaseUrl = "https://lemon.fcaus.autodata.tech/hostd/api";
        private List<Map<String, String>> list;

        public successful_response_Steps()
        {
            jObj = new JSONObject();
            this.requestParam = new RequestParams();
        }

        @Given("{word} has a list of models to be Compared")
        public void mathewHasAListOfModelsToBeCompared(String name, DataTable dt) {
            list = dt.asMaps(String.class, String.class);
            this.jObj.put("models", list);
            requestParam.setBody(jObj);
            actor =  Actor.named(name).whoCan(CallAnApi.at(theRestApiBaseUrl));
        }

        @Given("{word} requires the compare Information for the {string} and {string}")
        public void mathewRequiresTheCompareInformationForTheCcodeAndLlpCode(String name, String ccode, String llpCode) {
            JSONArray array = new JSONArray();
            JSONObject modelObj = new JSONObject();
            modelObj.put("code",ccode);
            modelObj.put("llpCode",llpCode);
            array.put(modelObj);
            this.jObj.put("models",array);
            actor =  Actor.named(name).whoCan(CallAnApi.at(theRestApiBaseUrl));
        }

        @And("He has a stateCode for the Request")
        public void hasAStateCodeForTheRequest(DataTable dt) {
            List<Map<String, String>> list = dt.asMaps(String.class, String.class);
            this.jObj.put("stateCode", list.get(0).get("stateCode"));
            requestParam.setBody(jObj);
        }

        @When("He Makes a post request to configuration-Compare service with the Models and Ccode")
        public void theUserMakesAPostRequestToTheConfigurationCompareService() {
            actor.attemptsTo(postCompareRequest.Execute(this.requestParam));
        }

        @Then("He should receive a successful response")
        public void heShouldReceiveASuccessfulResponse() {
            compareResp = new JSONObject(SerenityRest.lastResponse().getBody().asString()) ;
            actor.should(seeThatResponse(response -> response.statusCode(200)));
        }

        @And("The response has options that matches the Generated Options.")
        public void theResponseShouldContainACompleteListOfOptions() {
            JSONArray rObj = requestParam.getBody().getJSONArray("models");
            String ccode = rObj.getJSONObject(0).getString("code");
            String llpCode = rObj.getJSONObject(0).getString("llpCode");

            List<Option> generatedOptions = ConfigCompareUtils.GenerateOptionsListForCcodeLlp(ccode,llpCode,"standard");
            List<Option> compareOptions = ConfigCompareUtils.GenerateOptionsListFromCompareResponse(compareResp);

            Assert.assertTrue("Response has all the Options.", ValidateOptions.hasAllTheOptions(generatedOptions, compareOptions));
            Assert.assertTrue("Response has correct Descriptions for Options.", ValidateOptions.hasValidOptionDescription(generatedOptions, compareOptions));
            Assert.assertTrue("Response has correct Order value for Options.", ValidateOptions.hasCorrectOrderingValue(generatedOptions, compareOptions));
            Assert.assertTrue("Response has correct Visibility value for Options.", ValidateOptions.hasCorrectVisibleValue(generatedOptions, compareOptions));
        }

        @And("The response has options that matches the Configure Options.")
        public void theValuesForFlagsMatchesTheValuesReturnedByConfigureService() {
            JSONArray rObj = requestParam.getBody().getJSONArray("models");
            String ccode = rObj.getJSONObject(0).getString("code");
            String llpCode = rObj.getJSONObject(0).getString("llpCode");

            List<Option> configOptions = ConfigCompareUtils.retrieveOptionsFromConfigureResponse(ccode, llpCode,"standard");
            List<Option> compareOptions = ConfigCompareUtils.GenerateOptionsListFromCompareResponse(compareResp);

            Assert.assertTrue("Response has all the Options.", ValidateOptions.hasAllTheConfigOptions(configOptions, compareOptions));
            Assert.assertTrue("Response has correct values for flags.", ValidateOptions.hasValidValuesForFlags(configOptions, compareOptions));
        }

        @And("The sections has DisplayGroup node and a new VCM-Option Section.")
        public void theSectionsHasDisplayGroupNodeAndANewVCMOptionSection() {
            JSONObject sections = compareResp.getJSONObject("sections");
            Assert.assertTrue("Validate that Sections node has VCMOptions node", validateSchema.hasVCMOptionsSectionAndIsEmpty(sections));
            Assert.assertTrue("Validate that Sections node has VCMOptions node", validateSchema.hasDisplayGroupNodeForEachSection(sections));
        }

        @And("The Equipment-categories node has the correct Views data for the Model")
        public void theEquipmentCategoriesNodeHasTheCorrectViewsDataForTheModel() {

            JSONArray rObj = requestParam.getBody().getJSONArray("models");
            String ccode = rObj.getJSONObject(0).getString("code");
            String llpCode = rObj.getJSONObject(0).getString("llpCode");

            JSONObject EquipCatResp =  ConfigCompareUtils.retrieveEquipCatResponse(ccode, llpCode);
            JSONObject Compare_eqiupCat = compareResp.getJSONObject("equipment-categories");

            Assert.assertTrue("validate that views section is returned", validateEquipCat.hasViewsNodePresent(Compare_eqiupCat));
            Assert.assertTrue("validate the views data against Equip-cat Response", validateEquipCat.hasValidDataForViews(EquipCatResp, Compare_eqiupCat));
        }
    }
