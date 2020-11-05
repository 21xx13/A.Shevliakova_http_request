package com.company;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("ai-info.ru", 80)) {
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(("GET / HTTP/1.1\r\n" +
                    "Host: ai-info.ru\r\n" +
                    "Connection: close\r\n\r\n").getBytes());
            outputStream.flush();
            Files.createFile(Paths.get("src/ai-info.html"));
            FileWriter fw = new FileWriter("src/ai-info.html");
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            int read = in.read();
            while (read > 0) {
                System.out.print((char) read);
                read = in.read();
                fw.write((char) read);
            }
            fw.close();
        } catch (Exception ignored) {
        }

        URL url = new URL("https://google.com");
        InputStream is = url.openStream();
        Files.createFile(Paths.get("src/google.html"));
        FileWriter fw = new FileWriter("src/google.html");
        BufferedReader in = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        int read = in.read();
        while (read > 0) {
            System.out.print((char) read);
            read = in.read();
            fw.write((char) read);
        }
        fw.close();
    }
}
