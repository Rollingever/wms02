package com.wms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.entity.User;
import com.wms.mapper.UserMapper;
import com.wms.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wms
 * @since 2024-07-13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<User> selectPage(String name, String sex,String roleId, int start, int end) {
        return baseMapper.selectPage(name, sex, roleId, start, end);
    }
    @Override
    public void insertUser(User insertUsers) {
        userMapper.insertUser(insertUsers);
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }
}
