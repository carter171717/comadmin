package com.xiaoshu.admin.service.impl;

import com.xiaoshu.admin.entity.OrderInfo;
import com.xiaoshu.admin.mapper.OrderInfoMapper;
import com.xiaoshu.admin.service.OrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 乘客订单信息表 服务实现类
 * </p>
 *
 * @author wangly
 * @since 2020-09-21
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

}
