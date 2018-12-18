package Poker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PruebaPoker {
    double numero, num[], feTodo, fePar, feDosPar, feTercia, feFull, fePoker;
    double feQuin, formTodo, formPar, formDosPar, formTercia, formFull;
    double formPoker, formQuin, sumatoria, sumatoria2;
    int n, d[] = new int[5], contador, totales[] = new int[5], suma, foTodo;
    int foPar, foDosPar, foTercia, foFull, foPoker, foQuin;
    String dTemp[][] = new String[7][4];
    DecimalFormat df = new DecimalFormat("#,0000");

    public boolean isNum(String cad) {
        try {
            Double.parseDouble(cad);
            return true;
        } catch(NumberFormatException nfe) {
            System.out.println("Solo se admiten numeros.");
            return false;
        }
    }

    public void sumaDatos(File arch) {
        Scanner in=null;
        n = 0;

        try {
            if(arch.exists() && arch.canRead()) {
                in = new Scanner(arch);
                while(in.hasNextLine()) {
                    numero = Double.parseDouble(in.nextLine());
                    n = n + 1;
                }
            }
        } catch(FileNotFoundException e){}
        finally { if(in != null) in.close(); }
    }

    public boolean leerArch(File arch) {
        int i = 0;
        Scanner in = null;
        sumaDatos(arch);
        num = new double [n];

        try {
            if(arch.exists() && arch.canRead()) {
                in = new Scanner(arch);
                while(in.hasNextLine()) {
                    numero = Double.parseDouble(in.nextLine());
                    num[i] = numero;
                    i++;
                }
                return true;
            } else return false;
        } catch(FileNotFoundException e) { return false; }
        finally { if(in != null) in.close(); }
    }
    
    public double[] leerNums(File arch) {
        int i = 0;
        FileReader fr = null;
        BufferedReader br = null;
        num = new double[n];

        try {
            fr = new FileReader(arch);
            br = new BufferedReader(fr);
            
            if(arch.exists() && arch.canRead()) {
                String l;
                while((l = br.readLine()) != null) {
                    num[i] = numero;
                    i++;
                }
            }
        } catch(IOException e) { return null; }
        finally {
            if(br != null)
                try {
                    br.close();
                    return num;
                } catch (IOException ex) {
                    System.out.println(ex);
                }
        }
        System.out.println("El arreglo es nulo");
        return null;
    }

    public void digitos(int n) {
        for(int i = 0; i < num.length; i++) {
            d[0] = (int)(num[i] * Math.pow(10, 5)) % 100000 / 10000;
            d[1] = (int)(num[i] * Math.pow(10, 5)) % 10000 / 1000;
            d[2] = (int)(num[i] * Math.pow(10, 5)) % 1000 / 100;
            d[3] = (int)(num[i] * Math.pow(10, 5)) % 100 / 10;
            d[4] = (int)(num[i] * Math.pow(10, 5)) % 10;
            frecObs();
        }
    }

    public void frecObs() {
        suma = 0;
        //Compara cantidad de digitos iguales dentro de un numero
        for(int i = 0; i < d.length; i++) {
            contador = 0;
            for(int j = 0; j < d.length; j++) if(d[i] == d[j]) contador++;
            totales[i] = contador;
        }
        //Suma la frecuencia de cada digito
        for(int i = 0; i < totales.length; i++) suma = totales[i] + suma;
        //Compara la suma de cada numero y clasifica sus frecuencias obs.
        switch(suma) {
            case 5: foTodo = foTodo + 1; break;
            case 7: foPar = foPar + 1; break;
            case 11: foTercia = foTercia + 1; break;
            case 9: foDosPar = foDosPar + 1; break;
            case 13: foFull = foFull + 1; break;
            case 17: foPoker = foPoker + 1; break;
            case 25: foQuin = foQuin + 1; break;
            default: System.out.println("La comparacion esta mal."); break;
        }
        sumatoria2 = foTodo + foPar + foDosPar + foTercia + foFull + foPoker;
        sumatoria2 += foQuin;
    }

    public void frecEsp(int n) {
        switch(n) {
            case 3:
                feTodo = 0.72 * n;
                fePar = 0.27 * n;
                feTercia = 0.01 * n;
                feDosPar = 0;
                fePoker = 0;
                feFull = 0;
                feQuin = 0;
                break;
            case 4:
                feTodo = 0.5040 * n;
                fePar = 0.4320 * n;
                feTercia = 0.036 * n;
                feDosPar = 0.0270 * n;
                fePoker = 0.0045 * n;
                feFull = 0;
                feQuin = 0;
                break;
            case 5:
                feTodo = 0.3024 * n;
                fePar = 0.5040 * n;
                feTercia = 0.072 * n;
                feDosPar = 0.1080 * n;
                fePoker = 0.0045 * n;
                feFull = 0.009 * n;
                feQuin = 0.0001 * n;
                break;
            default: break;
        }
    }

    public void formula() {
        formTodo = (Math.pow((foTodo - feTodo), 2)) / feTodo;
        formPar = (Math.pow((foPar - fePar), 2)) / fePar;
        formTercia = (Math.pow((foTercia - feTercia), 2)) / feTercia;
        formDosPar = (Math.pow((foDosPar - feDosPar), 2)) / feDosPar;
        formPoker = (Math.pow((foPoker - fePoker), 2)) / fePoker;
        formFull = (Math.pow((foFull - feFull), 2)) / feFull;
        formQuin = (Math.pow((foQuin - feQuin), 2)) / feQuin;
        sumatoria = formTodo + formPar + formDosPar + formTercia + formFull;
        sumatoria += formPoker + formQuin;
    }

    public double chiCuadrada(int m, double alfa, int confianza) {
        double valorTabla = 0;
        switch(confianza) {
            case 80:
                switch(m) {
                    case 2: valorTabla = 3.2189; break;
                    case 4: valorTabla = 5.9886; break;
                    case 6: valorTabla = 8.5581; break;
                    default: break;
                }
                break;
            case 90:
                switch(m) {
                    case 2: valorTabla = 4.6052; break;
                    case 4: valorTabla = 7.7794; break;
                    case 6: valorTabla = 10.6446; break;
                    default: break;
                }
                break;
            case 95:
                switch(m) {
                    case 2: valorTabla = 5.9915; break;
                    case 4: valorTabla = 9.4877; break;
                    case 6: valorTabla = 12.5916; break;
                    default: break;
                }
                break;
            case 99:
                switch(m) {
                    case 2: valorTabla = 9.2104; break;
                    case 4: valorTabla = 13.2767; break;
                    case 6: valorTabla = 16.8119; break;
                    default: break;
                }
                break;
            default: break;
        }
        return valorTabla;
    }

    public String conclusion(double sumatoria, double vTabla) {
        String c,m1,m2;
        m1 = "\n:. Los números\nson independientes.";
        m2 = "\n:. Los números no\nson independientes.";
        if(Double.compare(sumatoria, vTabla)<0) {
            c = df.format(sumatoria) + " < " + vTabla + m1;
            return c;
        } else if(Double.compare(sumatoria, vTabla)>0) {
            c = df.format(sumatoria) + " < " + vTabla + m2;
            return c;
        } else {
            c = df.format(sumatoria) + " < " + vTabla + m2;
            return c;
        }
    }
    
    public void formato() {
        foTodo = Integer.parseInt(df.format(foTodo));
        feTodo = Integer.parseInt(df.format(feTodo));
        formTodo = Integer.parseInt(df.format(formTodo));
        foPar = Integer.parseInt(df.format(foPar));
        fePar = Integer.parseInt(df.format(fePar));
        formPar = Integer.parseInt(df.format(formPar));
        foTercia = Integer.parseInt(df.format(foTercia));
        feTercia = Integer.parseInt(df.format(feTercia));
        formTercia = Integer.parseInt(df.format(formTercia));
        foDosPar = Integer.parseInt(df.format(foDosPar));
        feDosPar = Integer.parseInt(df.format(feDosPar));
        formDosPar = Integer.parseInt(df.format(formDosPar));
        foPoker = Integer.parseInt(df.format(foPoker));
        fePoker = Integer.parseInt(df.format(fePoker));
        formPoker = Integer.parseInt(df.format(formPoker));
        foFull = Integer.parseInt(df.format(foFull));
        feFull = Integer.parseInt(df.format(feFull));
        formFull = Integer.parseInt(df.format(formFull));
        foQuin = Integer.parseInt(df.format(foQuin));
        feQuin = Integer.parseInt(df.format(feQuin));
        formQuin = Integer.parseInt(df.format(formQuin));
        sumatoria2 = Integer.parseInt(df.format(sumatoria2));
        sumatoria = Integer.parseInt(df.format(sumatoria));
    }
    
    public String[][] resultados(int m, int n) {
        dTemp[0][0] = "Todos Diferentes";
        dTemp[0][1] = "" + df.format(foTodo);
        dTemp[0][2] = "" + df.format(feTodo);
        dTemp[0][3] = "" + df.format(formTodo);
        dTemp[1][0] = "Un Par";
        dTemp[1][1] = "" + df.format(foPar);
        dTemp[1][2] = "" + df.format(fePar);
        dTemp[1][3] = "" + df.format(formPar);
        dTemp[2][0] = "Tercia";
        dTemp[2][1] = "" + df.format(foTercia);
        dTemp[2][2] = "" + df.format(feTercia);
        dTemp[2][3] = "" + df.format(formTercia);
        dTemp[3][0] = "Dos Pares";
        dTemp[3][1] = "" + df.format(foDosPar);
        dTemp[3][2] = "" + df.format(feDosPar);
        dTemp[3][3] = "" + df.format(formDosPar);
        dTemp[4][0] = "Poker";
        dTemp[4][1] = "" + df.format(foPoker);
        dTemp[4][2] = "" + df.format(fePoker);
        dTemp[4][3] = "" + df.format(formPoker);
        dTemp[5][0] = "Full";
        dTemp[5][1] = "" + df.format(foFull);
        dTemp[5][2] = "" + df.format(feFull);
        dTemp[5][3] = "" + df.format(formFull);
        dTemp[6][0] = "Quintilla";
        dTemp[6][1] = "" + df.format(foQuin);
        dTemp[6][2] = "" + df.format(feQuin);
        dTemp[6][3] = "" + df.format(formQuin);
        frecEsp(n);
        formula();
        return dTemp;
    }

    public String sumatorias(int d, double alfa, int confianza) {
        String s = "n = " + df.format(sumatoria2);
        s += "\nX_0^2 = " + df.format(sumatoria);
        switch(d) {
            case 3: d = 2; break;
            case 4: d = 4; break;
            case 5: d = 6; break;
            default: break;
        }
        s += "\nX_(" + alfa + ", " + d + ")^2 = " + chiCuadrada(d, alfa, confianza);
        s += "\n" + conclusion(sumatoria, chiCuadrada(d, alfa, confianza));
        return s;
    }
}
