package com.atguigu.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.UserInfo;
import com.atguigu.result.RedisMessageConstant;
import com.atguigu.result.Result;
import com.atguigu.result.ResultCodeEnum;
import com.atguigu.service.UserInfoService;
import com.atguigu.util.MD5;
import com.atguigu.util.SMSUtils;
import com.atguigu.util.ValidateCodeUtils;
import com.atguigu.vo.LoginVo;
import com.atguigu.vo.RegisterVo;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName UserInfoController
 * @Description TODO
 * @Author 郑帅
 * @DATE 2022/7/8 20:21
 * @Version 1.0
 */
@RestController
@RequestMapping(value="/userInfo")
public class UserInfoController {

    //@Reference
    //private JedisPool jedisPool;

    @Reference
    private UserInfoService userInfoService;

    //发送验证码
    //@GetMapping("/sendCode/{phone}")
    //public Result sendCode(@PathVariable String phone, HttpServletRequest request){
    //    //此处不采用阿里短信验证码
    //    //String code = "1111";
    //    String code = ValidateCodeUtils.generateValidateCode4String(6);
    //    request.getSession().setAttribute("code",code);
    //    return Result.ok(code);
    //}

    //阿里云短信验证码
    @GetMapping("/sendCode/{phone}")
    public Result sendCode(@PathVariable String phone, HttpServletRequest request){
        try {
            //生成一个验证码
            //Integer validateCode = ValidateCodeUtils.generateValidateCode(6);
            String validateCode = ValidateCodeUtils.generateValidateCode4String(6);
            //使用山东鼎信短信接口发送验证码
            SMSUtils.sendMessageCode(phone,validateCode.toString());
            //在redis中存储验证码，后面和用户输入的验证码进行比较，如果一样，则验证通过，否则验证不通过
            JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxTotal(200);
            poolConfig.setMaxIdle(32);
            poolConfig.setMaxWaitMillis(100 * 1000);
            poolConfig.setBlockWhenExhausted(true);
            poolConfig.setTestOnBorrow(true);  // ping  PONG
            //创建一个连接池
            JedisPool jedisPool = new JedisPool(poolConfig, "192.168.74.128", 6379);
            jedisPool.getResource().setex(phone + RedisMessageConstant.SENDTYPE_ORDER,10*60,validateCode.toString());
            //返回验证码
            System.out.println("手机号:"
                    + phone + "验证码："+validateCode);
            return Result.build(null,ResultCodeEnum.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(null,ResultCodeEnum.CODE_ERROR);
        }
    }

    /**
     * 会员注册
     * @param registerVo
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody RegisterVo registerVo, HttpServletRequest request){
        String nickName = registerVo.getNickName();
        String phone = registerVo.getPhone();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        //校验参数
        if(StringUtils.isEmpty(nickName) ||
                StringUtils.isEmpty(phone) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(code)) {
            return Result.build(null, ResultCodeEnum.PARAM_ERROR);
        }

        //判断验证码是否正确
        String codeStr =  (String)request.getSession().getAttribute("code");
        if(!code.equals(codeStr)){
            return Result.build(null,ResultCodeEnum.CODE_ERROR);
        }

        UserInfo userInfo = userInfoService.getByPhone(phone);
        if(null != userInfo) {
            return Result.build(null, ResultCodeEnum.PHONE_REGISTER_ERROR);
        }

        userInfo = new UserInfo();
        userInfo.setNickName(nickName);
        userInfo.setPhone(phone);
        userInfo.setPassword(MD5.encrypt(password));
        userInfo.setStatus(1);
        userInfoService.insert(userInfo);
        return Result.ok();
    }

    @GetMapping("/logout")
    public Result logout(HttpServletRequest request) {
        request.getSession().removeAttribute("USER");
        return Result.ok();
    }

    /**
     * 会员登录
     * @param loginVo
     * @param request
     * @return
     */
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo, HttpServletRequest request) {
        String phone = loginVo.getPhone();
        String password = loginVo.getPassword();

        //校验参数
        if(StringUtils.isEmpty(phone) ||
                StringUtils.isEmpty(password)) {
            return Result.build(null, ResultCodeEnum.PARAM_ERROR);
        }

        UserInfo userInfo = userInfoService.getByPhone(phone);
        if(null == userInfo) {
            return Result.build(null, ResultCodeEnum.ACCOUNT_ERROR);
        }

        //校验密码
        if(!MD5.encrypt(password).equals(userInfo.getPassword())) {
            return Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
        }

        //校验是否被禁用
        if(userInfo.getStatus() == 0) {
            return Result.build(null, ResultCodeEnum.ACCOUNT_LOCK_ERROR);
        }
        request.getSession().setAttribute("USER", userInfo);

        Map<String, Object> map = new HashMap<>();
        map.put("phone", userInfo.getPhone());
        map.put("nickName", userInfo.getNickName());
        return Result.ok(map);
    }

}