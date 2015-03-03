package com.artreset.admin.user.repository;

import com.artreset.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User 정보 저장 Repository Class
 * 
 * @author Taehyun Jung
 */
public interface AdminUserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String email);
}
