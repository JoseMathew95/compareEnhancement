package org.autodatacorp.fcaus.Common;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class utils
{
    /***
     * Verify if a string match with valid format Date
     * @param inDate the date string to verify
     * @param formatPattern the format of date
     * @return True if match the Format, else False
     * @author Elmer A.
     */
    public static boolean isValidDate(String inDate, String formatPattern)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatPattern);
        dateFormat.setLenient(false);
        try
        {
            dateFormat.parse(inDate.trim());
        }
        catch (ParseException pe)
        {
            return false;
        }
        return true;
    }

    /***
     * Verify if a generic list has duplicated values
     * @param genericList List to verify
     * @param <T> Generic type of the List
     * @return True if has duplicated, else return False
     * @author Elmer A.
     */
    public static <T> boolean isListHasDuplicates( Collection<T> genericList)
    {
        boolean hasDuplicate = false;
        Set<T> uniques = new HashSet<>();

        for(T t : genericList)
        {
            if(!uniques.add(t))
            {
                hasDuplicate = true;
            }
        }
        return hasDuplicate;
    }

    /***
     * Verify if a value is a Numeric type
     * @param genericValue value to verify
     * @return True if is Numeric type, else False
     * @author Elmer A.
     */
    public static boolean isNumericValue(Object genericValue)
    {
        return (genericValue instanceof Number);
    }

    /***
     * Verify if a value is a Numeric, and equal and greater than zero
     * @param genericValue value to verify
     * @return True if is Positive, else False
     */
    public static boolean isNumericAndPositiveValue(Object genericValue) {
        return isNumericValue(genericValue) && ((float) genericValue >= 0);
    }

    /***
     *
     * @param arrayList
     * @return
     */
    public static ArrayList<HashMap<String,Object>> convertToIntIfThereFloat(ArrayList<HashMap<String,Object>> arrayList)
    {
        for (HashMap<String, Object> stringObjectHashMap : arrayList) {
            for (Map.Entry<String, Object> entry : stringObjectHashMap.entrySet()) {
                if(entry.getValue().getClass().toString().equals("class java.lang.Float")){
                    stringObjectHashMap.replace(entry.getKey(),Math.round((Float)entry.getValue()));
                }
            }
        }
        return arrayList;
    }

    /**
     * Parse string list into Json Array
     * @param listString list of strings
     * @return Json Array with elements from string list
     */
    public static JsonElement parseStringListToJsonArray( List<String> listString)
    {
        JsonArray jsonArray = new JsonArray();
        for (String value: listString)
        {
            jsonArray.add(value);
        }
        return jsonArray;
    }
}
