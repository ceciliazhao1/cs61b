package lab14;
import lab14lib.Generator;
import lab14lib.GeneratorAudioVisualizer;

public class Animation {
    //public static void main(String[] args) {
        //Generator generator = new SawToothGenerator(512);
        //GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator);
        //gav.drawAndPlay(4096, 1000000);
    //}
    //public static void main(String[] args) {
        //Generator generator = new AcceleratingSawToothGenerator(200, 1.1);
        //GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator);
        //gav.drawAndPlay(4096, 1000000);
    //}
    public static void main(String[] args) {
        Generator generator = new StrangeBitwiseGenerator(1024);
        GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator);
        gav.drawAndPlay(128000, 1000000);
    }
}
