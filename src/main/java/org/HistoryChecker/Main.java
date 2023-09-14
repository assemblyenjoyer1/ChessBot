package org.HistoryChecker;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.HistoryChecker.model.MatchRegion;
import org.HistoryChecker.model.Region;
import org.HistoryChecker.model.v4Summoner.Summoner;
import org.HistoryChecker.model.v5Match.Match;
import org.HistoryChecker.model.v5Match.Participant;
import org.HistoryChecker.service.LeagueService;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<String> gameIds = new ArrayList<>();
        Gson gson = new Gson();

        LeagueService leagueService = new LeagueService();
        Summoner summoner = leagueService.getSummonerByName(Region.EUN1, "SPYROPOULOS 1V9");
        String puuid = summoner.getPuuid();
        HttpResponse<JsonNode> result = leagueService.getMatchHistoryResponse(MatchRegion.EUROPE, puuid);
        JSONArray matchHistoryArrayJson = result.getBody().getArray();
        String[] stringArray = gson.fromJson(String.valueOf(matchHistoryArrayJson), String[].class);

        int counter = 0;
        for (String x : stringArray) {
            sleep(1000);
            Match match = leagueService.getMatchHistoryData(MatchRegion.EUROPE, x);
            boolean foundSomething = false;
            if (match.getInfo().getParticipants() == null){
                return;
            }
            for (Participant participant : match.getInfo().getParticipants()) {
                if (participant.getSummonerName().equalsIgnoreCase("lethality kayle")) {
                    System.out.println("Played a game together. Match-ID: " + x);
                    foundSomething = true;
                    counter++;
                    gameIds.add(x);
                    break;
                }
            }
            if (!foundSomething) {
                System.out.println("Checked game, nothing found. Match-ID: " + x);
            }
            System.out.println("Current amount of games found: " + counter);
        }

        System.out.println("Finished Program. Games together played: " + counter);
        System.out.println("Game IDs:");
        gameIds.forEach(System.out::println);
    }

    private static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}

   // Goleaun, tempbkvrkulkhccw, Egirl Uwu, Estsawy, L9 DOG MOVEMENT, VIP the best, why he left, Dava Peňašku, EDG Killaki, fiflak, tekkmaus, lluko4 twitter, u s, YassouT31, wxks meow, 65472572547 v2, homer, ALA KING, PORNSTAR XERATH, gliding atm, Leaguesharp, 72119, dj khaled khaled, Hannah rose, l9 psz ap0 rat,