package com.drillvisual.service;

import com.drillvisual.pojo.DrillPoint;
import com.drillvisual.pojo.DrillStratum;
import com.drillvisual.pojo.LayerLine;

import java.util.ArrayList;
import java.util.List;

public class MaxMatchConnector implements Connector{
    public List<LayerLine> layerLineList;
    public List<DrillPoint> drillPointList;
    public List<List<String>> stratumIdLists;

    // 最大匹配
    public void maxMatch(int idx, int leftStart, int leftEnd, int rightStart, int rightEnd) {
        // 结束递归条件
        if (leftEnd == leftStart && rightEnd == rightStart)
            return;
        else if (leftEnd == leftStart) {
            link2left(idx, leftStart, leftEnd, rightStart, rightEnd);
            return;
        }
        else if (rightEnd == rightStart) {
            link2right(idx, leftStart, leftEnd, rightStart, rightEnd);
            return;
        }
        // 获取地层id列表
        List<String> leftIdList = stratumIdLists.get(idx);
        List<String> rightIdList = stratumIdLists.get(idx + 1);
        // 初始化递归指针
        int leftMatchBegin, leftMatchEnd, rightMatchBegin, rightMatchEnd;
        leftMatchBegin = leftMatchEnd = leftStart;
        rightMatchBegin = rightMatchEnd = rightStart;
        // 初始化匹配指针
        int curLeft = leftStart, curRight = rightStart;
        // 初始化匹配长度
        int curLen = 0, maxLen = 0;
        // 开始匹配
        for (int i = leftStart; i < leftEnd; i++) {
            curLeft = i;
            for (int j = rightStart; j < rightEnd; j++) {
                curRight = j;
                curLen = 0;
                while (curLeft < leftEnd && curRight < rightEnd && leftIdList.get(curLeft).compareTo(rightIdList.get(curRight)) == 0) {
                    curLen++;
                    curLeft++;
                    curRight++;
                }
                if (curLen > maxLen) {
                    leftMatchBegin = curLeft - curLen;
                    leftMatchEnd = curLeft;
                    rightMatchBegin = curRight - curLen;
                    rightMatchEnd = curRight;
                    maxLen = curLen;
                }
            }
        }
        // 连接最大匹配
        link(idx, leftMatchBegin, leftMatchEnd, rightMatchBegin, rightMatchEnd);
        // 递归未匹配段
        maxMatch(idx, leftStart, leftMatchBegin, rightStart, rightMatchBegin);
        maxMatch(idx, leftMatchEnd, leftEnd, rightMatchEnd, rightEnd);
    }

    public void link(int idx, int leftStart, int leftEnd, int rightStart, int rightEnd) {
        if (leftEnd - leftStart == rightEnd - rightStart) {
            // 两边等长度匹配，对应连接
            linkEqualLen(idx, leftStart, leftEnd, rightStart, rightEnd);
        } else if (rightEnd == rightStart) {
            // 左边有多余地层，右边无剩余，左边全部连向右边
            link2right(idx, leftStart, leftEnd, rightStart, rightEnd);
        } else if (leftEnd == leftStart) {
            // 右边有多余地层，左边无剩余，右边全部连向左边
            link2left(idx, leftStart, leftEnd, rightStart, rightEnd);
        }
    }

    public void linkEqualLen(int idx, int leftStart, int leftEnd, int rightStart, int rightEnd)  {
        DrillStratum stratumLeft, stratumRight;
        for (int i = 0; i < leftEnd - leftStart; i++) {
            // 获取stratum对象
            stratumLeft = drillPointList.get(idx).getDrillStratumList().get(leftStart + i);
            stratumRight = drillPointList.get(idx + 1).getDrillStratumList().get(rightStart + i);
            // 设置layerline对象属性
            LayerLine layerLine = new LayerLine();
            layerLine.setColumnIndex(idx);
            layerLine.setStratumId(stratumLeft.getStratumId());
            layerLine.setDepthLeft(drillPointList.get(idx).getDrillHeight() - stratumLeft.getBottomDepth());
            layerLine.setDepthRight(drillPointList.get(idx + 1).getDrillHeight() - stratumRight.getBottomDepth());
            layerLineList.add(layerLine);
        }
    }

