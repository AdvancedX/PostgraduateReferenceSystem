package com.pgs.blogs.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pgs.blogs.mapper.BlogsMapper;
import com.pgs.blogs.domain.Blogs;
import com.pgs.blogs.service.IBlogsService;
import com.pgs.common.core.text.Convert;

/**
 * 博客论坛Service业务层处理
 * 
 * @author xzr
 * @date 2025-03-10
 */
@Service
public class BlogsServiceImpl implements IBlogsService 
{
    @Autowired
    private BlogsMapper blogsMapper;

    /**
     * 查询博客论坛
     * 
     * @param id 博客论坛主键
     * @return 博客论坛
     */
    @Override
    public Blogs selectBlogsById(Long id)
    {
        return blogsMapper.selectBlogsById(id);
    }

    /**
     * 查询博客论坛列表
     * 
     * @param blogs 博客论坛
     * @return 博客论坛
     */
    @Override
    public List<Blogs> selectBlogsList(Blogs blogs)
    {
        return blogsMapper.selectBlogsList(blogs);
    }

    /**
     * 新增博客论坛
     * 
     * @param blogs 博客论坛
     * @return 结果
     */
    @Override
    public int insertBlogs(Blogs blogs)
    {
        return blogsMapper.insertBlogs(blogs);
    }

    /**
     * 修改博客论坛
     * 
     * @param blogs 博客论坛
     * @return 结果
     */
    @Override
    public int updateBlogs(Blogs blogs)
    {
        return blogsMapper.updateBlogs(blogs);
    }

    /**
     * 批量删除博客论坛
     * 
     * @param ids 需要删除的博客论坛主键
     * @return 结果
     */
    @Override
    public int deleteBlogsByIds(String ids)
    {
        return blogsMapper.deleteBlogsByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除博客论坛信息
     * 
     * @param id 博客论坛主键
     * @return 结果
     */
    @Override
    public int deleteBlogsById(Long id)
    {
        return blogsMapper.deleteBlogsById(id);
    }
}
