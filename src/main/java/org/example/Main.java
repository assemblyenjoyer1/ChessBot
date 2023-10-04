package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static java.lang.Thread.sleep;

public class Main {

    public static void main(String[] args) {
        Stockfisch fishy = new Stockfisch();
        boolean success = fishy.startEngine();
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\plasma\\Desktop\\chromedriver-win64\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        String board = """
        RNBQKBNR
        PPPPPPPP
        OOOOOOOO
        OOOOOOOO
        OOOOOOOO
        POOOOOOO
        OPPPPPPP
        RNBQKBNR""";

        String previousFen = null;
        System.out.println("----- CURRENT BOARD -----");
        System.out.println(board);
        System.out.println("----- CURRENT BOARD -----");
        int a = 0;

        if(success){
            try {
                fishy.sendCommand("uci");
                if (success){
                    System.out.println("started engine");
                }else{
                    System.out.println("failed to start engine");
                }
                driver.get("https://www.chess.com/play/computer");
                while (true) {
                    Thread.sleep(500);
                    String fen = getFen(driver);
                    if (previousFen != null && !fen.equals(previousFen)) {
                        System.out.println("----- CURRENT BOARD -----");
                        //System.out.println(fen);
                        //System.out.println(fishy.getLegalMoves(fen));
                        // Use Stockfish to calculate the best move
                        String bestMove = fishy.getBestMove(fen, 1000); // Adjust the wait time as needed

                        // Print the suggested best move
                        System.out.println("Best Move: " + bestMove);

                        System.out.println("----- CURRENT BOARD -----");
                        if (a >= 10) {
                            break;
                        }
                        a++;
                    }

                    previousFen = fen;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                driver.quit();
                fishy.stopEngine();
            }
        }else{
            System.out.println("lol noob");
        }
    }

    public static String getFen(WebDriver driver) {
        StringBuilder fen = new StringBuilder();
        for (int i = 8; i >= 1; i--) {
            int  nums = 0;
            for (int j = 1; j <= 8; j++) {
                try {
                    WebElement piece = driver.findElement(By.xpath("//div[contains(@class, 'piece') and contains(@class, 'square-" + j + i + "')]"));
                    String[] pieceClasses = piece.getAttribute("class").split(" ");
                    String pieceName;
                    if (!pieceClasses[1].startsWith("s")) {
                        pieceName = pieceClasses[1];
                    } else {
                        pieceName = pieceClasses[2];
                    }
                    char firstLetter = pieceName.charAt(0);
                    char secondLetter = pieceName.charAt(1);
                    if (firstLetter == 'w') {
                        secondLetter = Character.toUpperCase(secondLetter);
                    }
                    fen.append(secondLetter);
                } catch (Exception e) {
                    nums++;
                }
            }
            fen.append(nums);
            fen.append("/");
        }
        return fen.substring(0, fen.length() - 1);
    }

    public static char[][] transformBoardToArray(String board){
        char[][] chars = new char[8][8];
        for(int i = 0; i < chars.length; i++){
            for(int j = 0; j < chars[i].length; j++){
                chars[i][j] = board.charAt(i+j);
            }
        }
        return chars;
    }

}
