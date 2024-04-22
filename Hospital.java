package req6;
import java.io.*;

import java.util.Scanner;

public class Hospital {
    //ids it's the indexs
    private doctor [] doctors;
    public int size=0;
    public Hospital(String path) // to read file
    {
        try
        {
            File file = new File(path);
            try (Scanner reader = new Scanner(file)) {
                size = reader.nextInt();
                reader.nextLine();  // consume the newline
                doctors = new doctor[size];
                for(int i=0; i<size; i++)
                {
                    String[] nameAndTimeSlots = reader.nextLine().split(" ");
                    int id = Integer.parseInt(nameAndTimeSlots[0]);
                    String name = nameAndTimeSlots[1];
                    int timeSlots = Integer.parseInt(nameAndTimeSlots[2]);
                    doctors[i] = new doctor(id, name, timeSlots);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    
    public boolean makeAppointment(int doctorId, int slotIndex, String patientName) {

            return doctors[doctorId].newAppointment(slotIndex, patientName);
    }
    public boolean cancelAppointment(int doctorId, int slotIndex, String patientName) {
            return  this.doctors[doctorId].deleteAppointment(slotIndex, patientName);
    }
    public void print() {
        for (int j=0;j<size;j++) {
            doctor doc=doctors[j];
            System.out.println("Doctor ID: " + doc.getId() + ", Name: " + doc.getName());
            String[] patients = doc.getPatients();
            for (int i = 0; i < patients.length; i++) {
                System.out.println("patient " + i + ": " + (doc.isAvailable(i) ? "Available" : patients[i]));
            }
            System.out.println();
        }
    }
    int inDoctors(int id,int time){
        boolean check=false;
        for(int i=0;i<size;i++){
            if(doctors[i].getId()==id){
                if(doctors[i].timeslots.length<=time)
                    return 2;
            check=true;
            }
        }
        if(!check) return 1;

        return 0;
    }

}