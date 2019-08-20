package com.webServices.rutas.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;
import org.springframework.data.couchbase.core.mapping.id.IdPrefix;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;

/**
 * Representa una Cooperativa
 * @author Davids Adrian Gonzalez Tigrero
 * @version 1.0
 */
@Document
public class Cooperativa {
	
	@IdPrefix
	private String prefix = "cooperativa";
	
	@Id @GeneratedValue(strategy = GenerationStrategy.UNIQUE,delimiter = "::")
	private String id;
	
	/**
	 * Nombre de la Cooperativa.
	 */
	@NotNull(message = "Debe ingresar Nombre de Cooperativa.")
	@Size(min = 3,max = 100,message = "El nombre debe contener de 3 a 100 caracteres.")
	@Field
	private String nombre;
	
	/**
	 * Descripción de la Cooperativa
	 */
	@Size(min = 5, message = "La descripción debe ser mayor a 5 caracteres.")
	@Field
	private String descripcion;
	
	/**
	 * Dirección de la Cooperativa.
	 */
	@Size(min = 5,message = "La direccion debe contener minimo 5 caracteres.")
	@Field
    private String direccion;
	
	/**
	 * Telefono de la Cooperativa.
	 */
	@Pattern(regexp="(^$|[0-9]{10})",message = "Debe ingresar solo Numeros")
	@Field
    private String telefono;
	
	/**
	 * Email de la Cooperativa.
	 */
	@Email(message = "Debe ingresar un Email valido.")
	@Field
	private String email;
	@Field
	private Boolean estado;
	public Cooperativa() {
		super();
		this.estado = true;
	}
	public Cooperativa(String id, String nombre, String descripcion, String direccion, String telefono, String email,
			String type, Boolean estado) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.direccion = direccion;
		this.telefono = telefono;
		this.email = email;
		this.estado = true;
	}
	public Boolean getEstado() {
		return estado;
	}
	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
