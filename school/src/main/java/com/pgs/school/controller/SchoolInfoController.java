package com.pgs.school.controller;

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
import com.pgs.common.annotation.Log;
import com.pgs.common.enums.BusinessType;
import com.pgs.school.domain.SchoolInfo;
import com.pgs.school.service.ISchoolInfoService;
import com.pgs.common.core.controller.BaseController;
import com.pgs.common.core.domain.AjaxResult;
import com.pgs.common.utils.poi.ExcelUtil;
import com.pgs.common.core.page.TableDataInfo;

/**
 * 院校信息Controller
 * 
 * @author 许哲睿
 * @date 2024-06-23
 */
@Controller
@RequestMapping("/school/schoolinfo")
public class SchoolInfoController extends BaseController
{
    private String prefix = "school/schoolinfo";

    @Autowired
    private ISchoolInfoService schoolInfoService;

    @RequiresPermissions("school:schoolinfo:view")
    @GetMapping()
    public String schoolinfo()
    {
        return prefix + "/schoolinfo";
    }

    /**
     * 查询院校信息列表
     */
    @RequiresPermissions("school:schoolinfo:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SchoolInfo schoolInfo)
    {
        startPage();
        List<SchoolInfo> list = schoolInfoService.selectSchoolInfoList(schoolInfo);
        return getDataTable(list);
    }

    /**
     * 导出院校信息列表
     */
    @RequiresPermissions("school:schoolinfo:export")
    @Log(title = "院校信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SchoolInfo schoolInfo)
    {
        List<SchoolInfo> list = schoolInfoService.selectSchoolInfoList(schoolInfo);
        ExcelUtil<SchoolInfo> util = new ExcelUtil<SchoolInfo>(SchoolInfo.class);
        return util.exportExcel(list, "院校信息数据");
    }

    /**
     * 新增院校信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存院校信息
     */
    @RequiresPermissions("school:schoolinfo:add")
    @Log(title = "院校信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SchoolInfo schoolInfo)
    {
        return toAjax(schoolInfoService.insertSchoolInfo(schoolInfo));
    }

    /**
     * 修改院校信息
     */
    @RequiresPermissions("school:schoolinfo:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        SchoolInfo schoolInfo = schoolInfoService.selectSchoolInfoById(id);
        mmap.put("schoolInfo", schoolInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存院校信息
     */
    @RequiresPermissions("school:schoolinfo:edit")
    @Log(title = "院校信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SchoolInfo schoolInfo)
    {
        return toAjax(schoolInfoService.updateSchoolInfo(schoolInfo));
    }

    /**
     * 删除院校信息
     */
    @RequiresPermissions("school:schoolinfo:remove")
    @Log(title = "院校信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(schoolInfoService.deleteSchoolInfoByIds(ids));
    }
}
