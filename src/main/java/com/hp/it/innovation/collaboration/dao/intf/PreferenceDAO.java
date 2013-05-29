package com.hp.it.innovation.collaboration.dao.intf;

import com.hp.it.innovation.collaboration.dao.BaseDAO;
import com.hp.it.innovation.collaboration.model.Preference;

public interface PreferenceDAO extends BaseDAO<Preference, Long> {
    
    public static final String QUERY_FROM_USER_PREFERENCE = "from Preference";
    
    public static final String QUERY_WHERE = "where";
    
    public static final String QUERY_AND = "and";
    
    public static final String QUERY_CRITERIA_PREFERENCE_KEY = "preferenceKey = ?";
    
    public static final String QUERY_CRITERIA_USERID = "userId = ?";

}
