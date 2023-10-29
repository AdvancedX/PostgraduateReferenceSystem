package com.ruoyi.major.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.major.domain.MajorInfo;
import com.ruoyi.major.service.IMajorInfoService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 专业信息Controller
 * 
 * @author 许哲睿
 * @date 2023-10-29
 */
@Controller
@RequestMapping("/major/majorinfo")
public class MajorInfoController extends BaseController
{
    private String prefix = "major/majorinfo";

    @Autowired
    private IMajorInfoService majorInfoService;

    @RequiresPermissions("major:majorinfo:view")
    @GetMapping()
    public String majorinfo()
    {
        return prefix + "/majorinfo";
    }

    /**
     * 查询专业信息列表
     */
    @RequiresPermissions("major:majorinfo:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(MajorInfo majorInfo)
    {
        startPage();
        List<MajorInfo> list = majorInfoService.selectMajorInfoList(majorInfo);
        return getDataTable(list);
    }

    /**
     * 导出专业信息列表
     */
    @RequiresPermissions("major:majorinfo:export")
    @Log(title = "专业信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(MajorInfo majorInfo)
    {
        List<MajorInfo> list = majorInfoService.selectMajorInfoList(majorInfo);
        ExcelUtil<MajorInfo> util = new ExcelUtil<MajorInfo>(MajorInfo.class);
        return util.exportExcel(list, "专业信息数据");
    }

    /**
     * 新增专业信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存专业信息
     */
    @RequiresPermissions("major:majorinfo:add")
    @Log(title = "专业信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(MajorInfo majorInfo)
    {
        return toAjax(majorInfoService.insertMajorInfo(majorInfo));
    }

    /**
     * 修改专业信息
     */
    @RequiresPermissions("major:majorinfo:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap)
    {
        MajorInfo majorInfo = majorInfoService.selectMajorInfoById(id);
        mmap.put("majorInfo", majorInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存专业信息
     */
    @RequiresPermissions("major:majorinfo:edit")
    @Log(title = "专业信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MajorInfo majorInfo)
    {
        return toAjax(majorInfoService.updateMajorInfo(majorInfo));
    }

    /**
     * 删除专业信息
     */
    @RequiresPermissions("major:majorinfo:remove")
    @Log(title = "专业信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(majorInfoService.deleteMajorInfoByIds(ids));
    }
}
