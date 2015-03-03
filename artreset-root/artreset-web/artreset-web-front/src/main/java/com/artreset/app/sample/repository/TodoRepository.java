package com.artreset.app.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.artreset.model.Todo;

/**
 * Artreset Project의 Application 개발 샘플로 제작한 Repository<br>
 * 일반적인 경우, 웹작업 Repository의 코딩구조 및 스타일 등은 이를 모델로 삼는다.
 * 
 * @author Taehyun Jung
 */
public interface TodoRepository extends JpaRepository<Todo, Long> {
}
