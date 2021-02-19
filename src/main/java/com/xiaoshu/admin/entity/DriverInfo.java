package com.xiaoshu.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * <p>
 * 司机信息表
 * </p>
 *
 * @author wangly
 * @since 2020-09-21
 */
@TableName("driver_info")
public class DriverInfo extends Model<DriverInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 司机账号ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 司机账号名称
     */
    @TableField("user_name")
    private String userName;

    /**
     * 司机姓名
     */
    @TableField("driver_name")
    private String driverName;

    /**
     * 司机性别
     */
    @TableField("driver_sex")
    private String driverSex;

    /**
     * 司机驾龄
     */
    @TableField("driver_age")
    private String driverAge;

    /**
     * 司机电话
     */
    @TableField("driver_phone")
    private String driverPhone;

    /**
     * 司机等级 1-9
     */
    @TableField("driver_level")
    private String driverLevel;

    /**
     * 车辆类型：1-SUV 2-轿车 3-货车 4-其他
     */
    @TableField("car_type")
    private String carType;

    /**
     * 车牌号
     */
    @TableField("car_num")
    private String carNum;

    /**
     * 车辆颜色
     */
    @TableField("car_color")
    private String carColor;

    /**
     * 备注
     */
    private String remark;

    /**
     * 司机状态：0-禁用 1-启用
     */
    private String status;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverSex() {
        return driverSex;
    }

    public void setDriverSex(String driverSex) {
        this.driverSex = driverSex;
    }

    public String getDriverAge() {
        return driverAge;
    }

    public void setDriverAge(String driverAge) {
        this.driverAge = driverAge;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getDriverLevel() {
        return driverLevel;
    }

    public void setDriverLevel(String driverLevel) {
        this.driverLevel = driverLevel;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "DriverInfo{" +
        ", id=" + id +
        ", userId=" + userId +
        ", userName=" + userName +
        ", driverName=" + driverName +
        ", driverSex=" + driverSex +
        ", driverAge=" + driverAge +
        ", driverPhone=" + driverPhone +
        ", driverLevel=" + driverLevel +
        ", carType=" + carType +
        ", carNum=" + carNum +
        ", carColor=" + carColor +
        ", remark=" + remark +
        ", status=" + status +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
