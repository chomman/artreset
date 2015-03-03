package com.artreset.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.artreset.security.model.PersistentToken;

/**
 * User 정보 저장 Repository Class
 * 
 * @author Taehyun Jung
 */
public interface PersistentTokenRepository extends JpaRepository<PersistentToken, String> {

}
