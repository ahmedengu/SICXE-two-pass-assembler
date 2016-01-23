package com.company;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
public class SICXE {

    public static Vector<String> format1Instructions = new Vector<>();
    public static Vector<String> format2Instructions = new Vector<>();
    public static Vector<String> format3Instructions = new Vector<>();
    public static Vector<String> format4Instructions = new Vector<>();


    public static Vector<Integer> format1Opcode = new Vector<>();
    public static Vector<Integer> format2Opcode = new Vector<>();
    public static Vector<Integer> format3Opcode = new Vector<>();
    public static Vector<Integer> format4Opcode = new Vector<>();

    public static Vector<String> registers = new Vector<>();

    public static Vector<Integer> registersOpcode = new Vector<>();

    public static Vector<String> locations = new Vector<>();
    public static Vector<String> labels = new Vector<>();
    public static Vector<String> instructions = new Vector<>();
    public static Vector<String> targetAddress = new Vector<>();
    public static Vector<String> objectCodes = new Vector<>();

    public static Set<String> litralsPool = new HashSet<>();

    public static Vector<String> globalExtRef = new Vector<>();


    public static void main(String[] args) throws Exception {






        GlobalClass globalClass = new GlobalClass();
//        globalClass.printFile();

        PassOneClass passOne = new PassOneClass();
//        passOne.printOne();

//        passOne.symbolTable();

//        System.out.println("-- Bin code of Objects --\n");
        PassTwoClass passTwoClass = new PassTwoClass();
//        passTwoClass.printTwo();

        globalClass.PrintOneTwo();
        HTEClass hteClass = new HTEClass();
        hteClass.HTE();


    }


}
