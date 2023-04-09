package com.drillvisual.service;

import com.drillvisual.pojo.DrillLine;
import com.drillvisual.pojo.DrillPoint;
import com.drillvisual.pojo.LayerLine;

import java.util.ArrayList;
import java.util.List;

public class SectionPloter {
    public void computeDistance() {
        drillDistance = new ArrayList<Double>();
        List<DrillPoint> drillPointList = drillLine.getDrillPointList();
        for (int i = 0; i < drillPointList.size() - 1; i++) {
            Double distX = drillPointList.get(i).getDrillX() - drillPointList.get(i + 1).getDrillX();
            Double distY = drillPointList.get(i).getDrillY() - drillPointList.get(i + 1).getDrillY();
            drillDistance.add(Math.sqrt(distX * distX + distY * distY));
        }
    }

    public DrillLine getDrillLine() {
        return drillLine;
    }

    public void setDrillLine(DrillLine drillLine) {
        this.drillLine = drillLine;
    }

    public List<LayerLine> getLayerLineList() {
        return layerLineList;
    }

    public void setLayerLineList(List<LayerLine> layerLineList) {
        this.layerLineList = layerLineList;
    }

    public DrillLine drillLine;
    public List<LayerLine> layerLineList;
    public List<Double> drillDistance;
}
