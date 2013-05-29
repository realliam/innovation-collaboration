package com.hp.it.innovation.collaboration.common.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.hp.it.innovation.collaboration.common.UserStatusEnum;
import com.hp.it.innovation.collaboration.dto.RoleDTO;
import com.hp.it.innovation.collaboration.dto.UserDTO;
import com.hp.it.innovation.collaboration.service.common.ServiceFactory;
import com.hp.it.innovation.collaboration.service.intf.UserService;

public class UserSecurityService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO userDTO = ServiceFactory.getService(UserService.class).findUserByUniqueName(username);
        if (userDTO != null) {
            Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
            for (RoleDTO role : userDTO.getRoles()) {
                GrantedAuthority auth = new SimpleGrantedAuthority(role.getName());
                auths.add(auth);
            }
            User user = new User(userDTO.getName(),
                                 userDTO.getPassword(),
                                 true,
                                 userDTO.getStatus().equals(UserStatusEnum.ACTIVE.toString()),
                                 true,
                                 true,
                                 auths);
            return user;
        } else {
            return null;
        }
    }

}
