package com.employeemanagement.employeedetails.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Table(name = "emp_outbox_tbl")
@Entity
public class EmployeeOutboxEntity {
    @Column(name="emp_id")
    @Id
     String eventId;

    @Column(name="event_status")
    String eventStatus;

    @Column(name="old_data")
    String oldData;

    @Column(name="new_data")
    String newData;

    @Column(name="create_date")
    Timestamp createDate;

    @Column(name="update_date")
    Timestamp updateDate;


    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }

    public String getOldData() {
        return oldData;
    }

    public void setOldData(String oldData) {
        this.oldData = oldData;
    }

    public String getNewData() {
        return newData;
    }

    public void setNewData(String newData) {
        this.newData = newData;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "EmpoyeeOutboxEntity{" +
                "eventId=" + eventId +
                ", eventStatus='" + eventStatus + '\'' +
                ", oldData='" + oldData + '\'' +
                ", newData='" + newData + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
