package me.zhengjie.modules.sgmw.service.dto;

import cn.hutool.core.date.DateTime;
import lombok.Data;
import org.bouncycastle.asn1.cms.TimeStampAndCRL;

import java.sql.Timestamp;
import java.util.List;

/**
 * @auther: wukenan
 * @date: 2020/11/5
 * @description:
 */
@Data
public class LessonLearnQueryCriteria {
    private String name;
    private String sysName;
    private List<Timestamp> createTime;
    private Boolean enabled;
    private Integer pid;
    private Boolean pidIsNull;

}
