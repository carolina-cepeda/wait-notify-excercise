/*
 * 
 *
 */
package edu.eci.arsw.primefinder;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 */
public class Control extends Thread {
    
    private final static int NTHREADS = 3;
    private final static int MAXVALUE = 30000000;
    private final static int TMILISECONDS = 1000;

    private final int NDATA = MAXVALUE / NTHREADS;

    private PrimeFinderThread pft[];
    
    private Control() {
        super();
        this.pft = new  PrimeFinderThread[NTHREADS];
        int i;
        for(i = 0;i < NTHREADS - 1; i++) {
            PrimeFinderThread elem = new PrimeFinderThread(i*NDATA, (i+1)*NDATA);
            pft[i] = elem;
        }
        pft[i] = new PrimeFinderThread(i*NDATA, MAXVALUE + 1);

    }
         
    
    public static Control newControl() {
        return new Control();
    }

     @Override
    public void run() {

        for (PrimeFinderThread t : pft) {
            t.start();
        }

        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                Thread.sleep(TMILISECONDS);
            } catch (InterruptedException e) {
                break;
            }

            for (PrimeFinderThread t : pft) {
                t.pauseThread();
            }

            int total = 0;
            for (PrimeFinderThread t : pft) {
                total += t.getPrimesFound();
            }

            System.out.println("Total primes found: " + total);
            System.out.println("ENTER to continue");

            String input = scanner.nextLine();

            for (PrimeFinderThread t : pft) {
                t.resumeThread();
            }
        }

        scanner.close();
    }
    
}
