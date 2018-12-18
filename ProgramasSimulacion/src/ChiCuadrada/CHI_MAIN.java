package ChiCuadrada;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CHI_MAIN extends JFrame {
    int n;
    int i;
    int p, p1, p2, p3, p4, p5, p6, p7, p8, p9;
    int g, g1, g2, g3, g4;
    String arch, aux;
    double m;
    double Ei, E1, E2, E3, E4, E5, E6, E7, E8, E9;
    double Eii, E1i, E2i, E3i, E4i;
    double XO2, XO21, XO22, XO23, XO24, XO25, XO26, XO27, XO28, XO29;
    double XOO2, XOO21, XOO22, XOO23, XOO24;
    double sumatoria_XO2;
    private JTextField sumatoria;
    private JTextField confianza;
    transient Container parent;
    private ArrayList<Double> aleatorios;
    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CHI_MAIN frame = new CHI_MAIN();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public ArrayList<Double> sacarNumerosDeTXT(File f) {
        FileReader lector;
        ArrayList<Double> Lista = new ArrayList();
        try {
            String linea;
            lector = new FileReader(f);
            BufferedReader br = new BufferedReader(lector);
            int c = 0;
            while ((linea = br.readLine()) != null) {
                Lista.add(Double.parseDouble(linea));
                c++;

            }
            System.out.println("se leyeron " + c + "numeros");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CHI_MAIN.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CHI_MAIN.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Lista;
    }

    public CHI_MAIN() {
        super("PROGRAMA DE METODO DE CHI CUADRADA");

        arch = "src/";
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 576);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 1000, 1000);
        contentPane.add(panel);
        panel.setLayout(null);

        //INTERFAZ
        JLabel lblpseudo = new JLabel("Numeros Pseudoaleatorios");
        lblpseudo.setBounds(390, 10, 170, 15);
        panel.add(lblpseudo);

        sumatoria = new JTextField();
        sumatoria.setBounds(570, 240, 99, 20);
        panel.add(sumatoria);
        sumatoria.setColumns(10);
        sumatoria.setEditable(false);

        confianza = new JTextField();
        confianza.setBounds(590, 290, 99, 20);
        panel.add(confianza);
        confianza.setColumns(10);

        JLabel lblintervalo = new JLabel("Intervalo");
        lblintervalo.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblintervalo.setBounds(50, 200, 79, 36);
        panel.add(lblintervalo);

        JLabel lblinOi = new JLabel("Oi");
        lblinOi.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblinOi.setBounds(220, 200, 79, 36);
        panel.add(lblinOi);

        JLabel lblinEi = new JLabel("Ei");
        lblinEi.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblinEi.setBounds(340, 200, 79, 36);
        panel.add(lblinEi);

        JLabel lblinXO2 = new JLabel("XO2");
        lblinXO2.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblinXO2.setBounds(440, 200, 79, 36);
        panel.add(lblinXO2);

        JLabel lblsumatoria = new JLabel("S-XO2");
        lblsumatoria.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblsumatoria.setBounds(520, 230, 79, 36);
        panel.add(lblsumatoria);

        JLabel lblconfianza = new JLabel("Confianza");
        lblconfianza.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblconfianza.setBounds(520, 280, 79, 36);
        panel.add(lblconfianza);

        final TextArea area_aletoria = new TextArea();
        area_aletoria.setBounds(10, 30, 963, 150);
        area_aletoria.setEditable(false);
        area_aletoria.setBackground(Color.white);
        panel.add(area_aletoria);

        final TextArea area_intervalo = new TextArea();
        area_intervalo.setBounds(10, 240, 150, 280);
        area_intervalo.setEditable(false);
        area_intervalo.setBackground(Color.white);
        panel.add(area_intervalo);

        final TextArea area_fobsersada = new TextArea();
        area_fobsersada.setBounds(180, 240, 100, 280);
        area_fobsersada.setEditable(false);
        area_fobsersada.setBackground(Color.white);
        panel.add(area_fobsersada);

        final TextArea area_fesperada = new TextArea();
        area_fesperada.setBounds(300, 240, 100, 280);
        area_fesperada.setEditable(false);
        area_fesperada.setBackground(Color.white);
        panel.add(area_fesperada);

        final TextArea area_XO2 = new TextArea();
        area_XO2.setBounds(410, 240, 100, 280);
        area_XO2.setEditable(false);
        area_XO2.setBackground(Color.white);
        panel.add(area_XO2);

        //BOTON PARA GENERAR NUMEROS ALEATORIOS
        JButton boton_aletorio = new JButton("Generar numeros");
        boton_aletorio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DecimalFormat decimales = new DecimalFormat("0.0000");
//                n = Integer.parseInt(JOptionPane.showInputDialog(null, "Introduzca la cantidad de numeros a generar"));
                n = aleatorios.size() * 2;
                m = Math.sqrt(n);
                i = 0;
                p = 0;
                Ei = (n / m);
                p1 = 0;
                E1 = (n / m);
                p2 = 0;
                E2 = (n / m);
                p3 = 0;
                E3 = (n / m);
                p4 = 0;
                E4 = (n / m);
                p5 = 0;
                E5 = (n / m);
                p6 = 0;
                E6 = (n / m);
                p7 = 0;
                E7 = (n / m);
                p8 = 0;
                E8 = (n / m);
                p9 = 0;
                E9 = (n / m);

                g = 0;
                Eii = (n / m);
                g1 = 0;
                E1i = (n / m);
                g2 = 0;
                E2i = (n / m);
                g3 = 0;
                E3i = (n / m);
                g4 = 0;
                E4i = (n / m);
                int j = 0;
                while (j < n) {
                    //Random rnd = new Random();
//                    area_aletoria.append(decimales.format(aleatorios.get(i)) + "\n");
//                    System.out.println(decimales.format(rnd.nextDouble
                    area_aletoria.append(decimales.format(aleatorios.get(j)) + "\n");
                    System.out.println(decimales.format(aleatorios.get(j)));
                    n = n - 1;
                    XO2 = Math.pow((Ei - p), 2) / Ei;
                    XO21 = Math.pow((E1 - p1), 2) / E1;
                    XO22 = Math.pow((E2 - p2), 2) / E2;
                    XO23 = Math.pow((E3 - p3), 2) / E3;
                    XO24 = Math.pow((E4 - p4), 2) / E4;
                    XO25 = Math.pow((E5 - p5), 2) / E5;
                    XO26 = Math.pow((E6 - p6), 2) / E6;
                    XO27 = Math.pow((E7 - p7), 2) / E7;
                    XO28 = Math.pow((E8 - p8), 2) / E8;
                    XO29 = Math.pow((E9 - p9), 2) / E9;

                    XOO2 = Math.pow((Eii - g), 2) / Eii;
                    XOO21 = Math.pow((E1i - g1), 2) / E1i;
                    XOO22 = Math.pow((E2i - g2), 2) / E2i;
                    XOO23 = Math.pow((E3i - g3), 2) / E3i;
                    XOO24 = Math.pow((E4i - g4), 2) / E4i;

                    if (m % 1 == 0) {
                        if (aleatorios.get(j) >= 0 && aleatorios.get(j) <= 0.1) {
                            p++;
                        }
                        if (aleatorios.get(j) > 0.1 && aleatorios.get(j) <= 0.2) {
                            p1++;
                        }
                        if (aleatorios.get(j) > 0.2 && aleatorios.get(j) <= 0.3) {
                            p2++;
                        }
                        if (aleatorios.get(j) > 0.3 && aleatorios.get(j) <= 0.4) {
                            p3++;
                        }
                        if (aleatorios.get(j) > 0.4 && aleatorios.get(j) <= 0.5) {
                            p4++;
                        }
                        if (aleatorios.get(j) > 0.5 && aleatorios.get(j) <= 0.6) {
                            p5++;
                        }
                        if (aleatorios.get(j) > 0.6 && aleatorios.get(j) <= 0.7) {
                            p6++;
                        }
                        if (aleatorios.get(j) > 0.7 && aleatorios.get(j) <= 0.8) {
                            p7++;
                        }
                        if (aleatorios.get(j) > 0.8 && aleatorios.get(j) <= 0.9) {
                            p8++;
                        }
                        if (aleatorios.get(j) > 0.9 && aleatorios.get(j) <= 1) {
                            p9++;
                        }

                    }
                    if (m % 1 > 0) {
                        if (aleatorios.get(j) >= 0 && aleatorios.get(j) <= 0.2) {
                            g++;
                        }
                        if (aleatorios.get(j) > 0.2 && aleatorios.get(j) <= 0.4) {
                            g1++;
                        }
                        if (aleatorios.get(j) > 0.4 && aleatorios.get(j) <= 0.6) {
                            g2++;
                        }
                        if (aleatorios.get(j) > 0.6 && aleatorios.get(j) <= 0.8) {
                            g3++;
                        }
                        if (aleatorios.get(j) > 0.8 && aleatorios.get(j) <= 1) {
                            g4++;
                        }
                    }
                    j++;
                }
                if (m % 1 == 0) {
                    area_intervalo.setText("\nestan entre 0 y 0.1: "
                            + "\nestan entre 0.1 y 0.2: "
                            + "\nestan entre 0.2 y 0.3: "
                            + "\nestan entre 0.3 y 0.4: "
                            + "\nestan entre 0.4 y 0.5: "
                            + "\nestan entre 0.5 y 0.6: "
                            + "\nestan entre 0.6 y 0.7: "
                            + "\nestan entre 0.7 y 0.8: "
                            + "\nestan entre 0.8 y 0.9: "
                            + "\nestan entre 0.9 y 1: ");

                    area_fobsersada.setText(String.valueOf("\n" + p + "\n" + p1
                            + "\n" + p2 + "\n" + p3 + "\n" + p4 + "\n" + p5
                            + "\n" + p6 + "\n" + p7 + "\n" + p8 + "\n" + p9));

                    area_fesperada.setText(String.valueOf("\n" + Ei + "\n"
                            + E1 + "\n" + E2 + "\n" + E3 + "\n" + E4 + "\n" + E5
                            + "\n" + E6 + "\n" + E7 + "\n"
                            + E8 + "\n" + E9));

                    area_XO2.setText(String.valueOf("\n" + decimales.format(XO2) + "\n"
                            + decimales.format(XO21) + "\n" + decimales.format(XO22) + "\n"
                            + decimales.format(XO23) + "\n" + decimales.format(XO24) + "\n"
                            + decimales.format(XO25) + "\n" + decimales.format(XO26) + "\n"
                            + decimales.format(XO27) + "\n"
                            + decimales.format(XO28) + "\n" + decimales.format(XO29)));

                    sumatoria_XO2 = (XO2 + XO21 + XO22 + XO23 + XO24 + XO25 + XO26 + XO27 + XO28 + XO29);
                    sumatoria.setText(String.valueOf(decimales.format(sumatoria_XO2)));

                    System.out.println("\nestan entre 0 y 0.1: \n" + p
                            + "\nestan entre 0.1 y 0.2: \n" + p1
                            + "\nestan entre 0.2 y 0.3: \n" + p2
                            + "\nestan entre 0.3 y 0.4: \n" + p3
                            + "\nestan entre 0.4 y 0.5: \n" + p4
                            + "\nestan entre 0.5 y 0.6: \n" + p5
                            + "\nestan entre 0.6 y 0.7: \n" + p6
                            + "\nestan entre 0.7 y 0.8: \n" + p7
                            + "\nestan entre 0.8 y 0.9: \n" + p8
                            + "\nestan entre 0.9 y 1: \n" + p9);
                }
                if (m % 1 > 0) {
                    area_intervalo.setText("\nentre 0 y 0.2 "
                            + "\nentre 0.2 y 0.4 "
                            + "\nentre 0.4 y 0.6 "
                            + "\nentre 0.6 y 0.8 "
                            + "\nentre 0.8 y 1.0 ");

                    area_fobsersada.setText(String.valueOf("\n" + g + "\n" + g1
                            + "\n" + g2 + "\n" + g3 + "\n" + g4));

                    area_fesperada.setText(String.valueOf("\n" + decimales.format(Eii) + "\n"
                            + decimales.format(E1i) + "\n" + decimales.format(E2i)
                            + "\n" + decimales.format(E3i) + "\n" + decimales.format(E4i)));

                    area_XO2.setText(String.valueOf("\n" + decimales.format(XOO2) + "\n"
                            + decimales.format(XOO21) + "\n" + decimales.format(XOO22) + "\n"
                            + decimales.format(XOO23) + "\n" + decimales.format(XOO24)));

                    sumatoria_XO2 = (XOO2 + XOO21 + XOO22 + XOO23 + XOO24);
                    sumatoria.setText(String.valueOf(decimales.format(sumatoria_XO2)));

                    System.out.println("\nentre 0 y 0.2 \n" + g
                            + "\nentre 0.2 y 0.4\n " + g1
                            + "\nentre 0.4 y 0.6\n " + g2
                            + "\nentre 0.6 y 0.8\n " + g3
                            + "\nentre 0.8 y 1.0\n " + g4);

                    JOptionPane.showMessageDialog(null, "Por Lo Tanto si son uniformemente Distribuidos");
                }
            }
        });
        boton_aletorio.setBounds(390, 180, 190, 23);
        panel.add(boton_aletorio);

