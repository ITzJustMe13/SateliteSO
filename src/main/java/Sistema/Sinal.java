package Sistema;

public class Sinal {

     boolean temDadosParaProcessar = false;
     public synchronized boolean temDadosParaProcessar(){
         return this.temDadosParaProcessar;
    }

    public synchronized void setDadosParaProcessar(boolean temDados){
         this.temDadosParaProcessar = temDados;
    }
}
