package lw.pers.blog.model;

/**
 * 最近访问人
 */
public class Visitor {
    private Integer id;
    //访问人的id
    private int visitorId;
    //最近访问时间
    private String visitTime;
    //这条记录关联的用户id
    private int userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(int visitorId) {
        this.visitorId = visitorId;
    }

    public String getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(String visitTime) {
        this.visitTime = visitTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
