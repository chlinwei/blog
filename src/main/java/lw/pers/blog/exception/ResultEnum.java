package lw.pers.blog.exception;

public enum ResultEnum {
    USER_EXIST(2001,"用户名已存在"),
    REGISTER_NAME_FORMAT_ERROR(2002,"用户名格式错误"),

    REGISTER_EMAIL_EXIST(2003,"邮箱已存在"),
    REGISTER_EMAIL_FORMAT_ERROR(2004,"邮箱格式错误"),

    REGISTER_PHONE_EXIST(2005,"电话号码已存在"),
    REGISTER_PHONE_FORMAT_ERROR(2006,"电话号码格式错误"),

    REGISTER_VERIFY1_ERROR(2007,"验证码错误"),
    REGISTER_VERIFY2_ERROR(2008,"短信验证码错误"),

    USER_LOGIN_ERROR(2009,"账号或者密码错误"),
    USER_NOT_LOGIN(2010,"用户还没有登陆"),

    CUSTOMTYPE_NOT_EXIST(3000,"自定义文章类型不存在"),
    SUCCESS(0, "success"),
    SUCCESS_RELOGIN(201,"success"),
    SYSTEM_ERROR(-1000001,"系统错误"),
    DATA_EXIST(402,"数据已存在");


    private Integer code;

    private String msg;

    private ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

