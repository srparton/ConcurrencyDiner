

public class Chop {
        private int N ;
        private boolean available [ ] ;
        
        Chop (int N) {
            this.N = N ;
            this.available = new boolean[N] ;
            for (int i =0 ; i < N ; i++) {
            available[i] = true ; // non allocated stick
        }
    }

    public synchronized void get_LR (int me) {
        while ( !available [me]) {
            System.out.println("Diner " + me + " is waitng for their left chopstick.");
            try { wait() ; } catch (InterruptedException e) {}
        }
        available [me] = false ;
        System.out.println("Diner " + me + " has their left chopstick.");

        while ( !available [(me + 1)% N]) {
            System.out.println("Diner " + me + " is waitng for their right chopstick.");
            try { wait() ; } catch (InterruptedException e) {}
        }
        available [(me + 1)% N] = false ;
        System.out.println("Diner " + me + " has their right chopstick.");
    }

    public synchronized void release (int me) {
        available [me] = true ; available [(me + 1)% N] = true ;
        notifyAll() ;
        System.out.println("Diner " + me + " has put down the chopsticks.");
    }
}
