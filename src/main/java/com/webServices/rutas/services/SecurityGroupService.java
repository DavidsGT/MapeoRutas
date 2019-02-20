package com.webServices.rutas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.webServices.rutas.model.SecurityGroup;
import com.webServices.rutas.repository.SecurityGroupRepository;

public class SecurityGroupService {
	@Autowired
	private SecurityGroupRepository securityGroupRepository;
	public List<SecurityGroup> listUserGroups(String companyId, String id) {
		// TODO Auto-generated method stub
		return securityGroupRepository.listUserGroups(companyId, id);
	}

}
