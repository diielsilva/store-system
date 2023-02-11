package com.dev.storesystem.domain.services;

import com.dev.storesystem.common.dtos.user.SaveUserDto;
import com.dev.storesystem.common.dtos.user.ShowUserDto;
import com.dev.storesystem.common.dtos.user.UpdateUserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    ShowUserDto save(SaveUserDto saveUserDto);

    Page<ShowUserDto> findAllActive(Pageable pageable);

    Page<ShowUserDto> findAllInactive(Pageable pageable);

    ShowUserDto findActiveById(Long id);

    ShowUserDto findInactiveById(Long id);

    ShowUserDto update(String username, UpdateUserDto updateUserDto);

    void restore(Long id);

    void delete(Long id);
}
