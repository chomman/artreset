package com.artreset.app.sample.service;

import com.artreset.app.sample.dto.TodoDTO;
import com.artreset.app.sample.exception.TodoNotFoundException;
import com.artreset.app.sample.repository.TodoRepository;
import com.artreset.model.Todo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * TodoService의 JPA Repository Service 구현 Class
 * @author Taehyun Jung
 */
@Service
public class RepositoryTodoService implements TodoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryTodoService.class);

    private final TodoRepository repository;

    @Autowired
    public RepositoryTodoService(TodoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public Todo add(TodoDTO added) {
        LOGGER.debug("Adding a new to-do entry with information: {}", added);

        Todo model = Todo.getBuilder(added.getTitle())
                .description(added.getDescription())
                .build();

        return repository.save(model);
    }
    
    @Transactional(rollbackFor = {TodoNotFoundException.class})
    @Override
    public Todo update(TodoDTO updated) throws TodoNotFoundException {
    	LOGGER.debug("Updating contact with information: {}", updated);
    	
    	Todo model = findById(updated.getId());
    	LOGGER.debug("Found a to-do entry: {}", model);
    	
    	model.update(updated.getDescription(), updated.getTitle());
    	
    	return model;
    }

    @Transactional(rollbackFor = {TodoNotFoundException.class})
    @Override
    public Todo deleteById(Long id) throws TodoNotFoundException {
        LOGGER.debug("Deleting a to-do entry with id: {}", id);

        Todo deleted = findById(id);
        LOGGER.debug("Deleting to-do entry: {}", deleted);

        repository.delete(deleted);
        return deleted;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Todo> findAll() {
        LOGGER.debug("Finding all to-do entries");
        return repository.findAll();
    }

    @Transactional(readOnly = true, rollbackFor = {TodoNotFoundException.class})
    @Override
    public Todo findById(Long id) throws TodoNotFoundException {
        LOGGER.debug("Finding a to-do entry with id: {}", id);

        Todo found = repository.findOne(id);
        LOGGER.debug("Found to-do entry: {}", found);

        if (found == null) {
            throw new TodoNotFoundException("No to-entry found with id: " + id);
        }

        return found;
    }
}
