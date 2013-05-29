package com.hp.it.innovation.collaboration.service.impl;

import java.util.List;

import org.springframework.util.CollectionUtils;

import com.hp.it.innovation.collaboration.builder.PreferenceBuilder;
import com.hp.it.innovation.collaboration.dao.intf.PreferenceDAO;
import com.hp.it.innovation.collaboration.dto.PreferenceDTO;
import com.hp.it.innovation.collaboration.model.Preference;
import com.hp.it.innovation.collaboration.service.common.AbstractBaseComponentServiceImpl;
import com.hp.it.innovation.collaboration.service.intf.UserPreferenceService;
import com.hp.it.innovation.collaboration.utilities.Constants;

/**
 * @author yhou
 */
public class UserPreferenceServiceImpl extends
                                      AbstractBaseComponentServiceImpl<Preference, PreferenceDTO, PreferenceDAO> implements
                                                                                                                UserPreferenceService {

    @Override
    public PreferenceDTO findPreferenceByUserIdByPreferenceKey(long userId, String preferenceKey) {

        PreferenceDAO preferenceDAO = getDao();
        StringBuilder hqlStringBuilder = new StringBuilder();
        hqlStringBuilder.append(PreferenceDAO.QUERY_FROM_USER_PREFERENCE);
        hqlStringBuilder.append(Constants.SPACE_PLACEHOLDER);
        hqlStringBuilder.append(PreferenceDAO.QUERY_WHERE);
        hqlStringBuilder.append(Constants.SPACE_PLACEHOLDER);
        hqlStringBuilder.append(PreferenceDAO.QUERY_CRITERIA_PREFERENCE_KEY);
        hqlStringBuilder.append(Constants.SPACE_PLACEHOLDER);
        hqlStringBuilder.append(PreferenceDAO.QUERY_AND);
        hqlStringBuilder.append(Constants.SPACE_PLACEHOLDER);
        hqlStringBuilder.append(PreferenceDAO.QUERY_CRITERIA_USERID);

        Object[] params = new Object[2];
        params[0] = preferenceKey;
        params[1] = userId;

        @SuppressWarnings("unchecked")
        List<Preference> preferences = (List<Preference>)preferenceDAO.queryByHQL(hqlStringBuilder.toString(), params);
        PreferenceDTO preferenceDTO;
        if (!CollectionUtils.isEmpty(preferences)) {
            PreferenceBuilder preferenceBuilder = new PreferenceBuilder();
            preferenceDTO = preferenceBuilder.getComponent(preferences.get(0));
            return preferenceDTO;
        }

        return null;
    }

    @Override
    public PreferenceDTO updatePreference(long userId, String preferenceKey, String value) {
        PreferenceDTO preferenceDTO = findPreferenceByUserIdByPreferenceKey(userId, preferenceKey);
        PreferenceBuilder preferenceBuilder = new PreferenceBuilder();
        Preference preference;
        if (preferenceDTO == null) {
            preference = new Preference();
            preference.setUserId(userId);
            preference.setPreferenceKey(PREFERENCE_KEY_DASHBOARD_LAYOUT);

        } else {
            preference = preferenceBuilder.getComponent(preferenceDTO);

        }
        preference.setValue(value);

        Preference result = merge(preference);
        return preferenceBuilder.getComponent(result);

    }

}
