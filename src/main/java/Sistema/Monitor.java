package Sistema;

public class Monitor {

    public synchronized void acordaTodas(){
        this.notifyAll();
    }

    public synchronized void espera(){
        try{
            this.wait();
        }catch(InterruptedException ie){}
    }

    public class WaitNotify{
        Monitor objetoPartilhado = new Monitor();

        public void doWait(){
            synchronized (objetoPartilhado){
                try{
                    objetoPartilhado.wait();
                }catch (InterruptedException e){};
            }
        }

        public void doNotify(){
            synchronized (objetoPartilhado){
                objetoPartilhado.notify();
            }
        }
    }
}
