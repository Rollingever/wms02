package com.wms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wms
 * @since 2024-07-13
 */
public interface UserService extends IService<User> {

    List<User> selectPage(String name, String sex,String roleId, int start, int end);
    void insertUser(User insertUsers);
    List<User> getAllUsers();
}
