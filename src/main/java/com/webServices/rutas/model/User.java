package com.webServices.rutas.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;

import lombok.Data;
@Data
public class User extends BasicEntity implements Serializable{
	
	@Id
    @NotNull
    private String id;
    @Field
    @NotNull
    private String username;
    @Field
    @NotNull
    private String companyId;
    @Field
    @NotNull
    private String password;
    @NotNull
    private Boolean isEnabled;
    @Field
    private Boolean isVisible;
    
    
	public User() {
		super();
	}
	public User(@NotNull String id, @NotNull String username, @NotNull String companyId, @NotNull String password,
			@NotNull Boolean isEnabled, Boolean isVisible) {
		super();
		this.id = id;
		this.username = username;
		this.companyId = companyId;
		this.password = password;
		this.isEnabled = isEnabled;
		this.isVisible = isVisible;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getIsEnabled() {
		return isEnabled;
	}
	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	public Boolean getIsVisible() {
		return isVisible;
	}
	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}
    
}
