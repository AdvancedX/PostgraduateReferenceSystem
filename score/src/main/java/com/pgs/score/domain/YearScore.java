package com.pgs.score.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.pgs.common.annotation.Excel;
import com.pgs.common.core.domain.BaseEntity;

/**
 * 历年分数对象 year_score
 * 
 * @author 许哲睿
 * @date 2023-12-30
 */
public class YearScore extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 年份 */
    @Excel(name = "年份")
    private String year;

    /** 院校名称 */
    @Excel(name = "院校名称")
    private String school;

    /** 专业代码 */
    @Excel(name = "专业代码")
    private String majorId;

    /** 专业 */
    @Excel(name = "专业")
    private String major;

    /** 总分 */
    @Excel(name = "总分")
    private String score;

    public void setYear(String year) 
    {
        this.year = year;
    }

    public String getYear() 
    {
        return year;
    }
    public void setSchool(String school) 
    {
        this.school = school;
    }

    public String getSchool() 
    {
        return school;
    }
    public void setMajorId(String majorId) 
    {
        this.majorId = majorId;
    }

    public String getMajorId() 
    {
        return majorId;
    }
    public void setMajor(String major) 
    {
        this.major = major;
    }

    public String getMajor() 
    {
        return major;
    }
    public void setScore(String score) 
    {
        this.score = score;
    }

    public String getScore() 
    {
        return score;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("year", getYear())
            .append("school", getSchool())
            .append("majorId", getMajorId())
            .append("major", getMajor())
            .append("score", getScore())
            .toString();
    }
}
