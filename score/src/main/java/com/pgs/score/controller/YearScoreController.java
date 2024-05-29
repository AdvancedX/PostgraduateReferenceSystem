package com.pgs.score.controller;

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
import com.pgs.score.domain.YearScore;
import com.pgs.score.service.IYearScoreService;
import com.pgs.common.core.controller.BaseController;
import com.pgs.common.core.domain.AjaxResult;
import com.pgs.common.utils.poi.ExcelUtil;
import com.pgs.common.core.page.TableDataInfo;

/**
 * 历年分数Controller
 * 
 * @author 许哲睿
 * @date 2023-12-30
 */
@Controller
@RequestMapping("/score/score")
public class YearScoreController extends BaseController
{
    private String prefix = "score/score";

    @Autowired
    private IYearScoreService yearScoreService;

    @RequiresPermissions("score:score:view")
    @GetMapping()
    public String score()
    {
        return prefix + "/score";
    }

    /**
     * 查询历年分数列表
     */
    @RequiresPermissions("score:score:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(YearScore yearScore)
    {
        startPage();
        List<YearScore> list = yearScoreService.selectYearScoreList(yearScore);
        return getDataTable(list);
    }

    /**
     * 导出历年分数列表
     */
    @RequiresPermissions("score:score:export")
    @Log(title = "历年分数", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(YearScore yearScore)
    {
        List<YearScore> list = yearScoreService.selectYearScoreList(yearScore);
        ExcelUtil<YearScore> util = new ExcelUtil<YearScore>(YearScore.class);
        return util.exportExcel(list, "历年分数数据");
    }

    /**
     * 新增历年分数
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存历年分数
     */
    @RequiresPermissions("score:score:add")
    @Log(title = "历年分数", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(YearScore yearScore)
    {
        return toAjax(yearScoreService.insertYearScore(yearScore));
    }

    /**
     * 修改历年分数
     */
    @RequiresPermissions("score:score:edit")
    @GetMapping("/edit/{year}")
    public String edit(@PathVariable("year") String year, ModelMap mmap)
    {
        YearScore yearScore = yearScoreService.selectYearScoreByYear(year);
        mmap.put("yearScore", yearScore);
        return prefix + "/edit";
    }

    /**
     * 修改保存历年分数
     */
    @RequiresPermissions("score:score:edit")
    @Log(title = "历年分数", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(YearScore yearScore)
    {
        return toAjax(yearScoreService.updateYearScore(yearScore));
    }

    /**
     * 删除历年分数
     */
    @RequiresPermissions("score:score:remove")
    @Log(title = "历年分数", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(yearScoreService.deleteYearScoreByYears(ids));
    }
}
