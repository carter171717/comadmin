package com.xiaoshu.common.enums;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 年龄段枚举
 */
public enum AgePeriodTypeEnum implements IEnum {
    ONE_TEN("201001","1-10岁"),
    TEN_TWENTY("201002","11-20岁"),
    TWENTY_THIRTY("201003","21-30岁"),
    THIRTY_FORTY("201004","31-40岁"),
    FORTY_FIFTY("201005","41-50岁"),
    FIFTY_SIXTY("201006","51-60岁"),
    SIXTY_SEVENTY("201007","61-70岁"),
    SEVENTY_BIGGER("201008","70岁以上"),
    UNKNOWN("201009","未知")
    ;

    public final String value;
    public final String desc;

    @Override
    public Serializable getValue() {
        return this.value;
    }

    AgePeriodTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    static Map<Object, AgePeriodTypeEnum> enumMap = new HashMap<Object, AgePeriodTypeEnum>();
    static {
        for (AgePeriodTypeEnum type : AgePeriodTypeEnum.values()) {
            enumMap.put(type.getValue(), type);
        }
    }

    public static AgePeriodTypeEnum getEnum(String value) {
        return enumMap.get(value);
    }

    }

