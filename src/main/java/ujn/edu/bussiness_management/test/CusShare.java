package ujn.edu.bussiness_management.test;

import java.util.Date;

public class CusShare {
    private Long id;

    private Long shareUserId;

    private Long recUserId;

    private Long cusId;

    private Date time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShareUserId() {
        return shareUserId;
    }

    public void setShareUserId(Long shareUserId) {
        this.shareUserId = shareUserId;
    }

    public Long getRecUserId() {
        return recUserId;
    }

    public void setRecUserId(Long recUserId) {
        this.recUserId = recUserId;
    }

    public Long getCusId() {
        return cusId;
    }

    public void setCusId(Long cusId) {
        this.cusId = cusId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}