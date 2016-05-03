package com.djk.pic.service.impl;

import java.util.Date;
import java.util.Optional;

import com.djk.pic.bean.PictureServer;
import com.djk.pic.bean.PictureServerCache;
import com.djk.pic.service.ActiceMqService;
import com.djk.pic.utils.LogUtils;
import org.apache.log4j.Logger;

/**
 * activemq 服务实现类
 *
 * @author dujinkai
 */
public class ActiceMqServiceImpl implements ActiceMqService {

    /**
     * 调试日志
     */
    public static final Logger DEBUG = Logger.getLogger(ActiceMqServiceImpl.class);

    @Override
    public void handelMessage(String message) {
        // 获得消息 第一步 首先获得负载最小的下载服务器IP
        Optional<PictureServer> pictureServer = PictureServerCache.getInstance().getBestPictureServer();

        if (pictureServer.isPresent()) {
            PictureServer server = pictureServer.get();

            String ip = server.getIp();

            LogUtils.debug(DEBUG, () -> "Begin to send message to pic Server and best PicServer Ip is :" + ip + " and message:" + message);

        } else {
            // 可以把消息记录文件 或者数据库 或者消息队列 这边就不做处理
            LogUtils.error(DEBUG, () -> "There is no server to handle message");
        }

    }

}
