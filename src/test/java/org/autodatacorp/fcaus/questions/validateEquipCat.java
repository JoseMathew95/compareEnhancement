package org.autodatacorp.fcaus.questions;

import org.json.JSONObject;
import org.junit.Assert;

import java.util.Iterator;
import java.util.Set;

public class validateEquipCat {

    public static boolean hasViewsNodePresent(JSONObject EquipCat){
        boolean flag = true;
        JSONObject views = EquipCat.getJSONObject("views");
        if(views.isEmpty())
            flag = false;
        return flag;
    }

    public static boolean hasValidDataForViews(JSONObject EquipCat, JSONObject compareEquipCat){
        boolean flag = true;

        JSONObject compareViews = compareEquipCat.getJSONObject("views");
        JSONObject EquipViews = EquipCat.getJSONObject("views");

        Assert.assertTrue(compareViews.keySet().containsAll(EquipViews.keySet()));

        Set<String> CompareKeySet = compareViews.keySet();
        for (String key:CompareKeySet) {
            JSONObject compView = compareViews.getJSONObject(key);
            JSONObject EquipView = EquipViews.getJSONObject(key);

            Assert.assertTrue(compView.get("description").equals(EquipView.get("description")));

            Iterator modelKey = compView.getJSONObject("models").keys();
            JSONObject compModelView = compView.getJSONObject("models");
            while(modelKey.hasNext()){
                String mkey = (String)modelKey.next();
                compModelView = compModelView.getJSONObject(mkey);
            }
            Assert.assertTrue(compModelView.get("order") == EquipView.get("order"));
            //Assert.assertTrue(compModelView.getJSONArray("subCategories").equals(EquipView.getJSONArray("subCategories")));
        }

        return flag;
    }
}
