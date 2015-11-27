package com.hp.it.innovation.collaboration.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hp.it.innovation.collaboration.common.UserRoleEnum;
import com.hp.it.innovation.collaboration.dto.RoleDTO;
import com.hp.it.innovation.collaboration.dto.UserDTO;

/**
 Merge test
 */
public class Utils {
    public static String populateUploadFileName(String originalFileName){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH-MM-SS");
        StringBuilder fileName = new StringBuilder(Constants.IMAGE_SUFFIX).append( format.format(date));
        fileName.append(originalFileName.substring(originalFileName.lastIndexOf(".")));
        return fileName.toString();
    }
    
    public static void storeUserIntoSession(UserDTO userDTO) {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        request.getSession().setAttribute(Constants.CURRENT_USER_KEY, userDTO);
    }
    
    public static void removeUserFromSession() {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        request.getSession().removeAttribute(Constants.CURRENT_USER_KEY);
    }
    
    public static UserDTO getCurrentUser(){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        return (UserDTO)request.getSession().getAttribute(Constants.CURRENT_USER_KEY);
    }
    
    public static boolean isAdmin(UserDTO userDTO) {
        if (userDTO.getRoles()!=null) {
            for (RoleDTO role : userDTO.getRoles()) {
                if (role.getName().equals(UserRoleEnum.ADMIN.toString()) || role.getName().equals(UserRoleEnum.SUPERVISOR.toString())) {
                    return true;
                }
            }
        }
        return false;
    }
}
