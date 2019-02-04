package com.dongyv.blog.mapper.user;


import com.dongyv.blog.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author xiachenhang
 */
public interface IUserMapper {

   User getLoginWithNamePsd(@Param("name") String name,@Param("password") String password);

   List<User> getUserList();
}
