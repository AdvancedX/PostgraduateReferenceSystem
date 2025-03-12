package com.pgs.score.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pgs.score.mapper.YearScoreMapper;
import com.pgs.score.domain.YearScore;
import com.pgs.score.service.IYearScoreService;
import com.pgs.common.core.text.Convert;

/**
 * 历年分数Service业务层处理
 * 
 * @author 许哲睿
 * @date 2024-06-23
 */
@Service
public class YearScoreServiceImpl implements IYearScoreService 
{
    @Autowired
    private YearScoreMapper yearScoreMapper;

    /**
     * 查询历年分数
     * 
     * @param id 历年分数主键
     * @return 历年分数
     */
    @Override
    public YearScore selectYearScoreById(Long id)
    {
        return yearScoreMapper.selectYearScoreById(id);
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
     * @param ids 需要删除的历年分数主键
     * @return 结果
     */
    @Override
    public int deleteYearScoreByIds(String ids)
    {
        return yearScoreMapper.deleteYearScoreByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除历年分数信息
     * 
     * @param id 历年分数主键
     * @return 结果
     */
    @Override
    public int deleteYearScoreById(Long id)
    {
        return yearScoreMapper.deleteYearScoreById(id);
    }
}
