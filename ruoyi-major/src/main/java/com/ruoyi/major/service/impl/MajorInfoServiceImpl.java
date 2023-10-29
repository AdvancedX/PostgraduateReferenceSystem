package com.ruoyi.major.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.major.mapper.MajorInfoMapper;
import com.ruoyi.major.domain.MajorInfo;
import com.ruoyi.major.service.IMajorInfoService;
import com.ruoyi.common.core.text.Convert;

/**
 * 专业信息Service业务层处理
 * 
 * @author 许哲睿
 * @date 2023-10-29
 */
@Service
public class MajorInfoServiceImpl implements IMajorInfoService 
{
    @Autowired
    private MajorInfoMapper majorInfoMapper;

    /**
     * 查询专业信息
     * 
     * @param id 专业信息主键
     * @return 专业信息
     */
    @Override
    public MajorInfo selectMajorInfoById(Integer id)
    {
        return majorInfoMapper.selectMajorInfoById(id);
    }

    /**
     * 查询专业信息列表
     * 
     * @param majorInfo 专业信息
     * @return 专业信息
     */
    @Override
    public List<MajorInfo> selectMajorInfoList(MajorInfo majorInfo)
    {
        return majorInfoMapper.selectMajorInfoList(majorInfo);
    }

    /**
     * 新增专业信息
     * 
     * @param majorInfo 专业信息
     * @return 结果
     */
    @Override
    public int insertMajorInfo(MajorInfo majorInfo)
    {
        return majorInfoMapper.insertMajorInfo(majorInfo);
    }

    /**
     * 修改专业信息
     * 
     * @param majorInfo 专业信息
     * @return 结果
     */
    @Override
    public int updateMajorInfo(MajorInfo majorInfo)
    {
        return majorInfoMapper.updateMajorInfo(majorInfo);
    }

    /**
     * 批量删除专业信息
     * 
     * @param ids 需要删除的专业信息主键
     * @return 结果
     */
    @Override
    public int deleteMajorInfoByIds(String ids)
    {
        return majorInfoMapper.deleteMajorInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除专业信息信息
     * 
     * @param id 专业信息主键
     * @return 结果
     */
    @Override
    public int deleteMajorInfoById(Integer id)
    {
        return majorInfoMapper.deleteMajorInfoById(id);
    }
}
