package com.company;

/**
 * Created by Ahmed Kamel Taha on 03/12/2015.
 */
public class HTEClass extends SICXE {


    public HTEClass() {

    }


    void HTE() {
        System.out.println("-- HTE Record --");
        HRecord();

        DRecord();


        RRecord();
        TRecord();
        MRecord();


        ERecord();
    }

    private void RRecord() {
        String tempArrayForEXTREF[] = targetAddress.get(instructions.indexOf("EXTREF")).split(",");
        String tempRRecord = "R";
        for (int i = 0; i < tempArrayForEXTREF.length; i++) {
            tempRRecord += " " + tempArrayForEXTREF[i];
        }
        System.out.println(tempRRecord);
    }

    private void DRecord() {
        String tempArrayForEXTDEF[] = targetAddress.get(instructions.indexOf("EXTDEF")).split(",");
        String tempDRecord = "D";
        for (int i = 0; i < tempArrayForEXTDEF.length; i++) {
            tempDRecord += " " + tempArrayForEXTDEF[i] + " " + locations.get(labels.indexOf(tempArrayForEXTDEF[i]) - 1);
        }
        System.out.println(tempDRecord);
    }

    void HRecord() {
        String h = "H " + labels.get(0) + " " + locations.get(0) + " ";
        h += Integer.toHexString(Integer.parseInt(locations.get(locations.size() - 1), 16) - Integer.parseInt(locations.get(0), 16));
        System.out.println(h);
    }

    void TRecord() {
        String tempt = "";
        String temStart = "";
        for (int i = 0, j = 0; i < objectCodes.size(); i++) {
            if (i == objectCodes.size()|| instructions.get(i).toLowerCase().contains("use")|| instructions.get(i).toLowerCase().contains("csect")) {
                System.out.println("T " + temStart + " " + Integer.toHexString((int) Math.ceil(tempt.length() / 2.0)) + " " + tempt);
                temStart = "";
                break;
            }

            if (instructions.get(i).toLowerCase().contains("resw") || instructions.get(i).toLowerCase().contains("resb") || instructions.get(i).toLowerCase().contains("base") || instructions.get(i).toLowerCase().contains("use")) {
                j = 1;
            } else if (j == 1) {
                System.out.println("T " + temStart + " " + Integer.toHexString((int) Math.ceil(tempt.length() / 2.0)) + " " + tempt);
                temStart = "";
                j = 0;
                tempt = "";
                i--;
            } else if (tempt.length() >= 30) {
                j = 1;
                i--;
            } else {
                if (temStart.length() < 1) {
                    temStart = locations.get(i);
                }
                tempt += objectCodes.get(i);

            }

        }
    }

    void ERecord() {
        String e = "E " + locations.get(0);
        System.out.println(e);
        System.out.println();
    }

    void MRecord() {
        for (int i = 0; i < instructions.size(); i++) {
            if ( instructions.get(i).toLowerCase().contains("use")|| instructions.get(i).toLowerCase().contains("csect")) {
                                break;
            }
            if (instructions.get(i).contains("+")) {
                if (globalExtRef.contains(targetAddress.get(i))) {
                    String tempStringToHandleEQU = targetAddress.get(i);
                    tempStringToHandleEQU = tempStringToHandleEQU.replace("-", ",-").replace("+", ",+");
                    String tempArrayToHandleEQU[] = tempStringToHandleEQU.split(",");
                    int eval = 0X0;
                    for (int j = 0; j < tempArrayToHandleEQU.length; j++) {
                        if (tempArrayToHandleEQU[j].charAt(0) == '-') {
                            if (globalExtRef.contains(tempArrayToHandleEQU[j].replace("-", "").replace("+", ""))) {
                                System.out.println("M " + String.format("%6s", Integer.toHexString(Integer.parseInt(locations.get(i - 1), 16) + 0x1)).replace(" ", "0") + " 05 " + tempArrayToHandleEQU[j]);
                            } else if (!Character.isDigit(tempArrayToHandleEQU[j].replace("-", "").replace("+", "").charAt(0))){
                                System.out.println("M " + String.format("%6s", Integer.toHexString(Integer.parseInt(locations.get(i - 1), 16) + 0x1)).replace(" ", "0") + " 05 -" + labels.get(0));
                            }

                        } else {
                            if (globalExtRef.contains(tempArrayToHandleEQU[j].replace("-", "").replace("+", ""))) {
                                System.out.println("M " + String.format("%6s", Integer.toHexString(Integer.parseInt(locations.get(i - 1), 16) + 0x1)).replace(" ", "0") + " 05 +" + tempArrayToHandleEQU[j].replace("+","").substring(0));
                            } else if (!Character.isDigit(tempArrayToHandleEQU[j].replace("-", "").replace("+", "").charAt(0))){
                                System.out.println("M " + String.format("%6s", Integer.toHexString(Integer.parseInt(locations.get(i - 1), 16) + 0x1)).replace(" ", "0") + " 05 +" + labels.get(0));
                            }
                        }

                    }

                }else{
                System.out.print("M ");
                System.out.print(String.format("%6s", Integer.toHexString(Integer.parseInt(locations.get(i - 1), 16) + 0x1)).replace(" ", "0"));
                System.out.println(" 05 +" + labels.get(0));}
            } else if (instructions.get(i).contains("WORD")) {
                if (targetAddress.get(i).contains("+") || targetAddress.get(i).contains("-")) {
                    String tempStringToHandleEQU = targetAddress.get(i);
                    tempStringToHandleEQU = tempStringToHandleEQU.replace("-", ",-").replace("+", ",+");
                    String tempArrayToHandleEQU[] = tempStringToHandleEQU.split(",");
                    int eval = 0X0;
                    for (int j = 0; j < tempArrayToHandleEQU.length; j++) {
                        if (tempArrayToHandleEQU[j].charAt(0) == '-') {
                            if (globalExtRef.contains(tempArrayToHandleEQU[j].replace("-", "").replace("+", ""))) {
                                System.out.println("M " + String.format("%6s", locations.get(i - 1)).replace(" ", "0") + " 06 " + tempArrayToHandleEQU[j]);
                            } else if (!Character.isDigit(tempArrayToHandleEQU[j].replace("-", "").replace("+", "").charAt(0))){
                                System.out.println("M " + String.format("%6s", locations.get(i - 1)).replace(" ", "0")  + " 06 -" + labels.get(0));
                            }

                        } else {
                            if (globalExtRef.contains(tempArrayToHandleEQU[j].replace("-", "").replace("+", ""))) {
                                System.out.println("M " + String.format("%6s", locations.get(i - 1)).replace(" ", "0")  + " 06 +" + tempArrayToHandleEQU[j].replace("+","").substring(0));
                            } else if (!Character.isDigit(tempArrayToHandleEQU[j].replace("-", "").replace("+", "").charAt(0))){
                                System.out.println("M " + String.format("%6s", locations.get(i - 1)).replace(" ", "0")  + " 06 +" + labels.get(0));
                            }
                        }

                    }

                }
            }
        }
    }
}
