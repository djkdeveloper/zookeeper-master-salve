package com.djk.pic.controller;

import com.djk.pic.bean.ConfigBean;
import com.djk.pic.bean.PictureServer;
import com.djk.pic.service.PictureService;
import com.djk.pic.utils.ApplicationContextHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 观察当前每台服务器缓存队列的数量
 *
 * @author dujinkai
 */
@Controller
public class PictureWatchController {
    /**
     * 注入图片服务接口
     */
    @Resource
    private PictureService pictureService;

    @RequestMapping("watch")
    @ResponseBody
    public String watch() {
        return pictureService.getAllPicServerInfo();
    }


    @RequestMapping("best")
    @ResponseBody
    public String getbest() {
        Optional<PictureServer> optional = pictureService.getBestPictureServer();
        if (optional.isPresent()) {
            return optional.get().toString();
        } else {
            return "";
        }

    }


}
