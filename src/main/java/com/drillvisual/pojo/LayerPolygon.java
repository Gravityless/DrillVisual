package com.drillvisual.pojo;

import java.util.List;

public class LayerPolygon {
    public String getStratumId() {
        return stratumId;
    }

    public void setStratumId(String stratumId) {
        this.stratumId = stratumId;
    }

    public List<Double[]> getData() {
        return data;
    }

    public void setData(List<Double[]> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LayerPolygon{" +
                "stratumId='" + stratumId + '\'' +
                ", data=" + data +
                '}';
    }

    private String stratumId;
    private List<Double[]> data;
    // data: x, y-low, y-high
}
