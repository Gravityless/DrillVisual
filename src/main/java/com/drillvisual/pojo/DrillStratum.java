package com.drillvisual.pojo;

public class DrillStratum {
    public String getDrillStratumId() {
        return drillStratumId;
    }

    public void setDrillStratumId(String drillStratumId) {
        this.drillStratumId = drillStratumId;
    }

    public String getDrillId() {
        return drillId;
    }

    public void setDrillId(String drillId) {
        this.drillId = drillId;
    }

    public String getStratumId() {
        return stratumId;
    }

    public void setStratumId(String stratumId) {
        this.stratumId = stratumId;
    }

    public Double getTopDepth() {
        return topDepth;
    }

    public void setTopDepth(Double topDepth) {
        this.topDepth = topDepth;
    }

    public Double getBottomDepth() {
        return bottomDepth;
    }

    public void setBottomDepth(Double bottomDepth) {
        this.bottomDepth = bottomDepth;
    }

    @Override
    public String toString() {
        return "DrillStratum{" +
                "drillStratumId='" + drillStratumId + '\'' +
                ", drillId='" + drillId + '\'' +
                ", stratumId='" + stratumId + '\'' +
                ", topDepth=" + topDepth +
                ", bottomDepth=" + bottomDepth +
                '}';
    }

    private String drillStratumId;
    private String drillId;
    private String stratumId;
    private Double topDepth;
    private Double bottomDepth;
}
