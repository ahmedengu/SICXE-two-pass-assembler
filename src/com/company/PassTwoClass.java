package com.company;

import java.util.Arrays;
import java.util.Vector;


public class PassTwoClass extends SICXE {
    private Vector<String> externalRefs = new Vector<>();

    public PassTwoClass() {
        passTwo();
    }

    void passTwo() {

        objectCodes.add("");
        for (int i = 1; i < instructions.size() - 1; i++) {
            // -0,1,2,3,4,5 - n/6 , i/7 , x/8 , b/9 , p/10 , e/11 , .....
            Boolean[] objectCode = initalizeObjectCodeArray();

            if (HandleFormats(i, objectCode)) continue;
            handleBase(i, objectCode);
            handleIndirectAndImmidiate(i, objectCode);
            handleIndexed(i, objectCode);

            handleTheObjectCode(i, objectCode);

        }
    }

    private void handleTheObjectCode(int i, Boolean[] objectCode) {

        if (objectCode[11]) {
            handleFormat4ObjectCode(i, objectCode);
            return;
        } else {
            String tempObjectCodeString = "";

            if (objectCode[9]) {
                handleBaseObjectCode(i, objectCode, tempObjectCodeString);
                return;
            } else {

                if (objectCode[6] && objectCode[7]) {
                    tempObjectCodeString = HandleSempleObjectCode(i, objectCode[9]);
                } else if (objectCode[6]) {
                    tempObjectCodeString = handleIndirect(i, objectCode[9]);
                } else if (objectCode[7]) {
                    tempObjectCodeString = handleImmidite(i, objectCode);


                }

                StringToBinArray(objectCode, tempObjectCodeString);

            }

        }

        objectCodes.add(String.format("%6s", Long.toHexString(Long.parseLong(obtoString(objectCode, locations.get(i) + " " + labels.get(i) + " " + instructions.get(i) + " " + targetAddress.get(i)), 2))).replace(" ", "0"));
    }

    private void StringToBinArray(Boolean[] objectCode, String tempObjectCodeString) {
        for (int j = 0; j < tempObjectCodeString.length() && j < 20; j++) {
            objectCode[12 + j] = (tempObjectCodeString.charAt(j) == '1') ? true : false;
        }
    }

    private String handleImmidite(int i, Boolean[] objectCode) {
        String tempObjectCodeString;
        int tempObjectCodeInt = 0x0;

        try {
            tempObjectCodeString = String.format("%12s", Long.toBinaryString(Long.parseLong(targetAddress.get(i).replace("#", ""), 16))).replace(" ", "0");

        } catch (Exception e) {

            if (objectCode[10]) {
                tempObjectCodeInt = Integer.parseInt(locations.get(labels.indexOf(targetAddress.get(i).replace(",X", "").replace("#", "")) - 1), 16) - Integer.parseInt(locations.get(i), 16);
            }
            if (objectCode[9]) {
                tempObjectCodeInt = Integer.parseInt(locations.get(locations.indexOf(targetAddress.get(i)) - 1), 16) - Integer.parseInt(locations.get(locations.indexOf("base")), 16);
            }
            tempObjectCodeString = prepareTheOb(tempObjectCodeInt);
        }
        return tempObjectCodeString;
    }

    private String handleIndirect(int i, Boolean aBoolean) {
        String tempObjectCodeString;
        int tempObjectCodeInt = 0x0;
        if (aBoolean) {
            tempObjectCodeInt = Integer.parseInt(locations.get(labels.indexOf(targetAddress.get(i).replace("@", "")) - 1), 16) - Integer.parseInt(locations.get(targetAddress.indexOf(instructions.indexOf("base"))), 16);
        } else {
            tempObjectCodeInt = Integer.parseInt(locations.get(labels.indexOf(targetAddress.get(i).replace(",X", "").replace("@", "")) - 1), 16) - Integer.parseInt(locations.get(i), 16);
        }

        tempObjectCodeString = prepareTheOb(tempObjectCodeInt);
        return tempObjectCodeString;
    }

