package com.web.rest;

import com.service.UserService;
import com.service.dto.UserDTO;
import com.web.rest.errors.InternalServerErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private final UserService userService;

    public AccountResource(UserService userService) {
        this.userService = userService;
    }

    /**
     * GET  /authenticate : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request
     * @return the login if the user is authenticated
     */
    @GetMapping("/authenticate")
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    /**
     * GET  /account : get the current user.
     *
     * @param principal the current user; resolves to null if not authenticated
     * @return the current user
     * @throws InternalServerErrorException 500 (Internal Server Error) if the user couldn't be returned
     */
    @GetMapping("/account")
    @SuppressWarnings("unchecked")
    public UserDTO getAccount(Principal principal) {
        if (principal != null) {
            if (principal instanceof OAuth2Authentication) {
                return userService.getUserFromAuthentication((OAuth2Authentication) principal);
            } else {
                // Allow Spring Security Test to be used to mock users in the database
                return userService.getUserWithAuthorities()
                    .map(UserDTO::new)
                    .orElseThrow(() -> new InternalServerErrorException("User could not be found"));
            }
        } else {
            throw new InternalServerErrorException("User could not be found");
        }
    }
}
