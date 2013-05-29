package com.hp.it.innovation.collaboration.controller.ajax;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hp.it.innovation.collaboration.dto.UserDTO;
import com.hp.it.innovation.collaboration.service.common.ServiceFactory;
import com.hp.it.innovation.collaboration.service.intf.UserPreferenceService;
import com.hp.it.innovation.collaboration.utilities.Utils;

@Controller
@RequestMapping("/asyncConfig/*")
public class AsyncPreferenceService {

    @RequestMapping(value = "/saveLayout", method = RequestMethod.POST)
    public void saveDashboardLayout(@RequestParam("value") String value, HttpServletRequest request) {
        UserDTO currentUser = Utils.getCurrentUser();
        if (currentUser != null) {
            value = StringUtils.isBlank(value) ? "" : value;

            StringBuilder widgetIdsStringBuilder = null;

            String[] widgetTypes = value.split(",");

            Map<String, String> widgetTypeMap = new HashMap<String, String>();
            widgetTypeMap.put("1", "myprofile");
            widgetTypeMap.put("2", "todoreminder");
            widgetTypeMap.put("3", "ad1");
            widgetTypeMap.put("4", "skillmatrix");
            widgetTypeMap.put("5", "ad2");
            widgetTypeMap.put("6", "ad3");
            widgetTypeMap.put("7", "placeholder1");
            widgetTypeMap.put("8", "placeholder2");
            widgetTypeMap.put("9", "admin");

            Set<String> widgetIdsSet = widgetTypeMap.keySet();

            for (int i = 0; i < widgetTypes.length; i++) {

                for (String widgetId : widgetIdsSet) {
                    if (widgetTypes[i].equals(widgetTypeMap.get(widgetId))) {
                        widgetIdsStringBuilder = ((widgetIdsStringBuilder == null) ? new StringBuilder() : widgetIdsStringBuilder);
                        widgetIdsStringBuilder.append((i == 0) ? widgetId : "," + widgetId);
                        break;
                    }
                }

            }

            ServiceFactory.getService(UserPreferenceService.class)
                          .updatePreference(currentUser.getId(),
                                            UserPreferenceService.PREFERENCE_KEY_DASHBOARD_LAYOUT,
                                            widgetIdsStringBuilder.toString());
        }
    }

}
