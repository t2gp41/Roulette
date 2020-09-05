# Roulette
Console Roulette Game

Welcome to the console Roulette game.

To play this game just download the Roulette.jar file and simply run it from your terminal or CMD.

All of your logs will be stored at com.example.roulette.logs folder in the same directory.

Log hierarchy 

system - is used to store all system events and update the summary. Every 30 seconds, it will produce a log reporting that session data.
the log file is designed to use hold information in JSON format so that other applications can use them and also it's semi human-readable. The file name represents by ( logs-0 Friday 04 14:57:03 PM.json ) also have a UUID to grow at a massive scale.

DEMO DATA - 
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
  
  
