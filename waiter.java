import java.util.BitSet;
import java.util.concurrent.*;

public class Waiter {
    private BitSet dinersEating;
    private int numOfDiners;
    private int doneEating = 0;
    private TransferQueue<Integer> queue;

    public Waiter (int numOfDiners){
        dinersEating = new BitSet(numOfDiners);
    }

    public Waiter() {
    }

    public void setNumOfDiners (int numOfDiners){
        dinersEating = new BitSet(numOfDiners);
    }

    public void startService(DiningPhil[] diners) {
        Integer dinerNum;
        while (doneEating < numOfDiners){
            try {
                dinerNum = queue.take();
            } catch (InterruptedException e) {               
                e.printStackTrace();
                System.out.println("Diner service interupted!");
                return;
            }
            if (dinerNum == 0){
                if (!dinersEating.get(numOfDiners - 1) && !dinersEating.get(1)){
                    dinersEating.set(0);
                    diners[0].interrupt();
                }
            }
            else if (!dinersEating.get(dinerNum - 1) && !dinersEating.get((dinerNum + 1) % numOfDiners)){
                dinersEating.set(dinerNum);
                diners[dinerNum].interrupt();
            }
            else { 
                try {
                queue.add(dinerNum);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Out of room in the queue, this shouldn't happen.");
                    System.exit(1);
                }

            }
        }
    }

    public void page(int dinerNum) {
        try {
            queue.put(dinerNum);
        } catch (InterruptedException e) {
            System.out.println("Out of room in the queue, this shouldn't happen.");
            e.printStackTrace();
            System.exit(1);
        }
        Thread.sleep();
    }
}
