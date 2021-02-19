package com.xiaoshu.admin.service.impl;

import com.xiaoshu.admin.mapper.CarBillMapper;
import com.xiaoshu.admin.mapper.CarIncomeMapper;
import com.xiaoshu.admin.mapper.CreditCardMapper;
import com.xiaoshu.admin.mapper.FamilyMemberMapper;
import com.xiaoshu.admin.service.StatisticsService;
import com.xiaoshu.common.util.LunarUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    FamilyMemberMapper familyMemberMapper;

    @Autowired
    CreditCardMapper creditCardMapper;

    @Autowired
    CarIncomeMapper carIncomeMapper;

    @Autowired
    CarBillMapper carBillMapper;

    @Override
    public Map<String, Object> getMainData(String userId) {
        Map<String, Object> map = new HashMap<>();
        // 获取今日日期，以及今日农历日期 以及月份
        //String lunar =  LunarUtil.getLunarDate();
        //String current =  LunarUtil.getNowDate();
        //map.put("current",current);
        //map.put("lunar",lunar);
        Map<String,String> dateMap = LunarUtil.getLunarDate();
        String month = dateMap.get("month").toString();
        map.put("dateMap",dateMap);
        // 获取本月生日的成员列表
        List<Map<String ,Object>> listCurrentMonth = familyMemberMapper.getBirthInCurrentMonth(userId,month);
        String birthNames = "  ";
        for(Map<String ,Object> birth: listCurrentMonth){
            String name = birth.get("name").toString();
            name +="  ";
            birthNames += name;
        }
        map.put("birthNames",birthNames);
        // 获取顺风车总收入
        String incomeTotal =  carIncomeMapper.getIncomeTotal(userId);
        map.put("incomeTotal",incomeTotal);
        // 获取用车总支出
        String billTotal = carBillMapper.getTotalBill(userId);
        map.put("billTotal",billTotal);
        // 获取加油总额
        String oilbillTotal = carBillMapper.getTotalOilBill(userId);
        map.put("oilbillTotal",oilbillTotal);
        // 信用卡
        String money = creditCardMapper.getTotalCreditMoney(userId);
        map.put("creditBill",money);

        // 获取按月顺风车收入总额 图表
        List<Map<String ,Object>> listIncome = carIncomeMapper.getCountByMonth(userId);
        map.put("incomeByMonth",listIncome);

        List<Map<String ,Object>> listIncomeResult = new ArrayList<Map<String ,Object>>();

        List<Map<String ,Object>> listOnRodeBill = carBillMapper.getCountOnRodeByMonth(userId);
        map.put("listOnRodeBill",listOnRodeBill);

        // 获取按月加油统计图表
        List<Map<String ,Object>> listOilBill  =carBillMapper.getCountOilByMonth(userId);
        map.put("listOilBill",listOilBill);

        // 获取按月总消费统计
        List<Map<String ,Object>> listAllBill  =carBillMapper.getCountAllByMonth(userId);
        map.put("listAllBill",listAllBill);

        // 获取按类型消费总额图表
        List<Map<String ,Object>> listTypeBill  =carBillMapper.getCountByType(userId);
        map.put("listTypeBill",listTypeBill);

        return map;
    }


    /**
     *
     * 年度用车收入支出报告
     * @param userId
     * @return
     */
    @Override
    public Map<String, Object> getYearCarData(String userId) {
        Map<String, Object> map = new HashMap<>();

        List<Map<String ,Object>> listBillTotal  =carBillMapper.getBillTotalByYear(userId);
        List<Map<String ,Object>> listIncomeTotal  =carBillMapper.getIncomeByYear(userId);
        map.put("listBillTotal",listBillTotal);
        map.put("listIncomeTotal",listIncomeTotal);
        return map;
    }

    @Override
    public List<Map<String ,Object>> getDetailPieData(String userId, String year) {

        List<Map<String ,Object>> list = new ArrayList<>();
        list  =carBillMapper.getBillDetailByYear(userId,year);

        return list;
    }
}