    private String prepareTheOb(int tempObjectCodeInt) {
        String tempObjectCodeString;
        if (tempObjectCodeInt < 0) {
            tempObjectCodeString = String.format("%12s", Integer.toBinaryString(tempObjectCodeInt).replace(" ", "0"));

            String tempNoOneKnow = "";
            for (int j = 20; j < tempObjectCodeString.length(); j++) {
                tempNoOneKnow += tempObjectCodeString.charAt(j);

            }
            tempObjectCodeString = tempNoOneKnow;

        } else {
            tempObjectCodeString = String.format("%12s", Integer.toBinaryString(tempObjectCodeInt).replace(" ", "0"));
        }
        return tempObjectCodeString;
    }

    private String HandleSempleObjectCode(int i, Boolean aBoolean) {
        String tempObjectCodeString;
        int tempObjectCodeInt;

        if (aBoolean) {
            tempObjectCodeInt = Integer.parseInt(locations.get(labels.indexOf(targetAddress.get(i)) - 1), 16) - Integer.parseInt(locations.get(targetAddress.indexOf(instructions.indexOf("base"))), 16);

        } else if (targetAddress.get(i).contains("=X'")) {

            tempObjectCodeInt = Integer.parseInt(locations.get(instructions.indexOf(targetAddress.get(i)) - 1), 16) - Integer.parseInt(locations.get(i), 16);

        } else {
            tempObjectCodeInt = Integer.parseInt(locations.get(labels.indexOf(targetAddress.get(i).replace(",X", "")) - 1), 16) - Integer.parseInt(locations.get(i), 16);

        }
        tempObjectCodeString = prepareTheOb(tempObjectCodeInt);
        return tempObjectCodeString;
    }

    private void handleBaseObjectCode(int i, Boolean[] objectCode, String tempObjectCodeString) {
        if (objectCode[6] && objectCode[7]) {
            tempObjectCodeString = String.format("%12s", Long.toBinaryString(Long.parseLong(locations.get(labels.indexOf(targetAddress.get(i).replace("#", "").replace("@", "")) - 1), 16))).replace(" ", "0");
        } else if (objectCode[6]) {
            tempObjectCodeString = String.format("%12s", Long.toBinaryString(Long.parseLong(locations.get(labels.indexOf(targetAddress.get(locations.indexOf(targetAddress.get(i).replace("#", "").replace("@", ""))).replace("#", "").replace("@", ""), 16) - 1)))).replace(" ", "0");

        } else if (objectCode[7]) {
            tempObjectCodeString = String.format("%12s", Long.toBinaryString(Long.parseLong(targetAddress.get(i).replace("#", ""), 16))).replace(" ", "0");

        }
        tempObjectCodeString = tempObjectCodeString.replace("'", "").replace("'", "");
        StringToBinArray(objectCode, tempObjectCodeString);

        objectCodes.add(Long.toHexString(Long.parseLong(obtoString(objectCode, locations.get(i) + " " + labels.get(i) + " " + instructions.get(i) + " " + targetAddress.get(i)), 2)));
        return;
    }

    private void handleFormat4ObjectCode(int i, Boolean[] objectCode) {
        String tempObjectCodeString = "";
        if (externalRefs.contains(targetAddress.get(i))) {
            tempObjectCodeString = String.format("%20s", "").replace(" ", "0");
        } else if (targetAddress.get(i).contains("=X'")) {

            tempObjectCodeString = String.format("%20s", Long.toBinaryString(Long.parseLong(locations.get(instructions.indexOf(targetAddress.get(i)) - 1), 16))).replace(" ", "0");
        } else if (objectCode[6] && objectCode[7]) {

            tempObjectCodeString = String.format("%20s", Long.toBinaryString(Long.parseLong(locations.get(labels.indexOf(targetAddress.get(i)) - 1), 16))).replace(" ", "0");
        }
        for (int j = 0; j < tempObjectCodeString.length(); j++) {
            objectCode[12 + j] = (tempObjectCodeString.charAt(j) == '1') ? true : false;
        }

        objectCodes.add(String.format("%8s", Long.toHexString(Long.parseLong(obtoString(objectCode, locations.get(i) + " " + labels.get(i) + " " + instructions.get(i) + " " + targetAddress.get(i)), 2))).replace(" ", "0"));
    }

    private void handleIndexed(int i, Boolean[] objectCode) {
        if (targetAddress.get(i).toLowerCase().contains(",x")) {
            objectCode[8] = true;
        }
    }

