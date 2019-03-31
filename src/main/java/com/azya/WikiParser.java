package com.azya;

import org.jsoup.Jsoup;

import java.net.MalformedURLException;
import java.net.URL;

public class WikiParser {
    String url;

    public WikiParser(String url) {


    }

    public void scrapePage(){
        Jsoup.parse(getHtml());
    }

    public String getHtml(){
        URL urlObj;
        try {
            urlObj = new URL(this.url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
