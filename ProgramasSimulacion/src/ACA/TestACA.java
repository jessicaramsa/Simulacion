package ACA;

import java.io.IOException;

public class TestACA {
    public static void main(String[] args) throws IOException {
        try {
            AlgoritmoCA test=new AlgoritmoCA();
        } catch(IOException e) {
            System.out.println("Error de corrida. "+e);
        }
    }
}
