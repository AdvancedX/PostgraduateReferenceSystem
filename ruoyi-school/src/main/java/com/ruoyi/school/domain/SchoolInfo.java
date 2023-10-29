package com.ruoyi.school.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 院校信息对象 school_info
 * 
 * @author 许哲睿
 * @date 2023-10-29
 */
public class SchoolInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Integer id;

    /** 院校代码 */
    @Excel(name = "院校代码")
    private String schoolid;

    /** 院校名称 */
    @Excel(name = "院校名称")
    private String school;

    /** 院校类型 */
    @Excel(name = "院校类型")
    private String schooltype;

    /** 地区 */
    @Excel(name = "地区")
    private String region;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setSchoolid(String schoolid) 
    {
        this.schoolid = schoolid;
    }

    public String getSchoolid() 
    {
        return schoolid;
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
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("schoolid", getSchoolid())
            .append("school", getSchool())
            .append("schooltype", getSchooltype())
            .append("region", getRegion())
            .toString();
    }
}
