package com.hp.it.innovation.collaboration.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hp.it.innovation.collaboration.dto.PreferenceDTO;
import com.hp.it.innovation.collaboration.dto.UserDTO;
import com.hp.it.innovation.collaboration.model.DashboardPanel;
import com.hp.it.innovation.collaboration.service.common.ServiceFactory;
import com.hp.it.innovation.collaboration.service.intf.UserPreferenceService;
import com.hp.it.innovation.collaboration.service.intf.UserService;
import com.hp.it.innovation.collaboration.utilities.Utils;

@Controller
@RequestMapping("/users/*")
public class UserController {

    @RequestMapping("/{userName}")
    @PreAuthorize("hasAnyRole('MEMBER','ADMIN','SUPERVISOR')")
    public String processUser(Model model, HttpServletRequest request, @PathVariable String userName) {
        List<UserDTO> users = ServiceFactory.getService(UserService.class).retrieveAllUsers();

        UserDTO currentUser = Utils.getCurrentUser();
        PreferenceDTO preference = null;
        List<DashboardPanel> panels = new ArrayList<DashboardPanel>();
        List<DashboardPanel> widgetLibPanels = new ArrayList<DashboardPanel>();
        if (currentUser != null) {
            preference = ServiceFactory.getService(UserPreferenceService.class)
                                       .findPreferenceByUserIdByPreferenceKey(currentUser.getId(),
                                                                              UserPreferenceService.PREFERENCE_KEY_DASHBOARD_LAYOUT);
        }

        Map<String, String> widgetTypeMap = new HashMap<String, String>();
        widgetTypeMap.put("1", "myprofile");
        widgetTypeMap.put("2", "todoreminder");
        widgetTypeMap.put("3", "ad1");
        widgetTypeMap.put("4", "skillmatrix");
        widgetTypeMap.put("5", "ad2");
        widgetTypeMap.put("6", "ad3");
        widgetTypeMap.put("7", "placeholder1");
        widgetTypeMap.put("8", "placeholder2");
        if (Utils.isAdmin(currentUser)) {
            widgetTypeMap.put("9", "admin");
        }

        if (preference != null && !StringUtils.isBlank(preference.getValue())) {
            String[] widgetIds = preference.getValue().split(",");
            for (int i = 0; i < widgetIds.length; i++) {
                String widgetType = widgetTypeMap.get(widgetIds[i]);
                widgetTypeMap.remove(widgetIds[i]);
                if ("myprofile".equals(widgetType)) {
                    panels.add(new DashboardPanel("my profile", "myprofile", null, null));
                } else if ("todoreminder".equals(widgetType)) {
                    panels.add(new DashboardPanel("todo reminder", "todoreminder", null, null));
                } else if ("ad1".equals(widgetType)) {
                    panels.add(new DashboardPanel("ad1", "ad1", null, null));
                } else if ("skillmatrix".equals(widgetType)) {
                    panels.add(new DashboardPanel("skill matrix", "skillmatrix", null, null));
                } else if ("ad2".equals(widgetType)) {
                    panels.add(new DashboardPanel("ad2", "ad2", null, null));
                } else if ("ad3".equals(widgetType)) {
                    panels.add(new DashboardPanel("ad3", "ad3", null, null));
                } else if ("placeholder1".equals(widgetType)) {
                    panels.add(new DashboardPanel("Placeholder1", "placeholder1", null, null));
                } else if ("placeholder2".equals(widgetType)) {
                    panels.add(new DashboardPanel("Placeholder2", "placeholder2", null, null));
                } else if ("admin".equals(widgetType) && Utils.isAdmin(currentUser)) {
                    panels.add(new DashboardPanel("Admin", "admin", null, null));
                }

            }
            Set<String> restWidgetIds = widgetTypeMap.keySet();
            if (!CollectionUtils.isEmpty(restWidgetIds)) {
                for (String restwidgetId : restWidgetIds) {
                    String widgetType = widgetTypeMap.get(restwidgetId);
                    if ("myprofile".equals(widgetType)) {
                        widgetLibPanels.add(new DashboardPanel("my profile", "myprofile", null, null));
                    } else if ("todoreminder".equals(widgetType)) {
                        widgetLibPanels.add(new DashboardPanel("todo reminder", "todoreminder", null, null));
                    } else if ("ad1".equals(widgetType)) {
                        widgetLibPanels.add(new DashboardPanel("ad1", "ad1", null, null));
                    } else if ("skillmatrix".equals(widgetType)) {
                        widgetLibPanels.add(new DashboardPanel("skill matrix", "skillmatrix", null, null));
                    } else if ("ad2".equals(widgetType)) {
                        widgetLibPanels.add(new DashboardPanel("ad2", "ad2", null, null));
                    } else if ("ad3".equals(widgetType)) {
                        widgetLibPanels.add(new DashboardPanel("ad3", "ad3", null, null));
                    } else if ("placeholder1".equals(widgetType)) {
                        widgetLibPanels.add(new DashboardPanel("Placeholder1", "placeholder1", null, null));
                    } else if ("placeholder2".equals(widgetType)) {
                        widgetLibPanels.add(new DashboardPanel("Placeholder2", "placeholder2", null, null));
                    } else if ("admin".equals(widgetType) && Utils.isAdmin(currentUser)) {
                        widgetLibPanels.add(new DashboardPanel("Admin", "admin", null, null));
                    }
                }
            }
            
        } else {
            // setup the panel list by default

            panels.add(new DashboardPanel("my profile", "myprofile", null, null));
            panels.add(new DashboardPanel("todo reminder", "todoreminder", null, null));
            panels.add(new DashboardPanel("ad1", "ad1", null, null));
            panels.add(new DashboardPanel("skill matrix", "skillmatrix", null, null));
            panels.add(new DashboardPanel("ad2", "ad2", null, null));
            panels.add(new DashboardPanel("ad3", "ad3", null, null));
            panels.add(new DashboardPanel("Placeholder1", "placeholder1", null, null));
            panels.add(new DashboardPanel("Placeholder2", "placeholder2", null, null));
            if (Utils.isAdmin(currentUser)) {
                panels.add(new DashboardPanel("Admin", "admin", null, null));
            }

        }
        model.addAttribute("panels", panels);
        model.addAttribute("widgetLibPanels", widgetLibPanels);
        System.out.println("panel count : " + panels.size());

        System.out.println(users.get(0).getDisplayName());
        System.out.println(userName);
        return "user/dashboard";
    }
    
}
