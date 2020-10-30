package org.jeecg.modules.com.chunmiao.demo1.service.impl;

import org.jeecg.modules.com.chunmiao.demo1.entity.RoleName;
import org.jeecg.modules.com.chunmiao.demo1.entity.UserBase;
import org.jeecg.modules.com.chunmiao.demo1.mapper.RoleNameMapper;
import org.jeecg.modules.com.chunmiao.demo1.service.IRoleNameService;
import org.jeecg.modules.com.chunmiao.demo1.service.IUserBaseRoleNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 角色表
 * @Author: jeecg-boot
 * @Date:   2020-10-27
 * @Version: V1.0
 */
@Service
public class RoleNameServiceImpl extends ServiceImpl<RoleNameMapper, RoleName> implements IRoleNameService {
    @Autowired
    private IUserBaseRoleNameService userBaseRoleNameService;

    public void update(RoleName roleName){
        // 首先更新角色表
        updateById(roleName);

        // 然后更新关系表
        userBaseRoleNameService.deleteAllByRoleNameId(roleName.getId());
        userBaseRoleNameService.addAll(roleName);
    }
}
