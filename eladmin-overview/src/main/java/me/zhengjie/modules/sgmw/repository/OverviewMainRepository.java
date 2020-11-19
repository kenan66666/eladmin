package me.zhengjie.modules.sgmw.repository;

import me.zhengjie.modules.sgmw.domain.OverviewMain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OverviewMainRepository extends JpaRepository<OverviewMain,Long>, JpaSpecificationExecutor<OverviewMain> {
    int countById(Long ID);
}
