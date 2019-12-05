package com.shijiucheng.konghua.main.entity;

public class CityInfo {

    private String cityId;
    private String city;
    private boolean isCheck;

    public CityInfo(String cityId, String city) {
        this.cityId = cityId;
        this.city = city;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheek(boolean check) {
        isCheck = check;
    }
}
