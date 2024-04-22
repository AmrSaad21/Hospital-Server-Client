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
    public boolean deleteAppointment(int indix,String name){
        if(indix<timeslots.length){
        if(this.timeslots[indix]==false){
            return false;
        }
        else{
            this.timeslots[indix]=false;
            this.patients[indix]=" ";
        }
        }
        return true;
    }
    public boolean isAvailable(int slotIndex) {
        return !timeslots[slotIndex];
    }
    public String[] getPatients() {
        return patients;
    }
    
}