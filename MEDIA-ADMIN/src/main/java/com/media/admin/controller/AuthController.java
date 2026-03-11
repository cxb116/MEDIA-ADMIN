package com.media.admin.controller;

import com.media.admin.common.Result;
import com.media.admin.dto.AuthLoginRequest;
import com.media.admin.dto.AuthLoginResponse;
import com.media.admin.entity.SspMedia;
import com.media.admin.repository.SspMediaRepository;
import com.media.admin.util.JwtUtil;
import com.media.admin.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * 前端适配 - 认证控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private SspMediaRepository sspMediaRepository;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 登录接口 - 适配前端
     */
    @PostMapping("/login")
    public Result<AuthLoginResponse> login(@Validated @RequestBody AuthLoginRequest request) {
        log.info("前端适配登录请求: userName={}", request.getUserName());

        // 使用 userName 作为 account 查询
        Optional<SspMedia> mediaOptional = sspMediaRepository.findByAccount(request.getUserName());
        if (!mediaOptional.isPresent()) {
            return Result.error("账号或密码错误");
        }

        SspMedia sspMedia = mediaOptional.get();

        // 验证密码
        // if (!PasswordUtil.matches(request.getPassword(), sspMedia.getPassword())) {
        //     return Result.error("账号或密码错误");
        // }

        if (!Objects.equals(request.getPassword(), sspMedia.getPassword())) {
            return Result.error("账号或密码错误");
        }

        // 检查账号状态
        if (sspMedia.getEnable() == 0) {
            return Result.error("账号已被禁用");
        }
        if (sspMedia.getEnable() == 2) {
            return Result.error("账号正在审核中");
        }
        if (sspMedia.getEnable() == 3) {
            return Result.error("账号审核未通过");
        }

        // 生成 Token
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", sspMedia.getId());
        claims.put("userName", sspMedia.getName());
        claims.put("account", sspMedia.getAccount());
        String token = jwtUtil.generateToken(claims);

        // 生成 Refresh Token (有效期更长)
        Map<String, Object> refreshClaims = new HashMap<>();
        refreshClaims.put("userId", sspMedia.getId());
        refreshClaims.put("type", "refresh");
        String refreshToken = jwtUtil.generateToken(refreshClaims);

        AuthLoginResponse response = AuthLoginResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();

        return Result.success("登录成功", response);
    }
}
