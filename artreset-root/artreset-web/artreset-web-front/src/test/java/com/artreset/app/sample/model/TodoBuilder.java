package com.artreset.app.sample.model;

import org.springframework.test.util.ReflectionTestUtils;

import com.artreset.model.Todo;


/**
 * @author Taehyun Jung
 */
public class TodoBuilder {

    private Todo model;

    public TodoBuilder() {
        model = new Todo();
    }

    public TodoBuilder id(Long id) {
        ReflectionTestUtils.setField(model, "id", id);
        return this;
    }

    public TodoBuilder description(String description) {
        model.update(description, model.getTitle());
        return this;
    }

    public TodoBuilder title(String title) {
        model.update(model.getDescription(), title);
        return this;
    }

    public Todo build() {
        return model;
    }
}
