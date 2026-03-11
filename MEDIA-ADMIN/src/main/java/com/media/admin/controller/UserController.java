package com.media.admin.controller;

import com.media.admin.common.Result;
import com.media.admin.dto.UserInfoResponse;
import com.media.admin.entity.SspMedia;
import com.media.admin.repository.SspMediaRepository;
import com.media.admin.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

/**
 * 前端适配 - 用户控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private SspMediaRepository sspMediaRepository;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 获取用户信息 - 适配前端
     */
    @GetMapping("/info")
    public Result<UserInfoResponse> getUserInfo(HttpServletRequest request) {
        // 从 Header 获取 Token
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return Result.error(401, "未提供有效的Token");
        }
        token = token.substring(7);

        // 验证 Token
        if (!jwtUtil.validateToken(token)) {
            return Result.error(401, "Token无效或已过期");
        }

        // 获取用户ID
        Long userId = jwtUtil.getUserIdFromToken(token);
        if (userId == null) {
            return Result.error(401, "Token无效");
        }

        // 查询用户信息
        Optional<SspMedia> mediaOptional = sspMediaRepository.findById(userId);
        if (!mediaOptional.isPresent()) {
            return Result.error("用户不存在");
        }

        SspMedia sspMedia = mediaOptional.get();

        // 构建响应 (暂时使用固定角色和权限)
        UserInfoResponse response = UserInfoResponse.builder()
                .userId(String.valueOf(sspMedia.getId()))
                .userName(sspMedia.getName())
                .roles(Arrays.asList("R_SUPER"))
                .buttons(Arrays.asList("B_CODE1", "B_CODE2", "B_CODE3"))
                .email(sspMedia.getContactEmail() != null ? sspMedia.getContactEmail() : "art.design@gmail.com")
                .build();

        return Result.success("请求成功", response);
    }
}
