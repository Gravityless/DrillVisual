package com.drillvisual.mapper;

import com.drillvisual.pojo.DrillStratum;

import java.util.List;

public interface DrillStratumMapper {
    DrillStratum selectById(String id);

    List<DrillStratum> selectByDrillId(String id);
}
