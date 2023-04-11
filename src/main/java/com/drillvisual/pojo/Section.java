package com.drillvisual.pojo;

import java.util.List;

public class Section {
    public List<LayerLine> getLayerLineList() {
        return layerLineList;
    }

    public void setLayerLineList(List<LayerLine> layerLineList) {
        this.layerLineList = layerLineList;
    }

    public List<DrillPoint> getDrillPointList() {
        return drillPointList;
    }

    public List<Double> getDrillDistance() {
        return drillDistance;
    }

    public void setDrillDistance(List<Double> drillDistance) {
        this.drillDistance = drillDistance;
    }

    public void setDrillPointList(List<DrillPoint> drillPointList) {
        this.drillPointList = drillPointList;
    }

    @Override
    public String toString() {
        return "Section{" +
                "drillPointList=" + drillPointList +
                ", layerLineList=" + layerLineList +
                ", drillDistance=" + drillDistance +
                '}';
    }

    public List<DrillPoint> drillPointList;
    public List<LayerLine> layerLineList;
    public List<Double> drillDistance;
}
