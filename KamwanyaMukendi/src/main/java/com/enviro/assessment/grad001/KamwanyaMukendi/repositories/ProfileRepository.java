package com.enviro.assessment.grad001.KamwanyaMukendi.repositories;

import com.enviro.assessment.grad001.KamwanyaMukendi.model.Client;
import com.enviro.assessment.grad001.KamwanyaMukendi.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
        Profile findByEmail(String email) ;

}

