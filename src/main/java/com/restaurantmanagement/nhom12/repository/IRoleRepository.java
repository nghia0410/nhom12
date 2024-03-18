package com.restaurantmanagement.nhom12.repository;

import com.restaurantmanagement.nhom12.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(String roleName);
}

