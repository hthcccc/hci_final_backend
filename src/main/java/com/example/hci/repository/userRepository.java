package com.example.hci.repository;

import com.example.hci.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface userRepository extends JpaRepository<User,String> {

    @Query("select u.name from User u where u.id=?1")
    String getName(String user_id);


    @Query("select u from User u where u.name like %?1%")
    List<User> getUsersByName(String name);


    @Query("select u from User u where u.phone=?1")
    List<User> getUserByPhone(String phone);

    @Query("select count(u.id) from User u where u.phone=?1")
    Integer existsByPhone(String phone);
}
