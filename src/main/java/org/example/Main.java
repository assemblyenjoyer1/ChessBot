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
import java.util.List;

import static java.lang.Thread.sleep;

public class Main {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\plasma\\Desktop\\chromedriver-win64\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        try {
            driver.get("https://www.chess.com/play/computer"); // Replace with the actual URL
            while(true){

                sleep(500);
                String fen = getFen(driver);
                System.out.println("----- CURRENT BOARD -----");
                System.out.println(fen);
                System.out.println("----- CURRENT BOARD -----");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            driver.quit();
        }
    }

    public static String getFen(WebDriver driver) {
        StringBuilder fen = new StringBuilder();
        for (int i = 8; i >= 1; i--) {
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
                    fen.append(pieceName.charAt(1) == 'w' ? Character.toUpperCase(pieceName.charAt(1)) : Character.toLowerCase(pieceName.charAt(1)));
                } catch (Exception e) {
                    fen.append("E");
                }
            }
            fen.append("\n");
        }
        return fen.substring(0, fen.length() - 1);
    }

}
