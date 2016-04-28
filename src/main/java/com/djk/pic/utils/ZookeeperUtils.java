package com.djk.pic.utils;

import com.djk.pic.bean.ZookeeperNode;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.Optional;

/**
 * @author dujinkai
 *         <p>
 *         生产CuratorFramework 对象的类
 */
public final class ZookeeperUtils {


    /**
     * zk的根节点
     */
    public static final String ROOT = "/Server";

    /**
     * 私有构造器防止初始化
     */
    private ZookeeperUtils() {

    }

    /**
     * 创建简易的zk实例
     *
     * @param connectionString 链接信息 比如127.0.0.1:2181
     * @return 返回CuratorFramework实例
     */
    public static CuratorFramework createSimpleZkClient(String connectionString) {
        return CuratorFrameworkFactory.newClient(connectionString,
                new ExponentialBackoffRetry(1000, 3));
    }

    /**
     * 创建自定义的zk实例
     *
     * @param connectionString    链接信息 比如127.0.0.1:2181
     * @param retryPolicy         重试策略
     * @param connectionTimeoutMs 链接超时时间
     * @param sessionTimeoutMs    session超时时间
     * @return 返回CuratorFramework实例
     */
    public static CuratorFramework createWithOptions(String connectionString,
                                                     RetryPolicy retryPolicy, int connectionTimeoutMs,
                                                     int sessionTimeoutMs) {

        return CuratorFrameworkFactory.builder()
                .connectString(connectionString).retryPolicy(retryPolicy)
                .connectionTimeoutMs(connectionTimeoutMs)
                .sessionTimeoutMs(sessionTimeoutMs).build();
    }


    /**
     * 判断节点是否存在
     *
     * @param client zk客户端
     * @param path   节点路径
     * @return 存在返回true  不存在返回false
     * @throws Exception
     */
    public static boolean isNodeExist(CuratorFramework client, String path) throws Exception {
        return client.checkExists().forPath(path) == null ? false : true;
    }


    /**
     * 创建节点 （固定节点）
     *
     * @param client  zk客户端
     * @param path    节点的路径
     * @param payload 节点下的数据
     * @throws Exception 抛出异常
     */
    public static void create(CuratorFramework client, String path,
                              byte[] payload) throws Exception {
        client.create().forPath(path, payload);
    }

    /**
     * 根据路径 获得根节点的名称
     *
     * @param client zk客户端
     * @param path   节点路径
     * @return 返回节点对象
     * @throws Exception
     */
    public static Optional<ZookeeperNode> getZookeeperNode(CuratorFramework client, String path) throws Exception {
        return ZookeeperNode.build(path, getNodeDate(client, path));
    }

    /**
     * 获得节点数据
     *
     * @param client 客户端
     * @param path   节点路径
     * @return 返回节点下的数据
     */
    public static String getNodeDate(CuratorFramework client, String path)
            throws Exception {
        return new String(client.getData().forPath(path));
    }

    /**
     * 根据node的名称 获得ip地址
     *
     * @param nodeName node的名称
     * @return
     */
    public static String getIpByNodename(String nodeName) {
        if (StringUtils.isEmpty(nodeName)) {
            return "";
        }

        if (nodeName.indexOf(ROOT) !=-1) {
            return nodeName.substring(nodeName.lastIndexOf("/") + 1);
        }

        return nodeName;
    }


}
