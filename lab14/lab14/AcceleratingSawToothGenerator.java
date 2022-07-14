package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {
    private int period;
    private int state;
    private double fractor;

    public AcceleratingSawToothGenerator(int period, double fractor) {
        state = 0;
        this.period = period;
        this.fractor = fractor;
    }

    public double next() {
        state = (state + 1);
        double x= (float)state/this.period;
        if( state>=period ) {
            this.period = (int) (this.period * fractor);
            state = 0;
        }
        return normalize(x);
    }

    //converts values between 0 and period - 1 to values between -1.0 and 1.0
    double normalize(double x){
        return 2*(x-0.5);
    }
}
