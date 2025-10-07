package com.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.User;
import com.repository.UserRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

	private final UserRepo userRepo;

	@Autowired
	public UserService(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	public void saveUser(User user) {
		userRepo.save(user);
	}
	
	public User findById(Long idUser) {
		User userTmp=userRepo.findById(idUser).orElseThrow(()->new EntityNotFoundException());
		return userTmp;
	}

	public void deleteUser(Long id) {
		User user = userRepo.findUserById(id).orElseThrow(() -> new EntityNotFoundException("user not found"));
		userRepo.delete(user);
	}

	public User updateUser(User user) {
		User userTmp = userRepo.findUserById(user.getId())
				.orElseThrow(() -> new EntityNotFoundException("user not found"));
		userTmp.setCognome(user.getCognome());
		userTmp.setEmail(user.getEmail());
		userTmp.setNome(user.getNome());
		userTmp.setUsername(user.getUsername());
		userRepo.save(userTmp);

		return userTmp;

	}

	public Optional<User> findUser(String username) {
		return  userRepo.findUserByEmailOrUsername(username);

		
	}

	public User findByEmail(String username) {
		return userRepo.findUserByEmailOrUsername(username).orElseThrow(() -> new EntityNotFoundException());
	}
	
	public User findByIdProfile(long idProfile) {
		User userTmp=userRepo.findByprofiloUser_id(idProfile).orElseThrow(()->new EntityNotFoundException());
		return userTmp;
		
	}
	
	public List<User> search(String termine){
	List<User>getAll=userRepo.getAllUser(termine);
	return getAll;
	}

}
