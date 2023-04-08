package com.drillvisual.service;

import com.drillvisual.pojo.DrillLine;
import com.drillvisual.pojo.LayerLine;

import java.util.List;

public class SectionPloter {
    public DrillLine getDrillLine() {
        return drillLine;
    }

    public void setDrillLine(DrillLine drillLine) {
        this.drillLine = drillLine;
    }

    public List<LayerLine> getLayerLineList() {
        return layerLineList;
    }

    public void setLayerLineList(List<LayerLine> layerLineList) {
        this.layerLineList = layerLineList;
    }

    public DrillLine drillLine;
    public List<LayerLine> layerLineList;
}
