package com.registration.Controllers;

import com.registration.Entities.Registration;
import com.registration.services.UserDetailsServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SecurityRequirement(name = "javainuseapi")
@RequestMapping("/user")
@RequiredArgsConstructor
@RestController
public class userController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @GetMapping("/users")
    public List<Registration> users()
    {
        return userDetailsServiceImpl.getAllUser();
    }


    @GetMapping("/home")
    public String getBaseUser(){
        return "user deshboard";
    }

    @DeleteMapping("/delete/{email}")
    public String userDelete(@PathVariable("email") String email)
    {
        System.out.println("email");
        userDetailsServiceImpl.deleteByEmail(email);
        return "success"+email;
    }

    @GetMapping("/user/{email}")
    public Registration updateUser(@PathVariable("email") String email)
    {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        System.out.println("matcher "+matcher.matches());
        if(matcher.matches())
        {
            return userDetailsServiceImpl.getUser(email);
        }else{
            throw new UsernameNotFoundException("user does not exits");
        }
    }

    @PostMapping("/update/{email}")
    public Registration updateUser(@PathVariable("email") String email,@RequestBody Registration registration)
    {
        Registration reg;
        String emailRegex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(registration.getEmail());

        String passwordValidation="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        Pattern patternn = Pattern.compile(passwordValidation);
        Matcher matcherr = patternn.matcher(registration.getPassword());

        String mobileValidation="[6789][0-9]{9}";
        Pattern patternnn = Pattern.compile(mobileValidation);
        Matcher matcherrr = patternnn.matcher(registration.getMobile());
        if(matcher.matches() && matcherr.matches() && matcherrr.matches()){
            registration.setPassword(this.bCryptPasswordEncoder.encode(registration.getPassword()));
            reg= userDetailsServiceImpl.update(email, registration);
            return reg;
        }else {
            throw new UsernameNotFoundException("user enter wrong format values");
        }
    }

}
