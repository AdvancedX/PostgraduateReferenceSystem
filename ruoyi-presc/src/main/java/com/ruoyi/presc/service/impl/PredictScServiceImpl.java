package com.ruoyi.presc.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.presc.mapper.PredictScMapper;
import com.ruoyi.presc.domain.PredictSc;
import com.ruoyi.presc.service.IPredictScService;
import com.ruoyi.common.core.text.Convert;

/**
 * 分数预测Service业务层处理
 * 
 * @author 许哲睿
 * @date 2024-01-01
 */
@Service
public class PredictScServiceImpl implements IPredictScService 
{
    @Autowired
    private PredictScMapper predictScMapper;

    /**
     * 查询分数预测
     * 
     * @param major 分数预测主键
     * @return 分数预测
     */
    @Override
    public PredictSc selectPredictScByMajor(String major)
    {
        return predictScMapper.selectPredictScByMajor(major);
    }

    /**
     * 查询分数预测列表
     * 
     * @param predictSc 分数预测
     * @return 分数预测
     */
    @Override
    public List<PredictSc> selectPredictScList(PredictSc predictSc)
    {
        return predictScMapper.selectPredictScList(predictSc);
    }

    /**
     * 新增分数预测
     * 
     * @param predictSc 分数预测
     * @return 结果
     */
    @Override
    public int insertPredictSc(PredictSc predictSc)
    {
        return predictScMapper.insertPredictSc(predictSc);
    }

    /**
     * 修改分数预测
     * 
     * @param predictSc 分数预测
     * @return 结果
     */
    @Override
    public int updatePredictSc(PredictSc predictSc)
    {
        return predictScMapper.updatePredictSc(predictSc);
    }

    /**
     * 批量删除分数预测
     * 
     * @param majors 需要删除的分数预测主键
     * @return 结果
     */
    @Override
    public int deletePredictScByMajors(String majors)
    {
        return predictScMapper.deletePredictScByMajors(Convert.toStrArray(majors));
    }

    /**
     * 删除分数预测信息
     * 
     * @param major 分数预测主键
     * @return 结果
     */
    @Override
    public int deletePredictScByMajor(String major)
    {
        return predictScMapper.deletePredictScByMajor(major);
    }
}
