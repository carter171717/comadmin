package com.xiaoshu.admin.service;

import java.util.List;
import java.util.Map;

public interface StatisticsService {

    public Map<String,Object> getMainData(String userId);

    public Map<String,Object> getYearCarData(String userId);

    public List<Map<String ,Object>> getDetailPieData(String userId, String year);
}
