package com.djk.pic.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片服务器的缓存
 *
 * @author dujinkai
 */
public final class PictureServerCache {

    /**
     * 私有构造器
     */
    private PictureServerCache() {

    }

    private static final PictureServerCache INSTANCE = new PictureServerCache();

    /**
     * 获得实例
     *
     * @return
     */
    public static PictureServerCache getInstance() {
        return INSTANCE;
    }

    /**
     * 图片服务器集合
     */
    private List<PictureServer> list = new ArrayList<>();

    /**
     * 获得所有图片服务器的信息
     *
     * @return 返回所有图片服务器的地址
     */
    public String getAllPictureServerInfo() {
        return list.toString();
    }
}
