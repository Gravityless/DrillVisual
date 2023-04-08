package com.drillvisual.service;

import com.drillvisual.pojo.DrillPoint;
import com.drillvisual.pojo.DrillStratum;
import com.drillvisual.pojo.LayerLine;

import java.util.List;

public class Connector {
    final static int TYPE_UNDEFINE = 0;
    final static int TYPE_CONTINUE = 1;
    final static int TYPE_PINCHOUT = 2;
    final static int TYPE_DISCONTINUE = 3;
    final static int TYPE_LENS = 4;

    public int checkRule(DrillStratum drillStratum, int drillIdx, int StratumIdx, List<DrillPoint> drillPointList) {
        return TYPE_UNDEFINE;
    }

    public LayerLine connect(int type, DrillStratum drillStratum, int drillIdx, int StratumIdx, List<DrillPoint> drillPointList) {
        LayerLine layerLine = new LayerLine();
        return layerLine;
    }
}
