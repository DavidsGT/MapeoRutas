package com.webServices.rutas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.webServices.rutas.model.Message;
import com.webServices.rutas.services.MessageService;

@RestController
public class MessageController {
	@Autowired
	private MessageService messageService;
	@RequestMapping("/message")
	public Iterable<Message> getAllMensajes(){
		return messageService.getAllMessage();
	}
	@RequestMapping(method=RequestMethod.POST, value="/message")
	public void addMessage(@RequestBody Message message) {
		messageService.addMessage(message);
	}
}