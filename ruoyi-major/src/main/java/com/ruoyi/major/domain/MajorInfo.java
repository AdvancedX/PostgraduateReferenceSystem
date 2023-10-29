package com.ruoyi.major.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 专业信息对象 major_info
 * 
 * @author 许哲睿
 * @date 2023-10-29
 */
public class MajorInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Integer id;

    /** 院校代码 */
    @Excel(name = "院校代码")
    private String schoolID;

    /** 专业代码 */
    @Excel(name = "专业代码")
    private String majorID;

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

    /** 数学 */
    @Excel(name = "数学")
    private String math;

    /** 专业科目 */
    @Excel(name = "专业科目")
    private String majorSubject;

    /** 复试科目 */
    @Excel(name = "复试科目")
    private String reSubject;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setSchoolID(String schoolID) 
    {
        this.schoolID = schoolID;
    }

    public String getSchoolID() 
    {
        return schoolID;
    }
    public void setMajorID(String majorID) 
    {
        this.majorID = majorID;
    }

    public String getMajorID() 
    {
        return majorID;
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
    public void setMath(String math) 
    {
        this.math = math;
    }

    public String getMath() 
    {
        return math;
    }
    public void setMajorSubject(String majorSubject) 
    {
        this.majorSubject = majorSubject;
    }

    public String getMajorSubject() 
    {
        return majorSubject;
    }
    public void setReSubject(String reSubject) 
    {
        this.reSubject = reSubject;
    }

    public String getReSubject() 
    {
        return reSubject;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("schoolID", getSchoolID())
            .append("majorID", getMajorID())
            .append("major", getMajor())
            .append("researchDirection", getResearchDirection())
            .append("politics", getPolitics())
            .append("language", getLanguage())
            .append("math", getMath())
            .append("majorSubject", getMajorSubject())
            .append("reSubject", getReSubject())
            .toString();
    }
}
