# Roulette
Console Roulette Game

Welcome to the console Roulette game.

To play this game just download the Roulette.jar file and simply run it from your terminal or CMD.

All of your logs will be stored at com.example.roulette.logs folder in the same directory.

Log hierarchy 

system - is used to store all system events and update the summary. Every 30 seconds, it will produce a log reporting that session data.
the log file is designed to use hold information in JSON format so that other applications can use them and also it's semi human-readable. The file name represents by ( logs-0 Friday 04 14:57:03 PM.json ) also have a UUID to grow at a massive scale.

DEMO DATA - 

```json
{
	 "session" : "2",
	 "date":"Friday 04 September 2020 14:57:03 PM",
	 "draw":"19",
	 "players":[
	 "Joy,98",
	 "Thor,78",
	 "Blackpanther,22"
	 ],
	 "history":[
	 "Thor,29,22",
	 "Blackpanther,5,50",
	 "Blackpanther,15,28",
	 "Joy,1,2"
	 ],
	 "winners":[
	 ],
	 "totalBet" : 102,
	 "totalPayout" : 0
	}
```  
  
  players - is used to storing all the events for an individual player. On start, it generates a folder for every player and stores all the events at 30-second intervals. It has also some real-time tracking over player activity. It tracks the player bet amount in real-time. So for a scenario like a player place a bet but kill the system before it makes a draw, the system is fully capable of deducting the amount of bet. It also tracks the overall stack  Like total Bet, total Won, total Loss, for that individual player over the lifetime.

DEMO DATA

```json
{
	 "join" : "Friday 04 September 2020 14:56:33 PM",
	 "lastVisit" : "Friday 04 September 2020 15:53:40 PM",
	 "balance" : 90,
	 "totalBet" : 10,
	 "totalWon" : 0,
	 "totalLoss" : 6
	}
```

EVENT RECOVERY
Please kill the program and run it again. If you remember the last username that you entered to start the game, you will able to play the game from that state that you left last time. That means once you resisted you not going to lose your money when you kill the game.

# FOR COLOR MODE PLEASE USE WINDOWS 10 OR LINUX TERMINAL. 
# FOR MORE INFO PLEASE SEARCH ANSI COMMAND AND IT'S LIMITATIONS ON WINDOWS

