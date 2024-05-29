package com.pgs.presc.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.pgs.common.annotation.Excel;
import com.pgs.common.core.domain.BaseEntity;

/**
 * 分数预测对象 predict_sc
 * 
 * @author 许哲睿
 * @date 2024-04-24
 */
public class PredictSc extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 专业名称 */
    @Excel(name = "专业名称")
    private Long id;

    /** 分数 */
    @Excel(name = "分数")
    private String score;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
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
            .append("id", getId())
            .append("score", getScore())
            .toString();
    }
}
