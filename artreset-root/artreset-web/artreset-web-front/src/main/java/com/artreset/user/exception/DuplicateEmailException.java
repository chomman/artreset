package com.artreset.user.exception;

/**
 * 회원 등록 시 주어진 이메일과 동일한 이메일이 DB상에서 발견되면 발생하는 Exception
 * 
 * @author Taehyun Jung
 */
public class DuplicateEmailException extends Exception {

    public DuplicateEmailException(String message) {
        super(message);
    }
}
