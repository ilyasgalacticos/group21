package com.bitlab.project.entities;

import java.util.Date;

public class Blogs {

    private Long id;
    private Users user;
    private String title;
    private String shortContent;
    private String content;
    private Date postDate;

    public Blogs(Long id, Users user, String title, String shortContent, String content, Date postDate) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.shortContent = shortContent;
        this.content = content;
        this.postDate = postDate;
    }
    public Blogs(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortContent() {
        return shortContent;
    }

    public void setShortContent(String shortContent) {
        this.shortContent = shortContent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }
}
