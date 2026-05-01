import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class vendas {
    public static enum Color {
        RESET("\u001b[0m"),
        BLACK("\u001b[30m"),
        RED("\u001b[31m"),
        GREEN("\u001b[32m"),
        YELLOW("\u001b[33m"),
        BLUE("\u001b[34m"),
        PURPLE("\u001b[35m"),
        CYAN("\u001b[36m"),
        WHITE("\u001b[37m"),
        
        // Cores brilhantes (bold)
        BRIGHT_RED("\u001b[91m"),
        BRIGHT_GREEN("\u001b[92m"),
        BRIGHT_YELLOW("\u001b[93m"),
        BRIGHT_BLUE("\u001b[94m"),
        BRIGHT_CYAN("\u001b[96m");
    }
}
