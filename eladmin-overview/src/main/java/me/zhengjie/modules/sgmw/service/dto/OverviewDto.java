package me.zhengjie.modules.sgmw.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import me.zhengjie.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @auther: wukenan
 * @date: 2020/11/19
 * @description:
 */

@Getter
@Setter
public class OverviewDto {

    private Long id;

    private String sysId;

    private String sysName;

    private String area;

    private String systemType;

    private String functionDescription;

    private String owner;

    private String developmentLanguage;

    private String clientType;

    private String supportSmartPhone;

    private String weblink;

    private String supportAd;

    private String serverLocation;

    private String softwareType;

    private String vitalOrNot;

    private String businessServiceLevel;

    private String businessWorkProperty;

    private String businessScope;

    private String pocOrNot;

    private String informationSecurityLevel;

    private String dataAboard;

    private String systemUseScope;

    private String personalInfoAmount;

    private String systemSecurityLevel;

    private String systemSecurityRecordNo;

    private String internetPoliceRecordNo;

    private String internetMiitRecordNo;

    private String rto;

    private String rpo;

    private String mop;

    private String businessPeakValue;

    private String systemCapability;

    private String changeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OverviewDto that = (OverviewDto) o;
        return sysId.equals(that.sysId) &&
                sysName.equals(that.sysName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sysId, sysName);
    }
}
