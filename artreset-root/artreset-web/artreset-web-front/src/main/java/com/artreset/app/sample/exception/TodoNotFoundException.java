package com.artreset.app.sample.exception;

/**
 * Artreset Project의 Application 개발 샘플로 제작한 Exception<br>
 * 일반적인 경우, 웹작업 Exception의 코딩구조 및 스타일 등은 이를 모델로 삼는다.
 * @author Taehyun Jung
 */
public class TodoNotFoundException extends Exception {

    public TodoNotFoundException(String message) {
        super(message);
    }

}
