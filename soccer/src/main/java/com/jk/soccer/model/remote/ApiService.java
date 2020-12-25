package com.jk.soccer.model.remote;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    String formatPlayer = "playerData";
    String formatTeam = "teams?type=team";
    String formatMatch = "matchDetails";
    String formatMatches = "matches";

    @GET(formatPlayer)
    Call<ResponseBody> getPlayer(
            @Query("id") int playerId
    );

    @GET(formatTeam)
    Call<ResponseBody> getTeam(
            @Query("id") int teamId,
            @Query("tab") String tab
    );

    @GET(formatMatch)
    Call<ResponseBody>getMatch(
            @Query("matchId") int matchId
    );

    @GET(formatMatches)
    Call<ResponseBody>getMatchList(
            @Query("date") String date
    );
}
