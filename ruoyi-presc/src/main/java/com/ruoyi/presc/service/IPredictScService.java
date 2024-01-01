package com.ruoyi.presc.service;

import java.util.List;
import com.ruoyi.presc.domain.PredictSc;

/**
 * 分数预测Service接口
 * 
 * @author 许哲睿
 * @date 2024-01-01
 */
public interface IPredictScService 
{
    /**
     * 查询分数预测
     * 
     * @param major 分数预测主键
     * @return 分数预测
     */
    public PredictSc selectPredictScByMajor(String major);

    /**
     * 查询分数预测列表
     * 
     * @param predictSc 分数预测
     * @return 分数预测集合
     */
    public List<PredictSc> selectPredictScList(PredictSc predictSc);

    /**
     * 新增分数预测
     * 
     * @param predictSc 分数预测
     * @return 结果
     */
    public int insertPredictSc(PredictSc predictSc);

    /**
     * 修改分数预测
     * 
     * @param predictSc 分数预测
     * @return 结果
     */
    public int updatePredictSc(PredictSc predictSc);

    /**
     * 批量删除分数预测
     * 
     * @param majors 需要删除的分数预测主键集合
     * @return 结果
     */
    public int deletePredictScByMajors(String majors);

    /**
     * 删除分数预测信息
     * 
     * @param major 分数预测主键
     * @return 结果
     */
    public int deletePredictScByMajor(String major);
}
