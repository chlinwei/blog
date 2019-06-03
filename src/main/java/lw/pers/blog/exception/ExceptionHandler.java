package lw.pers.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;
import java.util.*;


@ControllerAdvice
public class ExceptionHandler {
    //    private final static Logger logger= LoggerFactory.getLogger(ExceptionHandler.class);
    @org.springframework.web.bind.annotation.ExceptionHandler(MyException.class)
    @ResponseBody
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR),这里这么些就会导致jquery会判断返回码
    public ResponseMessage handle(MyException e){
        //打印异常信息
        e.printStackTrace();
        MyException myException = (MyException)e;
        return ResponseMessageUtil.error(myException.getCode(),myException.getMessage());
//        else if(e instanceof ValidationException){
//            //controller里普通的参数校验
//            //校验异常
//            String message = e.getMessage().trim();
//            String message_clients[] = message.split(", ");
//            String message_client = message_clients[0].substring(message_clients[0].indexOf(":"));
//            return ResponseMessageUtil.error(-1,message_client);
//        }else if(e instanceof BindException){
//            //controller里参数为实体类校验,产生的错误
//            BindException be = (BindException)e;
//            //总错误,例如:userName一个错误,passwd两个错误
//            List<FieldError> fieldErrors = be.getFieldErrors();
//            for(FieldError error:fieldErrors){
//                System.out.println(error.getDefaultMessage());
//            }
//            return ResponseMessageUtil.error("注册失败");
//        }
//        else
//            {
//            //如果发生的是系统异常
//            return ResponseMessageUtil.error(e.getMessage());
//        }
    }
}

