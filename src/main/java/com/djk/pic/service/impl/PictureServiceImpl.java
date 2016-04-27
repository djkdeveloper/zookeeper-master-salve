package com.djk.pic.service.impl;

import com.djk.pic.bean.PictureServerCache;
import com.djk.pic.service.PictureService;
import com.djk.pic.utils.LogUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * 图片服务器接口实现类
 * 
 * @author dujinkai
 *
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

}
