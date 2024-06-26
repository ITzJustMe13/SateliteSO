package Sistema;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;


public class Cpu extends Thread{

    //private static List<WorkerThread> cache = new ArrayList<>();
    private Mem memory;
    private Queue<Tarefa> bufferMiddle;
    private Sinal signal;
    private Monitor monitor;


    public Cpu(Mem memory, Sinal signal, Monitor monitor) {
        this.memory = memory;
        this.bufferMiddle = memory.getBuffer();
        this.signal = signal;
        this.monitor = monitor;
    }

    public void createThreads()  { //cria tarefas
        
            
            // Cria e inicia 5 threads trabalhadoras
            for (int i = 0; i < 5; i++) {
                new WorkerThread(i, bufferMiddle, memory, signal, monitor).start();
            }
        
 
    }
    
    @Override
    public void run(){

        createThreads();
             
    }
    



}