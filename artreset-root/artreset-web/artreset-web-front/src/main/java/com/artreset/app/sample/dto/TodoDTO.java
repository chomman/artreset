package com.artreset.app.sample.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.artreset.common.dto.BaseDTO;
import com.artreset.model.Todo;

/**
 * Artreset Project의 Application 개발 샘플로 제작한 DTO<br>
 * 일반적인 경우, 웹작업 DTO의 코딩구조 및 스타일 등은 이를 모델로 삼는다.
 * 
 * @author Taehyun Jung
 */
public class TodoDTO extends BaseDTO {

    private Long id;
    
    @NotEmpty
    @Length(max = Todo.MAX_LENGTH_TITLE)
    private String title;

    @Length(max = Todo.MAX_LENGTH_DESCRIPTION)
    private String description;

    public TodoDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }
    
    public String getTitle() {
    	return title;
    }
    
    public void setTitle(String title) {
    	this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
