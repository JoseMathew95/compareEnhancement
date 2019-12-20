package org.autodatacorp.fcaus.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class GetRequest implements Task {

    String path;

    public GetRequest(String path){
        this.path = path;
    }

    @Override
    public <T extends Actor> void performAs(T actor ){
        System.out.println("\n\n\n"+ path);
        actor.attemptsTo(Get.resource(path));
    }

    public static GetRequest Execute(String path){
        return instrumented(GetRequest.class, path);
    }
}