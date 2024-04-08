package com.mos.global.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GoogleOAuthController {

    @GetMapping("/login/oauth2/code/google")
    public String googleOAuth(String code) {
        return "/";
    }

}
