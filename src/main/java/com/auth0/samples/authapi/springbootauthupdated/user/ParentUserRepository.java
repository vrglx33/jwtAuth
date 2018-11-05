package com.auth0.samples.authapi.springbootauthupdated.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ParentUserRepository extends JpaRepository<ParentUser, Long> {

    ParentUser findByEmail(String username);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN 'true' ELSE 'false' END FROM ParentUser u WHERE u.email = ?1")
    public Boolean existsByemail(String email);
}