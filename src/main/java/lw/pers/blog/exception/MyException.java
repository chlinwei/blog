package lw.pers.blog.exception;

/*
作用:这个异常的构造函数将接受ResultEnum类型的参数
 */
public class MyException extends RuntimeException {
    private int code;
    private String errMsg;
    public MyException(ResultEnum resultEnum){
        this.code = resultEnum.getCode();
        this.errMsg = resultEnum.getMsg();
    }
    public MyException(int code,String msg){
        this.code = code;
        this.errMsg = msg;
    }
    public MyException(){
        this.code = -1;
    }
    public MyException(String msg){
        this.code = -1;
        this.errMsg = msg;
    }
    public int getCode() {
        return code;
    }

    public String getMessage(){
        return this.errMsg;
    }
}
