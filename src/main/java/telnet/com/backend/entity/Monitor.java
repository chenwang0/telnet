package telnet.com.backend.entity;

import telnet.com.backend.util.CheckUtil;

public class Monitor {

    // hostname
    public String hostname;
    // port
    private Integer port;
    // remark
    public String remark;
    // count sum
    private Integer countNumber = 0;
    // count err
    private Integer errNumber = 0;


    public String persis(){

        String c = ",";
        return getHostname() +c+ getPort() +c+ getRemark() +c+ getCountNumber() +c+ getErrNumber();
    }

    public Monitor() {
    }

    public Monitor(String[] val) {
        this.hostname = val[0];
        this.port = CheckUtil.parseInt(val[1]);
        this.remark = val[2];
        this.countNumber = CheckUtil.parseInt(val[3]);
        this.errNumber = CheckUtil.parseInt(val[4]);
    }

    public Monitor(String hostname, Integer port, String remark) {
        this.hostname = hostname;
        this.port = port;
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Monitor{" +
                "hostname='" + hostname + '\'' +
                ", port=" + port +
                ", remark='" + remark + '\'' +
                ", countNumber=" + countNumber +
                ", errNumber=" + errNumber +
                '}';
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCountNumber() {
        return countNumber;
    }

    public void setCountNumber(Integer countNumber) {
        this.countNumber = countNumber;
    }

    public Integer getErrNumber() {
        return errNumber;
    }

    public void setErrNumber(Integer errNumber) {
        this.errNumber = errNumber;
    }
}
