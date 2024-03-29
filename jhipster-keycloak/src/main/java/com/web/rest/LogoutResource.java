package com.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
* REST controller for managing global OIDC logout.
*/
@RestController
public class LogoutResource {
    private final Logger log = LoggerFactory.getLogger(LogoutResource.class);
    private final UserInfoRestTemplateFactory templateFactory;
    private final String accessTokenUri;

    public LogoutResource(UserInfoRestTemplateFactory templateFactory,
                          @Value("${security.oauth2.client.access-token-uri}") String accessTokenUri) {
        this.templateFactory = templateFactory;
        this.accessTokenUri = accessTokenUri;
    }

    /**
     * POST  /api/logout : logout the current user
     *
     * @return the ResponseEntity with status 200 (OK) and a body with a global logout URL and ID token
     */
    @PostMapping("/api/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, Authentication authentication) {
        log.debug("REST request to logout User : {}", authentication);
        OAuth2RestTemplate oauth2RestTemplate = this.templateFactory.getUserInfoRestTemplate();
        String idToken = (String) oauth2RestTemplate.getAccessToken().getAdditionalInformation().get("id_token");

        String logoutUrl = accessTokenUri.replace("token", "logout");
        Map<String, String> logoutDetails = new HashMap<>();
        logoutDetails.put("logoutUrl", logoutUrl);
        logoutDetails.put("idToken", idToken);
        request.getSession().invalidate();
        return ResponseEntity.ok().body(logoutDetails);
    }
}
