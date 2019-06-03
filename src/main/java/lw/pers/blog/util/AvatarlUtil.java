package lw.pers.blog.util;


/**
 * 作用:用户头像将uri转化为url
 *
 */
public class AvatarlUtil {
    public  static String getUrl(String host,String imgUri){
        String url = "";
        if("".equals(imgUri)||imgUri==null){
            //设置默认头像
            url = "http://" +host+ "/avatar.png";
        }else {
            url = "http://" +host+ imgUri;
        }
        return url;
    }
}
