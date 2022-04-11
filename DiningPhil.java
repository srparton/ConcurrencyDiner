import java.util.concurrent.Semaphore;
import java.lang.Thread;
import java.util.concurrent.ThreadLocalRandom;

public class DiningPhil extends Thread {
    
    public int thread_id; // Used in constructor for DiningPhil
    private static final int MAX_TIME = 10000; // May not be needed in java
    private static final int EAT = 2; //How many times should each philo eat
    private static final int DINERS_NUM = 5; //Number of philos
    private static Semaphore[] forks = new Semaphore[DINERS_NUM];// Array of semaphrores for forks.

    public DiningPhil(int id) {
        thread_id = id;
    }

    public static void diner(int id) {
        System.out.println("Diner " + id + " is sitting down at the table.");
        public int timesEaten = 0;
        if(id % 2 == 0){                        
            Semaphore firstFork = fork[i];      //Half of the diners will call for their left
            Semaphore secondFork = fork[(i+1) % DINERS_NUM];   //fork first, then call for their right fork.
        }
        else{
            Semaphore firstFork = fork[(i+1) % DINERS_NUM];    //The other half of the diners will call for
            Semaphore secondFork = fork[i];     //the right fork then the left fork.
        }
    }

    public void run() {
        while (timesEaten < EAT){
            System.out.println("Diner " + id + " says they are thinking.");
            Thread.sleep(ThreadLocalRandom.current().nextInt(0,2000)); //Diner "Thinks" for rand time between 0 and 2 seconds
            System.out.println("Diner " + id + " says they are hungry.");

            //Code for Grabbing Forks
            firstFork.acquire();
            secondFork.acquire();

            System.out.println("Diner " + id + " has forks and is eating.");
            timesEaten++;
            Thread.sleep(ThreadLocalRandom.current().nextInt(0,2000)); //Diner "eats" for rand time between 0 and 2 seconds

            System.out.println("Diner " + id + " is content. Putting forks back.");

            firstFork.release();
            secondFork.release();

        }
        System.out.println("Diner " + id + " is full, they are leaving the table.")
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