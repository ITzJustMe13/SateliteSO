package Sistema;


import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WorkerThread extends Thread{

    private int id;
    private Queue<Tarefa> buffer;

    private Mem memory;

    private MonitorNext tarefaMonitor;
    private Sinal signal;
    private Monitor monitor;
    


    public WorkerThread(int id, Queue<Tarefa> buffer, Mem memory, Sinal signal, Monitor monitor) {
        this.id = id;
        this.signal = signal;

        this.buffer = buffer;
        this.memory = memory;
        this.monitor = monitor;

    }

    @Override
    public void run() {

        setName("Worker " + id);
        executeTasks();

    }

    private void executeTasks() {
        
        
        
        while (true) {
            Tarefa task=null;
            
            while (!signal.temDadosParaProcessar() || buffer.isEmpty()) {
                monitor.espera(); //As outras threads ficam à espera de serem acordadas e do sinal
            }
            
            //mais do que uma thread podem tentar ir buscar a primeira tarefa da fila
            synchronized (this.buffer) {
                task = buffer.poll();
            }

            //Se a tarefa == null , significa que a thread nao conseguiu ir buscar mais nenhuma tarefa na fila
            if (task == null){
                signal.setDadosParaProcessar(false); //assinala que ja foi tudo executado
                continue;
             }

            System.out.println(Thread.currentThread().getName() + " processando a tarefa: " + task.getRequest());
            
            
            if (task.getRequest().equals("read")) {
                 System.out.println("Executing task: " + task.getRequest());
                 task.setResponse(memory.read());

                 System.out.println("Finished task: " + task.getRequest());
             }else if (task.getRequest().equals("getCoords")) {
                 System.out.println("Executing task: " + task.getRequest());
                 task.setResponse(memory.getCoords());

                 System.out.println("Finished task: " + task.getRequest());
             }else if (task.getRequest().equals("getTemp")){
                 System.out.println("Executing task: " + task.getRequest());
                 task.setResponse(memory.getTemp());

                 System.out.println("Finished task: " + task.getRequest());
             }else if (task.getRequest().equals("count")){
                
                 MonitorNext sem_monitor = new MonitorNext();
                 task.setResponse("");
                 
                 CountTask[] count_tasks = new CountTask[100];
                 for(int i=0; i<100; i++){ 
                     count_tasks[i] = new CountTask(i, task, sem_monitor);
                     count_tasks[i].start();
                 }
                 
                
                 
                 for(int i=0; i<100; i++){
                     try {
                         count_tasks[i].join();
                     } catch (InterruptedException ex) {
                         
                     }
                 }
                 
                
                 
             }else if(task.getRequest().equals("getSpeed")){
                 System.out.println("Executing task: " + task.getRequest());
                 task.setResponse(memory.getSpeed());

                 System.out.println("Finished task: " + task.getRequest());
             }else if(task.getRequest().equals("getName")){
                 System.out.println("Executing task: " + task.getRequest());
                 task.setResponse(memory.getName());

                 System.out.println("Finished task: " + task.getRequest());
             }else if(task.getRequest().equals("getAll")){
                 System.out.println("Executing task: " + task.getRequest());
                 task.setResponse(memory.getAll());

                 System.out.println("Finished task: " + task.getRequest());
             }
            
            
            
            task.setReady(true);
            
             if(buffer.isEmpty()){
                 signal.setDadosParaProcessar(false); //assinala que ja foi tudo executado
             }
            
        }
        
      
    }
    
    private class CountTask extends Thread{
        
        private Tarefa t;
        private MonitorNext monitor;
        
        public CountTask(int i, Tarefa t, MonitorNext monitor){
            setName("Worker " + i);
            this.t = t;
            this.monitor = monitor;
        }
        
        @Override
        public void run(){
            monitor.aquire();
            t.setResponse( t.getResponse() + getName()+" | "); ;
            monitor.release();
        }
    }
}
