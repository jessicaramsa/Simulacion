package ACA;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class AlgoritmoCA {
    private static final String f = "fin";
    int cI = 0, cA = 0, m;
    String aux, aux2, num, arch;
    ArrayList<Double> xi, ri;

    public AlgoritmoCA() throws IOException {
        xi = new ArrayList();
        ri = new ArrayList();
        arch = "src/ACA/";
        capturar();
        imprimir();
        capArch();
        escritura();
        navegabilidad();
    }

    private void capturar() {
        aux = "Algoritmo Congruencial Aditivo";
        System.out.println(aux);
        JOptionPane.showMessageDialog(null, aux, "Simulación", 3);
        aux = "Ingrese la semilla " + (cI + 1) + ": ";
        num = JOptionPane.showInputDialog(null, aux, "Semilla " + (cI + 1), 3);

        do {
            xi.add(Double.parseDouble(num));
            cI++;
            cA++;
            System.out.println(num);
            aux = "Ingrese la semilla " + (cI + 1) + " o 'fin': ";
            num = JOptionPane.showInputDialog(null, aux, "Semilla " + (cI + 1), 3);
        } while (!num.equals(f) && valInt(num) == true);

        do {
            aux = "Ingrese el valor del módulo(m):";
            num = JOptionPane.showInputDialog(null, aux, "Módulo", 3);
        } while (valInt(num) == false);
        m = Integer.parseInt(num);
        opR(xi.get(cA - 1), m);
    }

    public boolean valInt(String r) {
        try {
            Integer.parseInt(r);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private double opR(double x, int mod) {
        double rTemp;

        x = xi.get(cA - 1) + xi.get((cA) - cI);
        x = x % mod;
        rTemp = x / (mod - 1);

        if (valRep(xi, x) == true) {
            return rTemp;
        } else {
            xi.add(x);
            ri.add(rTemp);
            cA++;
            return opR(x, mod);
        }
    }

    private boolean valRep(ArrayList<Double> r, double vR) {
        boolean found = false;
        int resp, cont = 0;
        do {
            resp = Double.compare(r.get(cont), vR);
            cont++;
            if (resp == 0) {
                found = true;
            }
        } while (resp != 0 && cont < r.size());
        return found;
    }

    private void imprimir() {
        System.out.println("Semillas iniciales [" + cI + "] : ");
        System.out.println("\nMódulo[m] = " + m);
    }

    private void capArch() throws IOException {
        aux = "Ingrese el nombre del archivo:";
        arch += JOptionPane.showInputDialog(null, aux, "Archivo ACA", 3);
        arch += ".txt";
        crearArch(arch);
    }

    private void crearArch(String archivo) throws IOException {
        try {
            File file = new File(archivo);
            if (!file.exists()) {
                file.createNewFile();
            } else if (file.exists()) {
                System.out.println("El archivo " + archivo + " ya existe.");
            }
            System.out.println("Operación realizada con éxito.");
            System.out.println(arch);
        } catch (IOException ioe) {
            System.out.println("Error al crear archivo. " + ioe);
        }
    }

    private void escribir(String archivo, String linea) throws IOException {
        BufferedWriter bw;
        try {
            File file = new File(archivo);
            FileWriter fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);
            bw.write(linea);
            bw.newLine();
            bw.flush();
            bw.close();
        } catch (IOException ex) {
            System.out.println("Error escribir. " + ex);
        }
    }

    private void escritura() throws IOException {
        for (int i = 0; i < ri.size(); i++) {
            escribir(arch, "" + ri.get(i));
        }
    }

    private void navegabilidad() {
        int nav;
        aux = "¿Desea realizar otra prueba?\n1.-Si\n2.-No";
        aux2 = "Algoritmo Congruencial Aditivo";
        do {
            nav = Integer.parseInt(JOptionPane.showInputDialog(null, aux, aux2, 3));
            switch (nav) {
                case 1:
                    capturar();
                    break;
                case 2:
                    System.exit(0);
            }
        } while (!num.equals(2));
    }
}
