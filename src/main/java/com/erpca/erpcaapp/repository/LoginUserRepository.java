// src/main/java/com/erpca/erpcaapp/repository/LoginUserRepository.java
package com.erpca.erpcaapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.erpca.erpcaapp.model.LoginUser;

public interface LoginUserRepository extends JpaRepository<LoginUser, String> {
    // findByUsername is optional because findById(username) also works
    LoginUser findByUsername(String username);
}
