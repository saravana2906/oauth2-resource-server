package com.demo.resourceserver.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/foos")
public class FooController {

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public String findOne(@CurrentSecurityContext SecurityContext securitycontext) {

        System.out.println(securitycontext.getAuthentication().toString());
        return "Get-Mapping Foos";
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public String findAll(@CurrentSecurityContext SecurityContext securitycontext) {
        System.out.println(securitycontext.getAuthentication().toString());
        return "Post-Mapping-Foos";
    }

}
