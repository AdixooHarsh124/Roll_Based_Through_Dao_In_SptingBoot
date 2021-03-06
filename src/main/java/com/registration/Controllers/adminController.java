package com.registration.Controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@SecurityRequirement(name = "javainuseapi")
@RequiredArgsConstructor
@RequestMapping("/admin")
@RestController
public class adminController {


    @GetMapping("/home")
    public String getAdmin()
    {
        return "admin deshboard";
    }

    @GetMapping("/noms")
    public String noms()
    {
        return "admin noms";
    }
}
