package com.drillvisual.service;

import com.drillvisual.pojo.*;

import java.util.ArrayList;
import java.util.List;

public class StratumLineGenerator {
    // 创建Service层对象
    private DrillPointReader drillPointReader = new DrillPointReader();
    private DrillStratumReader drillStratumReader = new DrillStratumReader();
    private Section section = new Section();
    private Connector connector = new Connector();

    // 建立钻孔和地层的逻辑模型
    public List<DrillPoint> generatedrillPointList(String[] ids) {
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
    public List<LayerLine> generateLayerLineList(List<DrillPoint> drillPointList) {
        // 创建地层线表
        List<LayerLine> layerLineList = new ArrayList<LayerLine>();
        // 调用Connector连线
        connector.connect(layerLineList, drillPointList);

        return layerLineList;
    }

    public List<Double> computeDistance(List<DrillPoint> drillPointList) {
        List<Double> drillDistance = new ArrayList<Double>();
        for (int i = 0; i < drillPointList.size() - 1; i++) {
            Double distX = drillPointList.get(i).getDrillX() - drillPointList.get(i + 1).getDrillX();
            Double distY = drillPointList.get(i).getDrillY() - drillPointList.get(i + 1).getDrillY();
            drillDistance.add(Math.sqrt(distX * distX + distY * distY));
        }
        return drillDistance;
    }

    // 生成地层线的总方法
    public Section generate(String[] ids) {
        // 生成钻孔线
        List<DrillPoint> drillPointList = generatedrillPointList(ids);
        // 生成地层线
        List<LayerLine> layerLineList = generateLayerLineList(drillPointList);
        // 计算钻孔间距
        List<Double> drillDistanceList = computeDistance(drillPointList);
        // 设置剖面模型
        section.setDrillPointList(drillPointList);
        section.setLayerLineList(layerLineList);
        section.setDrillDistance(drillDistanceList);
        return section;
    }
}
