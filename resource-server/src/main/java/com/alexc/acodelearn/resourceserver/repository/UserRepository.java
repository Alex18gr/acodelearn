package com.alexc.acodelearn.resourceserver.repository;

import com.alexc.acodelearn.resourceserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    User findByuserName(String username);

    @Override
    <S extends User> List<S> saveAll(Iterable<S> iterable);
}
