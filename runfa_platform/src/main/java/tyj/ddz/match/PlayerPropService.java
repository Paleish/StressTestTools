package tyj.ddz.match;

import java.util.List;

public interface PlayerPropService {

    List<PlayerProp> queryPlayerPropList(int userId);

    PlayerProp queryPlayerProp(int userId, int propId);

    int changePlayerProp(int userId, int propId, int count, int type);
}
