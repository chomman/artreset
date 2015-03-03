package com.artreset.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.artreset.common.model.BaseEntity;

/**
 * Artreset Project의 Application 개발 샘플로 제작한 Model<br>
 * Builder 패턴을 이용한 Model 개발을 추천한다.
 * 
 * @author Taehyun Jung
 */
@Entity
@Table(name="todos")
public class Todo extends BaseEntity<Long> {

    public static final int MAX_LENGTH_DESCRIPTION = 500;
    public static final int MAX_LENGTH_TITLE = 100;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "description", nullable = true, length = MAX_LENGTH_DESCRIPTION)
    private String description;

    @Column(name = "title", nullable = false, length = MAX_LENGTH_TITLE)
    private String title;

    public Todo() {

    }

    public static Builder getBuilder(String title) {
        return new Builder(title);
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public void update(String description, String title) {
        this.description = description;
        this.title = title;
    }

    public static class Builder {

        private Todo built;

        public Builder(String title) {
            built = new Todo();
            built.title = title;
        }

        public Todo build() {
            return built;
        }

        public Builder description(String description) {
            built.description = description;
            return this;
        }
    }
}
