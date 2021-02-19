package com.xiaoshu.admin.entity.vo;

import com.xiaoshu.admin.entity.OrderInfo;
import lombok.Data;

import java.util.List;

@Data
public class OrderTableVo {

    public int code;
    public String msg;
    public int count;
    public List<OrderInfo> data;

}
