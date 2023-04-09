package com.drillvisual.pojo;

import java.util.List;

public class LayerLine {
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

    public DrillPoint getGetDrillPointRight() {
        return getDrillPointRight;
    }

    public void setGetDrillPointRight(DrillPoint getDrillPointRight) {
        this.getDrillPointRight = getDrillPointRight;
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
                "stratumId='" + stratumId + '\'' +
                ", drillPointLeft=" + drillPointLeft +
                ", getDrillPointRight=" + getDrillPointRight +
                ", depthLeft=" + depthLeft +
                ", depthRight=" + depthRight +
                '}';
    }

    private String stratumId;
    private DrillPoint drillPointLeft;
    private DrillPoint getDrillPointRight;
    private double depthLeft;
    private double depthRight;
}
