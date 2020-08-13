package org.tyj.ddz.match;

public interface MatchRequester {

    void requestMatchList(int userId);

    void requestApplyMatch(int userId, int matchId);
}
