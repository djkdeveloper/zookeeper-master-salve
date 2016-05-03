package com.djk.pic.utils;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.djk.pic.bean.ConfigBean;
import com.djk.pic.service.PicDownLoadService;
import org.apache.log4j.Logger;

import com.caucho.hessian.client.HessianProxyFactory;

/**
 * 图片服务器的hession工厂
 *
 * @author dujinkai
 */
public class PicHessionFactory {


    /**
     * 调试日志
     */
    public static final Logger DEBUG = Logger.getLogger(PicHessionFactory.class);

    private static PicHessionFactory picHessionFactory = new PicHessionFactory();

    private PicHessionFactory() {

    }

    /**
     * hessian 工厂
     */
    private HessianProxyFactory factory = new HessianProxyFactory();

    /**
     * 注入配置文件
     */
    private ConfigBean configBean = (ConfigBean) ApplicationContextHelper.getBean("configBean");

    public static PicHessionFactory getInstance() {
        return picHessionFactory;
    }

    /**
     * 缓存的图片服务器地址接口实现类
     * key  是图片服务器的地址
     * value  是图片服务器接口的实现类
     */
    private Map<String, PicDownLoadService> map = new ConcurrentHashMap<>();


    /**
     * 根据ip地址获得图片服务器的地址
     *
     * @param ip
     * @return
     * @throws ClassNotFoundException
     * @throws java.net.MalformedURLException
     */
    public Optional<PicDownLoadService> getPicDownLoadService(String ip) throws MalformedURLException, ClassNotFoundException {

        if (StringUtils.isEmpty(ip)) {
            return Optional.empty();
        }

        if (!map.containsKey(ip)) {

            LogUtils.debug(DEBUG, () -> "ip :" + ip + "is no service in cache and begin to create hession service for :" + ip);

            synchronized (ip) {
                if (!map.containsKey(ip)) {
                    String url = "http://" + ip + configBean.getPicServerUrl();
                    LogUtils.debug(DEBUG, () -> "url is :" + url);
                    PicDownLoadService picDownLoadService = (PicDownLoadService) factory.create(PicDownLoadService.class, url);
                    map.put(ip, picDownLoadService);
                    LogUtils.debug(DEBUG, () -> "Create hession service for :" + ip + "success...");
                }
            }
        }

        LogUtils.debug(DEBUG, () -> "Begin to get hession service in cache and ip :" + ip);


        return Optional.of(map.get(ip));

    }

}
