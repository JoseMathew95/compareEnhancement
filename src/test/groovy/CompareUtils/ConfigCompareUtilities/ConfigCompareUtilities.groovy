package CompareUtils.ConfigCompareUtilities

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper

class ConfigCompareUtilities {
    
    public static def buildPostBodyRequest(String State,  ArrayList<Map> Models) {
        
        def builder = new JsonBuilder();
        
        builder{
            if(State!=null)
                stateCode State
                
            models(Models)
        }
        
        def result = builder.toString().replace("\\", "");
        return result;
    }
    
    public static def parseResponseJson (String response)
    {
        def slurper = new JsonSlurper()
        def resp = slurper.parseText(response);
        return resp;
    }
    
    
    public static def createModelCompareElement(String Ccode=null, String Llp=null)
    {
        def builder = [:]
        if(Ccode!=null)
            builder['code'] = Ccode
        if(Llp!=null)
            builder['llpCode'] = Llp
        return builder
    }
    
    
    public static def createConfigCompareElement(String Ccode=null, String Llp=null, String config=null, Object Option=null)
    {

        def builder = [:]
        if(Ccode!=null)
            builder['code'] = Ccode
        if(Llp!=null)
            builder['llpCode'] = Llp
        if(config!=null)
            builder['configuration'] = config
        if(Option!=null){
            if(Option instanceof List){
                builder['options'] = Option
            } else {
                builder['options'] = [Option]
            }
        }
            return builder
      }

    
    public static def createVinCompareElement(String Vin=null )
    {
        def builder = [:]
        if(Vin!=null)
            builder['vin'] = Vin
            return builder
    }
    
    /**
     * To verify the response status and check response data for null/empty
     * @param response
     * @param expectedStatus
     * @return Boolean result
     */
    public static Boolean validateExpectedResponse(def response, Integer expectedStatus) {
        Boolean result = response.status == expectedStatus && response.data != null && response.data != "";
    }
    
    /**
     * This function parses reponse data using Json Slurper
     * @param inputResponse
     * @return parsed response data
     */
    public static def getParsedResponseData(inputResponse) {
        //To get around a character encoding issue with windows/gradlew, specifying UTF-8 here
        return new JsonSlurper().parse(inputResponse.data.getBytes(),'UTF-8');
    }
    
}
