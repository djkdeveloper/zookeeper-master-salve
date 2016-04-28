package com.djk.pic.servlet;

import com.djk.pic.bean.PictureServerObserver;
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

    /**
     * zk客户端
     */
    private CuratorFramework curatorFramework;

    /**
     * 节点监听对象
     */
    private PathChildrenCache childrenCache;

    @Override
    public void init() throws ServletException {

        LogUtils.debug(DEBUG, () -> "Begin to init zookeeper...");

        try {
            // 初始化zk服务 主要是对根节点的监听
            initZkService();
        } catch (Exception e) {
            LogUtils.error(DEBUG, () -> "InitZkService Fail...");
            throw new ServletException(e);
        }

        LogUtils.debug(DEBUG, () -> "Init zookeeper success...");
    }

    /**
     * 初始化zookeeper服务
     */
    private void initZkService() throws Exception {

        curatorFramework = ZookeeperUtils.createSimpleZkClient("xx.xx.xx:2181");
        curatorFramework.start();
        // 首先判断根节点是否存在
        if (!ZookeeperUtils.isNodeExist(curatorFramework, ZookeeperUtils.ROOT)) {
            LogUtils.debug(DEBUG, () -> ZookeeperUtils.ROOT + "is not exist and begin to create...");
            ZookeeperUtils.create(curatorFramework, ZookeeperUtils.ROOT, "".getBytes());
        }

        // 进行监听
        childrenCache = new PathChildrenCache(curatorFramework,
                ZookeeperUtils.ROOT, true);

        childrenCache.start(PathChildrenCache.StartMode.NORMAL);

        // 对根节点进行监听
        childrenCache.getListenable().addListener((client, event) -> {
            PictureServerObserver.getInstance().handleZkCallback(client, event);
        });
    }


    @Override
    public void destroy() {
        super.destroy();
        try {
            childrenCache.close();
            curatorFramework.close();
        } catch (Exception e) {
            LogUtils.error(DEBUG, () -> "Close zk fail...", e);
        }
    }
}
