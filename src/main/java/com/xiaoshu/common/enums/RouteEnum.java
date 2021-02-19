package com.xiaoshu.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public enum RouteEnum implements IEnum {


    XIAMEN("1", "厦门-仙游"),
    XIANYOU("2", "仙游-厦门");


    public final String value;
    public final String desc;

    RouteEnum(final String value, final String desc) {
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

    static Map<Object, RouteEnum> enumMap = new HashMap<Object, RouteEnum>();
    static {
        for (RouteEnum type : RouteEnum.values()) {
            enumMap.put(type.getValue(), type);
        }
    }

    public static RouteEnum getEnum(String value) {
        return enumMap.get(value);
    }
}
