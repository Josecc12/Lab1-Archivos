/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.lab1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author josed
 */
public class Main {

    private void writeBookmark(String name, byte age, int number) {
        try {
            
            /*El separador entre cada dato es el caracter "C" la longitud del nombre
            es dinamico para eso se asigna el primer byte para saber la longitud del nombre
            al querer leerlo.*/
            
            RandomAccessFile file = new RandomAccessFile("Agenda-Separador.bin", "rw");
            file.seek(file.length());//Para saber el tamaño del nombre
            file.write(name.length());
            file.writeBytes(name);
            file.write(age);
            file.writeInt(number);
            file.writeBytes("C");//SEPARADOR

            file.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void readBookmark() {
        try {
            RandomAccessFile file = new RandomAccessFile("Agenda-Separador.bin", "r");
            int size = file.read();

            while (size != -1) {
                byte letters[] = new byte[size];
                file.read(letters);
                int age = file.readByte();
                int number = file.readInt();
                byte bookmark[] = new byte[1];
                file.read(bookmark);
                System.out.println("Nombre: " + new String(letters));
                System.out.println("Edad: " + age);
                System.out.println("Numero: " + number);
                System.out.println("Separador: " + new String(bookmark));
                System.out.println("------------------");
                size = file.read();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void writeWithSize(String name, byte age, int number) {
        
        //Cada conjunto de datos siempre tendra 40 byts de longitud
        
        try {
            RandomAccessFile file = new RandomAccessFile("Agenda-Tamanio.bin", "rw");
            file.seek(file.length());

            file.writeBytes(name);
            file.write(age);
            file.writeInt(number);

            file.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void readWithSize() {
        try {
            RandomAccessFile file = new RandomAccessFile("Agenda-Tamanio.bin", "r");
            byte letters[] = new byte[35];
            while (file.read(letters) != -1) {
                int age = file.readByte();
                int number = file.readInt();
                System.out.println("Nombre: " + new String(letters));
                System.out.println("Edad: " + age);
                System.out.println("Numero: " + number);
                System.out.println("------------------");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Main main = new Main();
        char[] letters = new char[35];
        for (int i = 0; i < 35; i++) {
            letters[i] = ' ';
        }
        int option = 0;
        Scanner scInt = new Scanner(System.in);

        do {
            System.out.println("1.Ingresar contancto");
            System.out.println("2.Ver contanctos con separador Binario");
            System.out.println("3.Ver contanctos con separador por Tamañio");
            System.out.println("4.Salir");
            option = scInt.nextInt();

            switch (option) {

                case 1 -> {
                    Scanner scByte = new Scanner(System.in);
                    Scanner scString = new Scanner(System.in);
                    
                    System.out.println("Ingrese nombre");
                    String name = scString.nextLine();
                    String name2 = name;
                    for (int i = 0; i < name.length(); i++) {
                        letters[i] = name.charAt(i);
                    }
                    name = String.valueOf(letters);
                    System.out.println("Ingrese edad");
                    byte age = scByte.nextByte();
                    System.out.println("Ingrese numero telefonico");
                    int number = scInt.nextInt();
                    main.writeWithSize(name, age, number);

                    main.writeBookmark(name2, age, number);

                }

                case 2 -> {
                    main.readBookmark();

                }

                case 3 -> {
                    main.readWithSize();

                }

            }

        } while (option != 4);
    }

}
