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
 * 接单信息表
 * </p>
 *
 * @author wangly
 * @since 2020-09-21
 */
@TableName("take_order_info")
public class TakeOrderInfo extends Model<TakeOrderInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 乘客订单ID
     */
    @TableField("order_id")
    private String orderId;

    /**
     * 司机路线ID
     */
    @TableField("route_id")
    private String routeId;

    /**
     * 出发日期
     */
    @TableField("order_date")
    private String orderDate;

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
     * 订单状态： 1-已接单 2-已完成 3-已取消
     */
    private String status;

    /**
     * 乘客昵称
     */
    @TableField("passenger_name")
    private String passengerName;

    /**
     * 乘客联系电话
     */
    @TableField("passenger_phone")
    private String passengerPhone;

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
     * 乘客评价
     */
    private String valuation;

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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPassengerPhone() {
        return passengerPhone;
    }

    public void setPassengerPhone(String passengerPhone) {
        this.passengerPhone = passengerPhone;
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

    public String getValuation() {
        return valuation;
    }

    public void setValuation(String valuation) {
        this.valuation = valuation;
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
        return "TakeOrderInfo{" +
        ", id=" + id +
        ", orderId=" + orderId +
        ", routeId=" + routeId +
        ", orderDate=" + orderDate +
        ", route=" + route +
        ", routeName=" + routeName +
        ", status=" + status +
        ", passengerName=" + passengerName +
        ", passengerPhone=" + passengerPhone +
        ", driverId=" + driverId +
        ", driverName=" + driverName +
        ", driverPhone=" + driverPhone +
        ", carNum=" + carNum +
        ", takeTime=" + takeTime +
        ", finishTime=" + finishTime +
        ", cancelTime=" + cancelTime +
        ", valuation=" + valuation +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
