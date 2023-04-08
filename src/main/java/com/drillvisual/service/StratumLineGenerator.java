package com.drillvisual.service;

import com.drillvisual.pojo.DrillPoint;

import java.util.ArrayList;
import java.util.List;

public class StratumLineGenerator {
    // 创建Service层对象
    private DrillPointReader drillPointReader = new DrillPointReader();
    private DrillStratumReader drillStratumReader = new DrillStratumReader();
    public List<DrillPoint> generate(String[] ids) {
        List<DrillPoint> drillPointList = new ArrayList<DrillPoint>();
        for (String drillId: ids) {
            // 调用Service层完成查询钻孔ID
            DrillPoint drillPoint = drillPointReader.selectById(drillId);
            drillPointList.add(drillPoint);
        }
        return drillPointList;
    }
}
