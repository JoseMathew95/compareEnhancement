package org.autodatacorp.fcaus.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Patch;
import net.serenitybdd.screenplay.rest.interactions.Put;
import org.autodatacorp.fcaus.Api.RequestParams;
import org.autodatacorp.fcaus.EndPoint.CombinedEndPoints;

import java.util.HashMap;
import java.util.Map;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class patchRequest implements Task {

    private RequestParams requestParam;
    private Map<String, String> header =new HashMap<>();

    public patchRequest(RequestParams requestParam){
        this.requestParam = requestParam;

        header.put("Content-Type","application/json");
        this.requestParam.setHeader(header);
    }

    @Override
    public <T extends Actor> void performAs(T actor ){
        actor.attemptsTo(Patch.to(CombinedEndPoints.configurationCompare.path()).with(
                (request ->  request
                        .params(requestParam.getParam())
                        .headers(requestParam.getHeader())
                        .body(requestParam.getBody().toString()))));
    }

    public static patchRequest Execute(RequestParams requestParam)
    {
        return instrumented(patchRequest.class, requestParam);
    }
}
