package req6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.UnknownHostException;
import java.util.Scanner;

public class client {
   
    public static final int UPPERCASE_PORT = 6666;
	public static final int LOWERCASE_PORT = 6667;

    @SuppressWarnings("resource")
    public static void main(String[] args) throws UnknownHostException, IOException{
       Scanner userInput = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String patientName = userInput.nextLine();
     
        Socket makeAppointmentSocket = new Socket("localhost", 6666);
        PrintWriter makeAppointmentWriter = new PrintWriter(makeAppointmentSocket.getOutputStream(), true);
        
        
        Socket cancelAppointmentSocket = new Socket("localhost", 6667); 
        PrintWriter cancelAppointmentWriter = new PrintWriter(cancelAppointmentSocket.getOutputStream(), true);
       

        BufferedReader socketMakeReader = new BufferedReader(new InputStreamReader(makeAppointmentSocket.getInputStream()));
        BufferedReader socketCancelReader = new BufferedReader(new InputStreamReader(cancelAppointmentSocket.getInputStream()));

        makeAppointmentWriter.println(patientName);
        cancelAppointmentWriter.println(patientName);
        while(true){

            System.out.println("you need to cancel(1) or appointment(2): ");
            int cancel_or_make=userInput.nextInt();

            if(cancel_or_make!=1&&cancel_or_make!=2){
                 System.out.println("Please enter concel or appointment ");
                 continue;
            }

            System.out.println("Inter the doctor ID: ");
            int doctorId=userInput.nextInt();

            System.out.println("Inter the timeslot index: ");
            int timeslotIndex=userInput.nextInt();

            if(cancel_or_make==1){
                cancelAppointmentWriter.println(doctorId+","+timeslotIndex);
                System.out.println(socketCancelReader.readLine());

            }else if(cancel_or_make==2){
                makeAppointmentWriter.println(doctorId+","+timeslotIndex);
                System.out.println(socketMakeReader.readLine());
            }

        }
    }
}