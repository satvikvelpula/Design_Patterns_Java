package Singleton;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    private static Logger instance;
    private String file_name;
    private BufferedWriter writer;

    private Logger() {
        file_name = "log.txt";
        try {
            writer = new BufferedWriter(new FileWriter(file_name));
        } catch (IOException e) {
            System.out.println("Could not open log file.");
        }
    }

    public void setFileName(String text) {
        try {
            if (writer != null) {
                writer.close();
            }

            file_name = text;
            writer = new BufferedWriter(new FileWriter(file_name));

        } catch (IOException e) {
            System.out.println("Could not change log file.");
        }
    }

    public void write(String text) {
        try {
            writer.write(text);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Could not write to log file.");
        }
    }

    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            System.out.println("Could not close file. ");
        }
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }

        return instance;
    }

}
