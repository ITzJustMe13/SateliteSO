package Sistema;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Kernel {
    private Cpu cpu;

    //private BufferRead readBuffer;

    private BufferTempRead readTempBuffer;

    private Mem memory;

    public Sinal sinal;

    public Sinal sinalTemp;

    public Monitor monitor;

    public Monitor monitorTemp;

    private MovimentoSatelite simulator;

    private SensorTemp sensorTemp;


    public Kernel() {
        this.memory = new Mem();
        
        this.monitor = new Monitor();
        this.sinal = new Sinal();
        this.sinalTemp = new Sinal();
        this.monitorTemp = new Monitor();
       
        
        this.readTempBuffer = new BufferTempRead(memory.getBufferTemp());
        readTempBuffer.start();
        this.sensorTemp = new SensorTemp(this);
        
        this.cpu = new Cpu(memory,sinal, this.monitor);
        cpu.start();
        
        //this.readBuffer = new BufferRead(memory.getBuffer());
        //readBuffer.start();
        
        this.simulator = new MovimentoSatelite();
        simulator.start();
        
        new SimuladorVelocidade().start();
    }

    
    /*Metodo usado para adicionar uma tarefa à fila de processamento*/
    public synchronized void addToBuffer(Tarefa task)  {
        memory.addtoBuffer(task);
    }

    public synchronized void addToBufferTemp(Float Temp)  {
        memory.addtoBufferTemp(Temp);
    }


    public Mem getMemory() {
        return memory;
    }

    
    class BufferTempRead extends Thread{

        private Queue<Float> buffertemp;

        private Monitor monitorTemp;

        public BufferTempRead(Queue<Float> buffertemp){
            this.buffertemp = buffertemp;
            this.monitorTemp = Kernel.this.monitorTemp;
        }

        @Override
        public void run(){
            while(true){
                synchronized (monitorTemp) {
                    if (!buffertemp.isEmpty()) {
                        while (!sinalTemp.temDadosParaProcessar()) {
                            monitorTemp.espera();
                        }

                        System.out.println(buffertemp);
                        System.out.println("Changed Temp");
                        memory.setTemp(buffertemp.poll());


                        sinalTemp.setDadosParaProcessar(false);
                        monitorTemp.acordaTodas();

                    }else{
                        //para nao ser tao CPU intensive
                        try {
                            this.sleep(100);
                        } catch (InterruptedException ex) {
                      
                        }
                    }
                }
            }
        }

    }

    class MovimentoSatelite extends Thread{
        private static final int centerX = 5; // Centro x da função
        private static final int centerY = 5; // Centro y da função
        private static final int radius = 3; // Raio da função
        private static final int orbitSpeed = 5; // Velocidade de órbita
        double angle = 0;

        private int i;
        private int j;

        @Override
        public void run(){
            while(true){
                i = (int) (centerX + radius * Math.cos(angle));
                j = (int) (centerY + radius * Math.sin(angle));

                memory.setCoords(i, j);
                angle += Math.toRadians(orbitSpeed);
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }
    
    class SimuladorVelocidade extends Thread{
        
        
        double angle = 0;
        private final double BASE_SPEED = 40;
        
        
        @Override
        public void run(){
            double speed;
            while(true){
                
                speed = Math.abs( BASE_SPEED * Math.sin(angle) );
                this.angle += Math.toRadians(10);
                try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                memory.setSpeed(speed);
            }
        }
    }


}