    private void handleIndirectAndImmidiate(int i, Boolean[] objectCode) {
        if (!targetAddress.get(i).toLowerCase().contains("@") && !targetAddress.get(i).toLowerCase().contains("#")) {
            objectCode[6] = true;
            objectCode[7] = true;


        } else if (targetAddress.get(i).toLowerCase().contains("#")) {
            objectCode[7] = true;
        } else if (targetAddress.get(i).toLowerCase().contains("@")) {
            objectCode[6] = true;
        }
    }

    private void handleBase(int i, Boolean[] objectCode) {
        if (!objectCode[11]) {
            if (instructions.contains("base")) {
                try {
                    if (Integer.parseInt(locations.get(labels.indexOf(targetAddress.get(i).replace("#", "").replace("@", ""))), 16) - Integer.parseInt(locations.get(i), 16) > 0xfff) {
                        objectCode[9] = true;

                    }
                } catch (Exception e) {

                }
            } else {
                objectCode[10] = true;
            }
        }
    }

    private Boolean[] initalizeObjectCodeArray() {
        Boolean objectCode[] = new Boolean[32];

        for (int j = 0; j < objectCode.length; j++) {
            objectCode[j] = false;
        }
        return objectCode;
    }

    private boolean HandleFormats(int i, Boolean[] objectCode) {
        if (instructions.get(i).toLowerCase().contains("word") || instructions.get(i).toLowerCase().contains("byte")) {
            return HandleWordAndByte(i);
        } else if (format1Instructions.contains(instructions.get(i).toLowerCase())) {
            return HandleFormat1(i);
        } else if (format2Instructions.contains(instructions.get(i).toLowerCase())) {
            return HandleFormatt2(i);
        } else if (format3Instructions.contains(instructions.get(i).toLowerCase())) {
            HandleFormat3(i, objectCode);
        } else if (format4Instructions.contains(instructions.get(i).toLowerCase())) {
            HandleFormat4(i, objectCode);
        } else { // resw , resb ,
            if (instructions.get(i).toLowerCase().contains("extref")) {
                if (externalRefs.isEmpty()) {
                    externalRefs.addAll(Arrays.asList(targetAddress.get(i).split(",")));
                        if(globalExtRef.isEmpty()){
                            globalExtRef.addAll(Arrays.asList(targetAddress.get(i).split(",")));
                            System.out.println(globalExtRef+"glooooooooooooooooooooooooooooooooooooooooooooo");
                        }
                } else {
                    externalRefs.clear();
                    externalRefs.addAll(Arrays.asList(targetAddress.get(i).split(",")));


                }
                return HandleResAndBase("");

            } else if (instructions.get(i).contains("=X")) {
                objectCodes.add(String.format("%6s", instructions.get(i).substring(3, instructions.get(i).length() - 1)).replace(" ", "0"));
                return true;
            } else {
                return HandleResAndBase("");

            }
        }
        if (instructions.get(i).contains("RSUB")) {
            objectCodes.add(String.format("%-6s", Long.toHexString(format3Opcode.get(format3Instructions.indexOf("rsub")))).replace(" ", "0"));
            return true;
        }
        return false;
    }

    private boolean HandleResAndBase(String e) {
        objectCodes.add(e);
        return true;
    }

    private void HandleFormat4(int i, Boolean[] obcode) {
        String tempObjectCodeString = String.format("%8s", Integer.toBinaryString(format4Opcode.get(format4Instructions.indexOf(instructions.get(i).toLowerCase())))).replace(" ", "0");
        for (int j = 0; j < tempObjectCodeString.length() - 2; j++) {
            obcode[j] = (tempObjectCodeString.charAt(j) == '1') ? true : false;
        }
        obcode[11] = true;
        obcode[9] = false;
        obcode[10] = false;
    }

    private void HandleFormat3(int i, Boolean[] obcode) {
        String tempObjectCodeString = String.format("%8s", Integer.toBinaryString(format3Opcode.get(format3Instructions.indexOf(instructions.get(i).toLowerCase())))).replace(" ", "0");
        for (int j = 0; j < tempObjectCodeString.length() - 2; j++) {
            obcode[j] = (tempObjectCodeString.charAt(j) == '1') ? true : false;
        }
        obcode[11] = false;
    }

