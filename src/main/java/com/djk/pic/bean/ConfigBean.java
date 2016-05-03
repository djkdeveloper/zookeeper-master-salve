package com.djk.pic.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * ConfigBean
 * 配置实体
 *
 * @author djk
 * @date 2016/4/28
 */
public class ConfigBean {

    /**
     * zk客户端的链接
     */
    @Value("#{configProperties['zkconnection']}")
    private String zkConnection;

    /**
     * activemq客户端的链接
     */
    @Value("#{configProperties['acitvemqconnection']}")
    private String activemqConnection;

    /**
     * 消费主题
     */
    @Value("#{configProperties['topic']}")
    private String topic;

    /**
     * 图片服务器的地址
     */
    @Value("#{configProperties['picServerUrl']}")
    private String picServerUrl;

    public String getZkConnection() {
        return zkConnection;
    }

    public void setZkConnection(String zkConnection) {
        this.zkConnection = zkConnection;
    }

    public String getActivemqConnection() {
        return activemqConnection;
    }

    public void setActivemqConnection(String activemqConnection) {
        this.activemqConnection = activemqConnection;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getPicServerUrl() {
        return picServerUrl;
    }

    public void setPicServerUrl(String picServerUrl) {
        this.picServerUrl = picServerUrl;
    }

    @Override
    public String toString() {
        return "ConfigBean{" +
                "zkConnection='" + zkConnection + '\'' +
                ", activemqConnection='" + activemqConnection + '\'' +
                ", topic='" + topic + '\'' +
                ", picServerUrl='" + picServerUrl + '\'' +
                '}';
    }
}
