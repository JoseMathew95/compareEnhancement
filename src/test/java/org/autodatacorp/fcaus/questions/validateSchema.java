package org.autodatacorp.fcaus.questions;

import org.json.JSONObject;

import java.util.Set;

public class validateSchema {

    public static boolean hasVCMOptionsSectionAndIsEmpty(JSONObject sections){
        boolean flag = false;
        if(sections.keySet().contains("vcmOptions")){
            JSONObject VCMOption = sections.getJSONObject("vcmOptions");
            System.out.println(VCMOption.getJSONArray("displayGroup").get(0));

            if(VCMOption.get("description").toString().isEmpty() &&
                    VCMOption.getJSONArray("displayGroup").length() == 1 &&
                    VCMOption.getJSONArray("groupings").isEmpty() &&
                    VCMOption.getJSONArray("compareIds").isEmpty() &&
                    VCMOption.getJSONArray("displayGroup").get(0).toString().equals("OPTIONS"))
                flag = true;
            else{
                System.out.println("VCMOptions is not Empty");
            }
        }
        else
            System.out.println("Missing VCMOptions");
        return flag;
    }

    public static boolean hasDisplayGroupNodeForEachSection(JSONObject sections){
        Set<String> sectionKeys = sections.keySet();
        boolean flag = true;

        for (String key: sectionKeys) {
            if(sections.getJSONObject(key).getJSONArray("displayGroup").isEmpty()){
                flag = false;
                break;
            }
        }
        return flag;
    }

}
