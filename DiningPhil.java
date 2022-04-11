import java.util.concurrent.Semaphore;
import java.lang.Thread;
import java.util.concurrent.ThreadLocalRandom;

public class DiningPhil extends Thread {
    
    public int thread_id; // Used in constructor for DiningPhil
    //private static final int MAX_TIME = 10000; // May not be needed in java
    private static final int EAT = 2; //How many times should each philo eat
    private static final int DINERS_NUM = 5; //Number of philos
    private static Semaphore[] forks = new Semaphore[DINERS_NUM];// Array of semaphrores for forks.

    public DiningPhil(int id) {
        thread_id = id;
    }

    public static void diner(int id) {

    }

    public void run(){
        System.out.println("Diner " + thread_id + " is sitting down at the table.");
        int timesEaten = 0;
        Semaphore firstFork;
        Semaphore secondFork;
        if(thread_id % 2 == 0){                        
            firstFork = forks[thread_id];      //Half of the diners will call for their left
            secondFork = forks[(thread_id+1) % DINERS_NUM];   //fork first, then call for their right fork.
        }
        else{
            firstFork = forks[(thread_id+1) % DINERS_NUM];    //The other half of the diners will call for
            secondFork = forks[thread_id];     //the right fork then the left fork.
        }
        while (timesEaten < EAT){
            int sleepTime = ThreadLocalRandom.current().nextInt(0,2000); //determine a random amount of time between 0 and 2 seconds
            System.out.println("Diner " + thread_id + " says they are thinking.");
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } //Diner "Thinks"
            System.out.println("Diner " + thread_id + " says they are hungry.");

            //Code for Grabbing Forks
            try {
                firstFork.acquire();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {
                secondFork.acquire();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } 


            System.out.println("Diner " + thread_id + " has forks and is eating.");
            timesEaten++;

            sleepTime = ThreadLocalRandom.current().nextInt(0,2000);
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } //Diner "eats" for rand time between 0 and 2 seconds

            System.out.println("Diner " + thread_id + " is content. Putting forks back.");

            firstFork.release();
            secondFork.release();

        }
        System.out.println("Diner " + thread_id + " is full, they are leaving the table.");
    }

    public static void main(String[] args) {
        // USE IF WE WANT USER INPUT FOR DINER_NUM
        // do {
        //     System.out.printf("%d diners will be seated tonight.\n",DINERS_NUM);
        //     //System.out.printf("\nYou said %d diners.", diners);
        //     if (DINERS_NUM == 0) System.out.println("You must seat atleast 2 diners.");
        //     if (DINERS_NUM == 1) System.out.println("One is the loneliest number, please find a friend.");
        //     if (DINERS_NUM < 0)	System.out.println("It is impossible to seat a negative number of diners.");
        // } while (DINERS_NUM < 2 && DINERS_NUM > 12);

        // Initallizing forks array with semaphores
        for (int i = 0; i < DINERS_NUM; i++) {
            forks[i] = new Semaphore(1);
            System.out.println(forks[i]);
        }

        DiningPhil[] philo = new DiningPhil[DINERS_NUM];

        // Initiallizing diners array with threads
        for (int i = 0; i < DINERS_NUM; i++) {
            philo[i] = new DiningPhil(i);
            philo[i].start();
        }

        for (int i = 0; i < philo.length; i++) {
            try {
                philo[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}