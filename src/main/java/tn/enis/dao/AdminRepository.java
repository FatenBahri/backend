package tn.enis.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.enis.entities.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByUsername(String username);
}
