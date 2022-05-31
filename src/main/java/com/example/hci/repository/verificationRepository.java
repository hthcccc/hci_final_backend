package com.example.hci.repository;

import com.example.hci.model.Verification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface verificationRepository extends JpaRepository<Verification,String> {
}
