package com.hou.web;

import com.hou.dto.Exposer;
import com.hou.dto.SeckillExcution;
import com.hou.dto.SeckillStateEnum;
import com.hou.exception.MiaoShaException;
import com.hou.pojo.Seckill;
import com.hou.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @Author: edison
 * @Date: 2020/2/1 10时46分
 * @Description:
 */
@Controller
public class SeckillController {

    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = {"/list"}, method = RequestMethod.GET)
    public String list(Model model) {
        //我们采用第三种方式传值
        List<Seckill> seckillList = seckillService.getSeckillList();
        model.addAttribute("list", seckillList);
        return "list";  //转发给 WEB-INF/jsp/list.jsp
    }

    @RequestMapping(value = {"/{seckillId}/detail"}, method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
        //@PathVariable 指定{}中的变量名，可以从String-->Long
        //采用Model传值，原理是httpServletRequest.setAttribute()
        if (seckillId == null) {
            return "redirect:/list"; //重定向 http://localhost:8161/Seckill/list
        }
        //seckillId 存在
        Seckill seckillById = seckillService.getSeckillById(seckillId);
        System.out.println("detail方法中： " + seckillId);

        /**
         * return: forward: 不走视图解析器，所以不会被解析成jsp
         * return "forward:/hello" => 转发到能够匹配 /hello 的 controller 上
         * forward如果以 “/” 开头并且使用相对路径，那么会默认加上contextPath。
         * retutn /hello，框架还是转发到了 /WEB-INF/jsp/hello.jsp，即 /WEB-INF/jsp//hello.jsp 等同于/WEB-INF/jsp/hello.jsp。
         */
        if (seckillById == null) {
            return "forward:/list"; //转发到 list Controller
        }
        model.addAttribute("seckill", seckillById);
        return "detail"; //转发给 jsp
    }


    //暴漏秒杀接口
    @RequestMapping(value = {"/{seckillId}/exposer"}, method = RequestMethod.POST,
            produces = {"application/json;charset=utf-8"})
    @ResponseBody   //json直接响应，需要指定返回的编码类型，filter或者jsp都无法控制
    public Exposer exposer(@PathVariable("seckillId") Long seckillId) {
        Exposer exposer = null;
        try {
            exposer = seckillService.exportSeckillUrl(seckillId);
            System.out.println("Controller exposer方法" + exposer);
            return exposer;
            //成功 true  id md5
            //失败  false 各个时间
        } catch (MiaoShaException e) {
            //runtime异常
            exposer = new Exposer(false, e.getMessage());
            return exposer;
        } catch (Exception e) {
            e.printStackTrace();
            return new Exposer(false,"内部异常");
        }
    }


    //执行秒杀
    @RequestMapping(value = {"/{seckillId}/{md5}/execution"}, method = RequestMethod.POST,
            produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public SeckillExcution execute(@PathVariable("seckillId") Long seckillId, @PathVariable("md5") String md5,
                        @CookieValue(value = "killPhone", required = false) Long userPhone) {
        // cookie为空的话会报错，需要加给required =false 可以忽略cookie
        //userphone通过cookie来传递
        if (userPhone == null) {
            return new SeckillExcution(false, SeckillStateEnum.NO_REGISTER);//未注册
        }

        SeckillExcution seckillExcution = null;
        try {
            seckillExcution = seckillService.executeSeckill(seckillId, userPhone, md5);
            return seckillExcution;


        } catch (MiaoShaException e) {
            //service层抛出runtime异常
            //service未抛出过此异常，我们这里直接返回内部异常
            seckillExcution = new SeckillExcution(false, SeckillStateEnum.INNER_ERROR);
        } catch (Exception e) {
            //其他异常
            e.printStackTrace();

        }

        return seckillExcution; //null
    }

    @RequestMapping(value = {"/time/now"},method = {RequestMethod.GET})
    @ResponseBody
    public Long time(){
        Date now = new Date();
        return now.getTime();
    }



}
