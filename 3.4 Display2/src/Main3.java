import java.util.concurrent.*;
import java.util.Random;

public class Main3 {


   private static void nap(int millisecs) {
        try {
            Thread.sleep(millisecs);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void addProc(HighLevelDisplay d, Semaphore sem) throws InterruptedException {


	// Add a sequence of addRow operations with short random naps.
        Random rand1 = new Random();
        int n1 = rand1.nextInt(500);
        for (int i=0; i<20; i++){
            sem.acquire();
            d.addRow("MMMMMMMMMMMM " + i);
            d.addRow("JJJJJJJJJJJJ " + i);
            d.addRow("KKKKKKKKKKKK " + i);
            sem.release();
            nap(n1);

        }
   }

    private static void deleteProc(HighLevelDisplay d, Semaphore sem) throws InterruptedException {
	
	// Add a sequence of deletions of row 0 with short random naps.
        //Note: With the thread dangerous JDisplay2 class, delete line thread can go wrong: 1. no row0 was found so nothing gets deleted
        //2.

        Random rand2 = new Random();
        int n2 = rand2.nextInt(500);
        for (int i=0; i<20; i++){
            sem.acquire();
            d.deleteRow(0);
            sem.release();
            nap(n2);
        }
    }

    public static void main(String [] args) {
	final HighLevelDisplay d = new JDisplay2();
	final Semaphore sem = new Semaphore(1);

	new Thread () {
	    public void run() {
            try {
                addProc(d, sem);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
	}.start();


	new Thread () {
	    public void run() {
            try {
                deleteProc(d, sem);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
	}.start();

    }
}