package com.dev.storesystem.domain.services.impl;

import com.dev.storesystem.common.dtos.user.SaveUserDto;
import com.dev.storesystem.common.dtos.user.ShowUserDto;
import com.dev.storesystem.common.dtos.user.UpdateUserDto;
import com.dev.storesystem.common.mappers.EntityMapper;
import com.dev.storesystem.domain.enums.UserPermission;
import com.dev.storesystem.domain.exceptions.BusinessException;
import com.dev.storesystem.domain.exceptions.EntityNotFound;
import com.dev.storesystem.domain.exceptions.UsernameInUse;
import com.dev.storesystem.domain.helpers.DateHelper;
import com.dev.storesystem.domain.providers.UserProvider;
import com.dev.storesystem.domain.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserProvider provider;
    private final EntityMapper entityMapper;
    private final PasswordEncoder passwordEncoder;
    private final DateHelper dateHelper;

    public UserServiceImpl(UserProvider provider, EntityMapper entityMapper, PasswordEncoder passwordEncoder,
                           DateHelper dateHelper) {
        this.provider = provider;
        this.entityMapper = entityMapper;
        this.passwordEncoder = passwordEncoder;
        this.dateHelper = dateHelper;
    }

    @Override
    public ShowUserDto save(SaveUserDto saveUserDto) {
        var userEntity = entityMapper.mapFromSaveUserDtoToUserEntity(saveUserDto);
        if (isUsernameInUse(userEntity.getUsername())) {
            throw new UsernameInUse("A identificação de usuário: " + userEntity.getUsername() + " já está em uso!");
        }
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        dateHelper.setSaveTime(userEntity);
        provider.save(userEntity);
        return entityMapper.mapFromUserEntityToShowUserDto(userEntity);
    }

    @Override
    public Page<ShowUserDto> findAllActive(Pageable pageable) {
        var activeUsers = provider.findAllActive(pageable);
        return activeUsers.map(entityMapper::mapFromUserEntityToShowUserDto);
    }

    @Override
    public Page<ShowUserDto> findAllInactive(Pageable pageable) {
        var inactiveUsers = provider.findAllInactive(pageable);
        return inactiveUsers.map(entityMapper::mapFromUserEntityToShowUserDto);
    }

    @Override
    public ShowUserDto findActiveById(Long id) {
        var activeUser = provider.findActiveById(id);
        return entityMapper.mapFromUserEntityToShowUserDto(activeUser);
    }

    @Override
    public ShowUserDto findInactiveById(Long id) {
        var inactiveUser = provider.findInactiveById(id);
        return entityMapper.mapFromUserEntityToShowUserDto(inactiveUser);
    }

    @Override
    public ShowUserDto update(String username, UpdateUserDto updateUserDto) {
        var databaseUser = provider.findByUsername(username)
                .orElseThrow(() -> new EntityNotFound("O usuário com identificação: " + username + " não foi encontrado!"));
        databaseUser.setName(updateUserDto.getName());
        dateHelper.setUpdateTime(databaseUser);
        provider.save(databaseUser);
        return entityMapper.mapFromUserEntityToShowUserDto(databaseUser);
    }

    @Override
    public void restore(Long id) {
        var user = provider.findInactiveById(id);
        user.setDeletedAt(null);
        dateHelper.setUpdateTime(user);
        provider.save(user);
    }

    @Override
    public void delete(Long id) {
        var user = provider.findActiveById(id);
        if (user.getPermission().equals(UserPermission.ROLE_ADMIN)) {
            throw new BusinessException("Usuários ADMIN não podem ser removidos!");
        }
        dateHelper.setDeleteTime(user);
        provider.save(user);
    }

    private boolean isUsernameInUse(String username) {
        return provider.findByUsername(username).isPresent();
    }
}
