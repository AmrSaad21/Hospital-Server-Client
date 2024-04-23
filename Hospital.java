package req6;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Hospital {
    private Map<Integer, doctor> doctorsMap; // Changed from array to HashMap
    private int size;

    public Hospital(String path) {
        doctorsMap = new HashMap<>();
        try {
            File file = new File(path);
            try (Scanner reader = new Scanner(file)) {
                size = reader.nextInt();
                reader.nextLine();  // consume the newline
                for (int i = 0; i < size; i++) {
                    String[] nameAndTimeSlots = reader.nextLine().split(" ");
                    int id = Integer.parseInt(nameAndTimeSlots[0]);
                    String name = nameAndTimeSlots[1];
                    int timeSlots = Integer.parseInt(nameAndTimeSlots[2]);
                    doctor doc = new doctor(id, name, timeSlots);
                    doctorsMap.put(id, doc); // Store doctor in the map with ID as key
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean makeAppointment(int doctorId, int slotIndex, String patientName) {
        doctor doc = doctorsMap.get(doctorId); // Retrieve doctor by ID
        if (doc != null) {
            return doc.newAppointment(slotIndex, patientName);
        }
        return false;
    }

    public int cancelAppointment(int doctorId, int slotIndex, String patientName) {
        doctor doc = doctorsMap.get(doctorId); // Retrieve doctor by ID
        if (doc != null) {
            return doc.deleteAppointment(slotIndex, patientName);
        }
        return 0;
    }

    public void print() {
        for (Map.Entry<Integer, doctor> entry : doctorsMap.entrySet()) {
            doctor doc = entry.getValue();
            System.out.println("Doctor ID: " + doc.getId() + ", Name: " + doc.getName());
            String[] patients = doc.getPatients();
            for (int i = 0; i < patients.length; i++) {
                System.out.println("patient " + i + ": " + (doc.isAvailable(i) ? "Available" : patients[i]));
            }
            System.out.println();
        }
    }

    int inDoctors(int id, int time) {
        doctor doc = doctorsMap.get(id); // Retrieve doctor by ID
        if (doc != null) {
            if (doc.timeslots.length <= time)
                return 2;
            return 0;
        }
        return 1;
    }
}
