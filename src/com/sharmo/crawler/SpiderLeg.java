package com.sharmo.crawler;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class SpiderLeg {
	
        //private RetrievedLinks retlinks = new RetrievedLinks("",0) ;
	private List<RetrievedLinks> links = new LinkedList<RetrievedLinks>(); // Just a list of URLs
	private Document htmlDocument; // This is our web page, or in other words, our document
        // We'll use a fake USER_AGENT so the web server thinks the robot is a normal web browser.
        private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
	
    
    /**
     * This performs all the work. It makes an HTTP request, checks the response, and then gathers
     * up all the links on the page. Perform a searchForWord after the successful crawl
     * 
     * @param url
     *            - The URL to visit
     * @return whether or not the crawl was successful
     */
    public boolean crawl(String url , int depth)
    {
        try
        {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            Document html = connection.get();
            this.htmlDocument = html;
            //Boolean processingSeedLink = false;
            
            if(connection.response().statusCode() == 200) // 200 is the HTTP OK status code
                                                          // indicating that everything is great.
            {
                System.out.println("\n**Visiting** Received web page at " + url);
            }
            if(!connection.response().contentType().contains("text/html"))
            {
                System.out.println("**Failure** Retrieved something other than HTML");
                return false;
            }
            Elements linksOnPage = html.select("a[href]");
            System.out.println("Found (" + linksOnPage.size() + ") links");
            for(Element link : linksOnPage)
            {
                RetrievedLinks retlinks = new RetrievedLinks() ;
                String absURL = link.absUrl("href");
               if ( absURL.startsWith("https://en.wikipedia.org/wiki/") 
                    &&  !(absURL.equals("https://en.wikipedia.org/wiki/Main_Page"))
                        &&  absURL.lastIndexOf(":")== 5 
                            && !absURL.contains("#") 
                                && !absURL.equals(url))                   
                            {
                                //System.out.println("--->>" + absURL);
                                //System.out.println (absURL.lastIndexOf(":"));
                                retlinks.setURL(absURL);
                                retlinks.setDepth(depth+1);
                                //System.out.println(retlinks.getURL());
                                //System.out.println(retlinks.getDepth());
                                //retlinks.setDepth(depth);
                                //this.links.add(link.absUrl("href"));
                                links.add(retlinks);
                            }
            }
            /*for (RetrievedLinks linkss : links)
            {
                System.out.println("--->>"+linkss.getURL());
                System.out.println("--->>"+linkss.getDepth());
            }*/
            return true;
        }
        catch(IOException ioe)
        {
            // We were not successful in our HTTP request
            return false;
        }
    }
    
    
    /**
     * Performs a search on the body of on the HTML document that is retrieved. This method should
     * only be called after a successful crawl.
     * 
     * @param searchWord
     *            - The word or string to look for
     * @return whether or not the word was found
     */
    public boolean searchForWord(String searchWord)
    {
        int index =0 ;  // Keeps Track of multiple occurances of the KeyPhrase in the body section
        int count = 0;  //Counting the number of times keyword appears legally (eg: 'index' as a term is legal but 'index' in hyperlinks is invalid )
        Boolean keyPhraseFound ;
        String pat = "/^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?$/";
        //Creating an obj of the regex pattern
        Pattern r = Pattern.compile(pat);
        // Defensive coding. This method should only be used after a successful crawl.
        if(this.htmlDocument == null)
        {
            System.out.println("ERROR! Call crawl() before performing analysis on the document");
            return false;
        }
        System.out.println("Searching for the word " + searchWord + "...");
        String bodyText = this.htmlDocument.body().text();
        System.out.println("bodyyyy ---- >>");
        System.out.println(bodyText);
        keyPhraseFound = bodyText.toLowerCase().contains(searchWord.toLowerCase());
        
        // To check in case of multiple occurances of KeyPhrase, if it is included in hyperlinks or plain text
        if (keyPhraseFound == true)
        {
            while ((index = bodyText.indexOf(searchWord, index)) != -1) // Taken from http://www.java2s.com/Code/Java/Data-Type/Countthenumberofinstancesofsubstringwithinastring.htm
            {
                System.out.println (index);     System.out.println (bodyText.charAt(index+searchWord.length()));
                /*System.out.println (bodyText.substring(index, index+5));
                System.out.println (bodyText.substring(index+4, index+10)); */
                if (/*bodyText.charAt(index+searchWord.length())=='.' &&*/ bodyText.charAt(index-1)=='/')
                    {keyPhraseFound = false; System.out.println ("**** inside if***" +keyPhraseFound); }//count++;}
                else
                    {keyPhraseFound = true; System.out.println (" inside else  "+keyPhraseFound); break;}
                
                index++; 
                
            }
        }
        //if (count>0) return true ; else return false;
        return keyPhraseFound ;
    }
    
	
    public List<RetrievedLinks> getLinks() 
	{
            return this.links;
	}
	

}
