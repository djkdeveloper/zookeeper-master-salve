package com.djk.pic.bean;

import com.djk.pic.utils.LogUtils;
import com.djk.pic.utils.ZookeeperUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.log4j.Logger;

import java.util.Optional;

/**
 * PictureServerObserver
 * 图片服务器的观察者 主要是观察/Server 节点下的子节点的变化
 *
 * @author djk
 * @date 2016/4/27
 */
public final class PictureServerObserver {

    /**
     * 调试日志
     */
    private static final Logger DEBUG = Logger.getLogger(PictureServerObserver.class);

    private static PictureServerObserver pictureServerObserver = new PictureServerObserver();

    private PictureServerObserver() {
    }

    public static PictureServerObserver getInstance() {
        return pictureServerObserver;
    }


    /**
     * 处理zk 节点发生变化回调请求
     *
     * @param client zk客户端
     * @param event  变化类型
     */
    public synchronized void handleZkCallback(CuratorFramework client,
                                              PathChildrenCacheEvent event) {
        switch (event.getType()) {
            // 新增子节点
            case CHILD_ADDED:
                LogUtils.debug(DEBUG, () -> "CHILD_ADDED: "
                        + event.getData().getPath());

                handleChildAddEvent(client, event);

                break;
            // 子节点移除
            case CHILD_REMOVED:
                LogUtils.debug(DEBUG, () -> "CHILD_REMOVED: "
                        + event.getData().getPath());

                handleChildRemoveEvent(client, event);

                break;
            // 子节点的数据发生变化
            case CHILD_UPDATED:
                LogUtils.debug(DEBUG, () -> "CHILD_UPDATED: "
                        + event.getData().getPath());

                handleChildUpdateEvent(client, event);

                break;
            default:
                LogUtils.error(DEBUG, () -> "ERROR:" + event.getData().getPath());
                break;
        }

    }

    /**
     * 处理
     *
     * @param client zk客户端
     * @param event  事件
     */
    private void handleChildUpdateEvent(CuratorFramework client,
                                        PathChildrenCacheEvent event) {
        try {
            Optional<ZookeeperNode> zookeeperNode = ZookeeperUtils.getZookeeperNode(client, event.getData().getPath());

            zookeeperNode.ifPresent(x ->
                            PictureServerCache.getInstance().updatePictureServer(x.getNodeName(), x.getNodeValue())
            );
        } catch (Exception e) {
            LogUtils.error(DEBUG, () -> "CHILD_UPDATED Fail...", e);
        }

    }


    /**
     * 处理节点移除的情况
     *
     * @param client zk客户端
     * @param event  事件
     */
    private void handleChildRemoveEvent(CuratorFramework client,
                                        PathChildrenCacheEvent event) {
        try {
            PictureServerCache.getInstance().removePictureServer(ZookeeperUtils.getIpByNodename(event.getData().getPath()));
        } catch (Exception e) {
            LogUtils.error(DEBUG, () -> "CHILD_REMOVED Fail...", e);
        }
    }


    /**
     * 处理节点新增的情况
     *
     * @param client zk客户端
     * @param event  事件
     */
    private void handleChildAddEvent(CuratorFramework client,
                                     PathChildrenCacheEvent event) {
        try {
            Optional<ZookeeperNode> zookeeperNode = ZookeeperUtils.getZookeeperNode(client, event.getData().getPath());

            zookeeperNode.ifPresent(x -> {
                PictureServerCache.getInstance().addPictureServer(x.getNodeName(), x.getNodeValue());
            });

        } catch (Exception e) {
            LogUtils.error(DEBUG, () -> "CHILD_ADDED Fail...", e);
        }
    }


}
