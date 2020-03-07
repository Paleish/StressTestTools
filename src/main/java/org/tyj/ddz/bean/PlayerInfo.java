package org.tyj.ddz.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 功能描述: <br>
 * 〈玩家用户信息〉
 *
 * @return:
 * @since: 1.0.0
 * @Author:
 * @Date:
 */
public class PlayerInfo implements Serializable {

    private static final long serialVersionUID = -2564806577653693360L;

    private int userId;//从账号表获取到的userId Insert到这张表
    private String playerName;//玩家名字
    private String playerPortrait;//本地系统玩家头像
    private byte playerSex;//0 male  1 female 2unknow
    private String playerSign;//玩家签名
    private int playerExp;//玩家经验值，由    exp += 游戏场等级*参数 得出，可能会用于发牌洗牌时作为参数
    private int goldcoin;//用于游戏的金币
    private int diamond;//用于购买金币的人民币货币钻石
    private BigDecimal wincup;//获胜后获取到的奖杯
    private byte isAI;//0玩家，1AI
    private int winTimes;//胜利场数
    private int playTimes;//总场数
    private int signupDays;//签到天数
    private Date lastSignup;//最近签到时间
    private Date recordEndTime;

    public Date getRecordEndTime() {
        return recordEndTime;
    }

    public void setRecordEndTime(Date recordEndTime) {
        this.recordEndTime = recordEndTime;
    }

    public int getSignupDays() {
        return signupDays;
    }

    public void setSignupDays(int signupDays) {
        this.signupDays = signupDays;
    }

    public Date getLastSignup() {
        return lastSignup;
    }

    public void setLastSignup(Date lastSignup) {
        this.lastSignup = lastSignup;
    }

    public PlayerInfo(int userId, String playerName) {
        this.userId = userId;
        this.playerName = playerName;
    }

    public PlayerInfo(){

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGoldcoin() {
        return goldcoin;
    }

    public void setGoldcoin(int goldcoin) {
        this.goldcoin = goldcoin;
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

    public byte getPlayerSex() {
        return playerSex;
    }

    public void setPlayerSex(byte playerSex) {
        this.playerSex = playerSex;
    }

    public String getPlayerSign() {
        return playerSign;
    }

    public void setPlayerSign(String playerSign) {
        this.playerSign = playerSign;
    }

    public int getPlayerExp() {
        return playerExp;
    }

    public void setPlayerExp(int playerExp) {
        this.playerExp = playerExp;
    }

    public int getDiamond() {
        return diamond;
    }

    public void setDiamond(int diamond) {
        this.diamond = diamond;
    }

    public BigDecimal getWincup() {
        return wincup;
    }

    public void setWincup(BigDecimal wincup) {
        this.wincup = wincup;
    }

    public byte getIsAI() {
        return isAI;
    }

    public void setIsAI(byte isAI) {
        this.isAI = isAI;
    }

    public int getWinTimes() {
        return winTimes;
    }

    public void setWinTimes(int winTimes) {
        this.winTimes = winTimes;
    }

    public int getPlayTimes() {
        return playTimes;
    }

    public void setPlayTimes(int playTimes) {
        this.playTimes = playTimes;
    }
}
