package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Ahmed Kamel Taha on 03/12/2015.
 */
public class GlobalClass extends SICXE {
    public GlobalClass() throws Exception {
        initFormat();
        readFile();

    }

    void printFile() {
        System.out.println("-- file data --");
        for (int j = 0; j < instructions.size(); j++) {
            System.out.println(j + " " + labels.get(j) + " " + instructions.get(j) + " " + targetAddress.get(j) + " ");
        }
        System.out.println();
    }

    void readFile() throws FileNotFoundException {
        File file = new File("input.txt");

        Scanner scanner = new Scanner(file);

        String line = new String();
        int i;
        for (i = 0; scanner.hasNext(); i++) {
            line = scanner.nextLine();
            String s[] = line.split(";");
            labels.add(s[0]);
            instructions.add(s[1]);
            targetAddress.add(s[2]);
        }

    }

    void initFormat() {
        String localInstructionsFormat1[] = {"fix", "float", "hio", "norm", "sio", "tio"};
        String localInstructionsFormat2[] = {"addr", "clear", "compr", "divr", "rmo", "shiftl", "shiftr", "subr", "svc", "tixr", "mulr"};
        String localInstructionsFormat3[] = {"add", "addf", "and", "comp", "compf", "div", "divf", "j", "jeq", "jgt", "jlt", "jsub", "lda", "ldb", "ldch", "ldf", "ldl", "lds", "ldt", "ldx", "lps", "mull", "mulf", "or", "rd", "rsub", "ssk", "sta", "stb", "stch", "stf", "sti", "stl", "sts", "stsw", "stt", "stx", "sub", "subf", "td", "wd"};
        String localInstructionsFormat4[] = {"+add", "+addf", "+and", "+comp", "+compf", "+div", "+divf", "+j", "+jeq", "+jgt", "+jlt", "+jsub", "+lda", "+ldb", "+ldch", "+ldf", "+ldl", "+lds", "+ldt", "+ldx", "+lps", "+mull", "+mulf", "+or", "+rd", "+rsub", "+ssk", "+sta", "+stb", "+stch", "+stf", "+sti", "+stl", "+sts", "+stsw", "+stt", "+stx", "+sub", "+subf", "+td", "+wd"};
        String localRegisters[] = {"a", "l", "x", "pc", "sw", "b", "s", "t", "f"};

        Integer LocalOpcodeFormat1[] = {0xc4, 0xc0, 0xf4, 0xc8, 0xc8, 0xf0, 0xf8};
        Integer LocalOpcodeFormat2[] = {0x90, 0x4, 0xa0, 0x9c, 0xac, 0xa4, 0xa8, 0x94, 0xb0, 0xb8, 0x98};
        Integer LocalOpcodeFormat3[] = {0x18, 0x58, 0x40, 0x28, 0x88, 0x24, 0x64, 0x3c, 0x30, 0x34, 0x38, 0x48, 0x00, 0x68, 0x50, 0x70, 0x08, 0x6c, 0x74, 0x04, 0xd0, 0x20, 0x60, 0x44, 0xd8, 0x4c, 0xec, 0x0c, 0x78, 0x54, 0x80, 0xd4, 0x14, 0x7c, 0xe8, 0x84, 0x10, 0x1c, 0x5c, 0xb0, 0xe0, 0xdc};
        Integer LocalOpcodeFormat4[] = {0x18, 0x58, 0x40, 0x28, 0x88, 0x24, 0x64, 0x3c, 0x30, 0x34, 0x38, 0x48, 0x00, 0x68, 0x50, 0x70, 0x08, 0x6c, 0x74, 0x04, 0xd0, 0x20, 0x60, 0x44, 0xd8, 0x4c, 0xec, 0x0c, 0x78, 0x54, 0x80, 0xd4, 0x14, 0x7c, 0xe8, 0x84, 0x10, 0x1c, 0x5c, 0xb0, 0xe0, 0xdc};

        Integer localRegisterCode[] = {0x0, 0x1, 0x2, 0x8, 0x9, 0x3, 0x4, 0x5, 0x6};

        format1Instructions.addAll(Arrays.asList(localInstructionsFormat1));
        format2Instructions.addAll(Arrays.asList(localInstructionsFormat2));
        format3Instructions.addAll(Arrays.asList(localInstructionsFormat3));
        format4Instructions.addAll(Arrays.asList(localInstructionsFormat4));


        format1Opcode.addAll(Arrays.asList(LocalOpcodeFormat1));
        format2Opcode.addAll(Arrays.asList(LocalOpcodeFormat2));
        format3Opcode.addAll(Arrays.asList(LocalOpcodeFormat3));
        format4Opcode.addAll(Arrays.asList(LocalOpcodeFormat4));

        registers.addAll(Arrays.asList(localRegisters));

        registersOpcode.addAll(Arrays.asList(localRegisterCode));

    }

    void PrintOneTwo() {
        System.out.println();
        System.out.println(" -- print it all --");
        System.out.println("0 " + locations.get(0) + " " + labels.get(0) + " " + instructions.get(0) + " " + targetAddress.get(0) + " ");

        for (int i = 1; i < instructions.size(); i++) {
            System.out.print(i + " ");
            if (i > 0 && i <= locations.size()) {
                System.out.print(locations.get(i - 1) + " ");
            }
            System.out.print(labels.get(i) + " " + instructions.get(i) + " " + targetAddress.get(i) + " ");
            if (i > 0 && i < objectCodes.size()) {
                System.out.print(objectCodes.get(i));
            }
            System.out.println();
        }
        System.out.println();
    }
}
