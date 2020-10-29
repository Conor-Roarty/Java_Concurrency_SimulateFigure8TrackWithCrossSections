/* This is Assignment 1 question (c) for Concurrent and Distributed Systems */
package assignment1;
/**
 * @author Conor Roarty 
 */
public class Main {
    // Set Number Of Overall Trains To Run
    static final int NUM_OF_A_TRAINS = 200;
    static final int NUM_OF_B_TRAINS = 200;
    // Create Reference To TrainTrack.java
    static TrainTrack theTrainTrack;

    public static void main(String[] args) {

        // Create a train track instance
        theTrainTrack = new TrainTrack();

        System.out.println("STARTED");

        // create arrays to hold the trains 
        TrainAProcess[] trainAProcess = new TrainAProcess[NUM_OF_A_TRAINS];
        TrainBProcess[] trainBProcess = new TrainBProcess[NUM_OF_B_TRAINS];

        // create trains to enter the track
        for (int i = 0; i < NUM_OF_A_TRAINS; i++) {
            trainAProcess[i] = new TrainAProcess("A" + i, theTrainTrack);
        } // end for 
        for (int i = 0; i < NUM_OF_B_TRAINS; i++) {
            trainBProcess[i] = new TrainBProcess("B" + i, theTrainTrack);
        } // end for 

        // set the train processes running
        for (int i = 0; i < NUM_OF_A_TRAINS; i++) {
            trainAProcess[i].start();
        } // end for 
        for (int i = 0; i < NUM_OF_B_TRAINS; i++) {
            trainBProcess[i].start();
        } // end for 

        // trains now travelling    
        //  wait for all the train threads to finish before printing out final message. 
        for (int i = 0; i < NUM_OF_A_TRAINS; i++) {
            try {
                trainAProcess[i].join();
            } catch (InterruptedException ex) {
                System.out.println("Train A Interrupted: " + ex.toString());
            }
        } // end for 

        for (int i = 0; i < NUM_OF_B_TRAINS; i++) {
            try {
               trainBProcess[i].join();
            } catch (InterruptedException ex) {
                System.out.println("Train B Interrupted: " + ex.toString());
            }
        } // end for    

        // Display all the train activity that took place
        theTrainTrack.theTrainActivity.printActivities();

        // Final message
        System.out.println("All trains have successfully travelled the track ");
    } // end main     
}
