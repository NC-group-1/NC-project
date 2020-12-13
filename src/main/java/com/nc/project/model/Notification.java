package com.nc.project.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nc.project.model.util.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Notification {
    @Id
    private Integer notificationId;
    private TestCase testCase;
    private Float progress;
    private Date date;
    private NotificationType type;

    public Notification(Integer notificationId, TestCase testCase, Date date, NotificationType type) {
        this.notificationId = notificationId;
        this.testCase = testCase;
        this.date = date;
        this.type = type;
    }
}
