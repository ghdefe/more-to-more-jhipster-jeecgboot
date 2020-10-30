package org.jeecg.modules.com.chunmiao.demo1.controller;

import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.com.chunmiao.demo1.entity.RoleName;
import org.jeecg.modules.com.chunmiao.demo1.entity.UserBase;
import org.jeecg.modules.com.chunmiao.demo1.entity.UserBaseRoleName;
import org.jeecg.modules.com.chunmiao.demo1.service.IRoleNameService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.com.chunmiao.demo1.service.IUserBaseRoleNameService;
import org.jeecg.modules.com.chunmiao.demo1.service.IUserBaseService;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: 角色表
 * @Author: jeecg-boot
 * @Date: 2020-10-27
 * @Version: V1.0
 */
@Api(tags = "角色表")
@RestController
@RequestMapping("/com.chunmiao.demo1/roleName")
@Slf4j
public class RoleNameController extends JeecgController<RoleName, IRoleNameService> {
    @Autowired
    private IRoleNameService roleNameService;

    @Autowired
    private IUserBaseService userBaseService;

    @Autowired
    private IUserBaseRoleNameService userBaseRoleNameService;


    /**
     * 添加
     *
     * @param roleName
     * @return
     */
    @AutoLog(value = "角色表-添加")
    @ApiOperation(value = "角色表-添加", notes = "角色表-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody RoleName roleName) {
        roleNameService.save(roleName);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param roleName
     * @return
     */
    @AutoLog(value = "角色表-编辑")
    @ApiOperation(value = "角色表-编辑", notes = "角色表-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody RoleName roleName) {
        roleNameService.update(roleName);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "角色表-通过id删除")
    @ApiOperation(value = "角色表-通过id删除", notes = "角色表-通过id删除")
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
    @AutoLog(value = "角色表-批量删除")
    @ApiOperation(value = "角色表-批量删除", notes = "角色表-批量删除")
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
    @AutoLog(value = "角色表-通过id查询")
    @ApiOperation(value = "角色表-通过id查询", notes = "角色表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        RoleName roleName = roleNameService.getById(id);
        if (roleName == null) {
            return Result.error("未找到对应数据");
        }
        roleName.setUserBaseList(getUserBase(roleName));
        return Result.OK(roleName);
    }

    /**
     * 查询所有角色
     * @return
     */
    @AutoLog(value = "角色表-查询所有")
    @ApiOperation(value = "角色表-查询所有", notes = "角色表-查询所有")
    @GetMapping(value = "/queryAll")
    public Result<?> queryAll() {
        List<RoleName> roleNames = roleNameService.list();
        for (RoleName roleName : roleNames) {
            roleName.setUserBaseList(getUserBase(roleName));
        }
        return Result.OK(roleNames);
    }


    /**
     * 得到所有拥有该角色的用户
     * @param roleName
     * @return
     */
    List<UserBase> getUserBase(RoleName roleName) {
        LinkedList<UserBase> userBases = new LinkedList<>();
        if (Objects.nonNull(roleName)) {
            // 找出中间表中对应的数据
            QueryWrapper<UserBaseRoleName> wrapper = new QueryWrapper<>();
            wrapper.eq("role_name_id", roleName.getId());
            List<UserBaseRoleName> userBaseRoleNameList = userBaseRoleNameService.list(wrapper);

            // 拿着中间表的数据到role_name表里找角色信息
            for (UserBaseRoleName userBaseRoleName : userBaseRoleNameList) {
                UserBase userbase = userBaseService.getById(userBaseRoleName.getUserBaseId());
                userBases.add(userbase);
            }
        }
        return userBases;
    }

    /**
     * 通过id删除单个角色
     * @param id
     */
    void deleteById(String id){
        // 删除用户
        roleNameService.removeById(id);
        // 还要删除所有该用户关系
        userBaseRoleNameService.deleteAllByRoleNameId(id);
    }

}
