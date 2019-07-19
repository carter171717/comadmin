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
 * 信用卡信息表
 * </p>
 *
 * @author fugl
 * @since 2019-07-04
 */
@TableName("credit_card")
public class CreditCard extends Model<CreditCard> {

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
     * 银行名称
     */
    @TableField("bank_name")
    private String bankName;

    /**
     * 银行卡号
     */
    @TableField("card_num")
    private String cardNum;

    /**
     * 持卡人姓名
     */
    @TableField("card_owner")
    private String cardOwner;

    /**
     * 有效期
     */
    private String validity;

    /**
     * 账单日
     */
    @TableField("bill_date")
    private String billDate;

    /**
     * 还款日
     */
    @TableField("pay_date")
    private String payDate;

    /**
     * 本月账单金额
     */
    @TableField("current_bill")
    private String currentBill;

    /**
     * 本月未出账单金额
     */
    @TableField("uncount_bill")
    private String uncountBill;

    /**
     * 本月未出账单金额
     */
    @TableField("sort")
    private String sort;

    /**
     * 剩余额度
     */
    @TableField("left_quota")
    private String leftQuota;

    /**
     * 状态
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 额度
     */
    @TableField("card_quota")
    private String cardQuota;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    public String getUncountBill() {
        return uncountBill;
    }

    public void setUncountBill(String uncountBill) {
        this.uncountBill = uncountBill;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getLeftQuota() {
        return leftQuota;
    }

    public void setLeftQuota(String leftQuota) {
        this.leftQuota = leftQuota;
    }

    /**
     * 更新时间
     */
    @TableField("update_time")


    private LocalDateTime updateTime;

    public String getCardQuota() {
        return cardQuota;
    }

    public void setCardQuota(String cardQuota) {
        this.cardQuota = cardQuota;
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

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getCardOwner() {
        return cardOwner;
    }

    public void setCardOwner(String cardOwner) {
        this.cardOwner = cardOwner;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getCurrentBill() {
        return currentBill;
    }

    public void setCurrentBill(String currentBill) {
        this.currentBill = currentBill;
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
        return "CreditCard{" +
                ", id=" + id +
                ", userId=" + userId +
                ", userName=" + userName +
                ", bankName=" + bankName +
                ", cardNum=" + cardNum +
                ", cardOwner=" + cardOwner +
                ", validity=" + validity +
                ", billDate=" + billDate +
                ", payDate=" + payDate +
                ", currentBill=" + currentBill +
                ", status=" + status +
                ", remark=" + remark +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                "}";
    }
}
