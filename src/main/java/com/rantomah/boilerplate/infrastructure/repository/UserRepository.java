package com.rantomah.boilerplate.infrastructure.repository;

import com.rantomah.boilerplate.application.domain.entities.User;
import com.rantomah.boilerplate.core.GenericRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface UserRepository extends GenericRepository<User, UUID> {

    Optional<User> findByUsername(String username);

    Page<User> findAll(Specification<User> spec, Pageable pageable);
}
