package com.utknl.sqills.response;

//maybe lombok could be better idea
public class CustomerResponse {
    private String first_name;
    private String last_name;

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

}
