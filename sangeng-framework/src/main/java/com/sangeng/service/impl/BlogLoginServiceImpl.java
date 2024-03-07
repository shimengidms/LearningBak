package com.sangeng.service.impl;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.LoginUser;
import com.sangeng.domain.entity.User;
import com.sangeng.domain.vo.BlogUserLoginVo;
import com.sangeng.domain.vo.UserInfoVo;
import com.sangeng.service.BlogLoginService;
import com.sangeng.utils.BeanCopyUtils;
import com.sangeng.utils.JwtUtil;
import com.sangeng.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author: 甘廿甘廿
 * @create: 2023-10-21 17:25
 * @Description:
 */
@Service("blogLoginService")
public class BlogLoginServiceImpl implements BlogLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        // 通过config继承WebSecurityConfigurerAdapter获得对应bean
        // 这里调用会一直调用到UserDetailsService接口实现类，可以自己自定义
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        // 判断是否认证通过
        if(authentication == null){
            throw new RuntimeException("用户名或密码错误");
        }
        // 获取用户信息，生成token
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        String jwt = JwtUtil.createJWT(userId.toString());
        // 把用户信息存入redis
        redisCache.setCacheObject("bloglogin:"+userId, loginUser);

        // 把token和userinfo封装起来并返回
        UserInfoVo infoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo blogUserLoginVo = new BlogUserLoginVo(jwt, infoVo);
        return ResponseResult.okResult(blogUserLoginVo);
    }

    @Override
    public ResponseResult logout() {
        // 获取token， 解析获取userId
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 获取userId
        Long userId = loginUser.getUser().getId();
        redisCache.deleteObject("bloglogin:" + userId);
        return ResponseResult.okResult();
    }
}
