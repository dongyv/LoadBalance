package com.dongyv.blog.service.redis;

import java.util.concurrent.TimeUnit;

public interface ICacheCompone {

    void putString(String key, String object, long expire, TimeUnit timeUnit);

    void putObject(String key, Object object, long expire, TimeUnit timeUnit);

    void delete(String key);

    String getString(String key);

    Object getObject(String key);

    void increment(String key, int num);
}
