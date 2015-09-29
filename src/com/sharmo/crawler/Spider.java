package com.sharmo.crawler;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Sharmodeep
 *
 */
public class Spider {

    // Fields
    private final int MAX_PAGES_TO_SEARCH = 1000; // For unfocused crawling
    private static final int MAX_DEPTH_FOR_SEARCH = 5;
    private Set<String> pagesVisited = new HashSet<String>();
    private LinkedList<RetrievedLinks> pagesToVisit = new LinkedList<RetrievedLinks>();
    public static Set<String> relevantPages = new HashSet<String>();
    private RetrievedLinks retlinks ;
    
    
    /**
     * Returns the next URL to visit (in the order that they were found). We also do a check to make
     * sure this method doesn't return a URL that has already been visited.
     * 
     * @return
     */
    private RetrievedLinks nextUrl()
    {
        RetrievedLinks nextUrlToProcess = null;
        
        do
        {
            System.out.println("pagesToVisit size + nextURL -->> " + pagesToVisit.size() );
            if (pagesToVisit.size()>0)
                   nextUrlToProcess = pagesToVisit.removeFirst(); //--------- FOR DFS
                   //nextUrlToProcess = pagesToVisit.remove(0);  //---------- FOR BFS
            else
                {
                    System.out.println("NO MORE PAGES TO CRAWL");
                    //break;
                    return null;
                }
            //nextUrl = retlinks.getURL();
        } while(pagesVisited.contains(nextUrlToProcess.getURL()) || nextUrlToProcess.getDepth()>5);
        pagesVisited.add(nextUrlToProcess.getURL());
        return nextUrlToProcess;
    }
    
    
    /**
     * Our main launching point for the Spider's functionality. Internally it creates spider legs
     * that make an HTTP request and parse the response (the web page).
     * 
     * @param url
     *            - The starting point of the spider
     * @param searchWord
     *            - The word or string that you are searching for
     */
    
    public void search(String url, String searchWord)
    {
       if (searchWord == "")
       {
           System.out.println("UNFOCUSED CRAWLING BEGINS ::: ");
           unfocusedCrawl (url);
       }
       else
       {
       while(pagesVisited.size() < MAX_PAGES_TO_SEARCH)
        //while (relevantPages.size() < 1000) 
        {
            String currentUrl = "";
            int currentUrlDepth = 0;
            SpiderLeg leg = new SpiderLeg();
            if(pagesToVisit.isEmpty())
            {
                System.out.println("pagesToVisit is empty");
                currentUrl = url;
                pagesVisited.add(url);
                leg.crawl(currentUrl,1);
            }
            else
            {
              //  try {
                    System.out.println("pagesToVisit is NOT  empty");
                    retlinks = nextUrl();
                    if (retlinks == null)       // shows no more URL to crawl
                        break;
                        //return;
                    currentUrl = retlinks.getURL();
                    currentUrlDepth = retlinks.getDepth();
                    System.out.println("current url = "+ currentUrl+"  DEPTH :: "+currentUrlDepth);
                  //TimeUnit.SECONDS.sleep(1);  // To maintain 1 sec politeness before requesting the serverS
                    leg.crawl(currentUrl , currentUrlDepth);
                //    } 
              /*  catch (InterruptedException ex) {
                    Logger.getLogger(Spider.class.getName()).log(Level.SEVERE, null, ex);
                }*/
            }
            //leg.crawl(currentUrl); // Lots of stuff happening here. Look at the crawl method in
                                   // SpiderLeg
            boolean success = leg.searchForWord(searchWord);
            if(success || pagesToVisit.isEmpty())   // the OR part ensures that even if keyPhrase is not found on seed page, the links from the seeed page are traversed
            {
                System.out.println(String.format("**Success** Word %s found at %s", searchWord, currentUrl));
                //break;
                //if (success) // add pages  to relevent only if it truely has the keyword (unlike seed page, which enters the previous if , even if the keyphrase isn't found
                relevantPages.add(currentUrl);
                if (currentUrlDepth <= MAX_DEPTH_FOR_SEARCH)
                //    pagesToVisit.addAll(leg.getLinks());   //---- for BFS
                    for (RetrievedLinks links : leg.getLinks()) // ----- FOR  DFS
                    {
                        //pagesToVisit.add(0, links);
                        pagesToVisit.addFirst(links);
                    }   
                
            }
            System.out.println("pagesVisited :: " + pagesVisited.size());
            System.out.println("Total Relevant Pages Found :: " + relevantPages.size());            
            System.out.println("Pages To Visit  :: " + pagesToVisit.size());
            for (RetrievedLinks links : pagesToVisit)
            {
               // System.out.println("--->>"+links.getURL() + "---->>"+links.getDepth());
            }
        }
        System.out.println("\n**Done** Visited " + pagesVisited.size() + " web page(s)");
        for (String linkss : relevantPages)
            {
                System.out.println("--->>> "+linkss);
                
            }
       }
    }
    
    /*
    This method is for unfocused crawling. Crawls 1000 URLs to a MAX_DEPTH of 5.
    */
    
    public void unfocusedCrawl(String url) {
        
        while(pagesVisited.size() < MAX_PAGES_TO_SEARCH)
        {
            String currentUrl = "";
            int currentUrlDepth = 0;
            SpiderLeg leg = new SpiderLeg();
            if(pagesToVisit.isEmpty())
            {
                System.out.println("pagesToVisit is empty");
                currentUrl = url;
                pagesVisited.add(url);
                leg.crawl(currentUrl,1);
            }
            else
            {
              //  try {
                    System.out.println("pagesToVisit is NOT  empty");
                    retlinks = nextUrl();
                    if (retlinks == null)       // shows no more URL to crawl
                        break;
                        //return;
                    currentUrl = retlinks.getURL();
                    currentUrlDepth = retlinks.getDepth();
                    System.out.println("current url = "+ currentUrl+"  DEPTH :: "+currentUrlDepth);
                  //TimeUnit.SECONDS.sleep(1);  // To maintain 1 sec politeness before requesting the serverS
                  //  leg.crawl(currentUrl , currentUrlDepth);
                //    } 
              /*  catch (InterruptedException ex) {
                    Logger.getLogger(Spider.class.getName()).log(Level.SEVERE, null, ex);
                }*/
            }
            leg.crawl(currentUrl , currentUrlDepth); // Lots of stuff happening here. Look at the crawl method in
                                   // SpiderLeg
            //boolean success = leg.searchForWord("");
            relevantPages.add(currentUrl);
            if (currentUrlDepth <= MAX_DEPTH_FOR_SEARCH)
                //    pagesToVisit.addAll(leg.getLinks());   //---- for BFS
                    for (RetrievedLinks links : leg.getLinks()) // ----- FOR  DFS
                    {
                        //pagesToVisit.add(0, links);
                        pagesToVisit.addFirst(links);
                    }   
            
            
            
            System.out.println("pagesVisited :: " + pagesVisited.size());
            System.out.println("Total Relevant Pages Found :: " + relevantPages.size());            
            System.out.println("Pages To Visit  :: " + pagesToVisit.size());
            for (RetrievedLinks links : pagesToVisit)
            {
               // System.out.println("--->>"+links.getURL() + "---->>"+links.getDepth());
            }
        }
        System.out.println("\n**Done** Visited " + pagesVisited.size() + " web page(s)");
        for (String linkss : relevantPages)
            {
                System.out.println("--->>> "+linkss);
                
            }
       
    }
    
    public Set<String> getAllRelevantLinks()
    {
        return this.relevantPages;
    }

    
	
	

}
