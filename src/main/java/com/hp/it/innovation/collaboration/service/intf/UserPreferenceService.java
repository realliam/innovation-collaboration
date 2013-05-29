package com.hp.it.innovation.collaboration.service.intf;

import com.hp.it.innovation.collaboration.dao.intf.PreferenceDAO;
import com.hp.it.innovation.collaboration.dto.PreferenceDTO;
import com.hp.it.innovation.collaboration.model.Preference;
import com.hp.it.innovation.collaboration.service.BaseComponentService;

public interface UserPreferenceService extends BaseComponentService<Preference, PreferenceDTO, PreferenceDAO> {
    public static final String PREFERENCE_KEY_DASHBOARD_LAYOUT = "dashboardLayout";
    
    PreferenceDTO findPreferenceByUserIdByPreferenceKey(long userId, String preferenceKey);
    
    PreferenceDTO updatePreference(long userId, String preferenceKey, String value);
}
