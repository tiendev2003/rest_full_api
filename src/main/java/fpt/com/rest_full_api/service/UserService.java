package fpt.com.rest_full_api.service;

import java.util.List;

import fpt.com.rest_full_api.exception.UserException;
import fpt.com.rest_full_api.model.UserEntity;
 

public interface UserService {

	public List<UserEntity> getAllUsers() throws UserException;

	public UserEntity findUserById(Long userId) throws UserException;

	public UserEntity findUserProfileByJwt(String jwt) throws UserException;

	public UserEntity updateUserProfileByJwt(String jwt, UserEntity updateUser) throws UserException;
 
}
