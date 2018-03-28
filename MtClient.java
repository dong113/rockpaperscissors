/**
 * MTClient.java
 *
 * This program implements a simple multithreaded chat client.  It connects to the
 * server (assumed to be localhost on port 7654) and starts two threads:
 * one for listening for data sent from the server, and another that waits
 * for the user to type something in that will be sent to the server.
 * Anything sent to the server is broadcast to all clients.
 *
 * The MTClient uses a ClientListener whose code is in a separate file.
 * The ClientListener runs in a separate thread, recieves messages form the server,
 * and displays them on the screen.
 *
 * Data received is sent to the output screen, so it is possible that as
 * a user is typing in information a message from the server will be
 * inserted.
 *
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class MtClient {
  /**
   * main method.
   * @params not used.
   */
  public static void main(String[] args) {
    try {
      String hostname = "localhost";
      int port = 7654;
      Integer play = 1;


      System.out.println("Connecting to server on port " + port);
      Socket connectionSock = new Socket(hostname, port); // setting connection socket

      DataOutputStream serverOutput = new DataOutputStream(connectionSock.getOutputStream());

      System.out.println("Connection made, congratualtions!" + "\n");
      System.out.println("Welcome to (R)ock, (P)aper, (S)cissors! " + "\n");
      System.out.println("To make a valid choice, please read between the brackets above! ");
      System.out.println("Case sensitive- Please use uppercase letters");
      System.out.println("At anytime you may (Q)uit ");

      // Start a thread to listen and display data sent by the server
      ClientListener listener = new ClientListener(connectionSock);
      Thread theThread = new Thread(listener);
      theThread.start();

      boolean game = true; //setting the game repeat to true
      while (game == true) { //conditions while playing game equals true
        System.out.println("Round " + play + "," + " Your Pick: " + "\n");
        Scanner keyboard = new Scanner(System.in);
        String data = keyboard.nextLine();
        boolean goodBad = false; //setting option to fales
        while (goodBad == false) { //if lower case, exit
          if (data.equals("R") || data.equals("P") || data.equals("S") || data.equals("Q")) {
            goodBad = true; //change variable
          } else {
            System.out.println("Invalid. Come on! read the instructions!");
            data = keyboard.nextLine();
          }
        }

        serverOutput.writeBytes(data + "\n"); //Write to bytes
        System.out.println("Please be patient and wait for the other player to make a move" + "\n");

        String inputInfo = listener.returnOpponentInfo();

        if (inputInfo.equals("Q")) { //Client option to quit
          System.out.println("Other player is no longer connected, now disconnecting");
          game = false;
          break;
        }
        if (data.equals("Q")) { //Other players option to quit
          System.out.println("now exiting");
          game = false;
          break;
        }


        if (inputInfo.equals(data)) { //Rock Paper Scissors Conditions
          System.out.println("Tie, Try again!");
        } else if (data.equals("R") && inputInfo.equals("S")) {
          System.out.println("Rock Wins. Please try again!");
        } else if (data.equals("P") && inputInfo.equals("S")) {
          System.out.println("Scissors Wins. Please try again!");
        } else if (data.equals("R") && inputInfo.equals("P")) {
          System.out.println("Paper Wins. Please try again!");
        } else if (data.equals("S") && inputInfo.equals("R")) {
          System.out.println("Rock Wins. Please try again!");
        } else if (data.equals("S") && inputInfo.equals("P")) {
          System.out.println("Scissors Wins. Please try again!");
        } else if (data.equals("P") && inputInfo.equals("R")) {
          System.out.println("Paper Wins. Please try again!");
        }


        ++play; //incriment the game
        System.out.println();
      }

      // Read input from the keyboard and send it to everyone else.
      // The only way to quit is to hit control-c, but a quit command
      // could easily be added.
      connectionSock.close();
    } catch (SocketException ex) {
      System.out.println("Closing socket");
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
} // MtClient
