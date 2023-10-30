package com.ruoyi.presc.service;

import java.util.List;
import com.ruoyi.presc.domain.PredictSc;

/**
 * 分数预测Service接口
 * 
 * @author ruoyi
 * @date 2023-10-30
 */
public interface IPredictScService 
{
    /**
     * 查询分数预测
     * 
     * @param id 分数预测主键
     * @return 分数预测
     */
    public PredictSc selectPredictScById(Integer id);

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
     * @param ids 需要删除的分数预测主键集合
     * @return 结果
     */
    public int deletePredictScByIds(String ids);

    /**
     * 删除分数预测信息
     * 
     * @param id 分数预测主键
     * @return 结果
     */
    public int deletePredictScById(Integer id);
}
