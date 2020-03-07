package org.tyj.ddz.bean;

import java.io.Serializable;
import java.util.Date;

public class ShareTask implements Serializable {
    private Integer userId;

    private Integer taskId;

    private Integer condition;

    private Integer status;

    private Date finishTime;

    private static final long serialVersionUID = 1L;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getCondition() {
        return condition;
    }

    public void setCondition(Integer condition) {
        this.condition = condition;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }
}