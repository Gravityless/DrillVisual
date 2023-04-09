package com.drillvisual.pojo;

import java.util.List;

public class LayerLine {
    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public String getStratumId() {
        return stratumId;
    }

    public void setStratumId(String stratumId) {
        this.stratumId = stratumId;
    }

    public DrillPoint getDrillPointLeft() {
        return drillPointLeft;
    }

    public void setDrillPointLeft(DrillPoint drillPointLeft) {
        this.drillPointLeft = drillPointLeft;
    }

    public DrillPoint getDrillPointRight() {
        return drillPointRight;
    }

    public void setDrillPointRight(DrillPoint drillPointRight) {
        this.drillPointRight = drillPointRight;
    }

    public double getDepthLeft() {
        return depthLeft;
    }

    public void setDepthLeft(double depthLeft) {
        this.depthLeft = depthLeft;
    }

    public double getDepthRight() {
        return depthRight;
    }

    public void setDepthRight(double depthRight) {
        this.depthRight = depthRight;
    }


    @Override
    public String toString() {
        return "LayerLine{" +
                "columnIndex=" + columnIndex +
                ", stratumId='" + stratumId + '\'' +
                ", drillPointLeft=" + drillPointLeft +
                ", drillPointRight=" + drillPointRight +
                ", depthLeft=" + depthLeft +
                ", depthRight=" + depthRight +
                '}';
    }

    private int columnIndex;
    private String stratumId;
    private DrillPoint drillPointLeft;
    private DrillPoint drillPointRight;
    private double depthLeft;
    private double depthRight;
}
