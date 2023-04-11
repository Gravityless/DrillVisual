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
                ", depthLeft=" + depthLeft +
                ", depthRight=" + depthRight +
                '}';
    }

    private int columnIndex;
    private String stratumId;
    // private DrillPoint drillPointLeft;
    // private DrillPoint drillPointRight;
    private double depthLeft;
    private double depthRight;
}
