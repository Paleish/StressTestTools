package tyj.ddz.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import tyj.util.MessageHandler;
import tyj.util.MessageHandlerUtil;

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

    public static String ACTION_NAME = "actionName";

    public AbsMessageHandler() {
        super();
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
