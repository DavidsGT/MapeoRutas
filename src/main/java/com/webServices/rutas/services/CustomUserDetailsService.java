package com.webServices.rutas.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.webServices.rutas.model.CustomUserDetail;
import com.webServices.rutas.model.SecurityGroup;
import com.webServices.rutas.model.SegUsuario;
import com.webServices.rutas.repository.SegPerfilRepository;
import com.webServices.rutas.repository.SegUsuarioRepository;
import com.webServices.rutas.repository.UserRepository;

public class CustomUserDetailsService implements UserDetailsService{
	@Autowired
    private SegUsuarioRepository  userRepository;
	@Autowired
    private SegPerfilRepository  perfilRepository;
    @Autowired
    private SecurityGroupService securityGroupService;
	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		SegUsuario user = userRepository.findByNombre(name);
        if(user.equals(null)) {
            throw new UsernameNotFoundException("Could not find the user "+name);
        }
        //List<SecurityGroup> securityGroups = securityGroupService.listUserGroups(user.getCompanyId(), user.getId());
        List<GrantedAuthority> listaPrivilegios = new ArrayList<GrantedAuthority>(); 
        listaPrivilegios.add(new SimpleGrantedAuthority(perfilRepository.findById(user.getIdSegPerfil()).get().getNombre()));
        return new User(user.getNombre(), user.getClave(), listaPrivilegios);
	}

}
