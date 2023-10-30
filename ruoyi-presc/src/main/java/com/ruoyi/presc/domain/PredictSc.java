package com.ruoyi.presc.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresBuilder;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.apache.commons.math3.fitting.leastsquares.MultivariateJacobianFunction;
import org.apache.commons.math3.linear.RealVector;

/**
 * 分数预测对象 predict_sc
 * 
 * @author ruoyi
 * @date 2023-10-30
 */
public class PredictSc extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Integer id;

    /** 第一年录取分数 */
    @Excel(name = "第一年录取分数")
    private Integer sc1;

    /** 第二年录取分数 */
    @Excel(name = "第二年录取分数")
    private Integer sc2;

    /** 第三年录取分数 */
    @Excel(name = "第三年录取分数")
    private Integer sc3;

    /** 第四年录取分数 */
    @Excel(name = "第四年录取分数")
    private Integer sc4;

    /** 第五年录取分数 */
    @Excel(name = "第五年录取分数")
    private Integer sc5;

    /** 预测分数 */
    @Excel(name = "预测分数")
    private Double re;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setSc1(Integer sc1) 
    {
        this.sc1 = sc1;
    }

    public Integer getSc1() 
    {
        return sc1;
    }
    public void setSc2(Integer sc2) 
    {
        this.sc2 = sc2;
    }

    public Integer getSc2() 
    {
        return sc2;
    }
    public void setSc3(Integer sc3) 
    {
        this.sc3 = sc3;
    }

    public Integer getSc3() 
    {
        return sc3;
    }
    public void setSc4(Integer sc4) 
    {
        this.sc4 = sc4;
    }

    public Integer getSc4() 
    {
        return sc4;
    }
    public void setSc5(Integer sc5) 
    {
        this.sc5 = sc5;
    }

    public Integer getSc5() 
    {
        return sc5;
    }
    public void setRe(Double re) {this.re = re;}
    public Double getRe()
    {
        return re;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("sc1", getSc1())
            .append("sc2", getSc2())
            .append("sc3", getSc3())
            .append("sc4", getSc4())
            .append("sc5", getSc5())
            .append("re", getRe())
            .toString();
    }
    public Double PredictValue(int a,int b,int c,int d,int e) {
        double[] years = {1, 2, 3, 4, 5}; // 前五年的年份
        double[] scores = {a, b, c, d, e}; // 前五年的分数

        double yearToPredict = 6; // 第六年的年份
        double sumX = 0.0;
        double sumY = 0.0;
        double sumXY = 0.0;
        double sumX2 = 0.0;

        for (int i = 0; i < years.length; i++) {
            sumX += years[i];
            sumY += scores[i];
            sumXY += years[i] * scores[i];
            sumX2 += years[i] * years[i];
        }

        double n = years.length;
        double slope = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
        double intercept = (sumY - slope * sumX) / n;

        double predictedScore = intercept + slope * yearToPredict;

        // 限制预测值与前一年的分数差值不超过10
        if (years.length > 0) {
            double lastYearScore = scores[years.length - 1];
            double diff = Math.abs(predictedScore - lastYearScore);
            if (diff > 10) {
                if (predictedScore > lastYearScore) {
                    predictedScore = lastYearScore + 10;
                } else {
                    predictedScore = lastYearScore - 10;
                }
            }
        }
       return predictedScore;
    }
}