    private boolean HandleFormatt2(int i) {
        Integer x = format2Opcode.get(format2Instructions.indexOf(instructions.get(i).toLowerCase()));
        String reg[] = targetAddress.get(i).toLowerCase().split(",");
        String tempOb = Integer.toHexString(x);

        for (int j = 0; j < reg.length; j++) {
            tempOb += registersOpcode.get(registers.indexOf(reg[j]));

        }
        objectCodes.add(String.format("%4s", tempOb).replace(" ", "0"));

        return true;
    }

    private boolean HandleFormat1(int i) {

        String x = Integer.toHexString(format1Opcode.get(format1Instructions.indexOf(instructions.get(i).toLowerCase())));
        objectCodes.add(String.format("%2s", x).replace(" ", "0"));
        return true;

    }

    private boolean HandleWordAndByte(int i) {
        String without = targetAddress.get(i).replace("X'", "").replace("C'", "").replace("'", "").replace("=", "");
        if (targetAddress.get(i).contains("X'")) {
            objectCodes.add(without);
        } else {
            if (without.contains(",")) {
                String array[] = without.split(",");
                String c = "";
                for (int k = 0; k < array.length; k++)
                    c += String.format("%6s", Integer.toHexString(Integer.parseInt(array[k]))).replace(" ", "0");
                objectCodes.add(c);
                return true;
            } else if (!targetAddress.get(i).contains("'")) {
                String tempStringToHandleEQU = targetAddress.get(i);
                tempStringToHandleEQU = tempStringToHandleEQU.replace("-", ",-").replace("+", ",+");
                String tempArrayToHandleEQU[] = tempStringToHandleEQU.split(",");
                int eval = 0X0;
                for (int j = 0; j < tempArrayToHandleEQU.length; j++) {
                    if (tempArrayToHandleEQU[j].charAt(0) == '-') {
                        if (externalRefs.contains(tempArrayToHandleEQU[j].replace("-", "").replace("+", ""))) {
                            eval -= 0X0;
                        } else if (Character.isDigit(tempArrayToHandleEQU[j].replace("-", "").replace("+", "").charAt(0))) {
                            eval -= Integer.parseInt(tempArrayToHandleEQU[j].replace("-", ""));
                        } else {
                            eval -= Integer.parseInt(locations.get(labels.indexOf(tempArrayToHandleEQU[j].replace("-", "")) - 1), 16);
                        }

                    } else {
                        if (externalRefs.contains(tempArrayToHandleEQU[j].replace("-", "").replace("+", ""))) {
                            eval += 0X0;
                        } else if (Character.isDigit(tempArrayToHandleEQU[j].replace("-", "").replace("+", "").charAt(0))) {
                            eval += Integer.parseInt(tempArrayToHandleEQU[j].replace("+", ""));
                        } else {
                            eval += Integer.parseInt(locations.get(labels.indexOf(tempArrayToHandleEQU[j].replace("+", "")) - 1), 16);
                        }
                    }

                }

                objectCodes.add(String.format("%6s", Integer.toHexString(eval)).replace(" ", "0"));

            } else {
                try {
                    objectCodes.add(String.format("%6s", Integer.toHexString(Integer.parseInt(without))).replace(" ", "0"));

                } catch (Exception e) {
                    try {

                        String tempS = "";
                        String d3i = without;
                        for (int j = 0; j < d3i.length(); j++) {
                            if (d3i.charAt(j) != '\"' && d3i.charAt(j) != ',') {
                                tempS += "" + (int) d3i.charAt(j);

                            }
                        }
                        objectCodes.add(String.format("%6s", tempS).replace(" ", "0"));
                    } catch (Exception ee) {

                    }
                }
            }
        }

        return true;
    }


    String obtoString(Boolean[] obcode, String d) {
        String bin = "";
        int length = (obcode[11]) ? obcode.length : obcode.length - 8;
        for (int j = 0; j < length; j++) {
            bin += (obcode[j]) ? "1" : "0";
        }
        System.out.println("The bin code of  '" + d + "'  =  " + bin);
        return bin;
    }


    void printTwo() {
        System.out.println();
        System.out.println("-- print two --");
        for (int i = 0; i < objectCodes.size(); i++) {
            System.out.println(i + " " + objectCodes.get(i));
        }
        System.out.println();
    }


}
