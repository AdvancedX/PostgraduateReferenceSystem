package com.pgs.score.service;

import java.util.List;
import com.pgs.score.domain.YearScore;

/**
 * 历年分数Service接口
 * 
 * @author 许哲睿
 * @date 2024-06-23
 */
public interface IYearScoreService 
{
    /**
     * 查询历年分数
     * 
     * @param id 历年分数主键
     * @return 历年分数
     */
    public YearScore selectYearScoreById(Long id);

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
     * @param ids 需要删除的历年分数主键集合
     * @return 结果
     */
    public int deleteYearScoreByIds(String ids);

    /**
     * 删除历年分数信息
     * 
     * @param id 历年分数主键
     * @return 结果
     */
    public int deleteYearScoreById(Long id);
}
