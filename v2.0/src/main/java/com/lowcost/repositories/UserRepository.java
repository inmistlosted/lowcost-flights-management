package com.lowcost.repositories;

import com.lowcost.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    @Modifying
    @Transactional
    @Query("update User us set us.account=:newAccount where us.username=:userName")
    void updateUserAccount(@Param("newAccount") double newAccount, @Param("userName") String username);
}
