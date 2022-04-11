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

    public void startService() {
        Integer dinerNum;
        while (doneEating < numOfDiners){
            try {
                dinerNum = queue.take();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("Diner service interupted!");
                return;
            }
            if(!dinersEating.get(dinerNum - 1) && !dinersEating.get(dinerNum + 1)){
                dinersEating.set(dinerNum);
            }
        }
    }

    public void page(int dinerNum) {
        
    }
}
