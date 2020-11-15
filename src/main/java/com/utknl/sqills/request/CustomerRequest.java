package com.utknl.sqills.request;

import org.json.JSONObject;

//maybe lombok could be better idea
public class CustomerRequest {
    private String first_name;
    private String last_name;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        object.accumulate("first_name", first_name);
        object.accumulate("last_name", last_name);
        return object;
    }
}
