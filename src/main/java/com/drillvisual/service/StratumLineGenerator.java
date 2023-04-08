package com.drillvisual.service;

import com.drillvisual.pojo.DrillLine;
import com.drillvisual.pojo.DrillPoint;
import com.drillvisual.pojo.DrillStratum;
import com.drillvisual.pojo.LayerLine;

import java.util.ArrayList;
import java.util.List;

public class StratumLineGenerator {
    // 创建Service层对象
    private DrillPointReader drillPointReader = new DrillPointReader();
    private DrillStratumReader drillStratumReader = new DrillStratumReader();
    private SectionPloter sectionPloter = new SectionPloter();
    private Connector connector = new Connector();

    // 建立钻孔和地层的逻辑模型
    public List<DrillPoint> buildLogicalModel(String[] ids) {
        List<DrillPoint> drillPointList = getDrillPiontList(ids);
        tieStratum2DrillPoint(drillPointList);
        return drillPointList;
    }

    // 从DAO层获取钻孔表
    public List<DrillPoint> getDrillPiontList(String[] ids) {
        List<DrillPoint> drillPointList = new ArrayList<DrillPoint>();
        for (String drillId: ids) {
            // 调用Service层完成查询钻孔ID
            DrillPoint drillPoint = drillPointReader.selectById(drillId);
            drillPointList.add(drillPoint);
        }
        return drillPointList;
    }

    // 将地层绑定到钻孔点上
    public void tieStratum2DrillPoint(List<DrillPoint> drillPointList) {
        for (DrillPoint drillPoint: drillPointList) {
            // 调用Service层获取钻孔点地层表
            List<DrillStratum> drillStratumList = drillStratumReader.selectByDrillId(drillPoint.getDrillId());
            drillPoint.setDrillStratumList(drillStratumList);
        }
    }

    // 生成地层线表
    public List<LayerLine> generateLayerLine(List<DrillPoint> drillPointList) {
        // 创建地层线表
        List<LayerLine> layerLineList = new ArrayList<LayerLine>();
        // 遍历钻孔表
        for (int i = 0; i < drillPointList.size(); i++) {
            // 遍历钻孔地层
            List<DrillStratum> drillStratumList = drillPointList.get(i).getDrillStratumList();
            for (int j = 0; j < drillStratumList.size(); j++) {
                // 根据地层类型选择连线规则
                int type = connector.checkRule(drillStratumList.get(j), i, j, drillPointList);
                // 按规则连线
                LayerLine layerLine = connector.connect(type, drillStratumList.get(j), i, j, drillPointList);
                // 将地层线添加到地层线表
                layerLineList.add(layerLine);
            }
        }
        return layerLineList;
    }

    // 生成地层线的总方法
    public List<DrillPoint> generate(String[] ids) {
        // 建立逻辑模型
        List<DrillPoint> drillPointList = buildLogicalModel(ids);
        // 生成钻孔线
        DrillLine drillLine = new DrillLine();
        drillLine.setDrillPointList(drillPointList);
        // 生成地层线
        List<LayerLine> layerLineList = generateLayerLine(drillPointList);


        // 生成剖面模型
        sectionPloter.setDrillLine(drillLine);
        sectionPloter.setLayerLineList(layerLineList);

        return drillPointList;
    }
}
