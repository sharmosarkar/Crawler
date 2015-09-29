# Crawler
A Focused Crawler for Wikipedia

Specifications :
1. This Crawler crawls to a maximum depth of 5 (seed link being at depth 1)
2. The Crawler crawls 1000 Unique URLs at max
3. Wikipedia has a lot of links, so Crawler stop when it reaches 1000 unique URLs.
4. A time delay of 1sec is maintained between 2 consecutive HTTP requests (politeness factor)
5. In case of crawling with a Keyword , If the Keyword is not present, it stops crawling the subsequent children
6. The Crawler only crawls links with http://en.wikipedia.org/wiki/. In other words, do not follow links to non-English articles or to non-Wikipedia pages.
7. The Crawler does not follow links with a colon (:) in the rest of the URL. This will help filter out Wikipedia help and administration pages.
8. BFS is used as the traversing algorithm 
9. The Crawler has 2 features :
	<a> Traverses without Keyword / searchWord (brings out the first 1000 URLs that it crawls)
	<b> Traverses with Keyword / searchWord  (brings out the URLs of the pages that contains the Keyword )
10. The Links crawled (relevant links retrieved , in case of Keyword Search) with the time taken for the crawl is printed in a file
	<< Links_Crawled_WITHOUT_Keyword.txt   ---> Search without keyword >>
	<< Links_Crawled_WITH_Keyword.txt 	   ----> Search with keyword   >>
	
	

Technical Specifications :
1. The source code is developed in Java using NetBeans IDE version 8.0.2
2. Java Version 1.8.0
2. External JAR file used :  jsoup-1.8.3.jar   (included with source code)


Running the Code :
Crawler takes 2 command-line arguments at most but at least 1 argument to run successfully 
	First Argument:   seed URL 
	Second Argument:  search keyword (optional)
If no keyword for Searching is provided then the Crawler makes a crawl without searching any keyword and prints out the unique Links crawled upto depth 5 (1000 being the max URLs crawled)
If keyword is provided for Searching , the Crawler prints out the unique links which contain the Keyword (Soft-matched) upto depth 5 (1000 being the max URLs crawled)
The generated (output) Files are present in the directory from where the Code would run 


Steps to Run the code from LINUX :
Step 1 : Navigate to the folder where the Project Folder (Crawler) is present
Step 2 : Navigate to the src folder inside Crawler  (~~\Crawler\src)
Step 3 : Compile the code (with the External Jar ) from the src folder :::
		 javac -cp ".:./jsoup-1.8.3.jar" com/sharmo/crawler/CrawlerRun.java  
		 <<< CrawlerRun.java has the main function for the Project>>> 
Step 4 : Run the code from the same (src) folder with the desired number of arguments :::
		java -cp ".:./jsoup-1.8.3.jar" com.sharmo.crawler.CrawlerRun  ~~seedURL~~
		<<<eg -- >>   java -cp ".:./jsoup-1.8.3.jar" com.sharmo.crawler.CrawlerRun https://en.wikipedia.org/wiki/Hugh_of_Saint-Cher >>>
		or
		java -cp ".:./jsoup-1.8.3.jar" com.sharmo.crawler.CrawlerRun  ~~seedURL~~  ~~keyword~~
		<<eg -- >>    java -cp ".:./jsoup-1.8.3.jar" com.sharmo.crawler.CrawlerRun https://en.wikipedia.org/wiki/Hugh_of_Saint-Cher concordance >>>
Step 5 : Output File at ~~\Crawler\src\
		
		
Steps to Run the code from WINDOWS CMD :
Step 1 : Navigate to the folder where the Project Folder (Crawler) is present
Step 2 : Navigate to the src folder inside Crawler  (~~\Crawler\src)
Step 3 : Compile the code (with the External Jar ) from the src folder :::
		 javac -cp ".:./jsoup-1.8.3.jar" com/sharmo/crawler/CrawlerRun.java  
		 <<< CrawlerRun.java has the main function for the Project>>> 
Step 4 : Run the code from the same (src) folder with the desired number of arguments :::
		java -cp ".;./jsoup-1.8.3.jar" com.sharmo.crawler.CrawlerRun  ~~seedURL~~
		<<<eg -- >>   java -cp ".;./jsoup-1.8.3.jar" com.sharmo.crawler.CrawlerRun https://en.wikipedia.org/wiki/Hugh_of_Saint-Cher >>>
		or
		java -cp ".;./jsoup-1.8.3.jar" com.sharmo.crawler.CrawlerRun  ~~seedURL~~  ~~keyword~~
		<<eg -- >>    java -cp ".;./jsoup-1.8.3.jar" com.sharmo.crawler.CrawlerRun https://en.wikipedia.org/wiki/Hugh_of_Saint-Cher concordance >>>
Step 5 : Output File at ~~\Crawler\src\

		
Steps to Run the code from WINDOWS using NetBeans IDE :
Step 1 : Import the Project into the IDE  
		<< File --> Open Project >>
Step 2 : Put in the Parameters for the project
		<< Right click on the Project in the Navigator --> Properties --> Run --> Arguments  ~~type in arguments with a white-space between 2 arguments ~~  >>
Step 3 : Right Click the program and Click on Run 
Step 5 : Output File at ~~\Crawler\
	
	
	
Sources Used to Design the crawler ::
http://jsoup.org/cookbook/input/parse-body-fragment
http://stackoverflow.com/questions/24301986/the-type-java-lang-charsequence-cannot-be-resolved-in-package-declaration
https://code.google.com/p/crawler4j/
http://www.mkyong.com/java/java-time-elapsed-in-days-hours-minutes-seconds/ 
http://www.netinstructions.com/how-to-make-a-simple-web-crawler-in-java/
http://www.java2s.com/Code/Java/Data-Type/Countthenumberofinstancesofsubstringwithinastring.htm

		 
