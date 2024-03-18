package com.restaurantmanagement.nhom12.repository;

import com.restaurantmanagement.nhom12.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface IUserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);

    boolean existsByEmail(String email);
}
