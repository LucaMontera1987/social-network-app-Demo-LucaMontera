package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.ChatPrivate;
import com.model.User;
import com.repository.ChatPrivateRepo;

@Service
public class ChatService {
	
	private final ChatPrivateRepo chatPrivateRepo;
	
	@Autowired	
	public ChatService(ChatPrivateRepo chatPrivateRepo) {
		this.chatPrivateRepo = chatPrivateRepo;
	}




	public List <ChatPrivate> getListMessage(User user,Long idDestinatario){
		System.out.println("1  getListMessage");

		List <ChatPrivate>getListMessage=chatPrivateRepo.loadChat(user.getId(), idDestinatario);
		
		System.out.println("2  getListMessage" + getListMessage);

		return getListMessage;
	}

}
