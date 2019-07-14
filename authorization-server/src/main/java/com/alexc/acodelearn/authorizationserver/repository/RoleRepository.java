package com.alexc.acodelearn.authorizationserver.repository;

import com.alexc.acodelearn.authorizationserver.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findAll();

}
