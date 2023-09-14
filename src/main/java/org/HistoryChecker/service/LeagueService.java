package org.HistoryChecker.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.HistoryChecker.model.MatchRegion;
import org.HistoryChecker.model.Region;
import org.HistoryChecker.model.v4Summoner.Summoner;
import org.HistoryChecker.model.v4league.LeagueEntry;
import org.HistoryChecker.model.v5Match.Match;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Set;

public class LeagueService {

    Gson gson = new Gson();

    public Set<LeagueEntry> getLeagueEntryByEncryptedSummonerId(Region region, String encryptedSummonerId){
        HttpResponse<JsonNode> matchResponse;
        Set<LeagueEntry> leagueEntries = null;
        try{
            String apiKey = System.getenv("API_KEY");
            String matchUrl = "https://" + region + ".api.riotgames.com/lol/league/v4/entries/by-summoner/" + encryptedSummonerId + "?api_key=" + apiKey;
            matchResponse = Unirest.get(matchUrl).asJson();
            JSONArray leagueEntryArray = matchResponse.getBody().getArray();
            Type setType = new TypeToken<Set<LeagueEntry>>(){}.getType();
            leagueEntries = gson.fromJson(String.valueOf(leagueEntryArray), setType);
        }catch(UnirestException e){
            System.out.println("unable to get league entry for encrypted summoner id: " + encryptedSummonerId);
        }
        return leagueEntries;
    }

    public Match getMatchHistoryData(MatchRegion matchRegion, String matchId){
        HttpResponse<JsonNode> matchResponse;
        Match match = null;
        try{
            String apiKey = System.getenv("API_KEY");
            String matchUrl = "https://" + matchRegion + ".api.riotgames.com/lol/match/v5/matches/" + matchId + "?api_key=" + apiKey;
            matchResponse = Unirest.get(matchUrl).asJson();
            JSONObject matchHistoryJSON = matchResponse.getBody().getObject();
            match = gson.fromJson(String.valueOf(matchHistoryJSON), Match.class);
        }catch(UnirestException e){
            System.out.println("unable to get matchhistory root");
        }
        return match;
    }

    public Match getMatchHistory(MatchRegion matchRegion, String puuid){
        HttpResponse<JsonNode> matchResponse;
        Match match = null;
        try{
            String apiKey = System.getenv("API_KEY");
            String matchUrl = "https://" + matchRegion + ".api.riotgames.com/lol/match/v5/matches/" + puuid + "/ids?type=ranked&start=0&count=100" + "?api_key=" + apiKey;
            System.out.println(matchUrl);
            matchResponse = Unirest.get(matchUrl).asJson();
            JSONObject matchHistoryJSON = matchResponse.getBody().getObject();
            match = gson.fromJson(String.valueOf(matchHistoryJSON), Match.class);
        }catch(UnirestException e){
            System.out.println("unable to get matchhistory root");
        }
        return match;
    }

    public HttpResponse<JsonNode> getMatchHistoryResponse(MatchRegion matchRegion, String puuid){
        HttpResponse<JsonNode> matchHistoryResponse = null;
        try{
            String apiKey = System.getenv("API_KEY");
            String matchHistoryUrl = "https://" + matchRegion + ".api.riotgames.com/lol/match/v5/matches/by-puuid/" + puuid + "/ids?queue=420&type=ranked&start=0&count=100&api_key=" + apiKey;
            matchHistoryResponse = Unirest.get(matchHistoryUrl).asJson();
        }catch(UnirestException e){
            System.out.println("unable to get matchhistory response");

        }
        return matchHistoryResponse;
    }

    public Summoner getSummonerByName(Region region, String summonerName){
        try{
            HttpResponse<JsonNode> summonerResponse;
            String apiKey = System.getenv("API_KEY");
            String summonerUrl = "https://" + region + ".api.riotgames.com/lol/summoner/v4/summoners/by-name/" + summonerName + "?api_key=" + apiKey;
            summonerResponse = Unirest.get(summonerUrl).asJson();
            JSONObject summonerJson = summonerResponse.getBody().getObject();
            Summoner summoner = gson.fromJson(String.valueOf(summonerJson), Summoner.class);
            return summoner;
        }catch(UnirestException e){
            System.out.println("unable to get summoner for name:" + summonerName);
        }
        return null;
    }

}
