package com.pgs.school.service;

import java.util.List;
import com.pgs.school.domain.SchoolInfo;

/**
 * 院校信息Service接口
 * 
 * @author 许哲睿
 * @date 2024-06-23
 */
public interface ISchoolInfoService 
{
    /**
     * 查询院校信息
     * 
     * @param id 院校信息主键
     * @return 院校信息
     */
    public SchoolInfo selectSchoolInfoById(String id);

    /**
     * 查询院校信息列表
     * 
     * @param schoolInfo 院校信息
     * @return 院校信息集合
     */
    public List<SchoolInfo> selectSchoolInfoList(SchoolInfo schoolInfo);

    /**
     * 新增院校信息
     * 
     * @param schoolInfo 院校信息
     * @return 结果
     */
    public int insertSchoolInfo(SchoolInfo schoolInfo);

    /**
     * 修改院校信息
     * 
     * @param schoolInfo 院校信息
     * @return 结果
     */
    public int updateSchoolInfo(SchoolInfo schoolInfo);

    /**
     * 批量删除院校信息
     * 
     * @param ids 需要删除的院校信息主键集合
     * @return 结果
     */
    public int deleteSchoolInfoByIds(String ids);

    /**
     * 删除院校信息信息
     * 
     * @param id 院校信息主键
     * @return 结果
     */
    public int deleteSchoolInfoById(String id);
}
