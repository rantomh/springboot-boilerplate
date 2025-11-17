package com.rantomah.boilerplate.adapters.out.repository;

import com.rantomah.boilerplate.core.GenericRepository;
import com.rantomah.boilerplate.domain.model.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface UserRepository extends GenericRepository<User, UUID> {

    Optional<User> findByUsername(String username);

    Page<User> findAll(Specification<User> spec, Pageable pageable);
}
