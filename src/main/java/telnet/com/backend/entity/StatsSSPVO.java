package telnet.com.backend.entity;

import javafx.beans.property.SimpleStringProperty;

public class StatsSSPVO {

    // ip or domain name
    private final SimpleStringProperty  hostname;
    // port
    private final SimpleStringProperty port;
    // remark
    private final SimpleStringProperty remark;
    // count sum
    private final SimpleStringProperty  countNumber ;
    // count err
    private final SimpleStringProperty  errNumber ;


    public StatsSSPVO(Monitor stats) {
        this.hostname = new SimpleStringProperty(stats.getHostname());
        this.port =  new SimpleStringProperty(stats.getPort().toString());
        this.remark = new SimpleStringProperty(stats.getRemark());
        this.countNumber =  new SimpleStringProperty(stats.getCountNumber().toString());
        this.errNumber =  new SimpleStringProperty(stats.getErrNumber().toString());
    }




    public String getRemark() {
        return remark.get();
    }

    public SimpleStringProperty remarkProperty() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark.set(remark);
    }

    public String getHostname() {
        return hostname.get();
    }

    public SimpleStringProperty hostnameProperty() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname.set(hostname);
    }

    public String getPort() {
        return port.get();
    }

    public SimpleStringProperty portProperty() {
        return port;
    }

    public void setPort(String port) {
        this.port.set(port);
    }

    public String getCountNumber() {
        return countNumber.get();
    }

    public SimpleStringProperty countNumberProperty() {
        return countNumber;
    }

    public void setCountNumber(String countNumber) {
        this.countNumber.set(countNumber);
    }

    public String getErrNumber() {
        return errNumber.get();
    }

    public SimpleStringProperty errNumberProperty() {
        return errNumber;
    }

    public void setErrNumber(String errNumber) {
        this.errNumber.set(errNumber);
    }
}
