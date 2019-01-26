package com.inventory.inventory.user.service;

import com.inventory.inventory.user.dto.LoginDto;
import com.inventory.inventory.user.dto.RegistryDto;
import com.inventory.inventory.user.model.LoginSession;

public interface LoginService {

    void registry(RegistryDto registryDto);

    LoginSession login(LoginDto loginDto);
}
