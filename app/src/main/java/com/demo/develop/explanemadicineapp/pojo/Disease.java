package com.demo.develop.explanemadicineapp.pojo;


public class Disease {
    private String id;
    private String specialty;
    private String condition;

    public Disease(String id, String specialty, String condition) {
        this.id = id;
        this.specialty = specialty;
        this.condition = condition;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
