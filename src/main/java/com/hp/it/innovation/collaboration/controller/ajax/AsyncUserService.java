package com.hp.it.innovation.collaboration.controller.ajax;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.hp.it.innovation.collaboration.common.UserRoleEnum;
import com.hp.it.innovation.collaboration.dto.JsonResponseDTO;
import com.hp.it.innovation.collaboration.dto.RoleDTO;
import com.hp.it.innovation.collaboration.dto.ServiceResponseDTO;
import com.hp.it.innovation.collaboration.dto.TeamDTO;
import com.hp.it.innovation.collaboration.dto.UploadFileDTO;
import com.hp.it.innovation.collaboration.dto.UserDTO;
import com.hp.it.innovation.collaboration.service.common.ServiceFactory;
import com.hp.it.innovation.collaboration.service.intf.RoleService;
import com.hp.it.innovation.collaboration.service.intf.UserService;
import com.hp.it.innovation.collaboration.utilities.Constants;
import com.hp.it.innovation.collaboration.utilities.GlobalApplicationResources;
import com.hp.it.innovation.collaboration.utilities.Utils;

@Controller
@RequestMapping("/asyncUser/*")
public class AsyncUserService {
    
    @Autowired
    private GlobalApplicationResources globalResources;
    
    @RequestMapping("/user/{userName}")
    public @ResponseBody JsonResponseDTO retrieveUserInfo(@PathVariable String userName){
        JsonResponseDTO res = new JsonResponseDTO();
        if (StringUtils.isNotBlank(userName)) {
            UserDTO userDTO = ServiceFactory.getService(UserService.class).findUserByUniqueName(userName+".com");
            if (userDTO != null) {
                Map<String, String> userMap = convertUserDTOtoMap(userDTO);
                res.setStatus(JsonResponseDTO.SUCCESS_RESPONSE);
                res.setResult(userMap);
            } else {
                res.setStatus(JsonResponseDTO.FAILURE_RESPONSE);
                res.setResult("Cannot find user by specified user name!");
            }
        } else {
            res.setStatus(JsonResponseDTO.FAILURE_RESPONSE);
            res.setResult("Cannot find user by specified user name!");
        }
        return res;
    }
    
    private Map<String, String> convertUserDTOtoMap(UserDTO userDTO) {
        Map<String, String> userMap = new LinkedHashMap<String, String>();
        userMap.put("name", userDTO.getName());
        userMap.put("displayName", userDTO.getDisplayName());
        userMap.put("email", userDTO.getEmail());
        userMap.put("gender", userDTO.getGender());
        userMap.put("headURL", userDTO.getHeaderURL());
        userMap.put("status", userDTO.getStatus());
        if (userDTO.getRoles()!=null) {
            StringBuilder roles = new StringBuilder();
            for (RoleDTO role : userDTO.getRoles()) {
                roles.append("@");
                roles.append(role.getRoleName());
            }
            userMap.put("roles", roles.toString());
        }
        if (userDTO.getTeams()!=null) {
            StringBuilder teams = new StringBuilder();
            for (TeamDTO team : userDTO.getTeams()) {
                teams.append("@");
                teams.append(team.getTeamName());
            }
            userMap.put("teams", teams.toString());
        }
        return userMap;
    }
    
