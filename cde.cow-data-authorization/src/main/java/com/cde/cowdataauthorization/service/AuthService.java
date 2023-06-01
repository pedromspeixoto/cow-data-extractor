package com.cde.cowdataauthorization.service;

import com.cde.cowdataauthorization.dto.AuthResponseDto;
import com.cde.cowdataauthorization.dto.SignInRequestDto;
import com.cde.cowdataauthorization.dto.SignInResponseDto;
import com.cde.cowdataauthorization.dto.SignUpRequestDto;
import com.cde.cowdataauthorization.exception.EmailAlreadyTakenException;
import com.cde.cowdataauthorization.exception.ForbiddenException;
import com.cde.cowdataauthorization.exception.RoleNotFoundException;
import com.cde.cowdataauthorization.exception.UsernameAlreadyTakenException;

public interface AuthService {

    // new sign up
    void signUp(SignUpRequestDto signUpRequestDto) throws EmailAlreadyTakenException, UsernameAlreadyTakenException, RoleNotFoundException;

    // sign in
    SignInResponseDto signIn(SignInRequestDto signInRequestDto);

    // validate access
    AuthResponseDto validateAccess(String authorization, String method, String protocol, String host, String path, String sourceIp) throws ForbiddenException;

}