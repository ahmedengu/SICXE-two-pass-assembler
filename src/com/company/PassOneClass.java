package com.company;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Created by Ahmed Kamel Taha on 03/12/2015.
 */
public class PassOneClass extends SICXE {
    public PassOneClass() throws ScriptException {
        passOne();
    }

    String block0, block1, block2;

    void passOne() throws ScriptException {
        locations.add(targetAddress.get(0));
        block0 = targetAddress.get(0);
        boolean isLTORG = false;
        for (int j = 1; j < instructions.size(); j++) {

            if (isLTORG) {
                for (String litral : litralsPool) {

                    labels.insertElementAt("*", j);
                    instructions.insertElementAt(litral, j);
                    targetAddress.insertElementAt("     ", j);


                    locations.add(Integer.toHexString(Integer.parseInt(locations.lastElement(), 16) + (int) ((litral.length() - 4) / 2.0)));
                    j++;
                }
                litralsPool.clear();
                isLTORG = false;
            } else if (instructions.get(j).toLowerCase().contains("equ")) {
                if (targetAddress.get(j).contains("*")) {
                    locations.add(locations.lastElement());
                } else {
                    String tempStringToHandleEQU = targetAddress.get(j);
                    tempStringToHandleEQU = tempStringToHandleEQU.replace("-", ",-").replace("+", ",+");
                    String tempArrayToHandleEQU[] = tempStringToHandleEQU.split(",");
                    int eval = 0X0;
                    for (int i = 0; i < tempArrayToHandleEQU.length; i++) {
                        if (tempArrayToHandleEQU[i].charAt(0) == '-') {
                            if (Character.isDigit(tempArrayToHandleEQU[i].replace("-", "").replace("+", "").charAt(0))) {
                                eval -= Integer.parseInt(tempArrayToHandleEQU[i].replace("-", ""));
                            } else {
                                eval -= Integer.parseInt(locations.get(labels.indexOf(tempArrayToHandleEQU[i].replace("-", "")) - 1), 16);
                            }
                        } else {
                            if (Character.isDigit(tempArrayToHandleEQU[i].replace("-", "").replace("+", "").charAt(0))) {
                                eval += Integer.parseInt(tempArrayToHandleEQU[i].replace("+", ""));
                            } else {
                                eval += Integer.parseInt(locations.get(labels.indexOf(tempArrayToHandleEQU[i].replace("+", "")) - 1), 16);
                            }
                        }

                    }


                    locations.insertElementAt(Integer.toHexString(eval), j - 1);
                }
            } else if (instructions.get(j).toLowerCase().contains("csect")) {
                locations.add(targetAddress.get(0));
            } else if (instructions.get(j).contains("USE")) {
                if (targetAddress.get(j).contains("CDATA")) {
                    if (block1 == null) {
                        block0 = locations.lastElement();
                        locations.add(targetAddress.get(0));
                    } else {
                        block0 = locations.lastElement();
                        locations.add(block1);
                    }
                } else if (targetAddress.get(j).contains("CBLKS")) {
                    if (block2 == null) {
                        block1 = locations.lastElement();
                        locations.add(targetAddress.get(0));
                    } else {
                        block1 = locations.lastElement();
                        locations.add(block2);
                    }
                } else {
                    block2 = locations.lastElement();
                    locations.add(block0);
                }

            } else if (targetAddress.get(j).contains("=C'")) {
                String tempAscci = "=X'";
                for (int i = 3; i < targetAddress.get(j).length() - 1; i++) {
                    tempAscci += (int) targetAddress.get(j).charAt(i);
                }
                litralsPool.add(tempAscci + "'");
                targetAddress.set(j, tempAscci + "'");
            } else if (targetAddress.get(j).contains("=X'")) {
                litralsPool.add(targetAddress.get(j));
            }
            if (format1Instructions.contains(instructions.get(j).toLowerCase())) {
                locations.add(Integer.toHexString(Integer.parseInt(locations.get(j - 1), 16) + 0x1));
            } else if (format2Instructions.contains(instructions.get(j).toLowerCase())) {
                locations.add(Integer.toHexString(Integer.parseInt(locations.get(j - 1), 16) + 0x2));
            } else if (format4Instructions.contains(instructions.get(j).toLowerCase())) {

                locations.add(Integer.toHexString(Integer.parseInt(locations.get(j - 1), 16) + 0x4));

            } else if (format3Instructions.contains(instructions.get(j).toLowerCase())) {
                locations.add(Integer.toHexString(Integer.parseInt(locations.get(j - 1), 16) + 0x3));

            } else if (instructions.get(j).toLowerCase().contains("word")) {
                if (targetAddress.get(j).contains(",")) {
                    int comma = 1;
                    for (int i = 0; i < targetAddress.get(j).length(); i++) {
                        if (targetAddress.get(j).charAt(i) == ',') {
                            comma++;
                        }
                    }
                    comma *= 3;
                    locations.add(Integer.toHexString(Integer.parseInt(locations.get(j - 1), 16) + Integer.parseInt(String.valueOf(comma), 16)));

                } else {
                    locations.add(Integer.toHexString(Integer.parseInt(locations.get(j - 1), 16) + 0x3));

                }

            } else if (instructions.get(j).toLowerCase().contains("byte")) {
                if (targetAddress.get(j).toLowerCase().contains("x'")) {
                    String x = String.valueOf((int) ((targetAddress.get(j).toLowerCase().length() - 3) / 2.0));

                    locations.add(Integer.toHexString(Integer.parseInt(locations.get(j - 1), 16) + Integer.parseInt(x, 16)));
                } else if (targetAddress.get(j).toLowerCase().contains("c'")) {
                    String x = String.valueOf((int) ((targetAddress.get(j).toLowerCase().length() - 3))); // do  i need to * 3

                    locations.add(Integer.toHexString(Integer.parseInt(locations.get(j - 1), 16) + Integer.parseInt(x, 16)));
                }
            } else if (instructions.get(j).toLowerCase().contains("resw")) {
                String x = Integer.toHexString(Integer.parseInt(targetAddress.get(j)) * 0x3);

                locations.add(Integer.toHexString(Integer.parseInt(locations.get(j - 1), 16) + Integer.parseInt(x, 16)));
            } else if (instructions.get(j).toLowerCase().contains("resb")) {
                String x = Integer.toHexString(Integer.parseInt(targetAddress.get(j)));

                locations.add(Integer.toHexString(Integer.parseInt(locations.get(j - 1), 16) + Integer.parseInt(x, 16)));
            } else if (instructions.get(j).toLowerCase().contains("base") || instructions.get(j).toLowerCase().contains("extdef") || instructions.get(j).toLowerCase().contains("extref")) {

                locations.add(locations.lastElement());
            } else if (instructions.get(j).toLowerCase().contains("ltorg")) {
                locations.add(locations.lastElement());
                isLTORG = true;
            }
        }
        if (litralsPool.size() > 0) {

            for (String litral : litralsPool) {

                labels.insertElementAt("*", labels.size() - 1);
                instructions.insertElementAt(litral, instructions.size() - 1);
                targetAddress.insertElementAt("     ", targetAddress.size() - 1);


                locations.add(Integer.toHexString(Integer.parseInt(locations.lastElement(), 16) + (int) ((litral.length() - 4) / 2.0)));

            }
            litralsPool.clear();
        }

    }

    void symbolTable() {
        System.out.println("-- symbol table --");
        for (int i = 0; i < labels.size(); i++) {
            if (labels.get(i).equals("") == false && labels.get(i).equals("     ") == false && labels.get(i).equals(" ") == false) {
                System.out.println(locations.get(i) + " " + labels.get(i));
            }
        }
        System.out.println();
    }

    void printOne() {
        System.out.println("-- print one --");
        System.out.println(0 + " " + locations.get(0) + " " + labels.get(0) + " " + instructions.get(0) + " " + targetAddress.get(0));
        for (int i = 1; i < instructions.size(); i++) {
            System.out.println(i + " " + locations.get(i - 1) + " " + labels.get(i) + " " + instructions.get(i) + " " + targetAddress.get(i));
        }
        System.out.println();
    }
}
