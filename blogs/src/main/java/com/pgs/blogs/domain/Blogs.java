package com.pgs.blogs.domain;

import com.pgs.common.annotation.Excel;
import com.pgs.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 博客论坛对象 blogs
 * 
 * @author xzr
 * @date 2025-03-10
 */
public class Blogs extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    /** 主键 */
    private Long id;

    /** 用户名称 */
    @Excel(name = "用户名称")
    private String username;

    /** 文章标题 */
    @Excel(name = "文章标题")
    private String title;

    /** 正文 */
    @Excel(name = "正文")
    private String content;

    /** 点赞 */
    @Excel(name = "点赞")
    private String likes;

    /** 发布时间 */
    @Excel(name = "发布时间")
    private String createAt;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getUsername()
    {
        return username;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setLikes(String likes) 
    {
        this.likes = likes;
    }

    public String getLikes() 
    {
        return likes;
    }
    public void setCreateAt(String createAt) 
    {
        this.createAt = createAt;
    }

    public String getCreateAt() 
    {
        return createAt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("username", getUsername())
            .append("title", getTitle())
            .append("content", getContent())
            .append("likes", getLikes())
            .append("createAt", getCreateAt())
            .toString();
    }
}
