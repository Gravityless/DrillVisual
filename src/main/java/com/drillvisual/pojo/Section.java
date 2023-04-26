package com.drillvisual.pojo;

import java.util.List;

public class Section {
    public List<LayerLine> getLayerLineList() {
        return layerLineList;
    }

    public void setLayerLineList(List<LayerLine> layerLineList) {
        this.layerLineList = layerLineList;
    }

    public List<Double> getDrillPosition() {
        return drillPosition;
    }

    public void setDrillPosition(List<Double> drillPosition) {
        this.drillPosition = drillPosition;
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

    public List<LayerPolygon> getLayerPolygonList() {
        return layerPolygonList;
    }

    public void setLayerPolygonList(List<LayerPolygon> layerPolygonList) {
        this.layerPolygonList = layerPolygonList;
    }

    @Override
    public String toString() {
        return "Section{" +
                "drillDistance=" + drillDistance +
                ", drillPosition=" + drillPosition +
                ", drillPointList=" + drillPointList +
                ", layerLineList=" + layerLineList +
                ", layerPolygonList=" + layerPolygonList +
                '}';
    }

    public List<Double> drillDistance;
    public List<Double> drillPosition;
    public List<DrillPoint> drillPointList;
    public List<LayerLine> layerLineList;
    public List<LayerPolygon> layerPolygonList;
}
