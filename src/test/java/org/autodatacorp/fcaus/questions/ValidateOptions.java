package org.autodatacorp.fcaus.questions;

import org.autodatacorp.fcaus.Schema.Option;
import java.util.List;


public class ValidateOptions {

    public static boolean hasAllTheOptions(List<Option> generatedOptions, List<Option> compareOptions){
        System.out.println("\n\nNumber of Options in the Compare Response= " + compareOptions.size());
        System.out.println("Number of Options in the Generated Response = " + generatedOptions.size());

        for (Option gOpt: generatedOptions){
            Boolean match = false;
            for (Option compOpt: compareOptions) {
                if(compOpt.getOption().equals(gOpt.getOption()))
                    match = true;
            }
            if(match == false)
                System.out.println("Missing generated Option " + gOpt.getOption());
        }

        for (Option comOpt:compareOptions) {
            Boolean match = false;
            for(Option gOtp:generatedOptions){
                if(comOpt.getOption().equals(gOtp.getOption()))
                    match = true;
            }
            if(match == false)
                System.out.println("Missing Compare Option " + comOpt.getOption());
        }
        return true;
    }

    public static boolean hasValidOptionDescription(List<Option> generatedOptions, List<Option> compareOptions){
        for (Option compOpt: compareOptions){
            Boolean match = false;
            for (Option gOpt: generatedOptions) {
                if(compOpt.getOption().equals(gOpt.getOption()))
                {
                    if(compOpt.getDescription().equals(gOpt.getDescription()))
                        match = true;
                }
            }
            if(match == false)
                System.out.println("Wrong Description for " + compOpt.getOption());
        }
        return true;
    }

    public static boolean hasCorrectOrderingValue(List<Option> generatedOptions, List<Option> compareOptions){
        for (Option comOpt:compareOptions) {
            Boolean match = false;
            for(Option gOtp:generatedOptions){
                if(comOpt.getOption().equals(gOtp.getOption())) {
                    if ((comOpt.getOrder() == (gOtp.getOrder()) && (comOpt.getSubCategoryId() == gOtp.getSubCategoryId())))
                        match = true;
                }
            }
            if(match == false)
                System.out.println("Wrong Order/SubatId value for " + comOpt.getOption());
        }
        return true;
    }

    public static boolean hasCorrectVisibleValue(List<Option> generatedOptions, List<Option> compareOptions){
        for (Option comOpt:compareOptions) {
            Boolean match = false;
            for(Option gOtp:generatedOptions){
                if(comOpt.getOption().equals(gOtp.getOption())) {
                    if (comOpt.getVisible() == (gOtp.getVisible()))
                        match = true;
                }
            }
            if(match == false)
                System.out.println("Wrong Visible value for " + comOpt.getOption());
        }
        return true;
    }

    public static boolean hasAllTheConfigOptions(List<Option> configOptions, List<Option> compareOptions){
        System.out.println("\nNumber of Options in the Compare Response = " + compareOptions.size());
        System.out.println("Number of Options in the Config Response = " + configOptions.size());

        for (Option confOpt: configOptions){
            Boolean match = false;
            String configOpt = confOpt.getOption();

            if(configOpt.length()>2)
                configOpt = configOpt.substring(0,3).replaceAll("[^a-zA-Z0-9]","");

            for (Option compOpt: compareOptions) {
                if(compOpt.getOption().substring(0,3).replaceAll("[^a-zA-Z0-9]","").equals(configOpt))
                    match = true;
            }
            if(match == false)
                System.out.println("Missing " + confOpt.getOption());
        }
        return true;
    }

    public static boolean hasValidValuesForFlags(List<Option> configOptions, List<Option> compareOptions){

        for (Option compOpt: compareOptions) {
            Boolean match = false;

            for (Option confOpt: configOptions) {
                String configOpt = confOpt.getOption();
                if(configOpt.length()>2){
                    configOpt = configOpt.substring(0,3).replaceAll("[^a-zA-Z0-9]","");}

                if(configOpt.equals(compOpt.getOption().substring(0,3).replaceAll("[^a-zA-Z0-9]",""))){
                    if(compOpt.getSelected() == confOpt.getSelected())
                        if(compOpt.getStandard() == confOpt.getStandard())
                            //if(compOpt.getOnlyOne() == confOpt.getOnlyOne())
                                    match = true;
                }
            }
            if(match == false)
                System.out.println("Wrong flag values for the Option " + compOpt.getOption());
        }
        return true;
    }
}
