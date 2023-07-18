module telnet.com {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.alibaba.fastjson2;


    exports telnet.com.view;
    exports telnet.com.backend.entity;
    exports telnet.com.backend.util;
}