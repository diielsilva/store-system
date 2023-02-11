package com.dev.storesystem.domain.repositories;

import com.dev.storesystem.domain.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByUsernameAndDeletedAtIsNull(String username);

    Page<UserEntity> findByDeletedAtIsNull(Pageable pageable);

    Page<UserEntity> findByDeletedAtIsNotNull(Pageable pageable);

    Optional<UserEntity> findByIdAndDeletedAtIsNull(Long id);

    Optional<UserEntity> findByIdAndDeletedAtIsNotNull(Long id);
}
