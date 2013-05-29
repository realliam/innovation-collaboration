package com.hp.it.innovation.collaboration.builder;

import com.hp.it.innovation.collaboration.builder.base.AbstractComponentBuilder;
import com.hp.it.innovation.collaboration.dto.PreferenceDTO;
import com.hp.it.innovation.collaboration.model.Preference;

public class PreferenceBuilder extends AbstractComponentBuilder<Preference, PreferenceDTO> {

    @Override
    public PreferenceDTO getComponent(Preference entity) {
        PreferenceDTO preferenceDTO = new PreferenceDTO();
        if (entity != null) {
            transferBaseEntityToDTO(entity, preferenceDTO, false);
            preferenceDTO.setPreferenceKey(entity.getPreferenceKey());
            preferenceDTO.setUserId(entity.getUserId());
            preferenceDTO.setValue(entity.getValue());
        }
        
        return preferenceDTO;
    }

    @Override
    public Preference getComponent(PreferenceDTO dto) {
        Preference preference = new Preference();
        if(dto != null) {
            transferBaseDTOToEntity(dto, preference, false);
            preference.setPreferenceKey(dto.getPreferenceKey());
            preference.setUserId(dto.getUserId());
            preference.setValue(dto.getValue());
        }
        
        return preference;
    }

}
