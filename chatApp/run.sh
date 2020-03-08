
#! /usr/bin/env  bash


javac -d lib src/clientside/Pair.java src/clientside/RecieveMessage_itf.java
javac -d lib -cp .:lib src/serverside/Join_itf.java src/serverside/Leave_itf.java src/serverside/SendMessage_itf.java 
javac -d lib -cp .:lib:src/clientside src/clientside/Client.java src/clientside/ChatClient.java
javac -d lib -cp .:lib  src/serverside/ChatServer.java 

# pidof rmiregistry  | xargs kill -9
# rmiregistry &
