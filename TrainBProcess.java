/*
 * This process refers to each individual TrainB and 
 * the function from TrackTack.java it should call.
 * It will only allow new "runs" to execute if there is space.
 */
package assignment1;
/**
 * @author csf16roc - Conor Roarty B00657634
 */
class TrainBProcess extends Thread {
    // Note This process is used to emulate a train as it proceeds around the track
    String trainName;
    TrainTrack theTrack;
    //initialise (constructor)   
    public TrainBProcess(String trainName, TrainTrack theTrack) {
        this.trainName = trainName;
        this.theTrack = theTrack;
    }

    @Override
    public void run() {   // start train Process
        // wait for clearance before moving on to the track
        theTrack.trainB_MoveOnToTrack(trainName); // move on to track B
        theTrack.trainB_MoveAroundSectionOne(trainName); // move around slots 14 to 18
        theTrack.MoveAcrossJunctionOne(trainName); // move across junction from 18
        theTrack.trainB_MoveAroundSectionTwo(trainName); // move around slots 1 to 9
        theTrack.MoveAcrossJunctionTwo(trainName); // move across junction from 5
        theTrack.trainB_MoveAroundSectionThree(trainName); // move around slots 10 to 14
        theTrack.trainB_MoveOffTrack(trainName); // move off the track at 14
    } // end run  
}// end trainBProcess

