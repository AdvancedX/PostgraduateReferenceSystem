package com.ruoyi.linerpr.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.linerpr.mapper.LinerprMapper;
import com.ruoyi.linerpr.domain.Linerpr;
import com.ruoyi.linerpr.service.ILinerprService;
import com.ruoyi.common.core.text.Convert;

/**
 * 线性回归预测Service业务层处理
 * 
 * @author 许哲睿
 * @date 2024-03-16
 */
@Service
public class LinerprServiceImpl implements ILinerprService 
{
    @Autowired
    private LinerprMapper linerprMapper;

    /**
     * 查询线性回归预测
     * 
     * @param sc1 线性回归预测主键
     * @return 线性回归预测
     */
    @Override
    public Linerpr selectLinerprBySc1(Integer sc1)
    {
        return linerprMapper.selectLinerprBySc1(sc1);
    }

    /**
     * 查询线性回归预测列表
     * 
     * @param linerpr 线性回归预测
     * @return 线性回归预测
     */
    @Override
    public List<Linerpr> selectLinerprList(Linerpr linerpr)
    {
        return linerprMapper.selectLinerprList(linerpr);
    }

    /**
     * 新增线性回归预测
     * 
     * @param linerpr 线性回归预测
     * @return 结果
     */
    @Override
    public int insertLinerpr(Linerpr linerpr)
    {
        return linerprMapper.insertLinerpr(linerpr);
    }

    /**
     * 修改线性回归预测
     * 
     * @param linerpr 线性回归预测
     * @return 结果
     */
    @Override
    public int updateLinerpr(Linerpr linerpr)
    {
        return linerprMapper.updateLinerpr(linerpr);
    }

    /**
     * 批量删除线性回归预测
     * 
     * @param sc1s 需要删除的线性回归预测主键
     * @return 结果
     */
    @Override
    public int deleteLinerprBySc1s(String sc1s)
    {
        return linerprMapper.deleteLinerprBySc1s(Convert.toStrArray(sc1s));
    }

    /**
     * 删除线性回归预测信息
     * 
     * @param sc1 线性回归预测主键
     * @return 结果
     */
    @Override
    public int deleteLinerprBySc1(Integer sc1)
    {
        return linerprMapper.deleteLinerprBySc1(sc1);
    }
}
