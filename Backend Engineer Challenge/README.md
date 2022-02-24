# Tech Challenge for Backend Engineer

For this challenge you will need to code a match-making application. The application will receive both a file containing the list of servers available and a file containing the the list of players. The application should calculate the best match-making based on server capacity and latency.

### Requirements
- Players can be banned from specific servers
- Players can ban other users
- Matchmaking must never add players to a server where they are banned
- Matchmaking must never add two players to the same server if either one of them previously banned the other

### Nice to have
- Give a preference to putting the player and server in the same region since latency will be reduced

### Input & Output
You will receive the available servers and the list of players in two files. Check this directory to see the example inputs.

The output should be a set of files (one per server) containing the list of players each server will host. See the example_output.csv file.

### Bonus points
- A post request can me made to add servers or players
- Data is dumped to a database after processing the files
- Metrics are provided (players per region, number of servers or other)
