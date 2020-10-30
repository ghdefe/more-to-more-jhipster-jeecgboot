package org.jeecg.modules.com.chunmiao.demo1.service;

import org.jeecg.modules.com.chunmiao.demo1.entity.RoleName;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.com.chunmiao.demo1.entity.UserBase;

/**
 * @Description: 角色表
 * @Author: jeecg-boot
 * @Date:   2020-10-27
 * @Version: V1.0
 */
public interface IRoleNameService extends IService<RoleName> {
    public void update(RoleName roleName);
}
