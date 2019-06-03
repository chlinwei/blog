package lw.pers.blog.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ftp")
public class FtpProperties {
    private String host = "127.0.0.1";
    private int port = 21;
    private String userName = "ftp1";
    private String passwd = "linwei";
    //文件服务器根目录
    private String rootPath = "/web";

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
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

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    public String toString() {
        return "FtpProperties{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", userName='" + userName + '\'' +
                ", passwd='" + passwd + '\'' +
                ", rootPath='" + rootPath + '\'' +
                '}';
    }
}
