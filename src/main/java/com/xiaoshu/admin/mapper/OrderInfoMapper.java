package com.xiaoshu.admin.mapper;

import com.xiaoshu.admin.entity.OrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 乘客订单信息表 Mapper 接口
 * </p>
 *
 * @author wangly
 * @since 2020-09-21
 */
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {


    public int fallBack(@Param("id") String id);

}
