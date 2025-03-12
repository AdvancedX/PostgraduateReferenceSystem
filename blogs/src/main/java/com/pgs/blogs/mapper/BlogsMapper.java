package com.pgs.blogs.mapper;

import java.util.List;
import com.pgs.blogs.domain.Blogs;

/**
 * 博客论坛Mapper接口
 * 
 * @author xzr
 * @date 2025-03-10
 */
public interface BlogsMapper 
{
    /**
     * 查询博客论坛
     * 
     * @param id 博客论坛主键
     * @return 博客论坛
     */
    public Blogs selectBlogsById(Long id);

    /**
     * 查询博客论坛列表
     * 
     * @param blogs 博客论坛
     * @return 博客论坛集合
     */
    public List<Blogs> selectBlogsList(Blogs blogs);

    /**
     * 新增博客论坛
     * 
     * @param blogs 博客论坛
     * @return 结果
     */
    public int insertBlogs(Blogs blogs);

    /**
     * 修改博客论坛
     * 
     * @param blogs 博客论坛
     * @return 结果
     */
    public int updateBlogs(Blogs blogs);

    /**
     * 删除博客论坛
     * 
     * @param id 博客论坛主键
     * @return 结果
     */
    public int deleteBlogsById(Long id);

    /**
     * 批量删除博客论坛
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBlogsByIds(String[] ids);
}
