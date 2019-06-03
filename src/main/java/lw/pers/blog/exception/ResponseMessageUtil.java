package lw.pers.blog.exception;

public class ResponseMessageUtil {
    /**
     * 成功:返回信息
     */
    public static ResponseMessage success(Object object) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setCode(ResultEnum.SUCCESS.getCode());
        responseMessage.setMsg(ResultEnum.SUCCESS.getMsg());
        responseMessage.setData(object);
        return responseMessage;
    }

    /**
     * 成功:不返回信息
     */
    public static ResponseMessage success() {
        return success(null);
    }


    /**
     * 失败:返回信息
     */
    public static ResponseMessage error(int code, String msg) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setCode(code);
        responseMessage.setMsg(msg);
        return responseMessage;
    }


    /**
     * 失败:自定义异常
     */
    public static ResponseMessage error(ResultEnum resultEnum) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setCode(resultEnum.getCode());
        responseMessage.setMsg(resultEnum.getMsg());
        return responseMessage;
    }

    /**
     * 系统异常:参数为string
     */
    public static ResponseMessage error(String msg) {
        return error(-1, msg);
    }


    /**
     * 系统异常:参数为object
     */
    public static ResponseMessage error(Object o) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setCode(-1);
        responseMessage.setData(o);
        return responseMessage;
    }
}
