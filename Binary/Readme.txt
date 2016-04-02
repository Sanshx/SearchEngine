NOTE: Open this file in WordPad in Windows.


The instruction of running the Search Engine are according to Ubuntu(Linux) OS.

1.Software requirement
	1.Apache tomcat 7
	2.MySQl Server 5.5
	3.Java 7 Oracle

2.Copy the contents of 'Project' and 'Database' folder on your local disk. Make sure the location name should not contain space.
  If space is present in a folders name, replace it with underscore or remove it.
3.Create a database named 'Spider' in MySQL server.
4.Export the database file named 'Spider_nodata.sql' inside 'Spider' database. A file named 'Spider_withdata.sql' is also present if with a
  considerable amount of data present.
  Syntax: source <file_location>

Now the database is set and the crawler can be started now.

//----------------------------------------------Running the crawler-------------------------------------------//

1.Put the database connection details in 'database.xml' file situated at 'Crawler/bin/db_config/' location.
2.Put the seeds in the 'seeds.xml' file situated at 'Crawler/urls/' location. A 'Sample.xml' is given for reference.
3.Go to 'Crawler/bin/' location via terminal.
4.Make the 'crawl' script runnable via 'chmod' command. 
	Example: sudo chmod a+x ./crawl
5.Go back one directory to Crawler/ folder.
6.Command syntx to run the crawler is : bin/crawl <seeds.xml_location> <maxdepth>.
	Example: bin/crawl urls/ 3
	Here 'crawl' is the bash script used to run the crawler, 'urls/' is the folder containing seeds.xml file and 3 is the max depth up-to which
	crawler will go.
7.The crawler will start running performing crawling and indexing both.


Now as the crawling and indexing are in progress, searches to the crawled data can still be made.



//-----------------------------------------------Running the searcher----------------------------------------//

1.Go the 'Searching' folder where the Web archive file name 'MedSearch.war' of Searching application is present.
2.Start the apache tomcat server.
3.Go to the manager panel.
4.Deploy the 'MedSearch.war' on server.
5.Put the database connection details in 'web.xml. file presnet in the project and restart the server.
6.Open the deployed project on browser.
7.To access admin panel go to /AdminHome.html to login.
	Default username: admin 
	Default password: admin
  The username and password can be changed by database in 'maps_admin' table.


The home page of searching will appear.
Source Code is present 'Source' folder.	

