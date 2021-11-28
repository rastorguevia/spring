package ru.rastorguev.springlesson1.ioc.model;

public class ExternalInfo {

    private Integer id;
    private String info;

    public ExternalInfo(Integer id, String info) {
        this.id = id;
        this.info = info;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
