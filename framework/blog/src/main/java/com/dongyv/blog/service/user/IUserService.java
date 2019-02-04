package com.dongyv.blog.service.user;

import com.dongyv.blog.wrapper.ApiResult;

/**
 * @author xiachenhang
 */
public interface IUserService {
    ApiResult login(String name, String psd);
}
