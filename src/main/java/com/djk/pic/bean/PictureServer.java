package com.djk.pic.bean;

import org.springframework.util.StringUtils;

/**
 *  图片服务器实体类
 * @author dujinkai
 *
 */
public class PictureServer {

    public PictureServer(String ip, String serverLoadingNum) {
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
	private String serverLoadingNum;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getServerLoadingNum() {
		return serverLoadingNum;
	}

	public void setServerLoadingNum(String serverLoadingNum) {
		this.serverLoadingNum = serverLoadingNum;
	}

	@Override
	public String toString() {
		return "PictureServer [ip=" + ip + ", serverLoadingNum="
				+ serverLoadingNum + "]"+"\r\n";
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


}
