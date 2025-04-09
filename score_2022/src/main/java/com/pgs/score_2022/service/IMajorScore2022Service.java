package com.pgs.score_2022.service;

import java.util.List;
import com.pgs.score_2022.domain.MajorScore2022;

/**
 * 2022年详细信息Service接口
 * 
 * @author 许哲睿
 * @date 2025-04-08
 */
public interface IMajorScore2022Service 
{
    /**
     * 查询2022年详细信息
     * 
     * @param id 2022年详细信息主键
     * @return 2022年详细信息
     */
    public MajorScore2022 selectMajorScore2022ById(Long id);

    /**
     * 查询2022年详细信息列表
     * 
     * @param majorScore2022 2022年详细信息
     * @return 2022年详细信息集合
     */
    public List<MajorScore2022> selectMajorScore2022List(MajorScore2022 majorScore2022);

    /**
     * 新增2022年详细信息
     * 
     * @param majorScore2022 2022年详细信息
     * @return 结果
     */
    public int insertMajorScore2022(MajorScore2022 majorScore2022);

    /**
     * 修改2022年详细信息
     * 
     * @param majorScore2022 2022年详细信息
     * @return 结果
     */
    public int updateMajorScore2022(MajorScore2022 majorScore2022);

    /**
     * 批量删除2022年详细信息
     * 
     * @param ids 需要删除的2022年详细信息主键集合
     * @return 结果
     */
    public int deleteMajorScore2022ByIds(String ids);

    /**
     * 删除2022年详细信息信息
     * 
     * @param id 2022年详细信息主键
     * @return 结果
     */
    public int deleteMajorScore2022ById(Long id);
}
