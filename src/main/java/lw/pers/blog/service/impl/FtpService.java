package lw.pers.blog.service.impl;


import lw.pers.blog.exception.MyException;
import lw.pers.blog.properties.FtpProperties;
import lw.pers.blog.util.DirCreateUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FtpService {
    @Autowired
    FtpProperties ftpProperties;
    /**
     * 返回一个文件的uri
     */
    public  String uploadFile(String fileName, InputStream inputStream, String toSaveDir) throws IOException {
        //默认是主动模式
        FTPClient ftp = new FTPClient();
        ftp.setControlEncoding("utf-8");
        int reply;
        ftp.connect(ftpProperties.getHost(),ftpProperties.getPort());
        ftp.login(ftpProperties.getUserName(), ftpProperties.getPasswd());
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new MyException("连接ftp服务器失败");
        }
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
        //makeDirectory这个函数是如果目录存在则返回false,如果不存在,则创建目录,返回true
        if(!ftp.changeWorkingDirectory(ftpProperties.getRootPath())){
            //切换到nginx服务器的根目录
            throw new MyException("文件服务器的根路径不存在");
        }
        String dir = toSaveDir+DirCreateUtil.direCreate();
        String[] dirs = dir.substring(dir.indexOf("/")+1).split("/");
        //切换到dir目录
        for(String s:dirs){
            if(ftp.changeWorkingDirectory(s)){
                continue;
            }else{
                ftp.makeDirectory(s);
                ftp.changeWorkingDirectory(s);
            }
        }
        //如果文件已经存在或者文件创建成功都会返回true
        boolean flag = ftp.storeFile(fileName, inputStream);
        inputStream.close();
        ftp.logout();
        if(!flag){
            throw new MyException("文件上传失败");
        }
        return dir+"/"+fileName;
    }
    public String getHost(){
        return ftpProperties.getHost();
    }
}
