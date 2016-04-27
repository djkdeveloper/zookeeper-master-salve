package com.djk.pic.service;

import com.djk.pic.bean.PictureServer;

import java.util.Optional;

/**
 * 图片服务器接口
 * @author dujinkai
 *
 */
public interface PictureService {

	/**
	 * 获得所有图片服务器的信息
	 * 
	 * @return 返回 所有服务器的信息
	 */
	String getAllPicServerInfo();

    /**
     * 获得当前负载最小的服务器信息
     *
     * @return 返回当前负载最小的服务器信息
     */
    Optional<PictureServer> getBestPictureServer();
}
