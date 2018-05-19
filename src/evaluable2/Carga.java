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
public class Carga extends Avion{

    private final static float tankfuel=500;
    
            //Definimos el constructores
    public Carga(){
       super();
       super.setDestino("N/A");
       super.setTipo("Carga");
       this.iniciardeposito();
       this.setCombustibleinicial(this.getCombustible());
   }
   
    public Carga(String id, String destino){
       super( id, "Carga" , destino );
       this.iniciardeposito();
       this.setCombustibleinicial(this.getCombustible());
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
    
    
    //sobrescribimos el metodo toString. Es diferente al de Comercial y Avioneta para justificar el mostrar en el main.(Tiene mas espacios entre id vuelo y tipo)
    @Override
    public String toString(){
        return "   Vuelo "+this.getTipo()+"     " +this.getIdentificador() +"\t "+ this.getDestino()+ "\t salida prevista en " + timeToHour(this.getTiemposal())+" y su combustible es de "+this.getCombustible()+" litros.( "+ String.format("%.2f", this.getCombustible()/this.getTankfuel()*100) + "%).";        
    }

    /**
     * @return the tankfuel
     */
    public float getTankfuel() {
        return tankfuel;
    }
}
