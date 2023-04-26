package com.drillvisual.service;

import com.drillvisual.pojo.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PolygonGenerator {
    List<Double> drillDistance;
    List<Double> drillPosition;
    List<DrillPoint> drillPointList;
    List<LayerLine> layerLineList;
    public boolean samePoint(Double[] p1, Double[] p2) {
        return Math.abs(p1[0] - p2[0]) < 1e-6 && Math.abs(p1[1] - p2[1]) < 1e-6;
    }

    public Double[] getLeftData(LayerLine layerLine) {
        Double[] data = new Double[3];
        int index = layerLine.getColumnIndex();
        String id = layerLine.getDrillStratumIdLeft();
        List<DrillStratum> drillStratumList = drillPointList.get(index).getDrillStratumList();
        for (int i = 0; i < drillStratumList.size(); i++) {
            if (drillStratumList.get(i).getDrillStratumId().compareTo(id) != 0)
                continue;
            DrillStratum drillStratum = drillStratumList.get(i);
            data[0] = drillPosition.get(index);
            data[1] = drillPointList.get(index).getDrillHeight() - drillStratum.getBottomDepth();
            data[2] = drillPointList.get(index).getDrillHeight() - drillStratum.getTopDepth();
        }
        return data;
    }

    public Double[] getRightData(LayerLine layerLine) {
        Double[] data = new Double[3];
        int index = layerLine.getColumnIndex() + 1;
        String id = layerLine.getDrillStratumIdRight();
        List<DrillStratum> drillStratumList = drillPointList.get(index).getDrillStratumList();
        for (int i = 0; i < drillStratumList.size(); i++) {
            if (drillStratumList.get(i).getDrillStratumId().compareTo(id) != 0)
                continue;
            DrillStratum drillStratum = drillStratumList.get(i);
            data[0] = drillPosition.get(index);
            data[1] = drillPointList.get(index).getDrillHeight() - drillStratum.getBottomDepth();
            data[2] = drillPointList.get(index).getDrillHeight() - drillStratum.getTopDepth();
        }
        return data;
    }

    public LayerPolygon generatePolygon(LayerLine layerLine) {
        LayerPolygon layerPolygon = new LayerPolygon();
        Double[] dataLeft = null;
        Double[] dataRight = null;
        // 判断地层线类型
        String type = layerLine.getType();
        // 生成数据项
        if (type.compareTo("EqualLen") == 0) {
            dataLeft = getLeftData(layerLine);
            dataRight = getRightData(layerLine);
        } else if (type.compareTo("PinchOutLeft") == 0) {
            dataRight = getRightData(layerLine);
            dataLeft = new Double[3];
            dataLeft[0] = drillPosition.get(layerLine.getColumnIndex());
            dataLeft[1] = dataLeft[2] = layerLine.getDepthLeft();
        } else if (type.compareTo("PinchOutRight") == 0) {
            dataLeft = getLeftData(layerLine);
            dataRight = new Double[3];
            dataRight[0] = drillPosition.get(layerLine.getColumnIndex() + 1);
            dataRight[1] = dataRight[2] = layerLine.getDepthRight();
        }
        // 新建数据表
        List<Double[]> dataList = new ArrayList<>();
        dataList.add(dataLeft);
        dataList.add(dataRight);
        System.out.println("DEBUG >>> Data Left: " + Arrays.toString(dataLeft));
        System.out.println("DEBUG >>> Data Right: " + Arrays.toString(dataRight));
        layerPolygon.setStratumId(layerLine.getStratumId());
        layerPolygon.setData(dataList);
        return layerPolygon;
    }

    public void extendPolygon(LayerLine layerLine, LayerPolygon layerPolygon) {
        List<Double[]> dataList = layerPolygon.getData();
        Double[] data = null;
        // 判断地层线类型
        String type = layerLine.getType();
        // 生成数据项
        if (type.compareTo("EqualLen") == 0) {
            data = getRightData(layerLine);
        } else if (type.compareTo("PinchOutLeft") == 0) {
            data = getRightData(layerLine);
        } else if (type.compareTo("PinchOutRight") == 0) {
            data = new Double[3];
            data[0] = drillPosition.get(layerLine.getColumnIndex() + 1);
            data[1] = data[2] = layerLine.getDepthRight();
        }
        // 插入数据表
        dataList.add(data);
    }

    public void generate(Section section) {
        // 获取数据模型
        drillDistance = section.drillDistance;
        drillPosition = section.drillPosition;
        drillPointList = section.drillPointList;
        layerLineList = section.layerLineList;
        // 初始化地层面表
        List<LayerPolygon> layerPolygonList = new ArrayList<>();
        // 遍历地层线表
        for (int i = 0; i < layerLineList.size(); i++) {
            LayerLine layerLine = layerLineList.get(i);
            String type = layerLine.getType();
            System.out.println("DEBUG >>> Type of this line: " + type);
            // 若地层线类型为地表线，则不需要匹配，也不需要新增地层面
            if (type.compareTo("Surface") == 0) {
                continue;
            // 若地层线类型为左尖灭，则不需要匹配，直接在地层面表中新增地层面
            } else if (type.compareTo("PinchOutLeft") == 0) {
                layerPolygonList.add(generatePolygon(layerLine));
                continue;
            }
            // 若地层线类型为右尖灭或等长匹配，则按以下规则处理
            int index = layerLine.getColumnIndex();
            // 若是最左地层线，直接新建地层面
            if (index == 0) {
                layerPolygonList.add(generatePolygon(layerLine));
                continue;
            }
            // 非最左地层，与左侧地层面进行匹配
            boolean added = false;
            for (int j = 0; j < layerPolygonList.size(); j++) {
                if (layerPolygonList.get(j).getStratumId().compareTo(layerLine.getStratumId()) != 0)
                    continue;
                // 按照columnIndex从左向右的顺序，将地层线左端点与地层面右端点进行匹配
                LayerPolygon curPolygon = layerPolygonList.get(j);
                List<Double[]> curData = curPolygon.getData();
                Double[] polygonRight = {curData.get(curData.size() - 1)[0], curData.get(curData.size() - 1)[1]};
                Double[] layerlineLeft = {drillPosition.get(index), layerLine.getDepthLeft()};
                // 若匹配成功，则按照地层线类型扩展地层面
                if (samePoint(layerlineLeft, polygonRight)) {
                    extendPolygon(layerLine, curPolygon);
                    added = true;
                }
            }
            // 若匹配失败，则在地层面表中新增一个地层面
            if (added == false)
                layerPolygonList.add(generatePolygon(layerLine));
        }
        section.setLayerPolygonList(layerPolygonList);
    }
}
