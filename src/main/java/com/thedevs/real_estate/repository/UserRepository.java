package com.thedevs.real_estate.repository;

import com.thedevs.real_estate.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
