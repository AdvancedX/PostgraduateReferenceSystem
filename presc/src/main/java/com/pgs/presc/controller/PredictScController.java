package com.pgs.presc.controller;

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
import com.pgs.presc.domain.PredictSc;
import com.pgs.presc.service.IPredictScService;
import com.pgs.common.core.controller.BaseController;
import com.pgs.common.core.domain.AjaxResult;
import com.pgs.common.utils.poi.ExcelUtil;
import com.pgs.common.core.page.TableDataInfo;

/**
 * 分数预测Controller
 * 
 * @author 许哲睿
 * @date 2024-04-24
 */
@Controller
@RequestMapping("/presc/presc")
public class PredictScController extends BaseController
{
    private String prefix = "presc/presc";

    @Autowired
    private IPredictScService predictScService;

    @RequiresPermissions("presc:presc:view")
    @GetMapping()
    public String presc()
    {
        return prefix + "/presc";
    }

    /**
     * 查询分数预测列表
     */
    @RequiresPermissions("presc:presc:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(PredictSc predictSc)
    {
        startPage();
        List<PredictSc> list = predictScService.selectPredictScList(predictSc);
        return getDataTable(list);
    }

    /**
     * 导出分数预测列表
     */
    @RequiresPermissions("presc:presc:export")
    @Log(title = "分数预测", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(PredictSc predictSc)
    {
        List<PredictSc> list = predictScService.selectPredictScList(predictSc);
        ExcelUtil<PredictSc> util = new ExcelUtil<PredictSc>(PredictSc.class);
        return util.exportExcel(list, "分数预测数据");
    }

    /**
     * 新增分数预测
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存分数预测
     */
    @RequiresPermissions("presc:presc:add")
    @Log(title = "分数预测", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(PredictSc predictSc)
    {
        return toAjax(predictScService.insertPredictSc(predictSc));
    }

    /**
     * 修改分数预测
     */
    @RequiresPermissions("presc:presc:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        PredictSc predictSc = predictScService.selectPredictScById(id);
        mmap.put("predictSc", predictSc);
        return prefix + "/edit";
    }

    /**
     * 修改保存分数预测
     */
    @RequiresPermissions("presc:presc:edit")
    @Log(title = "分数预测", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(PredictSc predictSc)
    {
        return toAjax(predictScService.updatePredictSc(predictSc));
    }

    /**
     * 删除分数预测
     */
    @RequiresPermissions("presc:presc:remove")
    @Log(title = "分数预测", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(predictScService.deletePredictScByIds(ids));
    }
}
