package com.ruoyi.score.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.score.mapper.YearScoreMapper;
import com.ruoyi.score.domain.YearScore;
import com.ruoyi.score.service.IYearScoreService;
import com.ruoyi.common.core.text.Convert;

/**
 * 历年分数Service业务层处理
 * 
 * @author 许哲睿
 * @date 2023-12-30
 */
@Service
public class YearScoreServiceImpl implements IYearScoreService 
{
    @Autowired
    private YearScoreMapper yearScoreMapper;

    /**
     * 查询历年分数
     * 
     * @param year 历年分数主键
     * @return 历年分数
     */
    @Override
    public YearScore selectYearScoreByYear(String year)
    {
        return yearScoreMapper.selectYearScoreByYear(year);
    }

    /**
     * 查询历年分数列表
     * 
     * @param yearScore 历年分数
     * @return 历年分数
     */
    @Override
    public List<YearScore> selectYearScoreList(YearScore yearScore)
    {
        return yearScoreMapper.selectYearScoreList(yearScore);
    }

    /**
     * 新增历年分数
     * 
     * @param yearScore 历年分数
     * @return 结果
     */
    @Override
    public int insertYearScore(YearScore yearScore)
    {
        return yearScoreMapper.insertYearScore(yearScore);
    }

    /**
     * 修改历年分数
     * 
     * @param yearScore 历年分数
     * @return 结果
     */
    @Override
    public int updateYearScore(YearScore yearScore)
    {
        return yearScoreMapper.updateYearScore(yearScore);
    }

    /**
     * 批量删除历年分数
     * 
     * @param years 需要删除的历年分数主键
     * @return 结果
     */
    @Override
    public int deleteYearScoreByYears(String years)
    {
        return yearScoreMapper.deleteYearScoreByYears(Convert.toStrArray(years));
    }

    /**
     * 删除历年分数信息
     * 
     * @param year 历年分数主键
     * @return 结果
     */
    @Override
    public int deleteYearScoreByYear(String year)
    {
        return yearScoreMapper.deleteYearScoreByYear(year);
    }
}
