/*
 * This is the main creation page.
 * It handles forming the Train Track itself, the functions to navigation on\off\around the track
 * and limiting the number of trains that can be on the track at any one time (MageeSemaphore(9))
 */
package assignment1;
/**
 * @author csf16roc - Conor Roarty B00657634
 */
public class TrainTrack {
    private final String[] slots = {"[..]", "[..]", "[..]", "[..]", "[..]", "[..]", "[..]", "[..]", "[..]",
        "[..]", "[..]", "[..]", "[..]", "[..]", "[..]", "[..]", "[..]", "[..]", "[..]"};
    // declare array to hold the Binary Semaphores for access to track slots (sections)
    private final MageeSemaphore slotSem[] = new MageeSemaphore[19];

    // reference to train activity record
    Activity theTrainActivity;
   
    MageeSemaphore trainLimit; // limit the cars on inner loop

    /* Constructor for TrainTrack */
    public TrainTrack() {
        // record the train activity 
        theTrainActivity = new Activity(slots);
        // create the array of slotSems and set them all to the value 1
        for (int i = 0; i <= (slotSem.length - 1); i++) {
            slotSem[i] = new MageeSemaphore(1);
        }
        // create  semaphores for limiting number of trains on track
        trainLimit = new MageeSemaphore(8); // limit trains on track, 9 can be used instead of 8 when not as many holds are used. 
                                            // 10 mathmatically
    }  // constructor end

    // Code for moving A trains
    public void trainA_MoveOnToTrack(String trainName) {
        trainLimit.P();
        // record the train activity
        theTrainActivity.addMessage("Train " + trainName + " is joining/joined the A loop at section 5");
        slotSem[5].P();// wait for slot 5 to be free
        slots[5] = "[" + trainName + "]"; // move train type A on to slot 5  
        theTrainActivity.addMovedTo(5); // record the train activity
    }// end trainA_movedOnToTrack

    public void trainA_MoveAroundSectionOne(String trainName) {
        CDS.idle((int) (Math.random() * 100));
        int currentPosition = 5;
        do {
            // wait until the position ahead is empty and then move into it
            slotSem[currentPosition + 1].P(); // wait for the slot ahead to be free
            slots[currentPosition + 1] = slots[currentPosition]; // move train forward one position
            slots[currentPosition] = "[..]"; // clear the slot the train vacated
            theTrainActivity.addMovedTo(currentPosition + 1); // record the train activity
            slotSem[currentPosition].V(); // signal slot you are leaving 
            currentPosition++;// Increase Position For The Specific Train That Is Calling This Method
        } while (currentPosition < 8);
        CDS.idle((int) (Math.random() * 100));
    } // end trainA_MoveAroundToSharedTrack

    public void trainA_MoveAroundSectionTwo(String trainName) {
        CDS.idle((int) (Math.random() * 100));
        int currentPosition = 10;
        do {
            // wait until the position ahead is empty and then move into it
            slotSem[currentPosition + 1].P(); // wait for the slot ahead to be free
            slots[currentPosition + 1] = slots[currentPosition]; // move train forward one position
            slots[currentPosition] = "[..]"; // clear the slot the train vacated
            theTrainActivity.addMovedTo(currentPosition + 1); // record the train activity
            slotSem[currentPosition].V(); // signal slot you are leaving 
            currentPosition++;
        } while (currentPosition < 17);
        CDS.idle((int) (Math.random() * 100));
    } // trainA_MoveAroundSectionTwo
    
    public void trainA_MoveAroundSectionThree(String trainName) {
        CDS.idle((int) (Math.random() * 100));
        int currentPosition = 1;
        do {
            // wait until the position ahead is empty and then move into it
            slotSem[currentPosition + 1].P(); // wait for the slot ahead to be free
            slots[currentPosition + 1] = slots[currentPosition]; // move train forward one position
            slots[currentPosition] = "[..]"; // clear the slot the train vacated
            theTrainActivity.addMovedTo(currentPosition + 1); // record the train activity
            slotSem[currentPosition].V(); // signal slot you are leaving 
            currentPosition++;
        } while (currentPosition < 5);
        CDS.idle((int) (Math.random() * 100));
    } // end trainA_MoveAroundToSharedTrack

    public void trainA_MoveOffTrack(String trainName) {
        CDS.idle((int) (Math.random() * 100));
        // record the train activity
        slots[5] = "[..]"; // move train type A off slot zero 
        slotSem[5].V();// signal slot 0 to be free
        theTrainActivity.addMessage("Train " + trainName + " leaving/left the A loop at section 5");
        CDS.idle((int) (Math.random() * 100));
        trainLimit.V();// clear a space on the semaphore limit
    }// end trainA_movedOffTrack
    
