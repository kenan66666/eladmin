package me.zhengjie.modules.sgmw.service.impl;

import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.sgmw.domain.AreaSystem;
import me.zhengjie.modules.sgmw.domain.BussLogic;
import me.zhengjie.modules.sgmw.repository.AreaSystemRepository;
import me.zhengjie.modules.sgmw.service.AreaSystemService;
import me.zhengjie.modules.sgmw.service.dto.AreaSystemDto;
import me.zhengjie.modules.sgmw.service.dto.AreaSystemQueryCriteria;
import me.zhengjie.modules.sgmw.service.dto.BussLogicDto;
import me.zhengjie.modules.sgmw.service.mapstruct.AreaSystemMapper;
import me.zhengjie.utils.QueryHelp;
import me.zhengjie.utils.RedisUtils;
import me.zhengjie.utils.SecurityUtils;
import me.zhengjie.utils.StringUtils;
import me.zhengjie.utils.enums.DataScopeEnum;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @auther: wukenan
 * @date: 2020/11/12
 * @description:
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "areaSystem")
public class AreaSystemServiceImpl implements AreaSystemService {

    public final AreaSystemRepository areaSystemRepository;
    public final AreaSystemMapper areaSystemMapper;
    public RedisUtils redisUtils;

    @Override
    public List<AreaSystemDto> queryAll(AreaSystemQueryCriteria criteria, Boolean isQuery) throws Exception {
        return areaSystemMapper.toDto(areaSystemRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public List<AreaSystem> findBySysName(String sysName) {
        List<AreaSystem> areaSystems = areaSystemRepository.findBySysNameLike(sysName);
        return areaSystems;
    }

    @Override
    public List<AreaSystem> findAll() {
        List<AreaSystem> areaSystems = areaSystemRepository.findAll();
        return areaSystems;
    }

    @Override
    public void create(AreaSystem resources) {

    }

    @Override
    public void update(AreaSystem resources) {

    }

    @Override
    public void delete(Set<AreaSystemDto> AreaSystemDtos) {

    }

    @Override
    public Set<AreaSystemDto> getDeleteAreaSystems(List<AreaSystem> menuList, Set<AreaSystemDto> AreaSystemDtos) {
        return null;
    }

    @Override
    public void verification(Set<AreaSystemDto> AreaSystemDtos) {

    }
}
