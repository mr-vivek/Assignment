package com.vivek.assignment.utils;

public class ServiceResponseModel {
    private Status status;
    private Object  data;

    public ServiceResponseModel(Status status, Object data){
        this.status = status;
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public Object getData() {
        return data;
    }
}
