import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Task implements Runnable {

    private volatile boolean done = false; // a volatile boolean to properly stop a thread instead of using stop()

    public void run(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        while (!done){
            LocalDateTime now = LocalDateTime.now();
            System.out.println("Hello World! I'm " + Thread.currentThread().getName() + "."
                    + " The time is " + dtf.format(now));
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Function to stop a thread
    public void shutdown(){
        done = true;
    }

    public static void main(String args[]) throws InterruptedException {
        Task task0 = new Task();
        Thread t0 = new Thread(task0);
        t0.start();

        Task task1 = new Task();
        Thread t1 = new Thread(task1);
        t1.start();

        Map<String, Task> map = new HashMap<>();
        map.put("0", task0);
        map.put("1", task1);
        int number = 1;


        while (true) { // Scan user's input to give instructions: delete a thread, add new thread and terminate all threads
            Scanner myObj = new Scanner(System.in);
            System.out.println("Enter Instruction");
            String instruction = myObj.nextLine();
            if (map.containsKey(instruction)) {
                map.get(instruction).shutdown();
            }
            else if (instruction.equals("new")){
                number ++;
                Task task2 = new Task();
                Thread t2 = new Thread(task2);
                map.put(Integer.toString(number), task2);
                t2.start();
            }
            else if (instruction.equals("end")){
                System.out.println("Threads are terminated");
                System.exit(0);
            }
        }
    }

}
