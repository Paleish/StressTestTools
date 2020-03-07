/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: Result
 * Author:   Administrator
 * Date:     2019/7/11 15:48
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * TanYujie       修改时间           版本号              描述
 */
package org.tyj.ddz.mapper;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Administrator
 * @create 2019/7/11
 * @since 1.0.0
 */
public class ResultB implements Serializable {

    private static final long serialVersionUID = -8979822609293307602L;
    private int userId;
    private int finishTask;
    private String playerName;
    private String playerPortrait;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFinishTask() {
        return finishTask;
    }

    public void setFinishTask(int finishTask) {
        this.finishTask = finishTask;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerPortrait() {
        return playerPortrait;
    }

    public void setPlayerPortrait(String playerPortrait) {
        this.playerPortrait = playerPortrait;
    }
}