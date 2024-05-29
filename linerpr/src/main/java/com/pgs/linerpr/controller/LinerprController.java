package com.pgs.linerpr.controller;

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
import com.pgs.linerpr.domain.Linerpr;
import com.pgs.linerpr.service.ILinerprService;
import com.pgs.common.core.controller.BaseController;
import com.pgs.common.core.domain.AjaxResult;
import com.pgs.common.utils.poi.ExcelUtil;
import com.pgs.common.core.page.TableDataInfo;

/**
 * 线性回归预测Controller
 * 
 * @author 许哲睿
 * @date 2024-03-16
 */
@Controller
@RequestMapping("/linerpr/linerpr")
public class LinerprController extends BaseController
{
    private String prefix = "linerpr/linerpr";

    @Autowired
    private ILinerprService linerprService;

    @RequiresPermissions("linerpr:linerpr:view")
    @GetMapping()
    public String linerpr()
    {
        return prefix + "/linerpr";
    }

    /**
     * 查询线性回归预测列表
     */
    @RequiresPermissions("linerpr:linerpr:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Linerpr linerpr)
    {
        startPage();
        List<Linerpr> list = linerprService.selectLinerprList(linerpr);
        return getDataTable(list);
    }

    /**
     * 导出线性回归预测列表
     */
    @RequiresPermissions("linerpr:linerpr:export")
    @Log(title = "线性回归预测", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Linerpr linerpr)
    {
        List<Linerpr> list = linerprService.selectLinerprList(linerpr);
        ExcelUtil<Linerpr> util = new ExcelUtil<Linerpr>(Linerpr.class);
        return util.exportExcel(list, "线性回归预测数据");
    }

    /**
     * 新增线性回归预测
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存线性回归预测
     */
    @RequiresPermissions("linerpr:linerpr:add")
    @Log(title = "线性回归预测", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Linerpr predictSc)
    {
        int PredictValue=predictSc.PredictValue(predictSc.getSc1(), predictSc.getSc2(), predictSc.getSc3(), predictSc.getSc4(), predictSc.getSc5());
        predictSc.setRe(PredictValue);
        return toAjax(linerprService.insertLinerpr(predictSc));
    }

    /**
     * 修改线性回归预测
     */
    @RequiresPermissions("linerpr:linerpr:edit")
    @GetMapping("/edit/{sc1}")
    public String edit(@PathVariable("sc1") Integer sc1, ModelMap mmap)
    {
        Linerpr linerpr = linerprService.selectLinerprBySc1(sc1);
        mmap.put("linerpr", linerpr);
        return prefix + "/edit";
    }

    /**
     * 修改保存线性回归预测
     */
    @RequiresPermissions("linerpr:linerpr:edit")
    @Log(title = "线性回归预测", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Linerpr linerpr)
    {
        return toAjax(linerprService.updateLinerpr(linerpr));
    }

    /**
     * 删除线性回归预测
     */
    @RequiresPermissions("linerpr:linerpr:remove")
    @Log(title = "线性回归预测", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(linerprService.deleteLinerprBySc1s(ids));
    }
}
