package com.xiaoshu.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 顺风车收入表
 * </p>
 *
 * @author fugl
 * @since 2019-07-07
 */
@TableName("car_income")
public class CarIncome extends Model<CarIncome> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 所属用户ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 所属用户名称
     */
    @TableField("user_name")
    private String userName;

    /**
     * 乘客姓名
     */
    @TableField("passenger_name")
    private String passengerName;

    /**
     * 乘客性别
     */
    private String sex;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 微信号
     */
    @TableField("wx_id")
    private String wxId;

    /**
     * 乘车日期
     */
    @TableField("ride_day")
    private String rideDay;

    /**
     * 乘车起点
     */
    @TableField("start_location")
    private String startLocation;

    /**
     * 乘车终点
     */
    @TableField("end_location")
    private String endLocation;

    /**
     * 乘车费用
     */
    private String fee;

    /**
     * 备注
     */
    private String remark;

    /**
     * 乘车路线
     */
    private String rideLine;

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

    public String getRideLine() {
        return rideLine;
    }

    public void setRideLine(String rideLine) {
        this.rideLine = rideLine;
    }

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

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }

    public String getRideDay() {
        return rideDay;
    }

    public void setRideDay(String rideDay) {
        this.rideDay = rideDay;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        return "CarIncome{" +
        ", id=" + id +
        ", userId=" + userId +
        ", userName=" + userName +
        ", passengerName=" + passengerName +
        ", sex=" + sex +
        ", phone=" + phone +
        ", wxId=" + wxId +
        ", rideDay=" + rideDay +
        ", startLocation=" + startLocation +
        ", endLocation=" + endLocation +
        ", fee=" + fee +
        ", remark=" + remark +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
