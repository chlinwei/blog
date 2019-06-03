package lw.pers.blog.util;

/**
 * 将文章内容生成摘要
 */
public class SummaryUtil {
    public static int maxLength = 300;
    public static String toSummary(String article){
        String result = article.trim();
        String regex = "\\s*|\t|\r|\n";
        //去掉所有空格
        result = result.replaceAll(regex,"");
        if(result.length()>maxLength){
            result = result.substring(0,maxLength);
            result += "...";
        }
        return result;
    }
}
