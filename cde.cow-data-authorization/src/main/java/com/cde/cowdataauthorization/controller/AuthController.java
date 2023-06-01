package com.cde.cowdataauthorization.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.cde.cowdataauthorization.dto.AuthResponseDto;
import com.cde.cowdataauthorization.dto.ErrorResponse;
import com.cde.cowdataauthorization.dto.SignInRequestDto;
import com.cde.cowdataauthorization.dto.SignInResponseDto;
import com.cde.cowdataauthorization.dto.SignUpRequestDto;
import com.cde.cowdataauthorization.dto.SuccessResponse;
import com.cde.cowdataauthorization.exception.ForbiddenException;
import com.cde.cowdataauthorization.service.AuthService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class AuthController {

    Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    AuthService authService;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(HttpServletRequest httpRequest,
                                              @Valid @RequestBody SignInRequestDto signInRequest) {

        SignInResponseDto signInResponseDto;

        try {
            signInResponseDto = authService.signIn(signInRequest);
        } catch (Exception ex) {
            LOGGER.error("error message: " + ex.getMessage(), httpRequest);
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Unexpected error signing in.", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<SuccessResponse>(
                new SuccessResponse(HttpStatus.OK.value(), "Sign In Successful", signInResponseDto),
                HttpStatus.OK);

	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(HttpServletRequest httpRequest,
                                          @Valid @RequestBody SignUpRequestDto signUpRequest) {

        try {
            authService.signUp(signUpRequest);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.error("error message: " + ex.getMessage(), httpRequest);
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Unexpected error signing in.", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<SuccessResponse>(
                new SuccessResponse(HttpStatus.OK.value(), "Sign Up Successful"),
                HttpStatus.OK);

	}

	@GetMapping("/auth")
	public ResponseEntity<?> validateAccess(HttpServletRequest httpRequest,
                                            @RequestHeader(name = "Authorization", required = true) String authorization,
                                            @RequestHeader(name = "X-Forwarded-Method", required = false) String method,
                                            @RequestHeader(name = "X-Forwarded-Proto", required = false) String protocol,
                                            @RequestHeader(name = "X-Forwarded-Host", required = false) String host,
                                            @RequestHeader(name = "X-Forwarded-Uri", required = false) String path,
                                            @RequestHeader(name = "X-Forwarded-For", required = false) String sourceIp) {

        LOGGER.info(httpRequest.getRequestURI());
        LOGGER.info(httpRequest.getMethod());

        AuthResponseDto authResponseDto = new AuthResponseDto();

        try {
            authResponseDto = authService.validateAccess(authorization, method, protocol, host, path, sourceIp);
        } catch (ForbiddenException ex) {
            LOGGER.error("error message: " + ex.getMessage(), httpRequest);
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.FORBIDDEN.value(),
                    "Forbidden.", ex.getMessage()), HttpStatus.FORBIDDEN);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.error("error message: " + ex.getMessage(), httpRequest);
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Unexpected error authenticating request.", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok()
                             .header("X-User-Id", authResponseDto.getUserId())
                             .header("X-User-Roles", String.join(",", authResponseDto.getRoles()))
                             .build();

	}
}