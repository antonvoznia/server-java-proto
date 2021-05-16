package com.voznia.server;

import cz.cvut.fel.esw.server.proto.Messages;
import cz.cvut.fel.esw.server.proto.Request;
import cz.cvut.fel.esw.server.proto.Response;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentMap;
import java.util.zip.GZIPInputStream;

public class Worker implements Runnable {

    private final Socket socket;
    private final ConcurrentMap<String, Integer> totalWords;

    private DataInputStream input;
    private DataOutputStream output;

    public Worker(Socket socket, ConcurrentMap<String, Integer> totalWords) {
        this.socket = socket;
        this.totalWords = totalWords;
        try {
            this.input = new DataInputStream(socket.getInputStream());
            this.output = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Problem to get input stream from the socket!");
            e.printStackTrace();
        }
    }


    public void run() {
        while (true) {
            // read data
            int requestSize;
            int responseSize;
            Request request;
            Response response = null;

            try {
                requestSize = input.readInt();
                if (requestSize < 0) {
                    return;
                }
                byte[] data = new byte[requestSize];
                input.readFully(data);
                request = Request.parseFrom(data);
                switch (request.getMsgCase()) {
                    case GETCOUNT:
                        response = Response
                                .newBuilder()
                                .setStatus(Response.Status.OK)
                                .setCounter(totalWords.size())
                                .build();
                        this.totalWords.clear();
                        break;
                    case POSTWORDS:
                        response = Response
                                .newBuilder()
                                .setStatus(Response.Status.OK)
                                .build();
                        addNewWords(request.getPostWords().getData().toByteArray());
                        break;
                    case MSG_NOT_SET:
                        response = Response
                                .newBuilder()
                                .setErrMsg("Incorrect request message case!")
                                .setStatus(Response.Status.ERROR)
                                .build();
                        break;
                }
                responseSize = response.getSerializedSize();
                ByteBuffer bout = ByteBuffer.allocate(4 + responseSize);
                bout.putInt(responseSize);
                bout.put(response.toByteArray());
                output.write(bout.array());
            } catch (IOException e) {
                try {
                    input.close();
                    output.close();
                    socket.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                return;
            }
        }
    }

    private void addNewWords(byte[] compressedData) {
        ByteArrayInputStream bis = null;
        ByteArrayOutputStream bos = null;
        GZIPInputStream gzipIS = null;

        try {
            bis = new ByteArrayInputStream(compressedData);
            bos = new ByteArrayOutputStream();
            gzipIS = new GZIPInputStream(bis);

            byte[] buffer = new byte[1024];
            int len;
            while((len = gzipIS.read(buffer)) != -1){
                bos.write(buffer, 0, len);
            }
            String[] strs = bos.toString().split("\\s+");
            for (String v : strs) {
                this.totalWords.putIfAbsent(v, 0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                assert gzipIS != null;
                gzipIS.close();
                bos.close();
                bis.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
