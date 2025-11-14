package crewManagement;


public class CrewMember {
    private int id;
    private String name;
    private String role;
    private String contact;
    private String pastShift;
    private String currentShift;
    private String futureShift;
    private int trainId; 

    // --------------------------------------CONSTRUCTORS--------------------------------------
    // to add a crew member since at that time we do not know the id and while updating when we know id
    public CrewMember(int id, String name, String role, String contact, String pastShift, String currentShift, String futureShift, int trainId) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.contact = contact;
        this.pastShift = pastShift;
        this.currentShift = currentShift;
        this.futureShift = futureShift;
        this.trainId = trainId;
    }

    public CrewMember(String name, String role, String contact, String pastShift, String currentShift, String futureShift, int trainId) {
        this.name = name;
        this.role = role;
        this.contact = contact;
        this.pastShift = pastShift;
        this.currentShift = currentShift;
        this.futureShift = futureShift;
        this.trainId = trainId;
    }

    public CrewMember(String name, String role, String contact, String pastShift, String currentShift, String futureShift) {
        this(name, role, contact, pastShift, currentShift, futureShift, 0); // default unassigned (trainId = 0)
    }

    //--------------------------------------GETTER & SETTER--------------------------------------
    public int getId() { 
        return id; 
    }
    public void setId(int id) { 
        this.id = id; 
    }
    public String getName() { 
        return name; 
    }
    public void setName(String name) { 
        this.name = name; 
    }
    public String getRole() { 
        return role; 
    }
    public void setRole(String role) { 
        this.role = role; 
    }
    public String getContact() { 
        return contact; 
    }
    public void setContact(String contact) { 
        this.contact = contact; 
    }
    public String getPastShift() { 
        return pastShift; 
    }
    public void setPastShift(String pastShift) { 
        this.pastShift = pastShift; 
    }
    public String getCurrentShift() { 
        return currentShift; 
    }
    public void setCurrentShift(String currentShift) { 
        this.currentShift = currentShift; 
    }
    public String getFutureShift() { 
        return futureShift; 
    }
    public void setFutureShift(String futureShift) { 
        this.futureShift = futureShift; 
    }

    public int getTrainId() { 
        return trainId; 
    }
    public void setTrainId(int trainId) { 
        this.trainId = trainId; 
    }
}