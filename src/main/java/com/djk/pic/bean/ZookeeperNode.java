package com.djk.pic.bean;

import com.djk.pic.utils.ZookeeperUtils;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * ZookeeperNode
 * zookeeper的节点  该类是一个值类 值对象
 *
 * @author dujinkai
 * @date 2016/4/28
 */
public final class ZookeeperNode {

    /**
     * 节点名称  注意节点名称不包含 根节点的名称
     * 比如/Server/127.0.0.1  只返回127.0.0.1
     */
    private final String nodeName;

    /**
     * 节点值
     */
    private final String nodeValue;

    /**
     * 获得zk的节点对象
     *
     * @param nodeName  节点名称
     * @param nodeValue 节点值
     * @return 返回节点对象
     */
    public static Optional<ZookeeperNode> build(String nodeName, String nodeValue) {

        if (StringUtils.isEmpty(nodeName) || StringUtils.isEmpty(nodeValue)) {
            return Optional.empty();
        }

        return Optional.of(new ZookeeperNode(ZookeeperUtils.getIpByNodename(nodeName), nodeValue));
    }


    private ZookeeperNode(String nodeName, String nodeValue) {
        this.nodeName = nodeName;
        this.nodeValue = nodeValue;
    }

    public String getNodeName() {
        return nodeName;
    }

    public String getNodeValue() {
        return nodeValue;
    }

    @Override
    public String toString() {
        return "ZookeeperNode{" +
                "nodeName='" + nodeName + '\'' +
                ", nodeValue='" + nodeValue + '\'' +
                '}';
    }
}
