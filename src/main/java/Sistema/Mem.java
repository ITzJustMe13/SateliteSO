package Sistema;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Mem {
    private Queue<Tarefa> bufferMiddle; //fila de tarefas para serem executadas
    private float x;
    private float y;
    private String name = "Satelite_1";
    private double speed;
    private float[] cords = new float[]{x,y};

    private float temp;

    private Queue<Float> bufferTemp;

    public Mem() {
        this.bufferTemp = new PriorityQueue<Float>();
        this.bufferMiddle = new LinkedList<Tarefa>();
        this.x = 0;
        this.y = 0;
        this.speed = 0;
    }

    private String data;
    //synchronized funciona para um escrever porém para mais escrever é necessário semaforo

    public synchronized void setCoords(float x, float y) {
        this.cords[0] = x;
        this.cords[1] = y;
    }

    public synchronized String read() {
        return this.data + " " + this.cords[0] + " " + this.cords[1];
    }

    public synchronized String getCoords() {
        return this.cords[0] + " " + this.cords[1];
    }

    public synchronized Queue<Tarefa> getBuffer(){
        return this.bufferMiddle;
    }

    public synchronized Queue<Float> getBufferTemp(){
        return this.bufferTemp;
    }

    public synchronized void addtoBuffer(Tarefa task){
        bufferMiddle.add(task); // adicionou à fila de processamento uma Task (String)
    }

    public synchronized void addtoBufferTemp(Float data){
        bufferTemp.add(data);
    }

    public synchronized String getTemp() {
        return "Temperatura: " +  this.temp;
    }

    public synchronized void setTemp(float x) {
        this.temp = x;
    }
    
    public synchronized String getName() {
        return "Name: " + name;
    }
    
    public synchronized String getSpeed() {
        return "Speed: " + String.format("%.2f", speed);
    }
    
    public synchronized String getAll(){
        return "Name: " + name + " Coords: X: "+ this.cords[0] + " Y: " + this.cords[1] + " Speed: "+ this.speed + " Temperature: " + this.temp;
    }
    
    public synchronized void setSpeed(double speed){
        //System.out.println("Speed changed to: "+speed);
        this.speed = speed;
    }
}
