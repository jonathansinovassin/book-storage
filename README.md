# book-storage

## How to launch 

### Prerequisites

Install tomcat 9 -> [download here](https://tomcat.apache.org/download-90.cgi)

Install Postgres 11 -> [download here](https://www.postgresql.org/download/)

### Setup the database
  On your database execute the script [script.sql](https://github.com/jonathansinovassin/book-storage/blob/master/src/main/resources/script.sql)
  
### Configure tomcat
  Complete the tomcat server.xml file with the following `Resource` attribute
  
  Think to replace <DATABASE_URL> and <DATABASE_PORT> by the appropriates values.
  
  ```
<GlobalNamingResources>
...
  <Resource 	
		name="jdbc/book"
		auth="Container" 
		type="javax.sql.DataSource"
		driverClassName="org.postgresql.Driver"
		url="jdbc:postgresql://<DATABASE_URL>:<DATABASE_PORT>/books"
		username="book_manager"
		password="book_manager_pass" />
...
</GlobalNamingResources>
```

  Add the following `ResourceLink` attribute to your context.xml file 

```
<Context>
...
  <ResourceLink name="jdbc/book" global="jdbc/book" auth="Container" type="javax.sql.DataSource" />
...
<Context>
```

### Launch the application
#### Clone the application
  Clone on your computer the project https://github.com/jonathansinovassin/book-storage
  
  ```
  git clone https://github.com/jonathansinovassin/book-storage
  ```
  
 #### Build the application
  In a bash terminal launch the following command lines:
  
   ```
   cd book-storage
   ./mvnw clean install
   ```
   
 #### Deploy the application
 
 Copy the war file named book-storage.war in your tomcat webapps folder
 Then start tomcat.
   
   
