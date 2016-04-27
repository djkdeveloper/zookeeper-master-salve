package com.djk.pic.bean;

import org.springframework.util.StringUtils;

/**
 * 图片服务器实体类
 *
 * @author dujinkai
 */
public class PictureServer{

    public PictureServer(String ip, int serverLoadingNum) {
        this.ip = ip;
        this.serverLoadingNum = serverLoadingNum;
    }

    /**
     * 图片服务器的ip地址
     */
    private String ip;

    /**
     * 服务器负载数 （主要是固定线程池里面的队列大小 未处理任务的数量）
     */
    private int serverLoadingNum;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


    @Override
    public String toString() {
        return "PictureServer [ip=" + ip + ", serverLoadingNum="
                + serverLoadingNum + "]" + "\r\n";
    }

    public int getServerLoadingNum() {
        return serverLoadingNum;
    }

    public void setServerLoadingNum(int serverLoadingNum) {
        this.serverLoadingNum = serverLoadingNum;
    }

    /**
     * 判断传入的ip地址 是否是当前对象的ip
     *
     * @param ip 传入的ip
     * @return 是当前对象的ip 返回true  否则返回false
     */
    public boolean isCurrentPictureServer(String ip) {

        if (StringUtils.isEmpty(ip)) {
            return false;
        }

        return ip.equals(this.ip);
    }

    /**
     * 拷贝自己
     *
     * @return 返回自己的一个拷贝对象
     */
    public PictureServer copySelf() {
        return new PictureServer(this.ip, this.serverLoadingNum);
    }

    /**
     * 根据服务器的负载进行排序
     *
     * @param a 服务器A
     * @param b 服务器B
     * @return 返回;排序结果
     */
    public static int compareByServerLoadingNum(PictureServer a, PictureServer b) {
        if (null == a || null == b) {
            return 0;
        }
        return Integer.valueOf(a.serverLoadingNum).compareTo(Integer.valueOf(b.serverLoadingNum));
    }
}
