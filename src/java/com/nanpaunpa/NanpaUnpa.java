package com.nanpaunpa;

import java.util.ArrayList;
import java.util.ArrayDeque;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.FileNotFoundException;

public class NanpaUnpa {

    public static final String ONE = "wan";
    public static final String TWO = "tu";
    public static final String FIVE = "luka";

    public static final String PERIOD = ".";

    public static final int TAPE_LENGTH = 10000;

    public static final String MESSAGE_BIG_NUM =
        "pakala pi nasin toki: nanpa pali li mute namako.";
    public static final String MESSAGE_LOOP_WRONG =
        "pakala pi nasin toki: nasin toki pi pali awen li pakala.";
    public static final String MESSAGE_NO_FILE_NAME =
        "pakala: nimi lipu li ala.";
    public static final String MESSAGE_FILE_NOT_FOUND =
        "pakala: mi ken ala lukin e lipu.";
    public static final String MESSAGE_SOME_PROBLEM =
        "pakala: ijo pakala li lon. o lukin e sona anpa.";

    public static Integer[] parse(String code) {
        ArrayList<Integer> result = new ArrayList<>();
        int cur = 0;
        int inst = 0;
        int loopDepth = 0;
        
        while(cur < code.length()) {
            if(code.indexOf(ONE, cur) == cur) {
                inst += 1;
                cur += ONE.length() - 1;
            }
            if(code.indexOf(TWO, cur) == cur) {
                inst += 2;
                cur += TWO.length() - 1;
            }
            if(code.indexOf(FIVE, cur) == cur) {
                inst += 5;
                cur += FIVE.length() - 1;
            }
            if(code.indexOf(PERIOD,cur) == cur && inst != 0) {
                result.add(inst);
                if(inst == 7) loopDepth++;
                else if(inst == 8) loopDepth--;
                inst = 0;
                cur += PERIOD.length() - 1;
            }
            
            if(inst > 8) {
                System.err.println(MESSAGE_BIG_NUM);
                System.exit(1);
            }

            cur++;
        }
        
        if(loopDepth != 0) {
            System.err.println(MESSAGE_LOOP_WRONG);
            System.exit(1);
        }
        
        return result.toArray(new Integer[result.size()]);
    }

    public static void run(Integer[] insts) throws IOException {
        int pc = 0;
        int cur = 0;
        int[] tape = new int[10000];
        ArrayDeque<Integer> starts = new ArrayDeque<>();
        while(pc < insts.length) {
            switch(insts[pc]) {
                case 1 -> {
                    tape[cur]++;
                    if(tape[cur] == 256) tape[cur] = 0;
                }
                case 2 -> {
                    tape[cur]--;
                    if(tape[cur] == -1) tape[cur] = 255;
                }
                case 3 -> {
                    cur--;
                    if(cur == -1) cur = TAPE_LENGTH - 1;
                }
                case 4 -> {
                    cur++;
                    if(cur == TAPE_LENGTH) cur = 0;
                }
                case 5 -> {
                    int input;
                    while((input = System.in.read()) == '\r' || input == '\n');
                    tape[cur] = input;
                }
                case 6 -> {
                    System.out.print((char)tape[cur]);
                }
                case 7 -> {
                    starts.push(pc);
                    if(tape[cur] == 0) {
                        int depth = 1;
                        while(depth > 0) {
                            pc++;
                            if(insts[pc] == 7) depth++;
                            else if(insts[pc] == 8) depth--;
                        }
                        starts.pop();
                    }
                }
                case 8 -> {
                    pc = starts.pop() - 1;
                }
            }

            pc++;
        }
    }

    public static void main(String[] args) {
        if(args.length < 1) {
            System.err.println(MESSAGE_NO_FILE_NAME);
            System.exit(1);
        }

        try {
            String code = Files.readString(Path.of(args[0]));
            Integer[] insts = parse(code);
            run(insts);
        } catch(FileNotFoundException fe) {
            System.err.println(MESSAGE_FILE_NOT_FOUND);
            System.exit(1);
        } catch(Exception e) {
            System.err.println(MESSAGE_SOME_PROBLEM);
            e.printStackTrace();
            System.exit(1);
        }
    }
}
