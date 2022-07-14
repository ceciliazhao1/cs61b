package lab14;
import lab14lib.Generator;
import edu.princeton.cs.algs4.StdAudio;

public class SawToothGenerator implements Generator {
    private int period;
    private int state;

    public SawToothGenerator(int period) {
        state = 0;
        this.period = period;
    }

    public double next() {
        state = (state + 1);
        double x= (float)(state%period)/period;
        return normalize(x);
    }

    //converts values between 0 and period - 1 to values between -1.0 and 1.0
    double normalize(double x){
        return 2*(x-0.5);
    }
}
