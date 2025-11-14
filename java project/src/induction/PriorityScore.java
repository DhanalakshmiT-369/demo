package induction;


public class PriorityScore {

    static int calPriorityScore(String fitnessStatus, String jobCardStatus, String brandingStatus, double maintenanceBalance) {
        int score = 0;
        //-------------------FITNESS STATUS-------------------
        switch (fitnessStatus.trim().toLowerCase()) {
            case "fit":
                score += 50;
            case "unfit":
                score += 0;
            default :
                score += 0;
        }

        //-------------------JOB CARD STATUS-------------------
        switch (jobCardStatus.trim().toLowerCase()) {
            case "completed" :
                score += 30;
            case "in progress" :
                score += 15;
            case "pending" :
                score += 5;
            default :
                score += 0;
        }

        //-------------------BRANDING-------------------
        if (brandingStatus.trim().toLowerCase().equals("yes")) {
            score += 5;
        }

        //-------------------MAINTENANCE BALANCE-------------------
        if (maintenanceBalance <= 5000) {
            score += 0;       
         }else if (maintenanceBalance <= 20000) {
            score += 5;       
         }else if (maintenanceBalance <= 50000) {
            score += 10;      
         }else {
            score += 15;      
        }
        return score;
    }
}

