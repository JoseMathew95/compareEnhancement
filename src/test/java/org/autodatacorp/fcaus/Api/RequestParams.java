package org.autodatacorp.fcaus.Api;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class RequestParams {

    private JSONObject body;
    public Map<String, String> header;
    public Map<String, String> param;

    public RequestParams() {
        this.header =  new HashMap<>();
        this.param = new HashMap<>();
    }

    public void setBody(JSONObject body) {
        this.body = body;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public void setParam(Map<String, String> param) {
        this.param = param;
    }

    public JSONObject getBody() {
        return body;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public Map<String, String> getParam() {
        return param;
    }
}
