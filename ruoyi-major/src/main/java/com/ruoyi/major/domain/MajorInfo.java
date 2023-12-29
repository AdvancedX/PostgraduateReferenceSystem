package com.ruoyi.major.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 专业信息对象 major_info
 * 
 * @author 许哲睿
 * @date 2023-12-24
 */
public class MajorInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 院校名称 */
    @Excel(name = "院校名称")
    private String school;

    /** 专业名称 */
    @Excel(name = "专业名称")
    private String major;

    /** 研究方向 */
    @Excel(name = "研究方向")
    private String researchDirection;

    /** 思想政治 */
    @Excel(name = "思想政治")
    private String politics;

    /** 外语 */
    @Excel(name = "外语")
    private String language;

    /** 专业课（1） */
    @Excel(name = "专业课", readConverterExp = "1=")
    private String majorsubject1;

    /** 专业课（2） */
    @Excel(name = "专业课", readConverterExp = "2=")
    private String majorSubject2;

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
    public void setMajor(String major) 
    {
        this.major = major;
    }

    public String getMajor() 
    {
        return major;
    }
    public void setResearchDirection(String researchDirection) 
    {
        this.researchDirection = researchDirection;
    }

    public String getResearchDirection() 
    {
        return researchDirection;
    }
    public void setPolitics(String politics) 
    {
        this.politics = politics;
    }

    public String getPolitics() 
    {
        return politics;
    }
    public void setLanguage(String language) 
    {
        this.language = language;
    }

    public String getLanguage() 
    {
        return language;
    }
    public void setMajorsubject1(String majorsubject1) 
    {
        this.majorsubject1 = majorsubject1;
    }

    public String getMajorsubject1() 
    {
        return majorsubject1;
    }
    public void setMajorSubject2(String majorSubject2) 
    {
        this.majorSubject2 = majorSubject2;
    }

    public String getMajorSubject2() 
    {
        return majorSubject2;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("school", getSchool())
            .append("major", getMajor())
            .append("researchDirection", getResearchDirection())
            .append("politics", getPolitics())
            .append("language", getLanguage())
            .append("majorsubject1", getMajorsubject1())
            .append("majorSubject2", getMajorSubject2())
            .toString();
    }
}
