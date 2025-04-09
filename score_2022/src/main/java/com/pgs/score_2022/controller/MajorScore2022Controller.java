package com.pgs.score_2022.controller;

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
import com.pgs.score_2022.domain.MajorScore2022;
import com.pgs.score_2022.service.IMajorScore2022Service;
import com.pgs.common.core.controller.BaseController;
import com.pgs.common.core.domain.AjaxResult;
import com.pgs.common.utils.poi.ExcelUtil;
import com.pgs.common.core.page.TableDataInfo;

/**
 * 2022年详细信息Controller
 * 
 * @author 许哲睿
 * @date 2025-04-08
 */
@Controller
@RequestMapping("/score_2022/score_2022")
public class MajorScore2022Controller extends BaseController
{
    private String prefix = "score_2022/score_2022";

    @Autowired
    private IMajorScore2022Service majorScore2022Service;

    @RequiresPermissions("score_2022:score_2022:view")
    @GetMapping()
    public String score_2022()
    {
        return prefix + "/score_2022";
    }

    /**
     * 查询2022年详细信息列表
     */
    @RequiresPermissions("score_2022:score_2022:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(MajorScore2022 majorScore2022)
    {
        startPage();
        List<MajorScore2022> list = majorScore2022Service.selectMajorScore2022List(majorScore2022);
        return getDataTable(list);
    }

    /**
     * 导出2022年详细信息列表
     */
    @RequiresPermissions("score_2022:score_2022:export")
    @Log(title = "2022年详细信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(MajorScore2022 majorScore2022)
    {
        List<MajorScore2022> list = majorScore2022Service.selectMajorScore2022List(majorScore2022);
        ExcelUtil<MajorScore2022> util = new ExcelUtil<MajorScore2022>(MajorScore2022.class);
        return util.exportExcel(list, "2022年详细信息数据");
    }

    /**
     * 新增2022年详细信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存2022年详细信息
     */
    @RequiresPermissions("score_2022:score_2022:add")
    @Log(title = "2022年详细信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(MajorScore2022 majorScore2022)
    {
        return toAjax(majorScore2022Service.insertMajorScore2022(majorScore2022));
    }

    /**
     * 修改2022年详细信息
     */
    @RequiresPermissions("score_2022:score_2022:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        MajorScore2022 majorScore2022 = majorScore2022Service.selectMajorScore2022ById(id);
        mmap.put("majorScore2022", majorScore2022);
        return prefix + "/edit";
    }

    /**
     * 修改保存2022年详细信息
     */
    @RequiresPermissions("score_2022:score_2022:edit")
    @Log(title = "2022年详细信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MajorScore2022 majorScore2022)
    {
        return toAjax(majorScore2022Service.updateMajorScore2022(majorScore2022));
    }

    /**
     * 删除2022年详细信息
     */
    @RequiresPermissions("score_2022:score_2022:remove")
    @Log(title = "2022年详细信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(majorScore2022Service.deleteMajorScore2022ByIds(ids));
    }
}
