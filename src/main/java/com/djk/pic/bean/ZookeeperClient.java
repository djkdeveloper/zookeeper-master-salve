package com.djk.pic.bean;

import com.djk.pic.utils.ApplicationContextHelper;
import com.djk.pic.utils.LogUtils;
import com.djk.pic.utils.ZookeeperUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * ZookeeperClient
 * Zookeeper 客户端对象
 *
 * @author dujinkai
 * @date 2016/4/28
 */
public final class ZookeeperClient {

    /**
     * 调试日志
     */
    private static final Logger DEBUG = Logger.getLogger(ZookeeperClient.class);

    private static final ZookeeperClient instance = new ZookeeperClient();

    private ZookeeperClient() {
    }

    public static ZookeeperClient getInstance() {
        return instance;
    }


    /**
     * zk客户端
     */
    private CuratorFramework curatorFramework;

    /**
     * 节点监听对象
     */
    private PathChildrenCache childrenCache;

    /**
     * 注入配置文件
     */
    private ConfigBean configBean = (ConfigBean) ApplicationContextHelper.getBean("configBean");

    /**
     * 初始化zookeeper服务
     */
    public void initZkService() throws Exception {

        LogUtils.debug(DEBUG, () -> "Begin to start zk and connection is :" + configBean.getZkConnection());

        curatorFramework = ZookeeperUtils.createSimpleZkClient(configBean.getZkConnection());

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

    /**
     * 关闭zookeeper客户端
     */
    public void closeZookeeperClient() {
        try {
            childrenCache.close();
            curatorFramework.close();
        } catch (Exception e) {
            LogUtils.error(DEBUG, () -> "Close zk fail...", e);
        }
    }

}
