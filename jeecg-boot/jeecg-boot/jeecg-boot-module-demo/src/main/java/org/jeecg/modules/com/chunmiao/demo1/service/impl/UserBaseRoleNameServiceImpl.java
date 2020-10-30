package org.jeecg.modules.com.chunmiao.demo1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.catalina.User;
import org.jeecg.modules.com.chunmiao.demo1.entity.RoleName;
import org.jeecg.modules.com.chunmiao.demo1.entity.UserBase;
import org.jeecg.modules.com.chunmiao.demo1.entity.UserBaseRoleName;
import org.jeecg.modules.com.chunmiao.demo1.mapper.UserBaseRoleNameMapper;
import org.jeecg.modules.com.chunmiao.demo1.service.IUserBaseRoleNameService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description: 用户角色表
 * @Author: jeecg-boot
 * @Date:   2020-10-27
 * @Version: V1.0
 */
@Service
public class UserBaseRoleNameServiceImpl extends ServiceImpl<UserBaseRoleNameMapper, UserBaseRoleName> implements IUserBaseRoleNameService {
    @Transactional(
            rollbackFor = Exception.class
    )
    public void addAll(UserBase userBase){
        List<RoleName> roleNameList = userBase.getRoleNameList();
        for (RoleName roleName : roleNameList) {
            UserBaseRoleName userBaseRoleName = new UserBaseRoleName();
            userBaseRoleName.setRoleNameId(roleName.getId());
            userBaseRoleName.setUserBaseId(userBase.getId());
            save(userBaseRoleName);
        }
    }

    @Transactional(
            rollbackFor = Exception.class
    )
    public void addAll(RoleName roleName){
        List<UserBase> userBaseList = roleName.getUserBaseList();
        for (UserBase userBase : userBaseList) {
            UserBaseRoleName userBaseRoleName = new UserBaseRoleName();
            userBaseRoleName.setUserBaseId(userBase.getId());
            userBaseRoleName.setRoleNameId(roleName.getId());
            save(userBaseRoleName);
        }
    }

    @Transactional(
            rollbackFor = Exception.class
    )
    public void deleteAllByUserBaseId(String id){
        deleteAll(id,"user_base_id");
    }

    @Transactional(
            rollbackFor = Exception.class
    )
    public void deleteAllByRoleNameId(String id){
        deleteAll(id,"role_name_id");
    }

    public void deleteAll(String id,String col){
        QueryWrapper<UserBaseRoleName> wrapper = new QueryWrapper<>();
        wrapper.eq(col,id);
        remove(wrapper);
    }
}
