package com.jk.soccer.model.remote;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    String formatPlayer = "playerData";
    String formatTeam = "teams?type=team";
    String formatMatch = "matchDetails";
    String formatLeague = "leagues?type=league";

    @GET(formatPlayer)
    Call<ResponseBody> getPlayer(
            @Query("id") int playerID
    );

    @GET(formatTeam)
    Call<ResponseBody> getTeam(
            @Query("id") int teamID,
            @Query("tab") String tab
    );

    @GET(formatMatch)
    Call<ResponseBody>getMatch(
            @Query("matchId") int matchID
    );

    @GET(formatLeague)
    Call<ResponseBody>getLeague(
            @Query("id") int leagueID,
            @Query("tab") String tab
    );
}
