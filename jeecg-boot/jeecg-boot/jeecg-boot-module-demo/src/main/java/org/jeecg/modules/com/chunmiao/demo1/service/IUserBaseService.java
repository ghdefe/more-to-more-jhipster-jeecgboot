package org.jeecg.modules.com.chunmiao.demo1.service;

import org.jeecg.modules.com.chunmiao.demo1.entity.UserBase;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 用户表
 * @Author: jeecg-boot
 * @Date:   2020-10-27
 * @Version: V1.0
 */
public interface IUserBaseService extends IService<UserBase> {
    public void update(UserBase userBase);
}
