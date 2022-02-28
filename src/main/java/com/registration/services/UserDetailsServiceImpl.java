package com.registration.services;

import com.registration.Entities.Registration;
import com.registration.Repository.RegistrationRepository;
import com.registration.helper.JwtUtil;
import com.registration.model.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;


@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    private Registration reg=null;
    private User user;
    int mob=1;
    int email=1;

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public CustomUserDetails loadUserByUsername(String useremail) throws UsernameNotFoundException{

        Registration registration=registrationRepository.getUserByUserName(useremail);

        if(registration==null)
        {
            throw new UsernameNotFoundException("user not found");
        }
        System.out.println("success in loadUserByUserName");
        CustomUserDetails CustomUserDetails =new CustomUserDetails(registration);

        User user=new User(useremail,registration.getPassword(),new ArrayList<>());


        String token=this.jwtUtil.generateToken(user);
        System.out.println("token "+token);
        return CustomUserDetails;

    }

    /**
     *Get ALL Users
     */

    public List<Registration> getAllUser()
    {
        List<Registration> regi=registrationRepository.findAll();
        return regi;
    }

    /**
     *  Add New User in Mysqldb
     */

    public Registration addUser(Registration registration) throws Exception
    {

        List<Registration> users=getAllUser();
        users.forEach(user->{
            if(user.getMobile().equals(registration.getMobile()))
            {
                try {
                    throw new Exception("this mobile number is"+registration.getMobile()+" already exist");
                } catch (Exception e) {
                    System.out.println("mobile already exist");
                    mob=0;
//
                }
            } if(user.getEmail().equals(registration.getEmail()))
            {
                try {
                    throw new Exception("this "+registration.getEmail()+" is already exist");
                } catch (Exception e) {
                    System.out.println("email already exist");
                    email=0;
                }
            }
        });
        try {
            reg = registrationRepository.save(registration);
        }catch(Exception e)
        {
            if(mob==0)
            {
               throw new Exception("mobile is already register");
            }
            if(email==0)
            {
                throw new Exception("email is already register");
            }
            System.out.println("please fill details in right way");
        }
        return reg;
    }

    /**
     * Get a User by Email
     **/

    public Registration getUser(String email)
    {
        List<Registration> users=registrationRepository.findAll();
        int result=0;
        for (Registration user : users)
        {
            if (user.getEmail().equals(email)) {
                user.setFirstname(user.getFirstname());
                user.setLastname(user.getLastname());
                user.setEmail(user.getEmail());
                user.setMobile(user.getMobile());
                user.setPassword(user.getPassword());
                result=1;
                return user;
            }
        }    //new update
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
//    }
        if(result==0){
        throw new UsernameNotFoundException("user not found!!!");
        }
        return null;
    }


    public Registration update(String email,Registration registration)
    {
        Registration reg=getUser(email);
       if(getUser(email).getEmail().equals(registration.getEmail())==false)
       {
           System.out.println(" condition ");
          throw new UsernameNotFoundException("user does not exist ");
       }else{
           reg.setFirstname(registration.getFirstname());
           reg.setLastname(registration.getLastname());
           reg.setEmail(registration.getEmail());
           reg.setMobile(registration.getMobile());
           reg.setPassword(registration.getPassword());
          return registrationRepository.save(reg);
       }

    }

    public String deleteByEmail(String email) {
        System.out.println("delete BY email");
        Registration regi=getUser(email);
        if(getUser(email).getEmail().equals(email)==true)
        {
            registrationRepository.delete(regi);
            return "user delete successfully";

        }else {
            throw new UsernameNotFoundException("user doesn't found");
        }
    }

}
