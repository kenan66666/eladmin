package me.zhengjie.modules.sgmw.service;

import me.zhengjie.modules.sgmw.service.dto.OverviewDto;

import java.util.List;

public interface OverviewService {

    OverviewDto findById(Long id);
    OverviewDto findBySysId(String sysId);
    OverviewDto findBySysName(String sysName);
    List<OverviewDto> findByChangeId(String changeId);
    void create(OverviewDto resources);
    void update(OverviewDto resources);
}
