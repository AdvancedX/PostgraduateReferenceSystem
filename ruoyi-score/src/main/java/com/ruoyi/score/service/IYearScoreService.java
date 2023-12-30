package com.ruoyi.score.service;

import java.util.List;
import com.ruoyi.score.domain.YearScore;

/**
 * 历年分数Service接口
 * 
 * @author 许哲睿
 * @date 2023-12-30
 */
public interface IYearScoreService 
{
    /**
     * 查询历年分数
     * 
     * @param year 历年分数主键
     * @return 历年分数
     */
    public YearScore selectYearScoreByYear(String year);

    /**
     * 查询历年分数列表
     * 
     * @param yearScore 历年分数
     * @return 历年分数集合
     */
    public List<YearScore> selectYearScoreList(YearScore yearScore);

    /**
     * 新增历年分数
     * 
     * @param yearScore 历年分数
     * @return 结果
     */
    public int insertYearScore(YearScore yearScore);

    /**
     * 修改历年分数
     * 
     * @param yearScore 历年分数
     * @return 结果
     */
    public int updateYearScore(YearScore yearScore);

    /**
     * 批量删除历年分数
     * 
     * @param years 需要删除的历年分数主键集合
     * @return 结果
     */
    public int deleteYearScoreByYears(String years);

    /**
     * 删除历年分数信息
     * 
     * @param year 历年分数主键
     * @return 结果
     */
    public int deleteYearScoreByYear(String year);
}
