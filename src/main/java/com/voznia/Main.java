package com.voznia;

import com.voznia.server.Server;

public class Main {

    public static void main(String[] args) {
        System.out.println(args[0]);
        Server server = new Server(Integer.parseInt(args[0]));
        server.acceptUser();
    }
}
