package com.hoolai.chatmonitor.open.service.impl;


import com.google.common.base.Strings;
import com.hoolai.chatmonitor.common.returnvalue.exception.HException;
import com.hoolai.chatmonitor.common.returnvalue.exception.HException.HExceptionBuilder;
import com.hoolai.chatmonitor.common.returnvalue.exception.enums.HExceptionEnum;
import com.hoolai.chatmonitor.open.dao.AdminGameDao;
import com.hoolai.chatmonitor.open.dao.AdminGroupDao;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminGame;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminGroup;
import com.hoolai.chatmonitor.open.service.AdminGameService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class AdminGameServiceImpl implements AdminGameService {

    @Resource
    private AdminGameDao adminGameDao;

    @Resource
    private AdminGroupDao adminGropuDao;

    @Override
    public AdminGame add(String gameName, Integer groupId) throws HException {
        if (Strings.isNullOrEmpty(gameName)) {
            throw HExceptionBuilder.newBuilder(HExceptionEnum.GAME_IS_INVALID).build();
        }
        existGroup(groupId);//检测group是否存

        AdminGame game = new AdminGame();
        game.setGameKey(getStringRandom(16));
        game.setGameName(gameName);
        game.setGroupId(groupId);
        adminGameDao.save(game);
        return game;
    }

    private String getStringRandom(int len) {
        StringBuilder val = new StringBuilder();
        Random random = new Random();
        // 参数length，表示生成几位随机数
        for (int i = 0; i < len; i++) {
            boolean isChar = random.nextBoolean();
            // 输出字母还是数字
            if (isChar) {
                // 输出是大写字母还是小写字母
                int temp = random.nextBoolean() ? 65 : 97;
                val.append((char) (random.nextInt(26) + temp));
            } else {
                val.append(String.valueOf(random.nextInt(10)));
            }
        }
        return val.toString();
    }

    @Override
    public AdminGame update(Long gameId, String gameName, Integer groupId) throws HException {
        if (gameId == null || gameId == 0) {
            throw HExceptionBuilder.newBuilder(HExceptionEnum.GAME_ID_IS_NULL).build();
        }
        AdminGame game = adminGameDao.get(gameId);

        if (game == null) {
            throw HExceptionBuilder.newBuilder(HExceptionEnum.GAME_NOT_IEXIST).build();
        }

        game.setGameName(Strings.isNullOrEmpty(gameName) ? game.getGameName() : gameName);

        if (!(null == groupId || groupId == 0)) {
            existGroup(groupId);//检测group是否存
            game.setGroupId(groupId);
        }

        adminGameDao.update(game);
        return game;
    }

    private void existGroup(Integer groupId) {
        if (groupId == null || groupId == 0) {
            throw HExceptionBuilder.newBuilder(HExceptionEnum.GROUP_ID_IS_NULL).build();
        }

        AdminGroup group = adminGropuDao.get(groupId);
        if (group == null) {
            throw HExceptionBuilder.newBuilder(HExceptionEnum.GROUP_NOT_EXIST).build();
        }
    }

    @Override
    public List<AdminGame> get(Long gameId, String gameName, Integer groupId) throws HException {
        AdminGame property = new AdminGame();
        property.setGameId(gameId);
        property.setGameName(gameName);
        property.setGroupId(groupId);
        return adminGameDao.get(property);
    }

}
