package Sistema;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Middleware {
    private Kernel kernel;
    private final int PORTO = 4001; //vai receber pedidos da station no porto 4000

    private BufferAdd addtoBuffer; //buffer das tarefas a serem executadas

    public Middleware(Kernel kernel) {
        this.kernel = kernel;
    }
    
    public void sendTaskToKernel(Tarefa task) {
        
        addtoBuffer = new BufferAdd(task);
        addtoBuffer.start();

    }
    
    
    public void start() throws IOException{
        ServerSocket ss = new ServerSocket(PORTO);
        
        try{
            while(true){
                System.out.println("A aceitar comunicacoes no porto "+ PORTO);
                Socket socket = ss.accept();
                
                System.out.println("comunicacao estabelecida.");
                ClientHandler c = new ClientHandler(socket);
                new Thread(c).start();
            }
        }finally{
            ss.close();
        }
    }
    
    private  class ClientHandler implements Runnable{

        private Socket socket;
        private BufferedReader in;
        private BufferedWriter out;


        public ClientHandler(Socket socket){
            this.socket = socket;
        }

        public void doConnections() throws IOException{
            System.out.println("Starting server connections");
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
        }

        //Usado para receber os pedidos do Cliente (Station)
        public void serve() throws IOException{
            
            Queue<Tarefa> tarefas = new LinkedList<Tarefa>();
            Monitor m = new Monitor();
            //Inicializa o clientResponse
            ClientResponse cresp = new ClientResponse(tarefas, out, m);
            cresp.start();
            
            while(true){
                try{
                    Thread.currentThread().sleep(100);
                }catch(InterruptedException e){return;}

                if(in.ready()){
                    String msg = in.readLine();
                    System.out.println("Mensagem do cliente: "+msg);

                    /*public double getTemperatura();
                    public double getDistancia();
                    public double getVelocidade();
                    public double getOrientacao();
                    public int getStatus();
                    public double[] getAll();
    */
                    msg = msg.trim();
                    String response = "";
                    Tarefa t;
                    switch (msg) {
                        case "getTemp":
                            t = new Tarefa("getTemp");
                            tarefas.add(t);
                            sendTaskToKernel(t);
                            m.acordaTodas();
                            break;
                        case "getCoords":
                            t = new Tarefa("getCoords");
                            tarefas.add(t);
                            sendTaskToKernel(t);
                            m.acordaTodas();
                            break;

                        case "count": 
                            t = new Tarefa("count");
                            tarefas.add(t);
                            sendTaskToKernel(t);
                            m.acordaTodas();
                            break;

                        case "getSpeed":
                            t = new Tarefa("getSpeed");
                            tarefas.add(t);
                            sendTaskToKernel(t);
                            m.acordaTodas();
                            //response = ""+this.servicos.getOrientacao();
                            break;

                        case "getName":
                            t = new Tarefa("getName");
                            tarefas.add(t);
                            sendTaskToKernel(t);
                            m.acordaTodas();
                            break;

                        case "getAll":
                            t = new Tarefa("getAll");
                            tarefas.add(t);
                            sendTaskToKernel(t);
                            m.acordaTodas();
                            break;

                        default:
                            break;
                    }

                    
                }
            }
        }

        @Override
        public void run() {
            try{
                doConnections();
                serve();
                socket.close();

            }catch(IOException e){
                System.out.println("Erro de IO no client Handler");
            }
        }

    }
    
    private class ClientResponse extends Thread{
        
        private Queue<Tarefa> tarefas;
        private BufferedWriter out;
        private Monitor monitor; 

        public ClientResponse(Queue<Tarefa> tarefas, BufferedWriter out, Monitor monitor) {
            this.tarefas = tarefas;
            this.out = out;
            this.monitor = monitor;
        }
        
        
        @Override
        public void run(){
            while(true){
                
                if(tarefas.isEmpty()){
                    monitor.espera();
                }
                
                
                synchronized (this.tarefas) {
                    //procura tarefa que já tenha uma resposta produzida
                    Tarefa t=null;
                    for(Tarefa task: this.tarefas){
                        if(task.isReady()){
                            t = task;
                            try{
                                out.write(task.getResponse()+"\n");
                                out.flush();
                            }catch(IOException e){
                                System.out.println("Erro: nao foi possivel enviar a resposta para o cliente");
                                return;
                            }
                            break;
                        }
                    }
                    
                    if(t!=null){
                        this.tarefas.remove(t);
                    }else{
                        try{
                            this.sleep(100);
                        }catch(InterruptedException e){}
                    }
                }
                
            }
        }
    }


    private class BufferAdd extends Thread{

        private Tarefa task;
        private Monitor monitor; //sincronizacao

        public BufferAdd(Tarefa task){
           
            this.task = task; //tarefa String
            this.monitor = kernel.monitor;  //monitor que controla as workerThreads
        }

        @Override
        public void run(){
            synchronized (monitor) {  //usa o monitor definido no kernel para sincronizacao
                /*while (bufferMiddle.size() == 5 || kernel.sinal.temDadosParaProcessar()) {
                    monitor.espera();  //espera que uma thread termine para avancar
                }*/

                kernel.addToBuffer(task);
                System.out.println("ADDED " + task.getRequest());
          
                kernel.sinal.setDadosParaProcessar(true);


                monitor.acordaTodas(); //acorda as workerThreads para trabalharem mais
            }
        }
    }
}