package fpt.com.rest_full_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fpt.com.rest_full_api.model.Address;

 
public interface AddressRepository extends JpaRepository<Address, Long> {

}
