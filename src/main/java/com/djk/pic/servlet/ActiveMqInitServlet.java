package com.djk.pic.servlet;

import com.djk.pic.bean.ActiveMqListern;
import com.djk.pic.utils.LogUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * ActiveMqInitServlet
 * activemq 初始化的servlet
 *
 * @author dujinkai
 * @date 2016/4/28
 */
public class ActiveMqInitServlet extends HttpServlet {

    /**
     * 调试日志
     */
    private static final Logger DEBUG = Logger.getLogger(ActiveMqInitServlet.class);

    @Override
    public void init() throws ServletException {

        LogUtils.debug(DEBUG, () -> "Begin to init ActiveMq...");

        ActiveMqListern.getInstance().startListern();

        LogUtils.debug(DEBUG, () -> "Init ActiveMq Success...");
    }
}
