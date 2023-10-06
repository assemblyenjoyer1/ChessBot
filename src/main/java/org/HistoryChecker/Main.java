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
        ArrayList<String> userNames = new ArrayList<>();
        ArrayList<String> intList = new ArrayList<>();
        Gson gson = new Gson();

        LeagueService leagueService = new LeagueService();
        Summoner summoner = leagueService.getSummonerByName(Region.EUW1, "Vinciors");
        Summoner targetSummoner = leagueService.getSummonerByName(Region.EUW1, "flamemountain");
        //String puuid = "qjcTTW5H-5pJHb7cec5n7t8WrUZuKNXSmHlbUUCLy9D_lz8lu9DLCucYPXfURF32SlBrqev226gT9Q";
        String puuid = summoner.getPuuid();
        String targetPuuid = targetSummoner.getPuuid();
        System.out.println("target puuid: " + targetPuuid);
        int counter = 0;
        int startIndex = 0;
        boolean lastRotation = true;

        while(lastRotation){
            System.out.println("Current StartIndex: " + startIndex);
            HttpResponse<JsonNode> result = leagueService.getMatchHistoryResponse(MatchRegion.EUROPE, puuid, startIndex);
            startIndex += 100;
            JSONArray matchHistoryArrayJson = result.getBody().getArray();
            if(matchHistoryArrayJson.length() < 100){
                lastRotation = false;
                System.out.println("Last Rotation started");
            }
            String[] stringArray = gson.fromJson(String.valueOf(matchHistoryArrayJson), String[].class);
            System.out.println("Result from api received - we got: " + stringArray.length + " matches.");
            if(stringArray.length < 10){
                break;
            }
            for (String x : stringArray) {
                sleep(1500);
                Match match = leagueService.getMatchHistoryData(MatchRegion.EUROPE, x);

                if (match.getInfo() != null) {
                    boolean foundSomething = false;
                    for (Participant participant : match.getInfo().getParticipants()) {
                        if (participant.getPuuid().equalsIgnoreCase(targetPuuid)) {
                            System.out.println("Played a game together. Match-ID: " + x);
                            foundSomething = true;
                            counter++;
                            gameIds.add(x);
                        }
                        if(participant.getPuuid().equalsIgnoreCase(puuid)){
                            if(!userNames.contains(participant.getSummonerName())){
                                System.out.println("new username found - added it to list");
                                userNames.add(participant.getSummonerName());

                                userNames.forEach(System.out::println);
                            }
                            if(participant.getDeaths() > 15){
                                System.out.println("int game found - added it to list");
                                intList.add(match.getMetadata().getMatchId());
                            }
                        }
                    }
                    if (!foundSomething) {
                        System.out.println("Checked game, nothing found. Match-ID: " + x);
                    }
                    System.out.println("Current amount of games found: " + counter);
                } else {
                    System.out.println("Match info is null for Match-ID: " + x);
                }
            }

        }

        System.out.println("Finished Program. Games together played: " + counter);
        System.out.println("Game IDs:");
        gameIds.forEach(System.out::println);
        System.out.println(" - - - - - - - - - - -");
        System.out.println("Now printing user names the user had");
        userNames.forEach(System.out::println);
        System.out.println("int games:");
        intList.forEach(System.out::println);
    }

    private static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
