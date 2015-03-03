package com.artreset.user.service;

import com.artreset.model.User;
import com.artreset.user.dto.RegistrationForm;
import com.artreset.user.exception.DuplicateEmailException;

/**
 * @author Taehyun Jung
 */
public interface UserService {

    /**
     * 새로운 사용자 계정을 등록한다.
     * @param userAccountData   The information of the created user account.
     * @return  The information of the created user account.
     * @throws DuplicateEmailException Thrown when the email address is found from the database.
     */
    public User registerNewUserAccount(RegistrationForm userAccountData) throws DuplicateEmailException;
}
