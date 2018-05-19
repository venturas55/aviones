/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *
 * A mejorar: (Un simbolo - indica algo por mejorar, un + indica YA mejorado.)
 *      + Cuando un avion no puede despegar, el siguiente sigue esperando 5 segundos....Arreglado con la funcion restartiempo(ArrayList l,int t)
 *      + Excepcion propia para el Id del vuelo "RY8264"
 *      + Hacer demoraentredespegues como valor constante y final
 *      - Al introducir incorrectamente un id de vuelo , hay que volver a decir que tipo de avión se crea.
 *      + Para avionetas locales se descuadra el esquema de mostrarEspera. Se ha solventado modificando espacios en blanco y tabulaciones en el metodo mostrar() de cada subclase.
 *      + Añadir el tipo COMERCIAL, CARGA, AVIONETA a mostrar.
 *      - timeToHour() implementada tanto en el Main como en la clase Abstracta Avion. 
 *      + No hay espera de 5 sec cada despegue!!! Solucionado con wait
 *      - Hacer id de vuelo PRIMARY KEY
*/

package evaluable2;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author ventu
 */
public class Evaluable2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int t;                              //Tiempo de la aplicación.
        final int t1=1;                     //Division del Tiempo entre evento (1s en el enunciado).
        int wait;                           //tiempo para asegurar espera entre despegues cuando la cola esta vacia
        final String version="AVIATOR 3.1";
        int opc;
        boolean error;
        
        ArrayList<Avion> avionesEspera = new ArrayList<>();
        ArrayList<Avion> avionesDespegados = new ArrayList<>();
        ArrayList<Avion> avionesProblemas = new ArrayList<>();   
        
        /*
        //Para pruebas. Eliminar o comentar desde aqui antes de entregar evaluable//
        Avion myavioncito1 = new Comercial("ES9876",100,"Nacional"); //Constructor de Comercial ID,fuel,destino
        myavioncito1.setTiemposal(0);
        Avion myavioncito2 = new Comercial("RY8110",250,"Internacional"); //Constructor de Comercial ID,fuel,destino
        myavioncito2.setTiemposal(5);
        Avion myavioncito3 = new Carga("ZZ7777",100,"Nacional");        //Constructor de Carga ID,fuel,destino
        myavioncito3.setTiemposal(10);
        Avion myavioncito4 = new Avioneta("AV4567",40);                //Constructor de Avioneta ID,fuel,destino
        myavioncito4.setTiemposal(15);
        
        avionesEspera.add(myavioncito1);
        avionesEspera.add(myavioncito2);
        avionesEspera.add(myavioncito3);
        avionesEspera.add(myavioncito4);
        //Para pruebas.Eliminar o comentar hasta aqui antes de entregar evaluable//
        */
        
        System.out.println("BIENVENIDO A "+version);
        System.out.println("Software para la simulacion de colas de despegue de aviones de un aeropuerto a lo FIFO style");
        
        wait=0;
        t=0;
        System.out.println("\n\t\t\t\tTIEMPO / WAIT : "+t+ " / "+wait+" segundos");
        mostrarmenu();
        opc=leerentero();
        
        do{
            switch(opc){
                case 1:
                    do{
                        error=false;
                        try{
                            if(avionesEspera.isEmpty())   {          //Si no hay aviones en cola
                                avionesEspera.add(crearvuelo(wait));}   //Se crea un avion con tiempo espera el que quede
                            else  {                                  //Si hay aviones en cola, se crea avion con tiempo de espera el del último + 5
                                avionesEspera.add(crearvuelo(avionesEspera.get(avionesEspera.size()-1).getTiemposal()+5));}
                        }
                        catch (ExcepcionIdVuelo e)
                        {
                            System.out.println("capturada : "+e);
                            error=true;
                        }
                    }while(error);   
                    break;
                case 2:     
                    System.out.println("\t\t\tVUELOS EN ESPERA.....");
                    System.out.println("\tFlight & Number \t Departure \t\ttime to depart \t\t\t\t Fuel     "); 
                    mostrarEspera(avionesEspera); break;
                case 3: 
                    System.out.println("\nEl tiempo no se detiene, y ya ha pasado un segundo...");
                    break;
                default: break;
            }
            
                if(avionesEspera.size()>0 && wait==0) {
                    wait=5;                 //Aseguro que no despeguera el siguiente avion con cola vacia.
                    if(avionReady(avionesEspera.get(0))) {
                        avionesEspera.get(0).setTiemposal(t);       //Guardamos la hora de salida
                        System.out.println("Vuelo "+avionesEspera.get(0).getDestino()+" " +avionesEspera.get(0).getIdentificador()+ " despegando... Buen Viaje!!");
                        avionesDespegados.add(avionesEspera.get(0));    //Lo paso al listado de aviones despegados
                        avionesEspera.remove(0);

                    }else{
                        avionesProblemas.add(avionesEspera.get(0));   //Lo paso al listado de aviones con problemas
                        avionesEspera.get(0).setTiemposal(avionesEspera.get(avionesEspera.size()-1).getTiemposal()+5  );  //le configuro el nuevo tiempo de salida estimado
                        repostar(avionesEspera);                //lo paso a la ultima posicion
                        System.out.println("\nVuelo "+avionesEspera.get(avionesEspera.size()-1).getDestino()+" " +avionesEspera.get(avionesEspera.size()-1).getIdentificador()+ " con problemas, nueva previsión de salida en "+timeToHour(avionesEspera.get(avionesEspera.size()-1).getTiemposal()) );  //timeToHour(lista.get(avionesEspera.size()-1).getTiemposal()-tiempo)
                        } 
                }else{ 
                 //System.out.println("\t\t\t\tNO HAY AVIONES EN COLA QUE MOSTRAR");
                        }
                
        t++;
        restartiempo(avionesEspera,t1);     //reducimos espera de todos los aviones
        if(wait>0)                          //solo si el tiempo es estrictamente positivo
            wait--;                         //reducimos tiempo de espera.        
                
        System.out.println("\n\t\t\t\tTIEMPO / WAIT : "+t+ " / "+wait+" segundos");
        mostrarmenu();
        opc=leerentero();
        
        }while(opc!=4);
        
        System.out.println("*******************************************************************************************************");
        System.out.println("\t\t\tRELACION DE VUELOS DESPEGADOS.....");
        System.out.println("\tFlight & Number \t Departure");
        mostrarDespegados(avionesDespegados);
        System.out.println("-------------------------------------------------------------------------------------------------------");
        System.out.println("\n\t\t\tVUELOS EN ESPERA.....");
        System.out.println("\tFlight & Number \t Departure \t\ttime to depart \t\t\t\t Fuel     "); 
        mostrarEspera(avionesEspera);
        System.out.println("-------------------------------------------------------------------------------------------------------");
        System.out.println("\n\t\t\tRELACION DE VUELOS CON PROBLEMAS.....");
        System.out.println("\tFlight & Number \t Departure");
        mostrarProblemas(avionesProblemas);
        System.out.println("*******************************************************************************************************");
        
        System.out.println("Gracias por usar "+ version);
    }
    
    //Funcion que muestra por pantalla el menu
    public static void mostrarmenu(){
        System.out.println("\nIntroduzca por teclado la opcion deseada:");
        System.out.println("\t1.- Introducir un vuelo nuevo");
        System.out.println("\t2.- Ver estado de los vuelos");
        System.out.println("\t3.- Pasar un segundo");
        System.out.println("\t4.- Salir");
    }
    
    //Funcion que se asegura de que lo leido por teclado sea un entero
    public static int leerentero(){
        int a=0; //Inicializamos a cualquier valor para que no de compilacion en error en netbeans (variable a might not have been initializated)
        boolean error;
        Scanner en = new Scanner (System.in);
    
        do{
            try{
                error = false;
                a = Integer.parseInt(en.nextLine());
            }
            catch(InputMismatchException e)
            {
                System.out.println("Valor introducido por teclado no válido."+e);    
                error = true;
            }
              catch(NumberFormatException e)
            {
                System.out.println("Valor introducido por teclado no válido2."+e);    
                error = true;
            }
        }while(error);
    
    return a;
    }
    
     //Devuelve el tipo de avion en int. 1= Comercial, 2= Carga, 3=Avioneta
    public static int leertipovuelo(){
        int opc=0;
        do{
            System.out.println("\nIntroduzca el tipo de vuelo. Pulse la opción deseada:");
            System.out.println("\t1.- Comercial");
            System.out.println("\t2.- Carga");
            System.out.println("\t3.- Avioneta");
            opc=leerentero();
        }while(opc<1 || opc>3);

        return opc;
    }
    
    //Devuelve un String diciendo el tipo de destino Nacional o Internaiconal
    public static String leertipodestino(){
             int opc=0;
        do{
            System.out.println("\nIntroduzca el tipo de destino al que acude. Pulse la opción deseada:");
            System.out.println("\t1.- Vuelo Nacional");
            System.out.println("\t2.- Vuelo Internacional");
            opc=leerentero();
        }while(opc<1 || opc>2);

        if(opc==1)
            return "Nacional";
        else
            return "Internacional";
    }
    
    //Funcion que crea un vuelo para luego añadirlo al ArrayList del main
    //Se le pasa el parametro el tiemposalida del ultimo avion en espera+5
    public static Avion crearvuelo(int t) throws ExcepcionIdVuelo{
        int tipo;
        tipo=leertipovuelo();       //se lee que tipo de vuelo es COMERCIAL,CARGA,AVIONETA
        System.out.println("Introduzca el numero identificador de vuelo");
        
        switch(tipo){                       //si es COMERCIAL...
            case 1:   // Comercial comercialtmp = new Comercial(leerID(),leertipodestino());
            Comercial comercialtmp = new Comercial();  // se crea (Usar constructor con parametros?)
            comercialtmp.setIdentificador(leerID());            // se lee un ID correcto
            comercialtmp.setTiemposal(t);                       // se le pone el tiempo de salida
            comercialtmp.setDestino(leertipodestino());         // se lee el tipo de destino
            return comercialtmp; 
                                                // si es CARGA...
            case 2:  Carga cargatmp=new Carga();                // se crea (Usar constructor con parametros?)
            cargatmp.setIdentificador(leerID());                // se lee un ID correcto
            cargatmp.setTiemposal(t);                           // se le pone el tiempo de salida
            cargatmp.setDestino(leertipodestino());             // se lee el tipo de destino
             return cargatmp; 
                                                //si es AVIONETA...
            default: Avioneta avionetatmp=new Avioneta();        // se crea (Usar constructor con parametros?)
            avionetatmp.setIdentificador(leerID());             // se lee un ID correcto
            avionetatmp.setTiemposal(t);                        // se le pone el tiempo de salida
            return avionetatmp; 
        }
    }
    
    //Funcion para mostrar por pantalla toda la info de un ArrayList de Aviones
    public static void mostrarEspera(ArrayList<Avion> cola){
        for(int i=0;i<cola.size();i++) 
            System.out.println(cola.get(i).toString());
    }
    
    //Funcion para mostrar por pantalla la info básica de un ArrayList de Aviones
    public static void mostrarProblemas(ArrayList<Avion> problematicos){
        for(int i=0;i<problematicos.size();i++) 
            System.out.println("\tVuelo "+ problematicos.get(i).getIdentificador()+"\t"+problematicos.get(i).getDestino()+"\t tuvo problemas de combustible y repostó " + (problematicos.get(i).getCombustible()-problematicos.get(i).getCombustibleinicial()) + " l.");
    }
    
    public static void mostrarDespegados(ArrayList<Avion> despegados){
        for(int i=0;i<despegados.size();i++) {
            if(despegados.get(i).getCombustible()!=despegados.get(i).getCombustibleinicial())
                System.out.println("\tVuelo "+ despegados.get(i).getIdentificador()+"\t"+despegados.get(i).getDestino()+"\t despegó tras repostar " + (despegados.get(i).getCombustible()-despegados.get(i).getCombustibleinicial() ) + "l de combustible");
            else
                 System.out.println("\tVuelo "+ despegados.get(i).getIdentificador()+"\t"+despegados.get(i).getDestino()+"\t\t despegó con " + despegados.get(i).getCombustible() + " l de combustible.");
        }
    }
    
    //Funcion para comprobar si un avion debe de despegar o no. Devuelve TRUE or FALSE
    //Se le pasa como parametros el objeto avion a evaluar 
    public static boolean avionReady(Avion avion){
            if (avion.getTipo().equals("Comercial") || avion.getTipo().equals("Carga") )
                return ((avion.getDestino().equals("Internacional") && avion.getCombustible()>300 ) || avion.getDestino().equals("Nacional") && avion.getCombustible()>100 );
            else
                return (avion.getCombustible()>50.0); 
    }
        
    //Funcion para pasar un valor en segundos a formato mm:ss 
    //Tambien implementada en la clase Abstracta Avion, cosa a depurar.
    //De momento se implementa tambien aqui en el Main porque luego el metodo repostar tb utiliza timeToHour
    public static String timeToHour(int tiempo){
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

    //Funcion para pasar repostar un avion y pasarlo al final de la cola con un nuevo tiempo de espera
    public static void repostar(ArrayList<Avion> lista){
        lista.get(0).llenardeposito();              //Llena el deposito
        lista.add(lista.get(0));                    //Lo añadimos al final
        lista.remove(0);                            // y ya podemos eliminarlo del primer puesto
        }
    
    public static String leerID() throws ExcepcionIdVuelo{
        Scanner en = new Scanner(System.in);
        String idvuelo;
        
        idvuelo=en.nextLine();
        idvuelo=idvuelo.toUpperCase();
        if(idvuelo.length()!=6)
            throw new ExcepcionIdVuelo(idvuelo);
        else if ( !idvuelo.substring(0,2).matches("[A-Z]*")  ||  !idvuelo.substring(2).matches("[0-9]*")) //Si los primeros 2 digitos no son mayusculas o los 4 ultimos digitos no son solo numeros
            throw new ExcepcionIdVuelo(idvuelo);                                                            //lanza excepcion
        
        return idvuelo;      
    }
    
    //Funcion que le pasas el listado de aviones, y le resta t segundos en el tiempo de salida a cada avión
    public static void restartiempo(ArrayList<Avion> lista,int t){
        for(int i=0;i<lista.size();i++)
            lista.get(i).setTiemposal(lista.get(i).getTiemposal()-t);
    }
    
    //Futuro metodo a usar para comprobar que la ID de un avión introducida es unica.... 
    //Se le pasa por parametro el id a buscar y el listado donde buscarlo
    //devuelve true si la clave es única (y valida) y false si ya existe.
    public static boolean uniquekey(String idtest, ArrayList<Avion> lista){
        for(int i=0;i<lista.size();i++)
            if(idtest.equals(lista.get(i).getIdentificador()))
                return false;
        return true;
    }
    
}