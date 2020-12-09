package com.jk.soccer.data.remote;

import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    String formatPlayerString = "playerData";
    String formatPlayerImage = "image_resources/playerimages/{filename}";
    String formatTeamString = "teams";
    String formatTeamImage = "images/team/{teamId}";
    String formatTeamImageSmall = "images/team/{teamId}_xsmall";
    String formatMatchString = "matches";

    //@Headers({"Accept: application/json"})
    @GET(formatPlayerString)
    Call<ResponseBody> getPlayerString(
            @Query("id") int playerId
    );

    @GET(formatPlayerImage)
    Call<ResponseBody>getPlayerImage(
            @Path("filename") String filename
    );

    @GET(formatTeamString)
    Call<ResponseBody> getTeamString(
            @Query("id") int teamId,
            @Query("tab") String tab,
            @Query("type") String type
    );

    @GET(formatTeamImage)
    Call<ResponseBody>getTeamImage(
            @Path("teamId") int teamId
    );

    @GET(formatTeamImageSmall)
    Call<ResponseBody>getTeamImageSmall(
            @Path("teamId") int teamId
    );

    @GET(formatMatchString)
    Call<ResponseBody>getMatchString(
            @Query("date") Date date
    );
}
