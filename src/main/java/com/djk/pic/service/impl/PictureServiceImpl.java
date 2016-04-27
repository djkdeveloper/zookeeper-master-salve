package com.djk.pic.service.impl;

import com.djk.pic.bean.PictureServer;
import com.djk.pic.bean.PictureServerCache;
import com.djk.pic.service.PictureService;
import com.djk.pic.utils.LogUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 图片服务器接口实现类
 *
 * @author dujinkai
 */
@Service("pictureService")
public class PictureServiceImpl implements PictureService {

    /**
     * 调试日志
     */
    private static final Logger DEBUG = Logger
            .getLogger(PictureServiceImpl.class);

    @Override
    public String getAllPicServerInfo() {

        LogUtils.debug(DEBUG, () -> "Receive GetAllPicServerInfo request....");

        return PictureServerCache.getInstance().getAllPictureServerInfo();
    }

    /**
     * 获得当前负载最小的服务器信息
     *
     * @return 返回当前负载最小的服务器信息
     */
    @Override
    public Optional<PictureServer> getBestPictureServer() {
        LogUtils.debug(DEBUG, () -> "Receive GetBestPictureServer request...");
        return PictureServerCache.getInstance().getBestPictureServer();
    }

}
