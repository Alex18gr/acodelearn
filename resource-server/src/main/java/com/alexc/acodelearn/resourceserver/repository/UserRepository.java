package com.alexc.acodelearn.resourceserver.repository;

import com.alexc.acodelearn.resourceserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    User findByuserName(String username);

}
