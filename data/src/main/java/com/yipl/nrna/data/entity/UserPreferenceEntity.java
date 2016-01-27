package com.yipl.nrna.data.entity;

import java.util.List;

/**
 * Created by Nirazan-PC on 1/26/2016.
 */
public class UserPreferenceEntity {

    String language;
    String gender;
    String type;
    List<String> country;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String pDeviceId) {
        deviceId = pDeviceId;
    }

    String deviceId;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String pLanguage) {
        language = pLanguage;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String pGender) {
        gender = pGender;
    }

    public String getType() {
        return type;
    }

    public void setType(String pType) {
        type = pType;
    }

    public List<String> getCountry() {
        return country;
    }

    public void setCountry(List<String> pCountry) {
        country = pCountry;
    }
}
