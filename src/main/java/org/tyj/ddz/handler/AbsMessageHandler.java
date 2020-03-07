package org.tyj.ddz.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.tyj.util.MessageHandler;
import org.tyj.util.MessageHandlerUtil;

/**
 * 功能描述: <br>
 * 〈抽象逻辑处理类〉
 *
 * @return:
 * @since: 1.0.0
 * @Author:
 * @Date:
 */
public abstract class AbsMessageHandler implements MessageHandler, InitializingBean {

    protected Logger logger;
    public static String ACTION_NAME = "actionName";

    public AbsMessageHandler() {
        super();
        logger = LoggerFactory.getLogger(getClass().getName());
    }

    @Override
    public boolean newThread() {
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        MessageHandlerUtil.putMessageHandler(this);
    }
}