// Boton Para cargar archivo
        JButton btnfile = new JButton("Cargar Archivo");
        btnfile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Documento de Texto", "txt");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(parent);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    JOptionPane.showMessageDialog(null, "Se ha cargado el archivo correctamente");
                    File f = chooser.getSelectedFile();
                    aleatorios = sacarNumerosDeTXT(f);
                    System.out.println("Ha elegido: " + chooser.getSelectedFile().getName());
                }
            }
        });
        btnfile.setBounds(790, 180, 190, 23);
        panel.add(btnfile);

        JButton btnLimpiar = new JButton("Vaciar"); //Metodo para vaciar.
        btnLimpiar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                n = 0;
                m = 0;
                i = 0;
                p = 0;
                Ei = 0;
                XO2 = 0;
                p1 = 0;
                E1 = 0;
                XO21 = 0;
                p2 = 0;
                E2 = 0;
                XO22 = 0;
                p3 = 0;
                E3 = 0;
                XO23 = 0;
                p4 = 0;
                E4 = 0;
                XO24 = 0;
                p5 = 0;
                E5 = 0;
                XO25 = 0;
                p6 = 0;
                E6 = 0;
                XO26 = 0;
                p7 = 0;
                E7 = 0;
                XO27 = 0;
                p8 = 0;
                E8 = 0;
                XO28 = 0;
                p9 = 0;
                E9 = 0;
                XO29 = 0;

                g = 0;
                Eii = 0;
                XOO2 = 0;
                g1 = 0;
                E1i = 0;
                XOO21 = 0;
                g2 = 0;
                E2i = 0;
                XOO22 = 0;
                g3 = 0;
                E3i = 0;
                XOO23 = 0;
                g4 = 0;
                E4i = 0;
                XOO24 = 0;

                sumatoria_XO2 = 0;

                area_aletoria.setText(null);
                area_intervalo.setText(null);
                area_fobsersada.setText(null);
                area_fesperada.setText(null);
                area_XO2.setText(null);
                sumatoria.setText(null);
            }
        });
        btnLimpiar.setBounds(800, 220, 96, 23);
        panel.add(btnLimpiar);
    }
}
