/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: SerializeUtil
 * Author:   tyj
 * Date:     2019/3/27 18:09
 * Description: 序列化和反序列化工具类
 * History:
 * <author>          <time>          <version>          <desc>
 * TanYujie       修改时间           版本号              描述
 */
package tyj.util;

/**
 * 〈一句话功能简述〉<br>
 * 〈序列化和反序列化工具类〉
 *
 * @author tyj
 * @create 2019/3/27
 * @since 1.0.0
 */

import java.io.*;

public class SerializeUtil {

    //序列化
    public static byte[] serialize(Object obj) {
        if (null == obj) {
            return null;
        }
        ObjectOutputStream obi;
        ByteArrayOutputStream bai;
        try {
            bai = new ByteArrayOutputStream();
            obi = new ObjectOutputStream(bai);
            obi.writeObject(obj);
            byte[] byt = bai.toByteArray();
            return byt;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //反序列化
    public static Object unserizlize(byte[] byt) {
        if (null == byt) {
            return null;
        }
        ObjectInputStream oii;
        ByteArrayInputStream bis;
        bis = new ByteArrayInputStream(byt);
        try {
            oii = new ObjectInputStream(bis);
            Object obj = oii.readObject();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}