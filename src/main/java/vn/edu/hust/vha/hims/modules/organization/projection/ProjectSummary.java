package vn.edu.hust.vha.hims.modules.organization.projection;

import java.time.LocalDate;
import java.util.UUID;

public interface ProjectSummary {
    UUID getId();
    String getName();
    LocalDate getStartDate();
    LocalDate getExpectedEndDate();
    String getStatus();
    String getPmFullName();   
    Long getMemberCount();    
}