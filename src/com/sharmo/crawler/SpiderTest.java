package com.sharmo.crawler;


/**
 *
 * @author Sharmodeep
 */
public class SpiderTest
{
    /**
     * This is our test. It creates a spider (which creates spider legs) and crawls the web.
     * 
     * @param args
     *            - not used
     */
    public static void main(String[] args)
    {
        //System.out.println(args[0]+"---HELL  ");
        
        Spider spider = new Spider();
        TextWriter obj = new TextWriter();
        //concordance
        //spider.search("https://en.wikipedia.org/wiki/Hugh_of_Saint-Cher", "");
        spider.search("https://en.wikipedia.org/wiki/Hugh_of_Saint-Cher", "concordance");
        /*TextFileWriter writeObj = new TextFileWriter();
        writeObj.writeToTextFile();
                */
        obj.writeToTextFile();
        
        
    }
}
