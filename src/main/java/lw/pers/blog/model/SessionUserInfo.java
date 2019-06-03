package lw.pers.blog.model;

/**
 * 这个类的对象是用来在登录后,保存在session里的
 */
public class SessionUserInfo {

    private Integer id;
    private String userName;
    //用户头像链接
    private String avatarUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public String toString() {
        return "SessionUserInfo{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}
