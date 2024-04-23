package req6;

public class doctor {
    private int id;
    private String namedoctor;
    public boolean [] timeslots;
    public String [] patients; //patients name 

    public doctor(int id, String name, int numSlots) {
        this.id = id;
        this.namedoctor = name;
        this.timeslots = new boolean[numSlots];
        this.patients = new String[numSlots];
        for (int i = 0; i < numSlots; i++) {
            timeslots[i] = false; // Set all elements to true
            patients[i]=" ";
        }
    }
    public int getId(){
        return this.id;
    } 
    public String getName(){
        return this.namedoctor;
    } 
    public boolean newAppointment(int indix,String name){
        if(indix<timeslots.length){
        if(this.timeslots[indix]==true){
            return false;
        }
        else{
            this.timeslots[indix]=true;
            this.patients[indix]=name;
        }}
        return true;
    }
    public int deleteAppointment(int index, String name) {
        if (index >= 0 && index < timeslots.length) {
            if (timeslots[index] && name.trim().equals(patients[index].trim())) {
                timeslots[index] = false;
                patients[index] = " ";
                return 1; // Appointment successfully deleted
            }else if(!timeslots[index]){
                return 2;
            }else if(!name.trim().equals(patients[index].trim())){
                return 3;
            }
        }
        return 0; // Appointment not found or name doesn't match
    }

    public boolean isAvailable(int slotIndex) {
        return !timeslots[slotIndex];
    }
    public String[] getPatients() {
        return patients;
    }
    public static String removeSpaces(String input) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (ch != ' ') {
                result.append(ch);
            }
        }
        return result.toString();
    }
    
}