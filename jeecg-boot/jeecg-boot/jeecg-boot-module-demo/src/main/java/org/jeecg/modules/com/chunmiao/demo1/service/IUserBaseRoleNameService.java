package org.jeecg.modules.com.chunmiao.demo1.service;

import org.jeecg.modules.com.chunmiao.demo1.entity.RoleName;
import org.jeecg.modules.com.chunmiao.demo1.entity.UserBase;
import org.jeecg.modules.com.chunmiao.demo1.entity.UserBaseRoleName;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 用户角色表
 * @Author: jeecg-boot
 * @Date:   2020-10-27
 * @Version: V1.0
 */
public interface IUserBaseRoleNameService extends IService<UserBaseRoleName> {
    public void addAll(UserBase userBase);
    public void addAll(RoleName roleName);
    public void deleteAllByUserBaseId(String id);
    public void deleteAllByRoleNameId(String id);
}
