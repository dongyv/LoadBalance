package com.dongyv.blog.service.user.impl;

import com.dongyv.blog.mapper.user.IUserMapper;
import com.dongyv.blog.model.User;
import com.dongyv.blog.service.user.IUserService;
import com.dongyv.blog.wrapper.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiachenhang
 */
@Service
public class UserService implements IUserService {

    @Autowired(required = false)
    private IUserMapper userMapper;

    @Override
    public ApiResult login(String name, String password){
        List<User> users = userMapper.getUserList();
        User user = userMapper.getLoginWithNamePsd(name, password);
        if(user == null){
            //添加密码输入错误次数减一的逻辑 通过缓存进行比较

            return ApiResult.fail("请输入正确的密码");
        }else{
            //存入redis状态

            return ApiResult.success(user,"登录成功");
        }

    }


}
