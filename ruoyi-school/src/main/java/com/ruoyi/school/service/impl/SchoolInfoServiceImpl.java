package com.ruoyi.school.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.school.mapper.SchoolInfoMapper;
import com.ruoyi.school.domain.SchoolInfo;
import com.ruoyi.school.service.ISchoolInfoService;
import com.ruoyi.common.core.text.Convert;

/**
 * 院校信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-29
 */
@Service
public class SchoolInfoServiceImpl implements ISchoolInfoService 
{
    @Autowired
    private SchoolInfoMapper schoolInfoMapper;

    /**
     * 查询院校信息
     * 
     * @param id 院校信息主键
     * @return 院校信息
     */
    @Override
    public SchoolInfo selectSchoolInfoById(String id)
    {
        return schoolInfoMapper.selectSchoolInfoById(id);
    }

    /**
     * 查询院校信息列表
     * 
     * @param schoolInfo 院校信息
     * @return 院校信息
     */
    @Override
    public List<SchoolInfo> selectSchoolInfoList(SchoolInfo schoolInfo)
    {
        return schoolInfoMapper.selectSchoolInfoList(schoolInfo);
    }

    /**
     * 新增院校信息
     * 
     * @param schoolInfo 院校信息
     * @return 结果
     */
    @Override
    public int insertSchoolInfo(SchoolInfo schoolInfo)
    {
        return schoolInfoMapper.insertSchoolInfo(schoolInfo);
    }

    /**
     * 修改院校信息
     * 
     * @param schoolInfo 院校信息
     * @return 结果
     */
    @Override
    public int updateSchoolInfo(SchoolInfo schoolInfo)
    {
        return schoolInfoMapper.updateSchoolInfo(schoolInfo);
    }

    /**
     * 批量删除院校信息
     * 
     * @param ids 需要删除的院校信息主键
     * @return 结果
     */
    @Override
    public int deleteSchoolInfoByIds(String ids)
    {
        return schoolInfoMapper.deleteSchoolInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除院校信息信息
     * 
     * @param id 院校信息主键
     * @return 结果
     */
    @Override
    public int deleteSchoolInfoById(String id)
    {
        return schoolInfoMapper.deleteSchoolInfoById(id);
    }
}
