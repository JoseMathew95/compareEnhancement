package org.autodatacorp.fcaus.Api;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.autodatacorp.fcaus.Schema.Option;
import org.autodatacorp.fcaus.tasks.GetRequest;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConfigCompareUtils {

    static String baseUrl = "https://lemon.fcaus.autodata.tech/hostd/api";
    static Actor actorInSpotlight = Actor.named("actorInSpotlight").whoCan(CallAnApi.at(baseUrl));

    public static List<Option> retrieveOptionsFromConfigureResponse(String Ccode, String Llp, String stateString){
        ArrayList<Option> configOptions = new ArrayList<Option>();
        String path =  "/configure/en/states/ca/models/" + Ccode +"/lowerLevelPackages/" + Llp + "/configurations/" + stateString;
        actorInSpotlight.attemptsTo(GetRequest.Execute(path));

        JSONObject ConfigResponse = new JSONObject(SerenityRest.lastResponse().getBody().asString()) ;
        JSONObject Options = ConfigResponse.getJSONObject("options");
        Options.keySet().forEach(cat->{
            if(IsValidInteger(cat)){
                JSONObject Category = Options.getJSONObject(cat);
                Category.keySet().forEach(subCat -> {
                    if(IsValidInteger(subCat)){
                        JSONObject subCategory = Category.getJSONObject(subCat);
                        subCategory.keySet().forEach(opt ->{
                            if(!opt.equals("summary")){
                                JSONObject Option = subCategory.getJSONObject(opt);
                                Option tempOpt = new Option();
                                tempOpt.setOption(opt);
                                tempOpt.setSelected(Option.getBoolean("selected"));
                                tempOpt.setStandard(Option.getBoolean("standard"));
                                tempOpt.setOnlyOne(Option.getBoolean("onlyOne"));
                                tempOpt.setState(Option.getString("state"));
                                configOptions.add(tempOpt);

                                if(!Option.get("content").toString().equals("null")){
                                    JSONArray content = (JSONArray) Option.get("content");
                                    if(content.length()>0) {
                                        content.forEach(conOpt ->{
                                            JSONObject conOptObj = new JSONObject(conOpt.toString());
                                            Option contTempOpt = new Option();
                                            contTempOpt.setOption(conOptObj.getString("code"));
                                            contTempOpt.setSelected(tempOpt.getSelected());
                                            contTempOpt.setStandard(tempOpt.getStandard());
                                            contTempOpt.setState(tempOpt.getState());
                                            contTempOpt.setOnlyOne(false);
                                            configOptions.add(contTempOpt);
                                        });
                                    }
                                }
                            }
                        });
                    }
                });
            }
        });

//        System.out.println(configOptions.size());
//        configOptions.forEach(opt->{
//            System.out.println(opt.getOption() +
//                    " " + opt.getSelected() +
//                    " " + opt.getStandard() +
//                    " " + opt.getOnlyOne() +
//                    " " + opt.getState());
//        });
        return configOptions;
    }

    public static List<Option> retrieveOptionsFromEquipCatResponse(String Ccode, String Llp){
        ArrayList<Option> EquipCatOptions = new ArrayList<Option>();

        String path =  "/equipment-categories/models/" + Ccode +"/lowerLevelPackages/" + Llp ;
        actorInSpotlight.attemptsTo(GetRequest.Execute(path));

        JSONObject EquipResp = new JSONObject(SerenityRest.lastResponse().getBody().asString()) ;
        JSONObject Options = EquipResp.getJSONObject("options");

        Options.keySet().forEach(opt->{
            JSONObject option = Options.getJSONObject(opt);
            Option tempOption = new Option();
            tempOption.setOption(opt);
            tempOption.setOrder(option.getInt("order"));
            tempOption.setVisible(option.getBoolean("visible"));
            tempOption.setStandard(option.getBoolean("standard"));
            try{
                tempOption.setDescription(option.getString("longDescription"));
            }catch (Exception e){
                tempOption.setDescription(option.getString("shortDescription"));
            };

            tempOption.setSubCategoryId(option.getInt("subCategoryID"));

            EquipCatOptions.add(tempOption);
        });

//        System.out.println(EquipCatOptions.size());
//        EquipCatOptions.forEach(opt->{
//            System.out.println(opt.getOption() +
//                    " " + opt.getDescription() +
//                    " " + opt.getVisible() +
//                    " " + opt.getSubCategoryId() +
//                    " " + opt.getOrder() );
//        });
        return EquipCatOptions;
    }

    public static List<Option> retrieveOptionsFromCatalogOptions(String Ccode, String Llp){
        ArrayList<Option> CatalogOptions = new ArrayList<Option>();

        String path =  "/catalog-option/en/details/models/" + Ccode +"/lowerLevelPackages/" + Llp ;
        actorInSpotlight.attemptsTo(GetRequest.Execute(path));

        JSONObject catalogOptionResp = new JSONObject(SerenityRest.lastResponse().getBody().asString()) ;
        catalogOptionResp.keySet().forEach(opt->{
            Option tempOption = new Option();
            tempOption.setOption(opt);
            CatalogOptions.add(tempOption);
        });

        return CatalogOptions;
    }

    public static List<Option> GenerateOptionsListForCcodeLlp(String Ccode, String Llp, String stateString){
        List<Option> EquipOptions = retrieveOptionsFromEquipCatResponse(Ccode,Llp);
        List<Option> CatalogOptions = retrieveOptionsFromCatalogOptions(Ccode,Llp);
        List<Option> ConfigOptions = retrieveOptionsFromConfigureResponse(Ccode,Llp,stateString);
        List<Option> tobeRemovedOptions = new ArrayList<>() ;
        List<Option> GeneratedOptions = new ArrayList<>() ;

        CatalogOptions.forEach(opt ->{
            String option = opt.getOption().substring(0,3).replaceAll("[^a-zA-Z0-9]", "");
            EquipOptions.forEach(eOpt->{
                if(eOpt.getOption().replaceAll("[^a-zA-Z0-9]", "").equals(option)){
                    Option tempOption = new Option();
                    tempOption.setOption(opt.getOption());
                    tempOption.setDescription(eOpt.getDescription());
                    tempOption.setOrder(eOpt.getOrder());
                    tempOption.setVisible(eOpt.getVisible());
                    tempOption.setSubCategoryId(eOpt.getSubCategoryId());
                    GeneratedOptions.add(tempOption);
                }
            });
        });

        for(Option opt:GeneratedOptions){
            boolean flag = false;
            String configOpt;
            String option = opt.getOption().substring(0,3).replaceAll("[^a-zA-Z0-9]", "");
            for(Option confOpt:ConfigOptions){

                configOpt = confOpt.getOption();
                if(configOpt.length()>2)
                    configOpt = configOpt.substring(0,3).replaceAll("[^a-zA-Z0-9]","");

                if(configOpt.equals(option)){
                    opt.setStandard(confOpt.getStandard());
                    opt.setSelected(confOpt.getSelected());
                    opt.setState(confOpt.getState());
                    opt.setOnlyOne(confOpt.getOnlyOne());
                    flag = true;
                    break;
                }
            }

            if(!flag)
                tobeRemovedOptions.add(opt);
        }

        GeneratedOptions.removeAll(tobeRemovedOptions);

        EquipOptions.forEach(opt->{
            if(opt.getStandard() == true){
                boolean flag = false;
                String option = opt.getOption().substring(0,3).replaceAll("[^a-zA-Z0-9]", "");
                for(Option gOpt: GeneratedOptions){
                    if(gOpt.getOption().substring(0,3).replaceAll("[^a-zA-Z0-9]", "").equals(option)){
                        flag = true;
                        break;
                    }
                }

                if(flag == false)
                    GeneratedOptions.add(opt);
            }
        });


//        System.out.println(GeneratedOptions.size());
//        GeneratedOptions.forEach(opt->{
//            System.out.println(opt.getOption() +
//                    " " + opt.getDescription() +
//                    " " + opt.getVisible() +
//                    " " + opt.getSubCategoryId() +
//                    " " + opt.getOrder() );
//        });

        return GeneratedOptions;
    }

    public static List<Option> GenerateOptionsListFromCompareResponse(JSONObject compareResponse){
        List<Option> compareOptions = new ArrayList<>() ;

        JSONObject Options = compareResponse.getJSONObject("options");
        Options.keySet().forEach(opt->{
            JSONObject model = Options.getJSONObject(opt).getJSONObject("models");
            model.keySet().forEach(modelOpt->{
                JSONObject modelOption = model.getJSONObject(modelOpt);
                Option tempOpt = new Option();
                tempOpt.setOption(opt);
                tempOpt.setState(modelOption.getString("state"));
                tempOpt.setDescription(Options.getJSONObject(opt).getString("description"));
                tempOpt.setStandard(modelOption.getBoolean("standard"));
                tempOpt.setSelected(modelOption.getBoolean("selected"));
                tempOpt.setOnlyOne(modelOption.getBoolean("onlyOne"));
                tempOpt.setCategoryId(modelOption.getInt("categoryId"));
                tempOpt.setSubCategoryId(modelOption.getInt("subCategoryId"));
                if(tempOpt.getSubCategoryId() >= 0){
                    tempOpt.setVisible(modelOption.getBoolean("visible"));
                }
                tempOpt.setOrder(modelOption.getInt("order"));
                compareOptions.add(tempOpt);
            });
        });

//        System.out.println(compareOptions.size());
//        compareOptions.forEach(opt->{
//            System.out.println(opt.getOption() +
//                    " " + opt.getDescription() +
//                    " " + opt.getSelected() +
//                    " " + opt.getStandard() +
//                    " " + opt.getOnlyOne() +
//                    " " + opt.getState());
//        });

        return compareOptions;

    }

    public static boolean IsValidInteger(String value){
        boolean IsInteger = false;
        try{
            Integer.parseInt(value);
            IsInteger = true;
        }catch (NumberFormatException e){}
        return IsInteger;
    }

    public static JSONObject retrieveEquipCatResponse(String Ccode, String Llp){

        String path =  "/equipment-categories/models/" + Ccode +"/lowerLevelPackages/" + Llp ;
        actorInSpotlight.attemptsTo(GetRequest.Execute(path));

        JSONObject EquipResp = new JSONObject(SerenityRest.lastResponse().getBody().asString()) ;
        return EquipResp;
    }
}
