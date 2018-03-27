/**
 * ClientListener.java
 *
 * This class runs on the client end and just
 * displays any text received from the server.
 *
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.Socket;

import java.util.ArrayList;
import java.util.Scanner;


public class ClientListener implements Runnable {
  private Socket connectionSock = null;

  ClientListener(Socket sock) {
    this.connectionSock = sock;
  }
//returns values from other clients, instead of printing it to screen
   public String returnOpponentInfo() {
    String data = "";
    try {
      BufferedReader serverInput = new BufferedReader(
          new InputStreamReader(connectionSock.getInputStream()));

      // Get data sent from the server
      String serverText = serverInput.readLine();
      if (serverInput != null) {
        data = serverText;
      } else {
        // Connection was lost
        System.out.println("Closing connection for: " + connectionSock);
        connectionSock.close();
        //break;
      }

    } catch (Exception e) {
      System.out.println("Error: " + e.toString());
    }

    return data;
  }

  /**
   * Gets message from server and dsiplays it to the user.
   */
/*
  public void run() {
    try {
      BufferedReader serverInput = new BufferedReader(
          new InputStreamReader(connectionSock.getInputStream()));
      while (true) {
        // Get data sent from the server
        String serverText = serverInput.readLine();
        if (serverInput != null) {
          System.out.println(serverText);
        } else {
          // Connection was lost
          System.out.println("Closing connection for socket " + connectionSock);
          connectionSock.close();
          break;
        }
      }
    } catch (Exception e) {
      System.out.println("Error: " + e.toString());
    }
  }
*/

} // ClientListener for MtClient
