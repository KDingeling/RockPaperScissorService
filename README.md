Simples Stein-Papier-Schere Spiel als Testaufgabe für Spring Boot

# Spiel starten
curl -v -X GET http://localhost:8080/v1/games\?turns\=3
* output: {"gameId":0,"turnAmount":3,"currentTurn":0,"objectSet":["rock","paper","scissor"],"allTurns":[],"botWinCount":0,"playerWinCount":0,"finished":false}

# Züge machen
curl -X GET http://localhost:8080/v1/games/0\?object\=scissor
* output: {"playerObject":"scissor","botObject":"paper","result":"PLAYER_WIN","turnResultDescription":"Player wins"}

curl -X GET http://localhost:8080/v1/games/0\?object\=rock
* output: {"playerObject":"rock","botObject":"scissor","result":"PLAYER_WIN","turnResultDescription":"Player wins"}

curl -X GET http://localhost:8080/v1/games/0\?object\=paper
* output: {"playerObject":"paper","botObject":"scissor","result":"BOT_WIN","turnResultDescription":"Bot wins"}

# Alle getätigten Züge eines Spiels abfragen
curl -X GET http://localhost:8080/v1/games/0/turns
* output: [{"playerObject":"scissor","botObject":"paper","result":"PLAYER_WIN","turnResultDescription":"Player wins"},{"playerObject":"rock","botObject":"scissor","result":"PLAYER_WIN","turnResultDescription":"Player wins"},{"playerObject":"paper","botObject":"scissor","result":"BOT_WIN","turnResultDescription":"Bot wins"}]