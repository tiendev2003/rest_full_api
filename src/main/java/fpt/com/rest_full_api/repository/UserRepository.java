package fpt.com.rest_full_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import fpt.com.rest_full_api.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	Optional<UserEntity> findByUsername(String username);

	Boolean existsByUsername(String username);

	public UserEntity findByEmail(String email);

	@EntityGraph(attributePaths = "roles")
	UserEntity findByActivationCode(String code);

	@Query("SELECT u.email FROM UserEntity u WHERE COALESCE(u.passwordResetCode, '') = COALESCE(:code, '')")
	Optional<String> getEmailByPasswordResetCode(@Param("code") String code);

	@Query("SELECT u FROM UserEntity u WHERE " +
			"(CASE " +
			"   WHEN :searchType = 'first_name' THEN UPPER(u.firstName) " +
			"   WHEN :searchType = 'last_name' THEN UPPER(u.lastName) " +
			"   ELSE UPPER(u.email) " +
			"END) " +
			"LIKE UPPER(CONCAT('%',:text,'%'))")
	Page<UserEntity> searchUsers(String searchType, String text, Pageable pageable);
}
