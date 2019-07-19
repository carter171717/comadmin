package com.xiaoshu.admin.service.impl;

import com.xiaoshu.admin.entity.MyBlog;
import com.xiaoshu.admin.mapper.MyBlogMapper;
import com.xiaoshu.admin.service.MyBlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 我的博客表 服务实现类
 * </p>
 *
 * @author wangly
 * @since 2019-07-15
 */
@Service
public class MyBlogServiceImpl extends ServiceImpl<MyBlogMapper, MyBlog> implements MyBlogService {

}
