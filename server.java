package req6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class server {
   private static Hospital hos;
    public static final int CANCEL_PORT = 6667;
	public static final int MAKE_PORT = 6666;
    public static void main(String[] args) throws IOException {
        hos = new Hospital("C:/Users/Amr Saad/Desktop/req6/req6/hospital.txt");
        System.out.println("The server started .. ");

        /*  make thread  */ 
        new Thread() {
         public void run() {
            try {
                try (ServerSocket ss = new ServerSocket(MAKE_PORT)) {
                    while (true) {
                        new Clients(ss.accept()).start();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }.start();

    /*  cancel thread  */ 
    new Thread() {
        public void run() {
            try {
                try (ServerSocket ss = new ServerSocket(CANCEL_PORT)) {
                    while (true) {
                        new Clients(ss.accept()).start();
                    }
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }.start();
}
private static class Clients extends Thread {
    Socket socket;

    public Clients(Socket s) {
        this.socket = s;
        System.out.println("Connection with Client at socket " + socket);
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
            String patientName = in.readLine();

            while (true) {

                String clientData = in.readLine(); // Read appointment details

                String[] dataParts = clientData.split(",");

                try {
                    int doctorId = Integer.parseInt(dataParts[0]);
                    int timeslotIndex = Integer.parseInt(dataParts[1]);
                    int cc = hos.inDoctors(doctorId, timeslotIndex);
                    
                    if (cc == 1)
                        out.println("the doctor id is not found in hospital");
                    else if (cc == 2)
                        out.println("the timeslot index is out of boundary");
                    else if (cc == 0) {
                        if (this.socket.getLocalPort() == MAKE_PORT) {
                            boolean check = hos.makeAppointment(doctorId, timeslotIndex, patientName);
                            if (check) {
                                out.println("Making the appointment is done successfully");
                            } else {
                                out.println("the doctor is already busy at this timeslot");
                            }
                        } else {

                            int check = hos.cancelAppointment(doctorId, timeslotIndex, patientName);
                            if (check==1) {
                                out.println("canceling the appointment is done successfully");
                            } else if(check==3){

                                out.println("the doctor has an appointment to a different patient name at this timeslot");
                            }else if(check==2){
                                out.println("the doctor doesn’t have an appointment at this timeslot");
                            }
                        }
                    }
                    hos.print();
                } catch (NumberFormatException e) {
                    out.println("Invalid input format");
                    continue;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

}          
