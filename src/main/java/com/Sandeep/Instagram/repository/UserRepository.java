package com.Sandeep.Instagram.repository;

import com.Sandeep.Instagram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
