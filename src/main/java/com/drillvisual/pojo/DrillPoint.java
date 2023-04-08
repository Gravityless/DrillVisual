package com.drillvisual.pojo;

import java.util.List;

public class DrillPoint {
    public String getDrillId() {
        return drillId;
    }

    public void setDrillId(String drillId) {
        this.drillId = drillId;
    }

    public String getPrjId() {
        return prjId;
    }

    public void setPrjId(String prjId) {
        this.prjId = prjId;
    }

    public String getDrillCode() {
        return drillCode;
    }

    public void setDrillCode(String drillCode) {
        this.drillCode = drillCode;
    }

    public Double getDrillDiameter() {
        return drillDiameter;
    }

    public void setDrillDiameter(Double drillDiameter) {
        this.drillDiameter = drillDiameter;
    }

    public Double getDrillX() {
        return drillX;
    }

    public void setDrillX(Double drillX) {
        this.drillX = drillX;
    }

    public Double getDrillY() {
        return drillY;
    }

    public void setDrillY(Double drillY) {
        this.drillY = drillY;
    }

    public Double getDrillHeight() {
        return drillHeight;
    }

    public void setDrillHeight(Double drillHeight) {
        this.drillHeight = drillHeight;
    }

    public Double getDrillDepth() {
        return drillDepth;
    }

    public void setDrillDepth(Double drillDepth) {
        this.drillDepth = drillDepth;
    }

    public List<DrillStratum> getDrillStratumList() {
        return drillStratumList;
    }

    public void setDrillStratumList(List<DrillStratum> drillStratumList) {
        this.drillStratumList = drillStratumList;
    }

    @Override
    public String toString() {
        return "DrillPoint{" +
                "drillId='" + drillId + '\'' +
                ", prjId='" + prjId + '\'' +
                ", drillCode='" + drillCode + '\'' +
                ", drillDiameter=" + drillDiameter +
                ", drillX=" + drillX +
                ", drillY=" + drillY +
                ", drillHeight=" + drillHeight +
                ", drillDepth=" + drillDepth +
                ", drillStratumList=" + drillStratumList +
                '}';
    }

    private String drillId;
    private String prjId;
    private String drillCode;
    private Double drillDiameter;
    private Double drillX;
    private Double drillY;
    private Double drillHeight;
    private Double drillDepth;
    private List<DrillStratum> drillStratumList;
}
