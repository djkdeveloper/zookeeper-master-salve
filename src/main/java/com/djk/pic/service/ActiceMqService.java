package com.djk.pic.service;

/**
 * 服务接口
 *
 * @author dujinkai
 */
@FunctionalInterface
public interface ActiceMqService {
    /**
     * 处理activema发来的消息
     *
     * @param message
     */
    void handelMessage(String message);
}