    @RequestMapping(value="/head", method=RequestMethod.POST)
    @PreAuthorize("hasAnyRole('MEMBER','ADMIN','SUPERVISOR')")
    public @ResponseBody List<UploadFileDTO> upload(@RequestParam("files") MultipartFile file, HttpServletRequest request) {
        
        String realPath = request.getSession().getServletContext().getRealPath(globalResources.getImageFolder());
        StringBuilder imageLocation = new StringBuilder(globalResources.getImageHost()).append(globalResources.getImageFolder());
        String fileSuffix = Utils.populateUploadFileName(file.getOriginalFilename());
        imageLocation.append(fileSuffix);
        
        try {
            CommonsMultipartFile cFile = (CommonsMultipartFile)file;
            cFile.getFileItem().write(new File(realPath, fileSuffix));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        UserDTO userDTO = Utils.getCurrentUser();
        userDTO.setHeaderURL(imageLocation.toString());
        userDTO = ServiceFactory.getService(UserService.class).updateUser(userDTO);
        Utils.storeUserIntoSession(userDTO);
        
        List<UploadFileDTO> uploadedFiles = new ArrayList<UploadFileDTO>();
        UploadFileDTO fileDTO = new UploadFileDTO(file.getOriginalFilename(), Long.valueOf(file.getSize()).intValue(),
                                                  imageLocation.toString());

        uploadedFiles.add(fileDTO);
        return uploadedFiles;
    }
    
    @RequestMapping(value="/removeHeadPhoto", produces="application/json")
    @PreAuthorize("hasAnyRole('MEMBER','ADMIN','SUPERVISOR')")
    public @ResponseBody JsonResponseDTO removeHeadPhoto() {
        JsonResponseDTO res = new JsonResponseDTO();
        UserDTO userDTO = Utils.getCurrentUser();
        userDTO.setHeaderURL(globalResources.getDefaultHeadPhoto());
        userDTO = ServiceFactory.getService(UserService.class).updateUser(userDTO);
        if (userDTO != null) {
            Utils.storeUserIntoSession(userDTO);
            res.setStatus(JsonResponseDTO.SUCCESS_RESPONSE);
            res.setResult(userDTO.getHeaderURL());
        } else {
            res.setStatus(JsonResponseDTO.FAILURE_RESPONSE);
            res.setResult("Remove head photo failed! Please try again later.");
        }
        return res;
    }
    
    @RequestMapping(value="/updateProfile", produces="application/json")
    @PreAuthorize("hasAnyRole('MEMBER','ADMIN','SUPERVISOR')")
    public @ResponseBody JsonResponseDTO update(@ModelAttribute(value = "userDTO") UserDTO userDTO) {
        JsonResponseDTO res = new JsonResponseDTO();
        UserDTO currentUser = Utils.getCurrentUser();
        populateNewInfoForCurrentUser(currentUser, userDTO);
        currentUser = ServiceFactory.getService(UserService.class).updateUser(currentUser);
        Utils.storeUserIntoSession(currentUser);
        res.setStatus(JsonResponseDTO.SUCCESS_RESPONSE);
        return res;
    }
    
    private void populateNewInfoForCurrentUser(UserDTO currentUser, UserDTO newProfileInfo) {
        currentUser.setDisplayName(newProfileInfo.getDisplayName());
        currentUser.setEmail(newProfileInfo.getEmail());
        currentUser.setGender(newProfileInfo.getGender());
    }
    
    @RequestMapping(value="/login", produces="application/json")
    public @ResponseBody JsonResponseDTO login(HttpServletRequest request, @ModelAttribute(value = "userDTO") UserDTO userDTO) {
        JsonResponseDTO jsonResponseDTO = new JsonResponseDTO();
        ServiceResponseDTO responseDTO = ServiceFactory.getService(UserService.class).checkLogin(userDTO);
        if (responseDTO != null) {
            if (Constants.LOGIN_FAILURE_USERNAME_ERROR.equals(responseDTO.getStatus())) {
                jsonResponseDTO.setStatus(JsonResponseDTO.FAILURE_RESPONSE);
                jsonResponseDTO.setResult("E-mail address not exist! Be sure no special charactor input!");
            } else if (Constants.LOGIN_FAILURE_USER_INVALID.equals(responseDTO.getStatus())) {
                jsonResponseDTO.setStatus(JsonResponseDTO.FAILURE_RESPONSE);
                jsonResponseDTO.setResult("User status is inactive,please contact system administor!");
            } else if (Constants.LOGIN_FAILURE_PASSWORD_ERROR.equals(responseDTO.getStatus())) {
                jsonResponseDTO.setStatus(JsonResponseDTO.FAILURE_RESPONSE);
                jsonResponseDTO.setResult("Password incorrect,please re-input!");
            } else {
                jsonResponseDTO.setStatus(JsonResponseDTO.SUCCESS_RESPONSE);
                Utils.storeUserIntoSession((UserDTO)responseDTO.getResult());
            }
        }
        return jsonResponseDTO;
    }

    @RequestMapping(value="/checkUserName", produces="application/json")
    public @ResponseBody JsonResponseDTO checkUserNameExisting(@ModelAttribute(value = "userDTO") UserDTO userDTO) {
        JsonResponseDTO jsonResponse = new JsonResponseDTO();
        boolean notExisting = ServiceFactory.getService(UserService.class).checkUniqueName(userDTO.getName());
        if (!notExisting) {
            jsonResponse.setStatus(JsonResponseDTO.FAILURE_RESPONSE);
            jsonResponse.setResult("This user name is existing!!");
        } else {
            jsonResponse.setStatus(JsonResponseDTO.SUCCESS_RESPONSE);
        }
        return jsonResponse;
    }

    @RequestMapping(value="/register", produces="application/json")
    public @ResponseBody JsonResponseDTO register(@ModelAttribute(value = "userDTO") UserDTO userDTO) {
        JsonResponseDTO res = new JsonResponseDTO();
        List<RoleDTO> roles = new ArrayList<RoleDTO>();
        RoleDTO role = ServiceFactory.getService(RoleService.class).findRoleByUniqueName(UserRoleEnum.MEMBER.toString());
        roles.add(role);
        userDTO.setRoles(roles);
        userDTO = ServiceFactory.getService(UserService.class).registerUser(userDTO);
        res.setStatus(JsonResponseDTO.SUCCESS_RESPONSE);
        Utils.storeUserIntoSession(userDTO);
        return res;
    }
    
    @RequestMapping(value="/logout", produces="application/json")
    public @ResponseBody JsonResponseDTO logout() throws IOException{
        JsonResponseDTO response = new JsonResponseDTO();
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        Utils.removeUserFromSession();
        if (request.getSession().getAttribute(Constants.CURRENT_USER_KEY) == null) {
            response.setStatus(JsonResponseDTO.SUCCESS_RESPONSE);
        } else {
            response.setStatus(JsonResponseDTO.FAILURE_RESPONSE);
        }
        return response;
    }
}
