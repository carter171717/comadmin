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
 * 乘客订单信息表
 * </p>
 *
 * @author wangly
 * @since 2020-09-21
 */
@TableName("order_info")
public class OrderInfo extends Model<OrderInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 乘客昵称
     */
    @TableField("passenger_name")
    private String passengerName;

    /**
     * 预约出发日期
     */
    @TableField("order_date")
    private String orderDate;

    /**
     * 预约人数
     */
    @TableField("order_num")
    private String orderNum;

    /**
     * 出发时间点
     */
    @TableField("order_time")
    private String orderTime;

    /**
     * 路线类型
     */
    private String route;

    /**
     * 路线名称
     */
    @TableField("route_name")
    private String routeName;

    /**
     * 期望沟通方式：1-微信 2-电话
     */
    @TableField("connect_type")
    private String connectType;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 乘车起点
     */
    @TableField("start_address")
    private String startAddress;

    /**
     * 乘车终点
     */
    @TableField("end_address")
    private String endAddress;

    /**
     * 乘客评价
     */
    private String valuation;

    /**
     * 是否接受其他时间：0-否 1-是
     */
    @TableField("time_change")
    private String timeChange;

    /**
     * 乘客备注
     */
    private String remark;

    /**
     * 订单状态：0-初始化 1-已接单 2-已完成 3-已取消
     */
    private String status;

    /**
     * 接单司机ID
     */
    @TableField("driver_id")
    private String driverId;

    /**
     * 接单司机名称
     */
    @TableField("driver_name")
    private String driverName;

    /**
     * 接单司机电话
     */
    @TableField("driver_phone")
    private String driverPhone;

    /**
     * 车牌号
     */
    @TableField("car_num")
    private String carNum;

    /**
     * 接单时间
     */
    @TableField("take_time")
    private String takeTime;

    /**
     * 订单完成时间
     */
    @TableField("finish_time")
    private String finishTime;

    /**
     * 订单取消时间
     */
    @TableField("cancel_time")
    private String cancelTime;

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

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getConnectType() {
        return connectType;
    }

    public void setConnectType(String connectType) {
        this.connectType = connectType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public String getValuation() {
        return valuation;
    }

    public void setValuation(String valuation) {
        this.valuation = valuation;
    }

    public String getTimeChange() {
        return timeChange;
    }

    public void setTimeChange(String timeChange) {
        this.timeChange = timeChange;
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

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getTakeTime() {
        return takeTime;
    }

    public void setTakeTime(String takeTime) {
        this.takeTime = takeTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
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
        return "OrderInfo{" +
        ", id=" + id +
        ", passengerName=" + passengerName +
        ", orderDate=" + orderDate +
        ", orderNum=" + orderNum +
        ", orderTime=" + orderTime +
        ", route=" + route +
        ", routeName=" + routeName +
        ", connectType=" + connectType +
        ", phone=" + phone +
        ", startAddress=" + startAddress +
        ", endAddress=" + endAddress +
        ", valuation=" + valuation +
        ", timeChange=" + timeChange +
        ", remark=" + remark +
        ", status=" + status +
        ", driverId=" + driverId +
        ", driverName=" + driverName +
        ", driverPhone=" + driverPhone +
        ", carNum=" + carNum +
        ", takeTime=" + takeTime +
        ", finishTime=" + finishTime +
        ", cancelTime=" + cancelTime +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
