package com.djk.pic.bean;

import com.djk.pic.utils.LogUtils;
import com.djk.pic.utils.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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

        if (StringUtils.isEmpty(ip) || !StringUtils.isNumeric(serverLoadingNum)) {
            LogUtils.error(DEBUG, () -> "AddPictureServer due to ip or serverLoadingNum is null...ip:" + ip + " serverLoadingNum:" + serverLoadingNum);
            return;
        }

        LogUtils.debug(DEBUG, () -> "Being to addPictureServer and ip:" + ip + " serverLoadingNum:" + serverLoadingNum);
        list.add(new PictureServer(ip, Integer.parseInt(serverLoadingNum)));
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
        if (StringUtils.isEmpty(ip) || StringUtils.isNumeric(serverLoadingNum)) {
            LogUtils.error(DEBUG, () -> "UpdatePictureServer due to ip or serverLoadingNum is null...ip:" + ip + " serverLoadingNum:" + serverLoadingNum);
            return;
        }
        list.stream().filter(x -> x.getIp().equals(ip)).forEach(x -> x.setServerLoadingNum(Integer.parseInt(serverLoadingNum)));
    }

    /**
     * 获得一台负载最小的服务器
     *
     * @return 返回一台负载最小的服务器
     */
    public Optional<PictureServer> getBestPictureServer() {

        LogUtils.debug(DEBUG, () -> "Being to getBestPictureServer...servers:" + list);

        // 如果当前没有服务器则直接返回空
        if (CollectionUtils.isEmpty(list)) {
            LogUtils.error(DEBUG, () -> "GetBestPictureServer Fail due to servers is empty :" + list);
            return Optional.empty();
        }

        // 如果当前只有一台服务器则直接返回这一台服务器
        if (list.size() == 1) {
            LogUtils.debug(DEBUG, () -> "There is only one server in memory and begin to return :" + list);
            return Optional.of(list.get(0).copySelf());
        }

        // 如果有多台服务器则先按照服务器的负载从小到大排序然后取第一个
        Collections.sort(list, PictureServer::compareByServerLoadingNum);

        LogUtils.debug(DEBUG, () -> "After sort by ServerLoadingNum servers :" + list);

        return Optional.of(list.get(0).copySelf());
    }


    /**
     * 测试方法
     *
     * @param args
     */
    public static void main(String[] args) {

        PictureServerCache.getInstance().addPictureServer("2", "2");
        PictureServerCache.getInstance().addPictureServer("3", "3");
        PictureServerCache.getInstance().addPictureServer("1", "a");
        System.out.println(PictureServerCache.getInstance().list);
        PictureServerCache.getInstance().removePictureServer("1");
        System.out.println(PictureServerCache.getInstance().list);
        PictureServerCache.getInstance().updatePictureServer("2", "10");
        System.out.println(PictureServerCache.getInstance().list);

        Optional<PictureServer> optional = PictureServerCache.getInstance().getBestPictureServer();
        System.out.println(optional.get());
    }


}
