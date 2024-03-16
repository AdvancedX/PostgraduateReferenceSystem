package com.ruoyi.linerpr.mapper;

import java.util.List;
import com.ruoyi.linerpr.domain.Linerpr;

/**
 * 线性回归预测Mapper接口
 * 
 * @author 许哲睿
 * @date 2024-03-16
 */
public interface LinerprMapper 
{
    /**
     * 查询线性回归预测
     * 
     * @param sc1 线性回归预测主键
     * @return 线性回归预测
     */
    public Linerpr selectLinerprBySc1(Integer sc1);

    /**
     * 查询线性回归预测列表
     * 
     * @param linerpr 线性回归预测
     * @return 线性回归预测集合
     */
    public List<Linerpr> selectLinerprList(Linerpr linerpr);

    /**
     * 新增线性回归预测
     * 
     * @param linerpr 线性回归预测
     * @return 结果
     */
    public int insertLinerpr(Linerpr linerpr);

    /**
     * 修改线性回归预测
     * 
     * @param linerpr 线性回归预测
     * @return 结果
     */
    public int updateLinerpr(Linerpr linerpr);

    /**
     * 删除线性回归预测
     * 
     * @param sc1 线性回归预测主键
     * @return 结果
     */
    public int deleteLinerprBySc1(Integer sc1);

    /**
     * 批量删除线性回归预测
     * 
     * @param sc1s 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteLinerprBySc1s(String[] sc1s);
}
