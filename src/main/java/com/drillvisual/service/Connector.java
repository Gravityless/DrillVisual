package com.drillvisual.service;

import com.drillvisual.pojo.DrillPoint;
import com.drillvisual.pojo.LayerLine;

import java.util.List;

public interface Connector {
    public void connect(List<LayerLine> layerLineList, List<DrillPoint> drillPointList);
}
