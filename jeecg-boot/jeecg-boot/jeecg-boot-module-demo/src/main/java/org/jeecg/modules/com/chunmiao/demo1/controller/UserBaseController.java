package org.jeecg.modules.com.chunmiao.demo1.controller;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.com.chunmiao.demo1.entity.RoleName;
import org.jeecg.modules.com.chunmiao.demo1.entity.UserBase;
import org.jeecg.modules.com.chunmiao.demo1.entity.UserBaseRoleName;
import org.jeecg.modules.com.chunmiao.demo1.service.IRoleNameService;
import org.jeecg.modules.com.chunmiao.demo1.service.IUserBaseRoleNameService;
import org.jeecg.modules.com.chunmiao.demo1.service.IUserBaseService;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: 用户表
 * @Author: jeecg-boot
 * @Date: 2020-10-27
 * @Version: V1.0
 */
@Api(tags = "用户表")
@RestController
@RequestMapping("/com.chunmiao.demo1/userBase")
@Slf4j
public class UserBaseController extends JeecgController<UserBase, IUserBaseService> {
    @Autowired
    private IUserBaseService userBaseService;

    @Autowired
    private IUserBaseRoleNameService userBaseRoleNameService;

    @Autowired
    private IRoleNameService roleNameService;

    /**
     * 添加
     *
     * @param userBase
     * @return
     */
    @AutoLog(value = "用户表-添加")
    @ApiOperation(value = "用户表-添加", notes = "用户表-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody UserBase userBase) {
        // 保存用户
        userBaseService.save(userBase);

        // 保存用户角色关系
        userBaseRoleNameService.addAll(userBase);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param userBase
     * @return
     */
    @AutoLog(value = "用户表-编辑")
    @ApiOperation(value = "用户表-编辑", notes = "用户表-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody UserBase userBase) {
        userBaseService.update(userBase);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "用户表-通过id删除")
    @ApiOperation(value = "用户表-通过id删除", notes = "用户表-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        deleteById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "用户表-批量删除")
    @ApiOperation(value = "用户表-批量删除", notes = "用户表-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        String[] idList = ids.split(",");
        for (String id : idList) {
            deleteById(id);
        }
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "用户表-通过id查询")
    @ApiOperation(value = "用户表-通过id查询", notes = "用户表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        UserBase userBase = userBaseService.getById(id);
        if (userBase == null) {
            return Result.error("未找到对应数据");
        }
        userBase.setRoleNameList(getRoleName(userBase));
        return Result.OK(userBase);
    }

    /**
     * 查询所有用户信息
     */
    @AutoLog(value = "用户表-查询所有")
    @ApiOperation(value = "用户表-查询所有", notes = "用户表-查询所有")
    @GetMapping(value = "/queryAll")
    public Result<?> queryAll() {
        List<UserBase> userBaseList = userBaseService.list();
        for (UserBase userBase : userBaseList) {
            userBase.setRoleNameList(getRoleName(userBase));
        }
        return Result.OK(userBaseList);
    }

    List<RoleName> getRoleName(UserBase userBase) {
        LinkedList<RoleName> roleNames = new LinkedList<>();
        if (Objects.nonNull(userBase)) {
            // 找出中间表中对应的数据
            QueryWrapper<UserBaseRoleName> wrapper = new QueryWrapper<>();
            wrapper.eq("user_base_id", userBase.getId());
            List<UserBaseRoleName> userBaseRoleNameList = userBaseRoleNameService.list(wrapper);
            // 拿着中间表的数据到role_name表里找角色信息
            for (UserBaseRoleName userBaseRoleName : userBaseRoleNameList) {
                RoleName rolename = roleNameService.getById(userBaseRoleName.getRoleNameId());
                roleNames.add(rolename);
            }
        }
        // 将角色信息回填到userBase
        return roleNames;
    }

    /**
     * 通过id删除单个用户
     * @param id
     */
    void deleteById(String id){
        // 删除用户
        userBaseService.removeById(id);
        // 还要删除所有该用户关系
        userBaseRoleNameService.deleteAllByUserBaseId(id);
    }
}
