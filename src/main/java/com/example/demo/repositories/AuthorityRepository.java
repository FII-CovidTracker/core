package com.example.demo.repositories;

import com.example.demo.models.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    List<Authority> findAll();
    Authority findAuthorityByEmail(String email);
}
