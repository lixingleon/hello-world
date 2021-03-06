package es.datastructur.synthesizer;

import java.util.ArrayList;
import java.util.HashSet;

public class HarpString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = -.9; // energy decay factor

    /* Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public HarpString(double frequency) {
        // TODO: Create a buffer with capacity = SR / frequency. You'll need to
        //       cast the result of this division operation into an int. For
        //       better accuracy, use the Math.round() function before casting.
        //       Your buffer should be initially filled with zeros.
        buffer = new ArrayRingBuffer<>((int)(2*Math.round(SR/frequency)));
        while(buffer.isFull()!=true){
            buffer.enqueue(0.0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        // TODO: Dequeue everything in buffer, and replace with random numbers
        //       between -0.5 and 0.5. You can get such a number by using:
        //       double r = Math.random() - 0.5;
        //
        //       Make sure that your random numbers are different from each
        //       other.
        while(buffer.isEmpty()!=true){
            buffer.dequeue();
        }
        HashSet<Double> uniqueRandomDoubles = new HashSet<>();
        for (int i =0; i<buffer.capacity(); i++){
            Double r = Math.random() - 0.5;
            uniqueRandomDoubles.add(r);
        }
        ArrayList<Double> al = new ArrayList<>(uniqueRandomDoubles);
        for (int i =0; i<buffer.capacity(); i++){
            buffer.enqueue(al.get(i));
        }


    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        // TODO: Dequeue the front sample and enqueue a new sample that is
        //       the average of the two multiplied by the DECAY factor.
        //       Do not call StdAudio.play().
        Double front = buffer.dequeue();
        Double secondFront = buffer.peek();
        int factor = 0;
        if(Math.random()<0.5){
            factor = -1;
        }
        else{
            factor = 1;
        }

        Double newDouble = -DECAY*(front+secondFront)/2;
        buffer.enqueue(newDouble);


    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        // TODO: Return the correct thing.
        return buffer.peek();
    }
}
