# Backend challenge

For this challenge you will need to code a matchmaking application. The application will receive both a file containing the list of servers available and a file containing the the list of players. The application should calculate the best matchmaking based on server capacity and latency.

Players can be banned from specific servers, or can ban other users, matchmaking must never add players to a server where they are banned or add two players to the same server if either one of them banned the other.

Latency will be reduced if the player is in the same region as the server, while this is preferred, this is not a hard requirement as banning.

You will receive the available servers and the list of players in two files, check this directory to see the example inputs.

The output should be a set of files (one per server) containing the list of players each server will host. See the example_output.csv file.

Bonus points:
- A post request can me made to add servers or players
- Data is dumped to a database after processing the files
- Metrics are provided (players per region, number of servers or other)
