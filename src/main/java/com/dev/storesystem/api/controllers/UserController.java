package com.dev.storesystem.api.controllers;

import com.dev.storesystem.common.dtos.user.SaveUserDto;
import com.dev.storesystem.common.dtos.user.ShowUserDto;
import com.dev.storesystem.common.dtos.user.UpdateUserDto;
import com.dev.storesystem.domain.services.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ShowUserDto> save(@RequestBody @Valid SaveUserDto saveUserDto) {
        var user = service.save(saveUserDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<ShowUserDto>> findAllActive(Pageable pageable) {
        var users = service.findAllActive(pageable);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @GetMapping(value = "/inactive")
    public ResponseEntity<Page<ShowUserDto>> findAllInactive(Pageable pageable) {
        var users = service.findAllInactive(pageable);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ShowUserDto> findActiveById(@PathVariable Long id) {
        var user = service.findActiveById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @GetMapping(value = "/inactive/{id}")
    public ResponseEntity<ShowUserDto> findInactiveById(@PathVariable Long id) {
        var user = service.findInactiveById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ShowUserDto> update(Principal onlineUser, @RequestBody @Valid UpdateUserDto updateUserDto) {
        var user = service.update(onlineUser.getName(), updateUserDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @PatchMapping(value = "/{id}/restore")
    public ResponseEntity<ShowUserDto> restore(@PathVariable Long id) {
        service.restore(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ShowUserDto> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
