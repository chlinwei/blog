package lw.pers.blog.model;

/**
 * 收藏夹表和资源表是多对多的关系
 */
public class Collect {

    private Integer id;
    //收藏夹名称
    private String name;
    //收藏夹简介
    private String brief;
    //用户id
    private int userId;
    //创建事件
    private String createTime;

    //是否为默认收藏夹,1:表示时默认收藏夹,用户删除不了,0表示普通收藏夹
    private int isDefault;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    @Override
    public String toString() {
        return "Collect{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brief='" + brief + '\'' +
                ", userId=" + userId +
                ", createTime='" + createTime + '\'' +
                ", isDefault=" + isDefault +
                '}';
    }
}
