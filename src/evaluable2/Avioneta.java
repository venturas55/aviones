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
public class Avioneta extends Avion{
        private  static float tankfuel=100;
        
             //Definimos el constructores
   public Avioneta(){
       super();
       super.setDestino("Local");
       super.setTipo("Avioneta");
       this.iniciardeposito(); 
       this.setCombustibleinicial(this.getCombustible());  //Almaceno el deposito inicial en una variable
   }
   
   public Avioneta(String id){
       super( id , "Avioneta" , "Local" );
       this.iniciardeposito();
       this.setCombustibleinicial(this.getCombustible());  //Almaceno el deposito inicial en una variable
   }
        
        
        //Sobreescribimos el metodo abstracto de la clase abstracta.
   @Override
    public void llenardeposito(){
            this.setCombustible(getTankfuel());
    }
    
        @Override
    public final void iniciardeposito(){
        this.setCombustible((float)Math.random()*(this.getTankfuel()));
    }
    
    //sobrescribimos el metodo toString
    @Override
    public String toString(){
        return "   Vuelo "+this.getTipo()+"  " +this.getIdentificador() +"\t "+ this.getDestino()+ "\t salida prevista en " + timeToHour(this.getTiemposal())+" y su combustible es de "+this.getCombustible()+" litros.( "+String.format("%.2f", this.getCombustible()/this.getTankfuel()*100) + "%).";        
    }

    /**
     * @return the tankfuel
     */
    public float getTankfuel() {
        return tankfuel;
    }

}
