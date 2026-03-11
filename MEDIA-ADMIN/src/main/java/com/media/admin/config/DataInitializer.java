package com.media.admin.config;

import com.media.admin.entity.SspMedia;
import com.media.admin.repository.SspMediaRepository;
import com.media.admin.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 数据初始化器
 */
@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private SspMediaRepository sspMediaRepository;

    @Override
    public void run(String... args) throws Exception {
        initSuperUser();
    }

    /**
     * 初始化超级管理员
     */
    private void initSuperUser() {
        if (!sspMediaRepository.existsByAccount("lefei@123")) {
            SspMedia lefeiUser = new SspMedia();
            lefeiUser.setName("lefei");
            lefeiUser.setAccount("lefei@123");
            lefeiUser.setPassword(PasswordUtil.encode("lefei@123"));
            lefeiUser.setMediaCompanyName("Media Company");
            lefeiUser.setMediaCompanyShort("MC");
            lefeiUser.setEnable(1);
            lefeiUser.setContactEmail("lefei@media.com");
            lefeiUser.setCreateBy("system");

            sspMediaRepository.save(lefeiUser);
            log.info("用户 lefei@123 创建成功");
        } else {
            log.info("用户 lefei@123 已存在");
        }

        if (!sspMediaRepository.existsByAccount("Super")) {
            SspMedia superUser = new SspMedia();
            superUser.setName("Super");
            superUser.setAccount("Super");
            superUser.setPassword(PasswordUtil.encode("123456"));
            superUser.setMediaCompanyName("Super Admin Company");
            superUser.setMediaCompanyShort("SAC");
            superUser.setEnable(1);
            superUser.setContactEmail("art.design@gmail.com");
            superUser.setCreateBy("system");

            sspMediaRepository.save(superUser);
            log.info("超级管理员 Super 创建成功");
        } else {
            log.info("超级管理员 Super 已存在");
        }
    }
}