    // *************************************************************** 
    // Code for crossing junctions.
    // As the same rules apply to both trains at either point
    // these functions have been refractored into 2 reusable functions
    // for both trains.
    // *************************************************************** 
    public void MoveAcrossJunctionOne(String trainName) {
        slotSem[0].P(); // wait for the slot 0 ahead to be free
        slotSem[9].P(); // wait for the slot ahead to be free
        slotSem[18].P();// wait for the slot ahead to be free
        
        slots[9] = slots[8]; // move train forward one position
        slots[8] = "[..]"; // clear the slot the train vacated
        theTrainActivity.addMovedTo(9); // record the train activity
        slotSem[8].V(); // Signal slot you are leaving
        slotSem[18].V();// Signal 18 is free now to be moved into
        
        slotSem[10].P();
        slots[0] = slots[9];
        slots[9] = "[..]"; // clear the slot the train vacated

        theTrainActivity.addMovedTo(0); // record the train activity
        slotSem[9].V();

        slots[10] = slots[0]; // move train forward one position
        slots[0] = "[..]"; // clear the slot the train vacated
        theTrainActivity.addMovedTo(10); // record the train activity
        slotSem[0].V(); // signal slot you are leaving 
        CDS.idle((int) (Math.random() * 100));
    } // end trainB_MoveAcrossJunctionTwo 
    
    public void MoveAcrossJunctionTwo(String trainName) {
        slotSem[0].P(); // wait for the slot 0 ahead to be free
        slotSem[18].P(); // wait for the slot ahead to be free
        
        slots[18] = slots[17]; // move train forward one position
        slots[17] = "[..]"; // clear the slot the train vacated
        theTrainActivity.addMovedTo(18); // record the train activity
        slotSem[17].V(); // signal slot you are leaving
        
        slotSem[1].P();// wait for the slot 1 ahead to be free
        slots[0] = slots[18];// move train forward to junction slot
        slots[18] = "[..]"; // clear the slot the train vacated

        theTrainActivity.addMovedTo(0); // record the train activity
        slotSem[18].V();
        
        slots[1] = slots[0]; // move train forward one position
        slots[0] = "[..]"; // clear the slot the train vacated
        theTrainActivity.addMovedTo(1); // record the train activity
        slotSem[0].V(); // signal slot you are leaving 
        
        CDS.idle((int) (Math.random() * 100));
    } // end trainB_MoveAroundToSharedTrack
    
    // *************************************************************** 
    // Code for moving B trains
    // *************************************************************** 
    public void trainB_MoveOnToTrack(String trainName) {
        // record the train activity
        trainLimit.P();// Take A Space On The Semaphore\SpacesLimit
        theTrainActivity.addMessage("Train " + trainName + " is joining/joined the B loop at section 14");
        CDS.idle((int) (Math.random() * 100));
        slotSem[14].P();// wait for slot 16 to be free
        slots[14] = "[" + trainName + "]"; // move train type B on to slot sixteen  
        theTrainActivity.addMovedTo(14); // record the train activity
    }

    public void trainB_MoveAroundSectionOne(String trainName) {
        CDS.idle((int) (Math.random() * 100));
        int currentPosition = 14;
        do {
            // wait until the position ahead is empty and then move into it
            slotSem[currentPosition + 1].P(); // wait for the slot ahead to be free
            slots[currentPosition + 1] = slots[currentPosition]; // move train forward one position
            slots[currentPosition] = "[..]"; // clear the slot the train vacated
            theTrainActivity.addMovedTo(currentPosition + 1); // record the train activity
            slotSem[currentPosition].V(); // signal slot you are leaving 
            currentPosition++;
        } while (currentPosition < 17);
        CDS.idle((int) (Math.random() * 100));
    }

    public void trainB_MoveAroundSectionTwo(String trainName) {
       CDS.idle((int) (Math.random() * 100));
        int currentPosition = 1;
        do {
            // wait until the position ahead is empty and then move into it
            slotSem[currentPosition + 1].P(); // wait for the slot ahead to be free
            slots[currentPosition + 1] = slots[currentPosition]; // move train forward one position
            slots[currentPosition] = "[..]"; // clear the slot the train vacated
            theTrainActivity.addMovedTo(currentPosition + 1); // record the train activity
            slotSem[currentPosition].V(); // signal slot you are leaving 
            currentPosition++;
        } while (currentPosition < 8);
        CDS.idle((int) (Math.random() * 100));
    }

    public void trainB_MoveAroundSectionThree(String trainName) {
        CDS.idle((int) (Math.random() * 100));
        int currentPosition = 10;
        do {
            // wait until the position ahead is empty and then move into it
            slotSem[currentPosition + 1].P(); // wait for the slot ahead to be free
            slots[currentPosition + 1] = slots[currentPosition]; // move train forward one position
            slots[currentPosition] = "[..]"; // clear the slot the train vacated
            theTrainActivity.addMovedTo(currentPosition + 1); // record the train activity
            slotSem[currentPosition].V(); // signal slot you are leaving 
            currentPosition++;
        } while (currentPosition < 14);
        CDS.idle((int) (Math.random() * 100));
    } // end trainB_MoveAroundToSharedTrack  

    public void trainB_MoveOffTrack(String trainName) {
        CDS.idle((int) (Math.random() * 100));
        slotSem[14].V();// signal slot 14 to be free
        slots[14] = "[..]"; // move train type A off slot zero
        theTrainActivity.addMessage("Train " + trainName + " leaving/left the A loop at section 14"); // record the train activity
        trainLimit.V();// clear a space on the semaphore limit
        CDS.idle((int) (Math.random() * 100));
    }
}
