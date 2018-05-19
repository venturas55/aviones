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
public class ExcepcionIdVuelo extends Exception{
        private String texto;
        
        ExcepcionIdVuelo(String S){
        this.texto=S;
        }

    ExcepcionIdVuelo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        
    @Override
        public String toString(){
            return "Excepcion propia ["+this.texto+"]  Id de vuelo incorrecto\n\tDebe de :\n\t\t-Comenzar por dos letras en mayusculas.\n\t\t-Acabar con 4 números.";
        }
        
    
    
    
    
}
