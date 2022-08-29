package com.navi.mymoney; 

import java.io.FileInputStream;  
import java.io.IOException;  
import java.util.Scanner; 

public class Main {
    public static void main(String[] args) {
        
        // Sample code to read from file passed as command line argument
        try {
            // the file to be opened for reading
            FileInputStream fis = new FileInputStream(args[0]);
            Scanner sc = new Scanner(fis); // file to be scanned
            
            // Initiate our portfolio
            AssetClassType[] types = {AssetClassType.EQUITY, AssetClassType.DEBT, AssetClassType.GOLD}; 
            Portfolio pf = new Portfolio(types);

            // returns true if there is another line to read
            while (sc.hasNextLine()) {
                String x = sc.nextLine();  // Read user input
                String[] inputArr = x.split(" ", 6);
                String command = inputArr[0];

                switch(command) {
                    case "ALLOCATE":
                        String[] amounts = new String[types.length];
                        for (int i = 1; i < inputArr.length; i++) {
                            amounts[i-1] = inputArr[i];
                        }

                        pf.allocate(amounts);
                        break;
                    case "SIP":
                        String[] sipAmounts = new String[types.length];
                        for (int i = 1; i < inputArr.length; i++) {
                            sipAmounts[i-1] = inputArr[i];
                        }

                        pf.sip(sipAmounts);
                        break;
                    case "CHANGE":
                        String[] changes = new String[types.length];
                        String month = inputArr[inputArr.length - 1];

                        // Extract change percentage values
                        for (int i = 1; i < inputArr.length - 1; i++) {
                            changes[i-1] = inputArr[i].substring(0, inputArr[i].length() - 1);
                        }

                        pf.change(month, changes);
                        break;
                    case "BALANCE":
                        String givenMonth = inputArr[1];
                        int[] b = pf.balance(givenMonth);
                        printIntArray(b);
                        break;
                    case "REBALANCE":
                        try {
                            int[] rebalanced = pf.rebalance();
                            printIntArray(rebalanced);
                        } catch(Exception e) {
                            System.out.println(e.getMessage());
                        }
                      break;
                    default:
                        System.out.println("INVALID_INPUT");
                  }
                
            }

            sc.close(); // closes the scanner
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        
    }

    public static void printIntArray(int[] array) {
        String s = "";
        for(int i = 0; i < array.length; i++) {
            s += Integer.toString(array[i]);

            if(i != array.length - 1) {
                s += " ";
            }
        }

        System.out.println(s);
    }
}
