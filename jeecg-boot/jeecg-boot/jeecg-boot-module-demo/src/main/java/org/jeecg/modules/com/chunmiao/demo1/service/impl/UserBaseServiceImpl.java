package org.jeecg.modules.com.chunmiao.demo1.service.impl;

import org.checkerframework.checker.units.qual.A;
import org.jeecg.modules.com.chunmiao.demo1.entity.RoleName;
import org.jeecg.modules.com.chunmiao.demo1.entity.UserBase;
import org.jeecg.modules.com.chunmiao.demo1.mapper.UserBaseMapper;
import org.jeecg.modules.com.chunmiao.demo1.service.IUserBaseRoleNameService;
import org.jeecg.modules.com.chunmiao.demo1.service.IUserBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 用户表
 * @Author: jeecg-boot
 * @Date:   2020-10-27
 * @Version: V1.0
 */
@Service
public class UserBaseServiceImpl extends ServiceImpl<UserBaseMapper, UserBase> implements IUserBaseService {
    @Autowired
    private IUserBaseRoleNameService userBaseRoleNameService;

    public void update(UserBase userBase){
        // 首先更新用户表
        updateById(userBase);

        // 然后更新关系表
        userBaseRoleNameService.deleteAllByUserBaseId(userBase.getId());
        userBaseRoleNameService.addAll(userBase);
    }
}
