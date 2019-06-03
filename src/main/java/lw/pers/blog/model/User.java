package lw.pers.blog.model;


import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.Set;

public class User {
    private Integer id;
    @Pattern(regexp = "^(\\w|[\u4E00-\u9FA5])+$",message = "用户名含有非法字符")
    @Length(min = 4,max = 20,message = "用户名长度不能小于4个字符或用户名不能大于20个字符")
    private String userName;

    @Pattern(regexp = "^[^\\s]*$",message = "密码中不能含有空格")
    @Length(min = 6,max=20,message = "密码长度不能小于6字符,不能大于20个字符")
    private String passwd;

    @Pattern(regexp = "^[男女]$",message = "性别格式错误:性别只能为男或者女")
    private String gender;

    //头像的uri
    private String avatarImgUri;
    //邮箱
    @Email(message = "邮箱格式不正确")
    private String email;
    //电话
    private String phone;
    //个人简介
    private String personalBrief;

    //角色
    private Set<Role> roles;

    //生日
    private String birthday;

    //最近登录时间
    private String lastLoginTime;

    //个性签名
    private String sign;

    //创建时间
    private String createTime;
    public Integer getId() {
        return id;
    }

    public User() {
    }

    public User(String userName, String passwd) {
        this.userName = userName;
        this.passwd = passwd;
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

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatarImgUri() {
        return avatarImgUri;
    }

    public void setAvatarImgUri(String avatarImgUri) {
        this.avatarImgUri = avatarImgUri;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPersonalBrief() {
        return personalBrief;
    }

    public void setPersonalBrief(String personalBrief) {
        this.personalBrief = personalBrief;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", passwd='" + passwd + '\'' +
                ", gender='" + gender + '\'' +
                ", avatarImgUri='" + avatarImgUri + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", personalBrief='" + personalBrief + '\'' +
                ", roles=" + roles +
                ", birthday='" + birthday + '\'' +
                ", lastLoginTime='" + lastLoginTime + '\'' +
                ", sign='" + sign + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
