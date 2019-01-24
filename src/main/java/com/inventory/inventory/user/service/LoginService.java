package com.inventory.inventory.user.service;

import com.inventory.inventory.user.dto.LoginDto;
import com.inventory.inventory.user.dto.RegistryDto;
import com.inventory.inventory.user.model.UserInfo;

public interface LoginService {

    void registry(RegistryDto registryDto);

    UserInfo login(LoginDto loginDto);
}
