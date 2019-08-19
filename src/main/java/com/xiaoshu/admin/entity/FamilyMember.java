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
 * 家庭成员信息表
 * </p>
 *
 * @author fugl
 * @since 2019-07-06
 */
@TableName("family_member")
public class FamilyMember extends Model<FamilyMember> {

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
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 生肖
     */
    private String animal;

    /**
     * 出生日期新历 格式：1992-07-24
     */
    @TableField("birth_day_new")
    private String birthDayNew;

    /**
     * 出生日期农历 格式：六月廿五
     */
    @TableField("birth_day_lunar")
    private String birthDayLunar;

    /**
     * 与用户的关系
     */
    private String relation;

    /**
     * 家庭标志
     */
    @TableField("is_manager")
    private String isManager;

    /**
     * 家庭ID
     */
    @TableField("manager_id")
    private String managerId;

    /**
     * 状态
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

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

    /**
     * 是否已经发送邮件通知
     */
    @TableField("is_send")
    private String isSend;

    public String getIsSend() {
        return isSend;
    }

    public void setIsSend(String isSend) {
        this.isSend = isSend;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public String getBirthDayNew() {
        return birthDayNew;
    }

    public void setBirthDayNew(String birthDayNew) {
        this.birthDayNew = birthDayNew;
    }

    public String getBirthDayLunar() {
        return birthDayLunar;
    }

    public void setBirthDayLunar(String birthDayLunar) {
        this.birthDayLunar = birthDayLunar;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getIsManager() {
        return isManager;
    }

    public void setIsManager(String isManager) {
        this.isManager = isManager;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        return "FamilyMember{" +
        ", id=" + id +
        ", userId=" + userId +
        ", userName=" + userName +
        ", name=" + name +
        ", sex=" + sex +
        ", animal=" + animal +
        ", birthDayNew=" + birthDayNew +
        ", birthDayLunar=" + birthDayLunar +
        ", relation=" + relation +
        ", isManager=" + isManager +
        ", managerId=" + managerId +
        ", status=" + status +
        ", remark=" + remark +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
