package com.xiaoshu.admin.mapper;

import com.xiaoshu.admin.entity.MyBlog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 我的博客表 Mapper 接口
 * </p>
 *
 * @author wangly
 * @since 2019-07-15
 */
@Mapper
public interface MyBlogMapper extends BaseMapper<MyBlog> {

}
