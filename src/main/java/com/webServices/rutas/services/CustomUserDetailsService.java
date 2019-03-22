package com.webServices.rutas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.webServices.rutas.model.SegUsuario;
import com.webServices.rutas.repository.SegUsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private SegUsuarioRepository segUsuarioRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SegUsuario segUsuario = segUsuarioRepository.findByUsuario(username);
		if(segUsuario.equals(null)) {
            throw new UsernameNotFoundException("No se pudo encontrar el usuario: "+username);
        }
		return User.withUsername(segUsuario.getUsuario())
                .password("{noop}"+segUsuario.getClave())
                .roles(segUsuario.getPerfil()).build();
	}
}




