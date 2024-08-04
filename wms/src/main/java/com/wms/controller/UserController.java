package com.wms.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.Menu;
import com.wms.entity.User;
import com.wms.mapper.UserMapper;
import com.wms.service.MenuService;
import com.wms.service.UserService;
import com.wms.service.impl.MenuServiceImpl;
import org.apache.logging.log4j.message.ReusableMessage;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wms
 * @since 2024-07-13
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuServiceImpl menuServiceImpl;

    @GetMapping("/list")
    public List<User> list() {
        return userService.list();
    }

    @GetMapping("/findByNo")
    public Result findByNo(@RequestParam String no) {
        List list = userService.lambdaQuery().eq(User::getNo, no).list();
        return list.size()>0?Result.suc(list):Result.fail();
    }

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody User user) {

        return userService.save(user)?Result.suc():Result.fail();
    }

    //更新
    @PostMapping("/update")
    public Result update(@RequestBody User user) {
        return userService.updateById(user)?Result.suc():Result.fail();
    }

    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id) {
        return userService.removeById(id)?Result.suc():Result.fail();
    }

    //登录
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        List<User> list = userService.lambdaQuery()
                .eq(User::getNo, user.getNo())
                .eq(User::getPassword,user.getPassword()).list();

        if (list.size()>0){
            User user1 = list.get(0);
            List<Menu> menulist = menuService.lambdaQuery().like(Menu::getMenuright,user1.getRoleId()).list();
            HashMap<String, Object> res = new HashMap<>();
            res.put("user",user1);
            res.put("menu",menulist);
            return Result.suc(res);
        }
        return Result.fail();
    }

    //修改
    @PostMapping("/mod")
    public boolean mod(@RequestBody User user) {
        return userService.updateById(user);
    }

    //新增或修改
    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody User user) {
        return userService.saveOrUpdate(user);
    }

    //删除
    @GetMapping("/delete")
    public boolean delete(Integer id) {
        return userService.removeById(id);
    }

    //查询（模糊，匹配）
    @PostMapping("/listP")
    public Result listP(@RequestBody User user) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(user.getName())){
            lambdaQueryWrapper.like(User::getName, user.getName());
        }

        return Result.suc(userService.list(lambdaQueryWrapper));
    }

//    @PostMapping("/listPage")
//    public List<User> listPage(@RequestBody QueryPageParam query) {
//        HashMap<String, Object> param = query.getParam();
//        String name = (String) param.get("name");
//
//        int start = (query.getPageNum() - 1) * query.getPageSize() + 1;
//        int end = query.getPageNum() * query.getPageSize();
//
//        return userService.selectPage(name,sex, start, end);
//    }

    @PostMapping("/listPage1")
    public Result listPage1(@RequestBody QueryPageParam query) {
        HashMap<String, Object> param = query.getParam();
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        String name = (String) param.get("name");
        String sex = (String) param.get("sex");
        String roleId = (String) param.get("roleId");
        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            lambdaQueryWrapper.like(User::getName, name);
        }
        if (StringUtils.isNotBlank(sex)) {
            lambdaQueryWrapper.eq(User::getSex, sex);
        }
        if (StringUtils.isNotBlank(roleId)) {
            lambdaQueryWrapper.eq(User::getRoleId, roleId);
        }
        int pageNum = query.getPageNum();
        int pageSize = query.getPageSize();
        int start = (pageNum - 1) * pageSize + 1;
        int end = pageNum * pageSize;

        List<User> users = userMapper.selectPage(name, sex,roleId,start, end);

        int totalRecords = userMapper.selectCount(name,sex,roleId);

        Map<String, Object> resultData = new HashMap<>();
        resultData.put("users", users);
        resultData.put("total", totalRecords);

        return Result.suc(resultData, totalRecords);
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=data.xlsx");

        try (ServletOutputStream out = response.getOutputStream()) {
            List<User> data = userService.getAllUsers(); // 获取所有数据
            EasyExcel.write(out, User.class).sheet("Sheet1").doWrite(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
