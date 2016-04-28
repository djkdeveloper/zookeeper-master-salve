package com.djk.pic.servlet;

import com.djk.pic.bean.PictureServerObserver;
import com.djk.pic.bean.ZookeeperClient;
import com.djk.pic.utils.LogUtils;
import com.djk.pic.utils.ZookeeperUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * ZookeeperInitServlet
 * zookeeper 启动servlet
 *
 * @author dujinkai
 * @date 2016/4/27
 */
public class ZookeeperInitServlet extends HttpServlet {

    /**
     * 调试日志
     */
    private static final Logger DEBUG = Logger.getLogger(ZookeeperInitServlet.class);

    @Override
    public void init() throws ServletException {

        LogUtils.debug(DEBUG, () -> "Begin to init zookeeper...");

        try {
            // 初始化zk服务 主要是对根节点的监听
            ZookeeperClient.getInstance().initZkService();

        } catch (Exception e) {
            LogUtils.error(DEBUG, () -> "InitZkService Fail...");
            throw new ServletException(e);
        }

        LogUtils.debug(DEBUG, () -> "Init zookeeper success...");
    }


    @Override
    public void destroy() {
        super.destroy();
        try {
            ZookeeperClient.getInstance().closeZookeeperClient();
        } catch (Exception e) {
            LogUtils.error(DEBUG, () -> "Close zk fail...", e);
        }
    }
}
