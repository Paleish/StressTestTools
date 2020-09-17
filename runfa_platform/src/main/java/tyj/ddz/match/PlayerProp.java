package tyj.ddz.match;

import java.io.Serializable;

public class PlayerProp implements Serializable {

    private static final long serialVersionUID = -6749323763957190077L;
    private int userId;//userId
    private int propId;//道具id
    private int count;//道具数量

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPropId() {
        return propId;
    }

    public void setPropId(int propId) {
        this.propId = propId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
