package ca.mcgill.ecse321.projectgroup17.model;

import javax.persistence.Entity;

@Entity
public enum AppointmentStatus{
   private String Requested;

public void setRequested(String value) {
    this.Requested = value;
}
public String getRequested() {
    return this.Requested;
}
private String Accepted;

public void setAccepted(String value) {
    this.Accepted = value;
}
public String getAccepted() {
    return this.Accepted;
}
private String Refused;

public void setRefused(String value) {
    this.Refused = value;
}
public String getRefused() {
    return this.Refused;
}
private String Canceled;

public void setCanceled(String value) {
    this.Canceled = value;
}
public String getCanceled() {
    return this.Canceled;
}
private String Paid;

public void setPaid(String value) {
    this.Paid = value;
}
public String getPaid() {
    return this.Paid;
}
}
