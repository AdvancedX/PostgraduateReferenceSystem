package com.pgs.score_2022.domain;

import com.pgs.common.annotation.Excel;
import com.pgs.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 2022年详细信息对象 major_score_2022
 * 
 * @author 许哲睿
 * @date 2025-04-08
 */
public class MajorScore2022 extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 年份 */
    @Excel(name = "年份")
    private String year;

    /** 学校 */
    @Excel(name = "学校")
    private String school;

    /** 硕士类型 */
    @Excel(name = "硕士类型")
    private String type;

    /** 专业代码 */
    @Excel(name = "专业代码")
    private String majorCode;

    /** 专业名称 */
    @Excel(name = "专业名称")
    private String major;

    /** 总分 */
    @Excel(name = "总分")
    private String score;

    /** 政治 */
    @Excel(name = "政治")
    private String politic;

    /** 外语 */
    @Excel(name = "外语")
    private String language;

    /** 专业课Ⅰ */
    @Excel(name = "专业课Ⅰ")
    private String subject1;

    /** 专业课Ⅱ */
    @Excel(name = "专业课Ⅱ")
    private String subject2;

    /** 备注 */
    @Excel(name = "备注")
    private String note;

    /** 学校省份 */
    @Excel(name = "学校省份")
    private String province;

    /** 学校属性 */
    @Excel(name = "学校属性")
    private String schoolType;

    /** 学校官网 */
    @Excel(name = "学校官网")
    private String website;

    /** 专业官网 */
    @Excel(name = "专业官网")
    private String majorWebsite;

    /** 电话 */
    @Excel(name = "电话")
    private String phonenumber;

    /** 电子邮箱 */
    @Excel(name = "电子邮箱")
    private String email;

    /** 地址 */
    @Excel(name = "地址")
    private String site;

    /** 隶属 */
    @Excel(name = "隶属")
    private String belonging;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
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
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setMajorCode(String majorCode) 
    {
        this.majorCode = majorCode;
    }

    public String getMajorCode() 
    {
        return majorCode;
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
    public void setPolitic(String politic) 
    {
        this.politic = politic;
    }

    public String getPolitic() 
    {
        return politic;
    }
    public void setLanguage(String language) 
    {
        this.language = language;
    }

    public String getLanguage() 
    {
        return language;
    }
    public void setSubject1(String subject1) 
    {
        this.subject1 = subject1;
    }

    public String getSubject1() 
    {
        return subject1;
    }
    public void setSubject2(String subject2) 
    {
        this.subject2 = subject2;
    }

    public String getSubject2() 
    {
        return subject2;
    }
    public void setNote(String note) 
    {
        this.note = note;
    }

    public String getNote() 
    {
        return note;
    }
    public void setProvince(String province) 
    {
        this.province = province;
    }

    public String getProvince() 
    {
        return province;
    }
    public void setSchoolType(String schoolType) 
    {
        this.schoolType = schoolType;
    }

    public String getSchoolType() 
    {
        return schoolType;
    }
    public void setWebsite(String website) 
    {
        this.website = website;
    }

    public String getWebsite() 
    {
        return website;
    }
    public void setMajorWebsite(String majorWebsite) 
    {
        this.majorWebsite = majorWebsite;
    }

    public String getMajorWebsite() 
    {
        return majorWebsite;
    }
    public void setPhonenumber(String phonenumber) 
    {
        this.phonenumber = phonenumber;
    }

    public String getPhonenumber() 
    {
        return phonenumber;
    }
    public void setEmail(String email) 
    {
        this.email = email;
    }

    public String getEmail() 
    {
        return email;
    }
    public void setSite(String site) 
    {
        this.site = site;
    }

    public String getSite() 
    {
        return site;
    }
    public void setBelonging(String belonging) 
    {
        this.belonging = belonging;
    }

    public String getBelonging() 
    {
        return belonging;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("year", getYear())
            .append("school", getSchool())
            .append("type", getType())
            .append("majorCode", getMajorCode())
            .append("major", getMajor())
            .append("score", getScore())
            .append("politic", getPolitic())
            .append("language", getLanguage())
            .append("subject1", getSubject1())
            .append("subject2", getSubject2())
            .append("note", getNote())
            .append("province", getProvince())
            .append("schoolType", getSchoolType())
            .append("website", getWebsite())
            .append("majorWebsite", getMajorWebsite())
            .append("phonenumber", getPhonenumber())
            .append("email", getEmail())
            .append("site", getSite())
            .append("belonging", getBelonging())
            .toString();
    }
}
