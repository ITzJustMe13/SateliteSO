package Sistema;

import java.util.Queue;
import java.util.Random;

public class SensorTemp {

    private Kernel kernel;


    private SensorTemp.BufferTempAdd addtoBufferTemp;

    public SensorTemp(Kernel kernel) {
        this.kernel = kernel;
        addtoBufferTemp = new BufferTempAdd(kernel.getMemory().getBufferTemp());
        addtoBufferTemp.start();
    }

    class BufferTempAdd extends Thread{

        private Float temp;
        private Queue<Float> bufferTemp;

        private Monitor monitorTemp;

        public BufferTempAdd(Queue<Float> bufferTemp){
            this.bufferTemp = bufferTemp;

            this.monitorTemp = kernel.monitorTemp;
        }

        @Override
        public void run(){
            while(true){
                synchronized (monitorTemp) {
                while (kernel.sinalTemp.temDadosParaProcessar()) {
                    monitorTemp.espera();
                }

                Random random = new Random();

                float temp = random.nextFloat(8) + 10;
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                kernel.addToBufferTemp(temp);


                kernel.sinalTemp.setDadosParaProcessar(true);

                monitorTemp.acordaTodas();
                }
            }

        }
    }
}
