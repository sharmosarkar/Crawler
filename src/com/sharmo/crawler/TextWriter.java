/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sharmo.crawler;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sharmodeep
 */
public class TextWriter {
    
    private Set<String> relevantLinks = new HashSet<String>();
    private Spider obj = new Spider();
    private File f;
    
    public TextWriter ()
    {
        if (obj.getAllRelevantLinks().size()>0)
            relevantLinks  =  obj.getAllRelevantLinks();
        else
            relevantLinks.add("NO RELEVANT PAGES FOUND");
        
        f = new File("Links_Crawled_With_Index.txt");
        if (!f.exists()) 
        {
            try {
                    f.createNewFile();
                } 
            catch (IOException ex) 
                {  
                    System.out.println("Couldn't Create Output File !!!");
                    Logger.getLogger(TextWriter.class.getName()).log(Level.SEVERE, null, ex);
                
                }
        }
    }
    
    public void writeToTextFile ()   // Ref:: http://www.mkyong.com/java/how-to-write-to-file-in-java-bufferedwriter-example/
    {
        
        FileWriter fw = null;
        try {
            fw = new FileWriter(f.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            for (String links : relevantLinks)
            {
                bw.write(links);
                bw.newLine();
                //bw.write("\n");
            }
            bw.close();
            System.out.println("DONE !!!");
        } catch (IOException ex) {
            System.out.println("Couldn't Write to File !!!");
            Logger.getLogger(TextWriter.class.getName()).log(Level.SEVERE, null, ex);            
        } 
    
    }
    

    
}
