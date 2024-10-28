package com.nanpaunpa;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.FileNotFoundException;

import static com.nanpaunpa.NanpaUnpa.ONE;
import static com.nanpaunpa.NanpaUnpa.TWO;
import static com.nanpaunpa.NanpaUnpa.FIVE;
import static com.nanpaunpa.NanpaUnpa.PERIOD;

public class NUTranspiler {

    public static String transpile(String bfCode) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < bfCode.length(); i++) {
            char c = bfCode.charAt(i);
            sb.append( switch(c) {
                case '+' -> ONE + PERIOD + " ";
                case '-' -> TWO + PERIOD + " ";
                case '<' -> TWO + ' ' + ONE + PERIOD + " ";
                case '>' -> TWO + ' ' + TWO + PERIOD + " ";
                case ',' -> FIVE + PERIOD + " ";
                case '.' -> FIVE + ' ' + ONE + PERIOD + " ";
                case '[' -> FIVE + ' ' + TWO + PERIOD + " ";
                case ']' -> FIVE + ' ' + TWO + ' ' + ONE + PERIOD + " ";
                default -> "" + c;
            });
        }
        return sb.toString();
    }
    
    public static void main(String[] args) {
        if(args.length < 1) {
            System.err.println(NanpaUnpa.MESSAGE_NO_FILE_NAME);
            System.exit(1);
        }

        try {
            String bfCode = Files.readString(Path.of(args[0]));
            String nuCode = transpile(bfCode);
            if(args.length >= 2) {
                Files.writeString(Path.of(args[1]), nuCode);
            } else {
                System.out.println(nuCode);
            }
        } catch(FileNotFoundException fe) {
            System.err.println(NanpaUnpa.MESSAGE_FILE_NOT_FOUND);
            System.exit(1);
        } catch(Exception e) {
            System.err.println(NanpaUnpa.MESSAGE_SOME_PROBLEM);
            e.printStackTrace();
            System.exit(1);
        }
    }

}
