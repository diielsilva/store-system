package com.dev.storesystem.domain.providers.impl;

import com.dev.storesystem.domain.entities.UserEntity;
import com.dev.storesystem.domain.exceptions.EntityNotFound;
import com.dev.storesystem.domain.providers.UserProvider;
import com.dev.storesystem.domain.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserProviderImpl implements UserProvider {
    private final UserRepository repository;

    public UserProviderImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(UserEntity entity) {
        repository.save(entity);
    }

    @Override
    public Page<UserEntity> findAllActive(Pageable pageable) {
        return repository.findByDeletedAtIsNull(pageable);
    }

    @Override
    public Page<UserEntity> findAllInactive(Pageable pageable) {
        return repository.findByDeletedAtIsNotNull(pageable);
    }

    @Override
    public UserEntity findActiveById(Long id) throws EntityNotFound {
        return repository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new EntityNotFound("O usuário com ID: " + id + " não foi encontrado!"));
    }

    @Override
    public UserEntity findInactiveById(Long id) throws EntityNotFound {
        return repository.findByIdAndDeletedAtIsNotNull(id)
                .orElseThrow(() -> new EntityNotFound("O usuário com ID: " + id + " não foi encontrado!"));
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public UserEntity findActiveByUsername(String username) {
        return repository.findByUsernameAndDeletedAtIsNull(username)
                .orElseThrow(() -> new EntityNotFound("O usuário com identificação: " + username + " não foi encontrado!"));
    }
}
