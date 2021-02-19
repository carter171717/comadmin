package com.xiaoshu.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public enum OrderStatusEnum  implements IEnum{
    INIT("0", "初始化"),
    TAKED("1", "已接单"),
    FINISH("2", "已完成"),
    CANCEL("3", "已取消");

    public final String value;
    public final String desc;

    OrderStatusEnum(final String value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Serializable getValue() {
        return this.value;
    }

    @JsonValue // Jackson 注解，可序列化该属性为中文描述【可无】
    public String getDesc() {
        return this.desc;
    }

    static Map<Object, OrderStatusEnum> enumMap = new HashMap<Object, OrderStatusEnum>();
    static {
        for (OrderStatusEnum type : OrderStatusEnum.values()) {
            enumMap.put(type.getValue(), type);
        }
    }

    public static OrderStatusEnum getEnum(String value) {
        return enumMap.get(value);
    }


}
