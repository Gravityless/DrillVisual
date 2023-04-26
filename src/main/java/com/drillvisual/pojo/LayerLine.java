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

    public String getDrillStratumIdLeft() {
        return drillStratumIdLeft;
    }

    public void setDrillStratumIdLeft(String drillStratumIdLeft) {
        this.drillStratumIdLeft = drillStratumIdLeft;
    }

    public String getDrillStratumIdRight() {
        return drillStratumIdRight;
    }

    public void setDrillStratumIdRight(String drillStratumIdRight) {
        this.drillStratumIdRight = drillStratumIdRight;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "LayerLine{" +
                "columnIndex=" + columnIndex +
                ", stratumId='" + stratumId + '\'' +
                ", depthLeft=" + depthLeft +
                ", depthRight=" + depthRight +
                ", drillStratumIdLeft='" + drillStratumIdLeft + '\'' +
                ", drillStratumIdRight='" + drillStratumIdRight + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    private int columnIndex;
    private String stratumId;
    private double depthLeft;
    private double depthRight;
    private String drillStratumIdLeft;
    private String drillStratumIdRight;
    private String type;
}
