package com.codeimpact;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class UrlCrawler {

    public static void main(String[] args) throws IOException {

        Document doc = Jsoup.connect("http://www.startpagina.nl").get();
        Elements links = doc.select("a");
        for (Element e: links) {
            System.out.println(e.attr("abs:href"));
        }
    }
}
