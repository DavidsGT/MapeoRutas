package com.webServices.rutas.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.webServices.rutas.model.SegUsuario;
import com.webServices.rutas.repository.SegPerfilRepository;
import com.webServices.rutas.repository.SegUsuarioRepository;
//TODO no agregue la parte de slf4j de lombok
@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private SegUsuarioRepository segUsuarioRepository;
	@Autowired
	private SegPerfilRepository segPerfilRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SegUsuario segUsuario = segUsuarioRepository.findByNombre(username);
		List<GrantedAuthority> authorities = new ArrayList<>();
		if(segUsuario.equals(null)) {
            throw new UsernameNotFoundException("No se pudo encontrar el usuario: "+username);
        }
		authorities.add(new SimpleGrantedAuthority(segPerfilRepository.findById(segUsuario.getIdSegPerfil()).get().getNombre()));
		return User.withUsername(segUsuario.getNombre())
                .password("{noop}"+segUsuario.getClave())
                .roles("USER_MOVIL").build();
				//new User(segUsuario.getNombre(), "{noop}"+segUsuario.getClave(), getAuthority());
	}
}
