package com.pgs.school.domain;

import com.pgs.common.annotation.Excel;
import com.pgs.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 院校信息对象 school_info
 * 
 * @author 许哲睿
 * @date 2024-06-23
 */
public class SchoolInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 院校名称 */
    @Excel(name = "院校名称")
    private String school;

    /** 院校类型 */
    @Excel(name = "院校类型")
    private String schooltype;

    /** 地区 */
    @Excel(name = "地区")
    private String region;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setSchool(String school) 
    {
        this.school = school;
    }

    public String getSchool() 
    {
        return school;
    }
    public void setSchooltype(String schooltype)
    {
        this.schooltype = schooltype;
    }

    public String getSchooltype()
    {
        return schooltype;
    }
    public void setRegion(String region) 
    {
        this.region = region;
    }

    public String getRegion() 
    {
        return region;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("school", getSchool())
            .append("schooltype", getSchooltype())
            .append("region", getRegion())
            .toString();
    }
}
