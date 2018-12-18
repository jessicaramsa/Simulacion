package Poker;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class Ventana {
    JFrame window, mFile;
    JList nivConf, nums;
    JScrollPane sNC, sNums, sT;
    JTable tabla;
    JRadioButton d3, d4, d5;
    ButtonGroup decimal;
    JButton archivo, resolver, limpiar;
    JLabel etq[]=new JLabel[4];
    JTextArea x;
    String tEtq[] = {"Decimales","Nivel de confianza %","Números ri",
        ". . .","n = \nX_0^2 = \nX_(α,m-1)^2 = "};
    String niveles[] = {"80","90","95","99"};
    String lNums[] = {};
    String encabezado[] = {"Categoría","Oi","Ei","X0^2"};
    String datos[][] = {{"Todos Diferentes","Oi","Ei","X0^2"}};
    JFileChooser fc;
    int d = 0, m = 0, conf = 0, significancia = 0;
    double alfa = 0;
    DefaultTableModel dtm;
    DefaultListModel dlm;
    PruebaPoker pp;

    public Ventana() {
        window = new JFrame("Prueba de Poker");
        archivo = new JButton("Archivo");
        nivConf = new JList(niveles);
        sNC = new JScrollPane(nivConf);
        nums = new JList(lNums);
        sNums = new JScrollPane(nums);
        tabla = new JTable(datos, encabezado);
        sT = new JScrollPane(tabla);
        resolver = new JButton("Resolver");
        limpiar = new JButton("Limpiar");
        d3 = new JRadioButton("3");
        d4 = new JRadioButton("4");
        d5 = new JRadioButton("5");
        decimal = new ButtonGroup();
        x = new JTextArea();
        fc = new JFileChooser("src/Poker/");
        mFile = new JFrame();
        dtm = new DefaultTableModel();
        dlm = new DefaultListModel();
        for(int i = 0; i < etq.length; i++) etq[i] = new JLabel(tEtq[i]);
        pp = new PruebaPoker();

        atributos();
        armar();
        escuchas();
        mostrar();
    }

    private void atributos() {
        window.setDefaultCloseOperation(3);
        window.setResizable(false);
        window.setSize(700, 650);
        window.setLayout(null);

        sNums.setPreferredSize(new Dimension(150, 350));
        sT.setPreferredSize(new Dimension(300, 390));
        tabla.setModel(dtm);
        nums.setModel(dlm);
        x.setText(tEtq[4]);
        x.setOpaque(false);

        etq[0].setBounds(160, 10, 120, 30);
        etq[1].setBounds(360, 10, 120, 30);
        resolver.setBounds(500, 10, 120, 30);
        archivo.setBounds(10, 30, 130, 30);
        d3.setBounds(160, 50, 50, 30);
        d4.setBounds(225, 50, 50, 30);
        d5.setBounds(270, 50, 50, 30);
        sNC.setBounds(360, 50, 120, 20);
        etq[2].setBounds(10, 110, 150, 30);
        sNums.setBounds(10, 150, 80, 450);
        sT.setBounds(110, 110, 370, 490);
        x.setBounds(500, 110, 170, 100);
        etq[3].setBounds(500, 200, 170, 150);
        limpiar.setBounds(500, 520, 120, 30);
    }

    private void armar() {
        decimal.add(d3);
        decimal.add(d4);
        decimal.add(d5);

        window.add(etq[0]);
        window.add(etq[1]);
        window.add(resolver);
        window.add(archivo);
        window.add(sNC);
        window.add(d3);
        window.add(d4);
        window.add(d5);
        window.add(etq[2]);
        window.add(sNums);
        window.add(sT);
        window.add(x);
        window.add(etq[3]);
        window.add(limpiar);
    }

    private void escuchas() {
        escBotones escB = new escBotones();
        archivo.addActionListener(escB);
        resolver.addActionListener(escB);
        limpiar.addActionListener(escB);
        escDecimales escD = new escDecimales();
        d3.addActionListener(escD);
        d4.addActionListener(escD);
        d5.addActionListener(escD);
        escLista escL = new escLista();
        nivConf.addListSelectionListener(escL);
    }

    private void mostrar() {
        window.setVisible(true);
    }

    private class escDecimales implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource().equals(d3)) {
                d = 3;
                m = 2;
            } else if(e.getSource().equals(d4)) {
                d = 4;
                m = 4;
            } else if(e.getSource().equals(d5)) {
                d = 5;
                m = 6;
            }
        }
    }

    private class escLista implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if(nivConf.isSelectionEmpty()) {
                conf = 80;
                significancia = 100 - conf;
                alfa = significancia / 100;
            } else {
                conf = Integer.parseInt(nivConf.getSelectedValue().toString());
                significancia = 100 - conf;
                alfa = significancia / 100;
            }
        }
    }
    
    private class escBotones implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource().equals(resolver)) {
                if(pp.leerArch(fc.getSelectedFile()) == true) {
                    etq[3].setText("Capturado correctamente.");
                    double temp[] = pp.num;
                    lNums = new String[temp.length];
                    for(int i = 0; i < temp.length; i++) {
                        lNums[i] = String.valueOf(temp[i]);
                        dlm.addElement(lNums[i]);
                    }
                    pp.digitos(d);
                    datos = pp.resultados(m, d);
                    pp.resultados(m, d);
                    x.setText(pp.sumatorias(d, alfa, conf));
                    dtm.setDataVector(datos, encabezado);
                } else etq[3].setText("El archivo no existe.");
            } else if(e.getSource().equals(limpiar)) {
                decimal.clearSelection();
                lNums = null;
                datos = null;
                x.setText(tEtq[4]);
                etq[3].setText(tEtq[3]);
            } else if(e.getSource().equals(archivo)) {
                fc.showOpenDialog(window);
            }
        }
    }

    public static void main(String[] args) {
        Ventana test = new Ventana();
    }
}
