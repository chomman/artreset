package com.artreset.app.sample.service;

import java.util.List;

import com.artreset.app.sample.dto.TodoDTO;
import com.artreset.app.sample.exception.TodoNotFoundException;
import com.artreset.model.Todo;

/**
 * Artreset Project의 Application 개발 샘플로 제작한 Service<br>
 * 일반적인 경우, 웹작업Service의 코딩구조 및 스타일 등은 이를 모델로 삼는다.
 * 
 * @author Taehyun Jung
 */
public interface TodoService {

    /**
     * Adds a new to-do entry.
     * @param added The information of the added to-do entry.
     * @return  The added to-do entry.
     */
    public Todo add(TodoDTO added);

    /**
     * Deletes a to-do entry.
     * @param id    The id of the deleted to-do entry.
     * @return  The deleted to-do entry.
     * @throws TodoNotFoundException    if no to-do entry is found with the given id.
     */
    public Todo deleteById(Long id) throws TodoNotFoundException;

    /**
     * Returns a list of to-do entries.
     * @return
     */
    public List<Todo> findAll();

    /**
     * Finds a to-do entry.
     * @param id    The id of the wanted to-do entry.
     * @return  The found to-entry.
     * @throws TodoNotFoundException    if no to-do entry is found with the given id.
     */
    public Todo findById(Long id) throws TodoNotFoundException;

    /**
     * Updates the information of a to-do entry.
     * @param updated   The information of the updated to-do entry.
     * @return  The updated to-do entry.
     * @throws TodoNotFoundException    If no to-do entry is found with the given id.
     */
    public Todo update(TodoDTO updated) throws TodoNotFoundException;
}
