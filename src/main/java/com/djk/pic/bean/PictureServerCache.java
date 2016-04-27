package com.djk.pic.bean;

import com.djk.pic.utils.LogUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 图片服务器的缓存
 *
 * @author dujinkai
 */
public final class PictureServerCache {

    /**
     * 调试日志
     */
    private static final Logger DEBUG = Logger.getLogger(PictureServerCache.class);

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

    /**
     * 新增服务器
     *
     * @param ip               服务器ip地址
     * @param serverLoadingNum 服务器负载
     */
    public void addPictureServer(String ip, String serverLoadingNum) {

        if (StringUtils.isEmpty(ip) || StringUtils.isEmpty(serverLoadingNum)) {
            LogUtils.error(DEBUG, () -> "AddPictureServer due to ip or serverLoadingNum is null...");
            return;
        }

        LogUtils.debug(DEBUG, () -> "Being to addPictureServer and ip:" + ip + " serverLoadingNum:" + serverLoadingNum);
        list.add(new PictureServer(ip, serverLoadingNum));
    }

    /**
     * 移除服务器
     *
     * @param ip 服务器ip地址
     */
    public void removePictureServer(String ip) {
        if (StringUtils.isEmpty(ip) || CollectionUtils.isEmpty(list)) {
            LogUtils.error(DEBUG, () -> "RemovePictureServer Fail due to ip is null or servers is empty...");
            return;
        }
        list = list.stream().filter(x -> !(x.getIp().equals(ip))).collect(Collectors.toList());
    }


    /**
     * 更新服务器负载
     *
     * @param ip               服务器ip
     * @param serverLoadingNum 服务器负载
     */
    public void updatePictureServer(String ip, String serverLoadingNum) {
        if (StringUtils.isEmpty(ip) || StringUtils.isEmpty(serverLoadingNum)) {
            LogUtils.error(DEBUG, () -> "UpdatePictureServer due to ip or serverLoadingNum is null...");
            return;
        }
        list.stream().filter(x -> x.getIp().equals(ip)).forEach(x -> x.setServerLoadingNum(serverLoadingNum));
    }

    /**
     * 测试方法
     *
     * @param args
     */
    public static void main(String[] args) {

        PictureServerCache.getInstance().addPictureServer("1", "1");
        PictureServerCache.getInstance().addPictureServer("2", "2");
        PictureServerCache.getInstance().addPictureServer("3", "3");
        System.out.println(PictureServerCache.getInstance().list);
        PictureServerCache.getInstance().removePictureServer("1");
        System.out.println(PictureServerCache.getInstance().list);
        PictureServerCache.getInstance().updatePictureServer("2", "10");
        System.out.println(PictureServerCache.getInstance().list);
    }


}
