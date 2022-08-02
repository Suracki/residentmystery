package com.suracki.residentmystery.repository;

import com.suracki.residentmystery.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    @Query("SELECT u FROM User u WHERE u.username = ?1")
    User findByUsername(String username);

}
