package com.hp.it.innovation.collaboration.dto;

public class PreferenceDTO extends ComponentDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 4161553935434288181L;

    private long userId;

    private String preferenceKey;

    private String value;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getPreferenceKey() {
        return preferenceKey;
    }

    public void setPreferenceKey(String preferenceKey) {
        this.preferenceKey = preferenceKey;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
