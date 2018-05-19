/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluable2;

/**
 *
 * @author ventu
 */
public abstract class Avion {
    
    private String identificador;
    private int tiemposal;
    private float combustible;
    private String destino;
    private String tipo;
    private float combustibleinicial;
    
    //Definimos el constructor
    
   public Avion(){
       identificador="N/A";
       tiemposal=-1;
       tipo="N/A";
   }

   public Avion(String id,String tipo_,String destino_){
       identificador=id;
       tipo=tipo_;
       tiemposal=-1;
       destino=destino_;
   }
    
    

    /**
     * @return the identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * @param identificador the identificador to set
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * @return the tiemposal
     */
    public int getTiemposal() {
        return tiemposal;
    }

    /**
     * @param tiemposal the tiemposal to set
     */
    public void setTiemposal(int tiemposal) {
        this.tiemposal = tiemposal;
    }

    /**
     * @return the combustible
     */
    public float getCombustible() {
        return combustible;
    }

    /**
     * @param combustible the combustible to set
     */
    public void setCombustible(float combustible) {
        this.combustible = combustible;
    }
    
    //Definimos la clase abstracta
    abstract void llenardeposito();
    abstract void iniciardeposito();
    //abstract void mostrar();

    /**
     * @return the destino
     */
    public String getDestino() {
        if (destino.equals("Local"))
            return destino+"  ";    //Porque se me descuadra si el destino es Local
        else
            return destino;
    }

    /**
     * @param destino the destino to set
     */
    public void setDestino(String destino) {
        this.destino = destino;
    }
    
        //Funcion para pasar un valor en segundos a formato mm:ss 
    public String timeToHour(int tiempo){
        int sec,min;
        String ssec,smin;
        min=tiempo/60;
        sec=tiempo%60;
        ssec=Integer.toString(sec);
        smin=Integer.toString(min);
        if (ssec.length()==1)
            ssec="0"+ssec;
        if (smin.length()==1)
            smin="0"+smin;
        return ""+smin+":"+ssec;       
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the combustibleinicial
     */
    public float getCombustibleinicial() {
        return combustibleinicial;
    }

    /**
     * @param combustibleinicial the combustibleinicial to set
     */
    public void setCombustibleinicial(float combustibleinicial) {
        this.combustibleinicial = combustibleinicial;
    }
    
}
