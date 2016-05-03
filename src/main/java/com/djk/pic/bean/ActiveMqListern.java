package com.djk.pic.bean;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.djk.pic.service.ActiceMqService;
import com.djk.pic.service.impl.ActiceMqServiceImpl;
import com.djk.pic.utils.ApplicationContextHelper;
import com.djk.pic.utils.LogUtils;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * activemq 监听
 *
 * @author dujinkai
 */
public class ActiveMqListern {
    /**
     * 日志
     */
    private static Logger DEBUG = Logger.getLogger(ActiveMqListern.class);

    public static ActiveMqListern activeMqListern = new ActiveMqListern();

    private ActiveMqListern() {

    }

    public static ActiveMqListern getInstance() {
        return activeMqListern;
    }

    /**
     * 链接对象
     */
    private Connection connection;

    /**
     * 会话对象
     */
    private Session session;

    /**
     * 消费者
     */
    private MessageConsumer consumer;

    /**
     * 消息目的
     */
    private Destination destination;

    /**
     * active mq 服务接口
     */
    private ActiceMqService acticeMqService = new ActiceMqServiceImpl();

    /**
     * 注入配置文件
     */
    private ConfigBean configBean = (ConfigBean)ApplicationContextHelper.getBean("configBean");

    /**
     * 启动activeMq开始监听
     */
    public void startListern() {

        LogUtils.debug(DEBUG, () -> "Begin to start mq and connection is :" + configBean.getActivemqConnection() + " topic is :" + configBean.getTopic());

        // 得到activeq的链接工厂
        ConnectionFactory connectionFactory = getConnectionFactory();

        try {
            connection = connectionFactory.createConnection();
            session = connection.createSession(Boolean.FALSE,
                    Session.AUTO_ACKNOWLEDGE);
            destination = session.createTopic(configBean.getTopic());
            consumer = session.createConsumer(destination);
            consumer.setMessageListener(x -> {
                TextMessage activeMQMessage = (TextMessage) x;
                try {
                    acticeMqService.handelMessage(activeMQMessage.getText());
                } catch (Exception e) {
                    LogUtils.error(DEBUG, () -> "Get mq message fail...", e);
                }
            });

            connection.start();
        } catch (JMSException e) {
            LogUtils.error(DEBUG, () -> "Start mq fail...", e);
            return;
        }
    }


    /**
     * 得到acvitemq的链接工厂
     *
     * @return
     */
    private ConnectionFactory getConnectionFactory() {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD, configBean.getActivemqConnection());

        return connectionFactory;
    }

    /**
     * 关闭
     */
    public void close() {
        try {
            if (consumer != null) {
                consumer.close();
            }

            if (session != null) {
                session.close();
            }

            if (connection != null) {
                connection.close();
            }
        } catch (JMSException e) {
            LogUtils.error(DEBUG, () -> "Close mq fail...", e);
        }
    }

}
