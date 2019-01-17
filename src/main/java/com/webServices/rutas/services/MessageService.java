package com.webServices.rutas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webServices.rutas.model.Message;
import com.webServices.rutas.repository.MessageRepository;

@Service
public class MessageService {
	@Autowired
	private MessageRepository messageRepository;
	
	public void addMessage(Message message) {
		messageRepository.save(message);
	}
	public Iterable<Message> getAllMessage(){
		return messageRepository.findAll();
	}
}
