1. Check the database connection settings. Open the application.conf file and check that the database path is correct in the db.default.url value. The database file "database.db" is in the root folder of the project.
2. Use a command window to change into the project directory, for example: "cd E:\playscalademo"
3. Start sbt with the command "sbt".
4. Run the project with the command run
5. Enter the following URL in a browser: <http://localhost:9000>
6. Use following credentials to login: username: "test", password:
   "testpwd"
7. After a successful login you should see a list of the moomin mugs and their prices.