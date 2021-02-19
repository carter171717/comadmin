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
 * 司机发布路线信息表
 * </p>
 *
 * @author wangly
 * @since 2020-09-21
 */
@TableName("driver_route")
public class DriverRoute extends Model<DriverRoute> {

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
     * 司机ID
     */
    @TableField("driver_id")
    private String driverId;

    /**
     * 司机姓名
     */
    @TableField("driver_name")
    private String driverName;

    /**
     * 司机电话
     */
    @TableField("driver_phone")
    private String driverPhone;

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
     * 路线起点
     */
    @TableField("start_address")
    private String startAddress;

    /**
     * 路线终点
     */
    @TableField("end_address")
    private String endAddress;

    /**
     * 出发日期
     */
    @TableField("order_date")
    private String orderDate;

    /**
     * 可以携带人数
     */
    @TableField("take_num")
    private String takeNum;

    /**
     * 出发时间点
     */
    @TableField("order_time")
    private String orderTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 路线状态：0-已取消 1-正常 2-已完成 9-失效
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

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getTakeNum() {
        return takeNum;
    }

    public void setTakeNum(String takeNum) {
        this.takeNum = takeNum;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
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
        return "DriverRoute{" +
        ", id=" + id +
        ", userId=" + userId +
        ", userName=" + userName +
        ", driverId=" + driverId +
        ", driverName=" + driverName +
        ", driverPhone=" + driverPhone +
        ", route=" + route +
        ", routeName=" + routeName +
        ", startAddress=" + startAddress +
        ", endAddress=" + endAddress +
        ", orderDate=" + orderDate +
        ", takeNum=" + takeNum +
        ", orderTime=" + orderTime +
        ", remark=" + remark +
        ", status=" + status +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
