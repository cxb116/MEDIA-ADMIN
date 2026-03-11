package com.media.admin.controller;

import com.media.admin.common.Result;
import com.media.admin.dto.MediaLoginRequest;
import com.media.admin.dto.MediaLoginResponse;
import com.media.admin.dto.MediaRegisterRequest;
import com.media.admin.entity.SspMedia;
import com.media.admin.service.SspMediaService;
import com.media.admin.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 媒体用户认证控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/media/auth")
public class MediaAuthController {

    @Autowired
    private SspMediaService sspMediaService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 媒体用户注册
     */
    @PostMapping("/register")
    public Result<?> register(@Validated @RequestBody MediaRegisterRequest request) {
        log.info("媒体用户注册请求: account={}", request.getAccount());
        return sspMediaService.register(request);
    }

    /**
     * 媒体用户登录
     */
    @PostMapping("/login")
    public Result<MediaLoginResponse> login(@Validated @RequestBody MediaLoginRequest request) {
        log.info("媒体用户登录请求: account={}", request.getAccount());
        return sspMediaService.login(request);
    }

    /**
     * 获取媒体用户信息
     */
    @GetMapping("/info")
    public Result<SspMedia> getMediaInfo(@RequestParam Long id) {
        log.info("获取媒体用户信息: id={}", id);
        return sspMediaService.getMediaInfo(id);
    }

    /**
     * 验证Token
     */
    @GetMapping("/validate")
    public Result<Map<String, Object>> validateToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return Result.error(401, "未提供有效的Token");
        }
        token = token.substring(7); // 去掉 "Bearer " 前缀
        return sspMediaService.validateToken(token);
    }

    /**
     * 登出
     */
    @PostMapping("/logout")
    public Result<?> logout() {
        return Result.success("登出成功");
    }
}
