package com.ruoyi.presc.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 分数预测对象 predict_sc
 * 
 * @author 许哲睿
 * @date 2024-01-01
 */
public class PredictSc extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 专业名称 */
    @Excel(name = "专业名称")
    private String major;

    /** 分数 */
    @Excel(name = "分数")
    private String score;

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
            .append("major", getMajor())
            .append("score", getScore())
            .toString();
    }
}
