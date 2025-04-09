package com.pgs.score_2022.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pgs.score_2022.mapper.MajorScore2022Mapper;
import com.pgs.score_2022.domain.MajorScore2022;
import com.pgs.score_2022.service.IMajorScore2022Service;
import com.pgs.common.core.text.Convert;

/**
 * 2022年详细信息Service业务层处理
 * 
 * @author 许哲睿
 * @date 2025-04-08
 */
@Service
public class MajorScore2022ServiceImpl implements IMajorScore2022Service 
{
    @Autowired
    private MajorScore2022Mapper majorScore2022Mapper;

    /**
     * 查询2022年详细信息
     * 
     * @param id 2022年详细信息主键
     * @return 2022年详细信息
     */
    @Override
    public MajorScore2022 selectMajorScore2022ById(Long id)
    {
        return majorScore2022Mapper.selectMajorScore2022ById(id);
    }

    /**
     * 查询2022年详细信息列表
     * 
     * @param majorScore2022 2022年详细信息
     * @return 2022年详细信息
     */
    @Override
    public List<MajorScore2022> selectMajorScore2022List(MajorScore2022 majorScore2022)
    {
        return majorScore2022Mapper.selectMajorScore2022List(majorScore2022);
    }

    /**
     * 新增2022年详细信息
     * 
     * @param majorScore2022 2022年详细信息
     * @return 结果
     */
    @Override
    public int insertMajorScore2022(MajorScore2022 majorScore2022)
    {
        return majorScore2022Mapper.insertMajorScore2022(majorScore2022);
    }

    /**
     * 修改2022年详细信息
     * 
     * @param majorScore2022 2022年详细信息
     * @return 结果
     */
    @Override
    public int updateMajorScore2022(MajorScore2022 majorScore2022)
    {
        return majorScore2022Mapper.updateMajorScore2022(majorScore2022);
    }

    /**
     * 批量删除2022年详细信息
     * 
     * @param ids 需要删除的2022年详细信息主键
     * @return 结果
     */
    @Override
    public int deleteMajorScore2022ByIds(String ids)
    {
        return majorScore2022Mapper.deleteMajorScore2022ByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除2022年详细信息信息
     * 
     * @param id 2022年详细信息主键
     * @return 结果
     */
    @Override
    public int deleteMajorScore2022ById(Long id)
    {
        return majorScore2022Mapper.deleteMajorScore2022ById(id);
    }
}
