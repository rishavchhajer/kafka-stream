package entity;

import javax.persistence.*;

@Entity
@Table(name = "communication_logs")
public class CommunicationLogs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "logId", updatable = false)
    private Long logId = null;

    @Column(nullable = false)
    private String siteId;

    @Column(nullable = false)
    private String mobileNumber;

    @Column(nullable = false)
    private String notikoEvent;

    @Column(nullable = false)
    private String comboxEvent;

    private String requestId;

    private int communicationNumber;

    public void setLogId(Long logId){
        this.logId = logId;
    }

    public void setMobileNumber(String mobileNumber){
        this.mobileNumber = mobileNumber;
    }

    public void setSiteId(String siteId){
        this.siteId = siteId;
    }

    public void setNotikoEvent(String notikoEvent){
        this.notikoEvent = notikoEvent;
    }

    public void setComboxEvent(String comboxEvent){
        this.comboxEvent = comboxEvent;
    }

    public void setRequestId(String requestId){
        this.requestId = requestId;
    }

    public void setCommunicationNumber(int no){
        this.communicationNumber = no;
    }

    public Long getLogId(){
        return this.logId;
    }

    public String getMobileNumber(){
        return this.mobileNumber;
    }

    public String getSiteId(){
        return this.siteId;
    }

    public String getNotikoEvent(){
        return this.notikoEvent;
    }

    public String getComboxEvent(){
        return this.comboxEvent;
    }

    public String getRequestId(){
        return this.requestId;
    }

    public int getCommunicationNumber(){
        return this.communicationNumber;
    }

}
