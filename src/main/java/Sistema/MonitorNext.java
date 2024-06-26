package Sistema;

import java.util.concurrent.Semaphore;

public class MonitorNext {

    private Semaphore semaphore;

    public MonitorNext() {
        semaphore = new Semaphore(1, true);
    }
    
    public void aquire(){
        try {
            semaphore.acquire();
        } catch (InterruptedException ignored) {}
    }

    public void release() {
        semaphore.release();
    }
}
