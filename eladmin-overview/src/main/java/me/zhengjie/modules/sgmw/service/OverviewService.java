package me.zhengjie.modules.sgmw.service;

import me.zhengjie.modules.sgmw.service.dto.AreaSystemDto;
import me.zhengjie.modules.sgmw.service.dto.OverviewDto;
import me.zhengjie.modules.sgmw.service.dto.OverviewMainDto;

import java.util.List;

public interface OverviewService {

    AreaSystemDto findById(Long id);
    AreaSystemDto findBySysId(String sysId);
    AreaSystemDto findBySysName(String sysName);
    List<AreaSystemDto> findByChangeId(String changeId);
    void create(OverviewMainDto resources);
    void update(OverviewMainDto resources);
}
