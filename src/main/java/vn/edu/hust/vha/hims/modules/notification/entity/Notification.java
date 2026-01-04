package vn.edu.hust.vha.hims.modules.notification.entity;

import vn.edu.hust.vha.hims.common.entity.BaseEntity;
import vn.edu.hust.vha.hims.common.enumeration.NotificationPriority;
import vn.edu.hust.vha.hims.common.enumeration.NotificationType;

import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notification", schema = "hrm")
@Getter 
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification extends BaseEntity {  
    @Column(name = "title", nullable = false)
    private String title;
    
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private NotificationType type; 
    
    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private NotificationPriority priority; 
    
    @OneToMany(
        mappedBy = "notification", 
        cascade = CascadeType.ALL, 
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private List<NotificationRecipient> recipients;
}