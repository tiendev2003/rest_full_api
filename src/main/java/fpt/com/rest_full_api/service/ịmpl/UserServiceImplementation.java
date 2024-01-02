package fpt.com.rest_full_api.service.ịmpl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpt.com.rest_full_api.exception.UserException;
import fpt.com.rest_full_api.model.UserEntity;
import fpt.com.rest_full_api.model.emum.Rank;
import fpt.com.rest_full_api.repository.UserRepository;

import fpt.com.rest_full_api.security.JWTGenerator;
import fpt.com.rest_full_api.service.UserService;

@Service
public class UserServiceImplementation implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JWTGenerator jwtTokenProvider;

	@Override
	public UserEntity findUserById(Long userId) throws UserException {

		Optional<UserEntity> user = userRepository.findById(userId);

		if (user.isPresent()) {
			return user.get();
		}

		throw new UserException("user not found with id " + userId);
	}

	@Override
	public UserEntity findUserProfileByJwt(String jwt) throws UserException {
		System.out.println("user service");
		String email = jwtTokenProvider.getEmailFromJwtToken(jwt);

		System.out.println("email" + email);

		UserEntity user = userRepository.findByEmail(email);

		if (user == null) {
			throw new UserException("user not exist with email " + email);
		}
		System.out.println("email user" + user.getEmail());
		return user;
	}

	@Override
	public UserEntity updateUserProfileByJwt(String jwt, UserEntity updatedUser) throws UserException {

		String email = jwtTokenProvider.getEmailFromJwtToken(jwt);

		UserEntity currentUser = userRepository.findByEmail(email);

		if (currentUser == null) {
			throw new UserException("Người dùng không tồn tại với email " + email);
		}

		if (updatedUser.getFirstName() != null) {
			currentUser.setFirstName(updatedUser.getFirstName());
		}

		if (updatedUser.getLastName() != null) {
			currentUser.setLastName(updatedUser.getLastName());
		}

		if (updatedUser.getMobile() != null) {
			currentUser.setMobile(updatedUser.getMobile());
		}

		if (!currentUser.getEmail().equals(updatedUser.getEmail())) {

			UserEntity existingUserWithEmail = userRepository.findByEmail(updatedUser.getEmail());
			if (existingUserWithEmail != null) {
				throw new UserException("Email đã tồn tại");
			}
			if (updatedUser.getEmail() != null) {
				currentUser.setEmail(updatedUser.getEmail());
			}
		}

		currentUser.setPoints(updatedUser.getPoints());
		if (updatedUser.getPoints() >= 0 && updatedUser.getPoints() < 200) {
			currentUser.setRank(Rank.BRONZE);
		} else if (updatedUser.getPoints() >= 200 && updatedUser.getPoints() < 400) {
			currentUser.setRank(Rank.SILVER);
		} else if (updatedUser.getPoints() >= 400 && updatedUser.getPoints() < 600) {
			currentUser.setRank(Rank.GOLD);
		} else {
			currentUser.setRank(Rank.DIAMOND);
		}

		return userRepository.save(currentUser);
	}

	@Override
	public List<UserEntity> getAllUsers() {
		return userRepository.findAll();
	}

}