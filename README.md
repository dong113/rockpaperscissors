# Network Game: Rock Paper Scissors
David Jensen and Lisa Dong

This repo contains programs to implement a game of rock paper scissors.

* MtClient.java handles keyborad input from the user, accepting "R", "P", "S", and "Q"
* ClientListener.java recieves responses from the server and returns them to clients
* MtServer.java listens for client connections and creates a ClientHandler for each new client
* ClientHandler.java recieves messages from a client and relays it to the other clients.



To package the client and server in Docker containers:

* Copy the MtClient.class and ClientListener.class files to the client subdirectory
* Copy the MtServer.class and ClientHandler.class files to the server subdirectory

* In the client subdirectory, to create the client Docker image use:
	docker image build -t client . 

* In the server subdirectory, to create the server Docker image use:
	docker image build -t server .

