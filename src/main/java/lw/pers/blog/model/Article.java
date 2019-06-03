package lw.pers.blog.model;



//@JsonInclude
public class Article {
    //文章id
    private Integer id;
    //文章名成
    private String articleTitle;
    //文章内容
    private String articleContent;
    //文章标签
    private String articleTags;
    //文章类型
    private String articleType;
    //文章发布日期
    private String createTime;
    //文章最后一次修改时间
    private String updateTime;
    //文章摘要
    private String summary;
    //文章分类
    private Integer customTypeId;
    //用户id
    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public String getArticleTags() {
        return articleTags;
    }

    public void setArticleTags(String articleTags) {
        this.articleTags = articleTags;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getCustomTypeId() {
        return customTypeId;
    }

    public void setCustomTypeId(Integer customTypeId) {
        this.customTypeId = customTypeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