    public void link2left(int idx, int leftStart, int leftEnd, int rightStart, int rightEnd) {
        // 当leftStart == 0，即顶层地层不匹配时，避免出现越界错误
        if (leftStart == 0)
            return;
        DrillStratum stratumLeft, stratumRight;
        stratumLeft = drillPointList.get(idx).getDrillStratumList().get(leftEnd - 1);
        for (int i = 0; i < rightEnd - rightStart; i++) {
            stratumRight = drillPointList.get(idx + 1).getDrillStratumList().get(rightStart + i);
            LayerLine layerLine = new LayerLine();
            layerLine.setColumnIndex(idx);
            layerLine.setStratumId(stratumRight.getStratumId());
            layerLine.setDepthLeft(drillPointList.get(idx).getDrillHeight() - stratumLeft.getBottomDepth());
            layerLine.setDepthRight(drillPointList.get(idx + 1).getDrillHeight() - stratumRight.getBottomDepth());
            layerLineList.add(layerLine);
        }
    }

    public void link2right(int idx, int leftStart, int leftEnd, int rightStart, int rightEnd) {
        // 当rightStart == 0，即顶层地层不匹配时，避免出现越界错误
        if (rightStart == 0)
            return;
        DrillStratum stratumLeft, stratumRight;
        stratumRight = drillPointList.get(idx + 1).getDrillStratumList().get(rightEnd - 1);
        for (int i = 0; i < leftEnd - leftStart; i++) {
            stratumLeft = drillPointList.get(idx).getDrillStratumList().get(leftStart + i);
            LayerLine layerLine = new LayerLine();
            layerLine.setColumnIndex(idx);
            layerLine.setStratumId(stratumLeft.getStratumId());
            layerLine.setDepthLeft(drillPointList.get(idx).getDrillHeight() - stratumLeft.getBottomDepth());
            layerLine.setDepthRight(drillPointList.get(idx + 1).getDrillHeight() - stratumRight.getBottomDepth());
            layerLineList.add(layerLine);
        }
    }
    // 预处理
    public void preProcess() {
        stratumIdLists = new ArrayList<>();
        // 计算stratumIdLists
        for (int i = 0; i < drillPointList.size(); i++) {
            DrillPoint drillPoint = drillPointList.get(i);
            List<DrillStratum> drillStratumList = drillPoint.getDrillStratumList();
            List<String> drillStratumIdList = new ArrayList<>();
            for (int j = 0; j < drillStratumList.size(); j++) {
                drillStratumIdList.add(drillStratumList.get(j).getStratumId());
            }
            stratumIdLists.add(drillStratumIdList);
        }
    }

    // 后处理
    public void postProcess() {
        // 处理边界连线
        // 将最顶层连接在一起
        for (int i = 0; i < drillPointList.size() - 1; i++) {
            // 设置layerLine基本属性
            LayerLine layerLine = new LayerLine();
            layerLine.setColumnIndex(i);
            // 获取stratum对象
            DrillStratum stratumLeft = drillPointList.get(i).getDrillStratumList().get(0);
            DrillStratum stratumRight = drillPointList.get(i + 1).getDrillStratumList().get(0);
            // 判断地层年代顺序
            int type = stratumLeft.getStratumId().compareTo(stratumRight.getStratumId());
            if (type <= 0) {
                layerLine.setStratumId(stratumLeft.getStratumId());
            } else {
                layerLine.setStratumId(stratumRight.getStratumId());
            }
            // 连接顶板高度绝对坐标
            layerLine.setDepthLeft(drillPointList.get(i).getDrillHeight());
            layerLine.setDepthRight(drillPointList.get(i + 1).getDrillHeight());
            // 添加到地层线表
            layerLineList.add(layerLine);
        }
    }

    public void connect(List<LayerLine> layerLineList, List<DrillPoint> drillPointList) {
        this.layerLineList = layerLineList;
        this.drillPointList = drillPointList;
        // 预处理
        preProcess();
        // 最大匹配
        for (int i = 0; i < drillPointList.size() - 1; i++) {
            maxMatch(i, 0, drillPointList.get(i).getDrillStratumList().size(), 0, drillPointList.get(i + 1).getDrillStratumList().size());
        }
        // 后处理
        postProcess();
    }

}
