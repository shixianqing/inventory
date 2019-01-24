package com.inventory.inventory.user.service.impl;

import com.inventory.inventory.common.exception.BusinessException;
import com.inventory.inventory.common.response.ResponseCode;
import com.inventory.inventory.user.dao.UserInfoMapper;
import com.inventory.inventory.user.dao.UserRoleInfoMapper;
import com.inventory.inventory.user.dao.UserStoreInfoMapper;
import com.inventory.inventory.user.dto.LoginDto;
import com.inventory.inventory.user.dto.RegistryDto;
import com.inventory.inventory.user.model.UserInfo;
import com.inventory.inventory.user.model.UserRoleInfo;
import com.inventory.inventory.user.model.UserStoreInfo;
import com.inventory.inventory.user.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Date;

/**
 * @Author:shixianqing
 * @Date:2019/1/2414:50
 * @Description:
 **/
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserRoleInfoMapper userRoleInfoMapper;

    @Autowired
    private UserStoreInfoMapper userStoreInfoMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Override
    @Transactional
    public void registry(RegistryDto registryDto) {
        if (ObjectUtils.isEmpty(registryDto)){
            throw new BusinessException(ResponseCode.PARAM_EMPTY_CODE,"注册信息不能为空！");
        }

        if (ObjectUtils.isEmpty(registryDto.getLoginName())){
            throw new BusinessException(ResponseCode.PARAM_EMPTY_CODE,"登录名不能为空！");
        }
        if (ObjectUtils.isEmpty(registryDto.getUserName())){
            throw new BusinessException(ResponseCode.PARAM_EMPTY_CODE,"用户名不能为空！");
        }
        if (ObjectUtils.isEmpty(registryDto.getPassword())){
            throw new BusinessException(ResponseCode.PARAM_EMPTY_CODE,"密码不能为空！");
        }
        if (ObjectUtils.isEmpty(registryDto.getConfirmPassword())){
            throw new BusinessException(ResponseCode.PARAM_EMPTY_CODE,"确认密码不能为空！");
        }
        if (ObjectUtils.isEmpty(registryDto.getRoleId())){
            throw new BusinessException(ResponseCode.PARAM_EMPTY_CODE,"角色id不能为空！");
        }
        if (ObjectUtils.isEmpty(registryDto.getStoreId())){
            throw new BusinessException(ResponseCode.PARAM_EMPTY_CODE,"商户id不能为空！");
        }

        if (!registryDto.getPassword().equals(registryDto.getConfirmPassword())){
            throw new BusinessException(ResponseCode.PWD_NOT_EQUAL_CODE,"密码与确认密码不相等！");
        }

        registryDto.setCreateTime(new Date());

        try {
            userInfoMapper.registry(registryDto);

            UserRoleInfo userRoleInfo = new UserRoleInfo();
            userRoleInfo.setRoleId(registryDto.getRoleId());
            userRoleInfo.setUserId(registryDto.getUserId());
            userRoleInfoMapper.insert(userRoleInfo);

            UserStoreInfo userStoreInfo = new UserStoreInfo();
            userStoreInfo.setUserId(registryDto.getUserId());
            userStoreInfo.setStoreId(registryDto.getStoreId());
            userStoreInfoMapper.insert(userStoreInfo);

        }catch (Exception e){
            LOGGER.error("注册失败！{}",e.getCause());
            throw new BusinessException(ResponseCode.INSERT_FAIL_CODE,"注册失败!");
        }

    }

    @Override
    public UserInfo login(LoginDto loginDto) {
        if (ObjectUtils.isEmpty(loginDto)){
            throw new BusinessException(ResponseCode.PARAM_EMPTY_CODE,"登录参数不能为空！");
        }

        if (ObjectUtils.isEmpty(loginDto.getLoginName())){
            throw new BusinessException(ResponseCode.PARAM_EMPTY_CODE,"登录名不能为空！");
        }

        if (ObjectUtils.isEmpty(loginDto.getPassword())){
            throw new BusinessException(ResponseCode.PARAM_EMPTY_CODE,"登录密码不能为空！");
        }

        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(loginDto.getLoginName());

        if (ObjectUtils.isEmpty(userInfo)){
            throw new BusinessException(ResponseCode.USER_NOT_EXSIT_CODE,String.format("登录名为%s的用户不存在！",loginDto.getLoginName()));
        }

        if (ObjectUtils.isEmpty(loginDto.getLoginName())){//
            throw new BusinessException(ResponseCode.USER_NOT_EXSIT_CODE,String.format("登录名为%s的用户不存在！",loginDto.getLoginName()));
        }

        if (!userInfo.getUserPwd().equals(loginDto.getPassword())){
            throw new BusinessException(ResponseCode.PWD_ERROR_CODE,"密码输入错误！");
        }

        LOGGER.info("登录名为【{}】的用户登录成功！",loginDto.getLoginName());

        return userInfo;
    }
}
