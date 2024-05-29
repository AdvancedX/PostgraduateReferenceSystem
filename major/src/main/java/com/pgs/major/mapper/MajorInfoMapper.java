package com.pgs.major.mapper;

import java.util.List;
import com.pgs.major.domain.MajorInfo;

/**
 * 专业信息Mapper接口
 * 
 * @author 许哲睿
 * @date 2023-12-24
 */
public interface MajorInfoMapper 
{
    /**
     * 查询专业信息
     * 
     * @param id 专业信息主键
     * @return 专业信息
     */
    public MajorInfo selectMajorInfoById(String id);

    /**
     * 查询专业信息列表
     * 
     * @param majorInfo 专业信息
     * @return 专业信息集合
     */
    public List<MajorInfo> selectMajorInfoList(MajorInfo majorInfo);

    /**
     * 新增专业信息
     * 
     * @param majorInfo 专业信息
     * @return 结果
     */
    public int insertMajorInfo(MajorInfo majorInfo);

    /**
     * 修改专业信息
     * 
     * @param majorInfo 专业信息
     * @return 结果
     */
    public int updateMajorInfo(MajorInfo majorInfo);

    /**
     * 删除专业信息
     * 
     * @param id 专业信息主键
     * @return 结果
     */
    public int deleteMajorInfoById(String id);

    /**
     * 批量删除专业信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMajorInfoByIds(String[] ids);
}
