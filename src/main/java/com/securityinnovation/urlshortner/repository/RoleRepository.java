package com.securityinnovation.urlshortner.repository;


import com.securityinnovation.urlshortner.entity.Role;
import com.securityinnovation.urlshortner.enums.RoleName;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}