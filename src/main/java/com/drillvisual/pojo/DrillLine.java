package com.drillvisual.pojo;

import java.util.List;

public class DrillLine {
    public List<DrillPoint> getDrillPointList() {
        return drillPointList;
    }

    public void setDrillPointList(List<DrillPoint> drillPointList) {
        this.drillPointList = drillPointList;
    }

    private List<DrillPoint> drillPointList;
}
