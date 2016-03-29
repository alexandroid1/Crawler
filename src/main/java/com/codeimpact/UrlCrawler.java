package com.codeimpact;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class UrlCrawler {

    public static DB db = new DB();

    public static void main(String[] args) throws SQLException, IOException {
            db.runSql2("TRUNCATE Record;");
            //processPage("http://www.mit.edu");
            processPage("http://www.startpagina.nl");
            /* Document doc = Jsoup.connect("http://www.startpagina.nl").get();
             Elements links = doc.select("a");
             for (Element e: links) {
             System.out.println(e.attr("abs:href"));
              }*/
    }

    public static void processPage(String URL) throws SQLException, IOException{
            //check if the given URL is already in database
            String sql = "select * from Record where URL = '"+URL+"'";
            ResultSet rs = db.runSql(sql);
            if(rs.next()){

            }else{
                //store the URL to database to avoid parsing again
                sql = "INSERT INTO  Crawler.Record " + "(URL) VALUES " + "(?);";
                PreparedStatement stmt = db.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1,URL);
                stmt.execute();

                //get useful information
               // Document doc = Jsoup.connect("http://www.mit.edu/").get();
                Document doc = Jsoup.connect("http://www.startpagina.nl/").get();

                System.out.println(URL);

/*                if(doc.text().contains("research")){
                    System.out.println(URL);
                }*/

                //get all links and recursively call the processPage method
                Elements questions = doc.select("a[href]");

                for(Element link: questions){
                    if(link.attr("href").contains("startpagina.nl"))
                        processPage(link.attr("abs:href"));

                    /*if(link.attr("href").contains("mit.edu"))
                        processPage(link.attr("abs:href"));*/
                }

            }
    }

}

