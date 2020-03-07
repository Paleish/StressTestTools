/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: AIMessage
 * Author:   Administrator
 * Date:     2019/6/19 11:58
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * TanYujie       修改时间           版本号              描述
 */
package org.tyj.ddz.ai;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Administrator
 * @create 2019/6/19
 * @since 1.0.0
 */
public class AIMessage {

    private long executeTime;

    private String methodId;

    private String args;

    public AIMessage(long executeTime, String methodId, String args) {
        this.executeTime = executeTime;
        this.methodId = methodId;
        this.args = args;
    }

    public long getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(long executeTime) {
        this.executeTime = executeTime;
    }

    public String getMethodId() {
        return methodId;
    }

    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }
}