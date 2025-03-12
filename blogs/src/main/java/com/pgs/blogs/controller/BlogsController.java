package com.pgs.blogs.controller;

import java.util.List;

import com.pgs.common.utils.DateUtils;
import com.pgs.common.utils.ShiroUtils;
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
import com.pgs.blogs.domain.Blogs;
import com.pgs.blogs.service.IBlogsService;
import com.pgs.common.core.controller.BaseController;
import com.pgs.common.core.domain.AjaxResult;
import com.pgs.common.utils.poi.ExcelUtil;
import com.pgs.common.core.page.TableDataInfo;

/**
 * 博客论坛Controller
 * 
 * @author xzr
 * @date 2025-03-10
 */
@Controller
@RequestMapping("/blogs/blogs")
public class BlogsController extends BaseController
{
    private String prefix = "blogs/blogs";

    @Autowired
    private IBlogsService blogsService;

    @RequiresPermissions("blogs:blogs:view")
    @GetMapping()
    public String blogs()
    {
        return prefix + "/blogs";
    }

    /**
     * 查询博客论坛列表
     */
    @RequiresPermissions("blogs:blogs:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Blogs blogs)
    {
        startPage();
        List<Blogs> list = blogsService.selectBlogsList(blogs);
        return getDataTable(list);
    }

    /**
     * 导出博客论坛列表
     */
    @RequiresPermissions("blogs:blogs:export")
    @Log(title = "博客论坛", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Blogs blogs)
    {
        List<Blogs> list = blogsService.selectBlogsList(blogs);
        ExcelUtil<Blogs> util = new ExcelUtil<Blogs>(Blogs.class);
        return util.exportExcel(list, "博客论坛数据");
    }

    /**
     * 新增博客论坛
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存博客论坛
     */
    @RequiresPermissions("blogs:blogs:add")
    @Log(title = "博客论坛", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Blogs blogs) {
        blogs.setUsername(ShiroUtils.getLoginName());
        blogs.setCreateAt(DateUtils.getTime());

        return toAjax(blogsService.insertBlogs(blogs));  // 保存数据
    }

    /**
     * 修改博客论坛
     */
    @RequiresPermissions("blogs:blogs:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        Blogs blogs = blogsService.selectBlogsById(id);
        mmap.put("blogs", blogs);
        return prefix + "/edit";
    }

    /**
     * 修改保存博客论坛
     */
    @RequiresPermissions("blogs:blogs:edit")
    @Log(title = "博客论坛", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Blogs blogs)
    {
        return toAjax(blogsService.updateBlogs(blogs));
    }

    /**
     * 删除博客论坛
     */
    @RequiresPermissions("blogs:blogs:remove")
    @Log(title = "博客论坛", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(blogsService.deleteBlogsByIds(ids));
    }
}
