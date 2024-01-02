package fpt.com.rest_full_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fpt.com.rest_full_api.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
