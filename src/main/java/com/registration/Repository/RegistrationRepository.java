package com.registration.Repository;

import com.registration.Entities.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;


@EnableJpaRepositories
public interface RegistrationRepository extends JpaRepository<Registration,String> {
    @Query("SELECT r FROM Registration r WHERE r.email= :email")
    public Registration getUserByUserName(@Param("email") String email);

}