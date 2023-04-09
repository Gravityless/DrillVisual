package com.drillvisual.service;

import com.drillvisual.pojo.DrillPoint;
import com.drillvisual.pojo.DrillStratum;
import com.drillvisual.pojo.LayerLine;

import java.util.List;

public class Connector {
    final static int TYPE_UNDEFINE = 0;
    final static int TYPE_CONTINUE = 1;
    final static int TYPE_PINCH_RIGHT = 2;
    final static int TYPE_PINCH_LEFT = 3;

    public List<LayerLine> layerLineList;
    public List<DrillPoint> drillPointList;

    public void checkRule(int drillIdx) {
        // 分别获取左右钻孔点地层表，从左侧向右侧生成地层线
        List<DrillStratum> drillStratumListLeft = drillPointList.get(drillIdx).getDrillStratumList();
        List<DrillStratum> drillStratumListRight = drillPointList.get(drillIdx + 1).getDrillStratumList();
        int leftSize = drillStratumListLeft.size();
        int rightSize = drillStratumListRight.size();
        // 建立扫描指针
        int leftIdx = 0;
        int rightIdx = 0;
        // 两个扫描指针向下不回溯
        while (leftIdx < leftSize && rightIdx < rightSize) {
            String stratumIdLeft = drillStratumListLeft.get(leftIdx).getStratumId();
            String stratumIdRight = drillStratumListRight.get(rightIdx).getStratumId();
            // 地层连续
            if (stratumIdLeft.compareTo(stratumIdRight) == 0) {
                link(drillIdx, leftIdx, rightIdx, TYPE_CONTINUE);
                leftIdx++;
                rightIdx++;
            // 地层右尖灭
            } else if (stratumIdLeft.compareTo(stratumIdRight) < 0) {
                link(drillIdx, leftIdx, rightIdx, TYPE_PINCH_RIGHT);
                leftIdx++;
            // 地层左尖灭
            } else if (stratumIdLeft.compareTo(stratumIdRight) > 0) {
                link(drillIdx, leftIdx, rightIdx, TYPE_PINCH_LEFT);
                rightIdx++;
            // 所有情况均未匹配
            } else {
                link(drillIdx, leftIdx, rightIdx, TYPE_UNDEFINE);
                leftIdx++;
                rightIdx++;
            }
        }
        // 左侧钻孔具有未匹配地层
        while (leftIdx < leftSize) {
            link(drillIdx, leftIdx, rightSize - 1, TYPE_PINCH_RIGHT);
            leftIdx++;
        }
        // 右侧钻孔具有未匹配地层
        while (rightIdx < rightSize) {
            link(drillIdx, leftSize - 1, rightIdx, TYPE_PINCH_LEFT);
            rightIdx++;
        }
    }

    public void link(int drillIdx, int leftIdx, int rightIdx, int type) {
        switch (type) {
            case TYPE_UNDEFINE:
                break;
            case TYPE_CONTINUE:

                break;
            case TYPE_PINCH_RIGHT:
                break;
            case TYPE_PINCH_LEFT:
                break;
        }
    }

    public void connect(List<LayerLine> layerLineList, List<DrillPoint> drillPointList) {
        // 转存到实例字段
        this.layerLineList = layerLineList;
        this.drillPointList = drillPointList;
        // 分析每两个相邻钻孔
        for (int i = 0; i < drillPointList.size() - 1; i++) {
            checkRule(i);
        }
        // 后处理
    }
}
