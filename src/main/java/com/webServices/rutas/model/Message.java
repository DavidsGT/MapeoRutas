package com.webServices.rutas.model;

import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;

@Document
public class Message {
	@Id @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
	private String id;
	@Field
    private String asunto;
	@Field
    private String mensaje;
	@Field
	private String usuario;
	@Field
    private String movil;
	@Field
	private String type;

    public Message() {
    	this.type = "Message";
    }

    public Message(String id, String usuario,String movil, String subject, String text) {
    	super();
    	this.id = id;
        this.asunto = subject;
        this.mensaje = text;
        this.usuario = usuario;
        this.movil = movil;
        this.type = "Message";
    }

    public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getMovil() {
		return movil;
	}

	public void setMovil(String movil) {
		this.movil = movil;
	}

	public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }
    
    public String getType() {
		return type;
	}
    public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Message [id=" + id + ", asunto=" + asunto + ", mensaje=" + mensaje + ", type=" + type + "]";
	}
    
}