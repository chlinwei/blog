package lw.pers.blog.vo;

import lw.pers.blog.model.Article;
import lw.pers.blog.model.CustomType;

public class ArticleShow {
    private LastOrNextArticleVo lastArticleVo;
    private LastOrNextArticleVo nextArticleVo;

    //本文章
    private Article article;

    //文章分类
    private CustomType customType;

    //用户vo
    private UserVo userVo;

    public LastOrNextArticleVo getLastArticleVo() {
        return lastArticleVo;
    }

    public void setLastArticleVo(LastOrNextArticleVo lastArticleVo) {
        this.lastArticleVo = lastArticleVo;
    }

    public LastOrNextArticleVo getNextArticleVo() {
        return nextArticleVo;
    }

    public void setNextArticleVo(LastOrNextArticleVo nextArticleVo) {
        this.nextArticleVo = nextArticleVo;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public CustomType getCustomType() {
        return customType;
    }

    public void setCustomType(CustomType customType) {
        this.customType = customType;
    }

    public UserVo getUserVo() {
        return userVo;
    }

    public void setUserVo(UserVo userVo) {
        this.userVo = userVo;
    }
}
