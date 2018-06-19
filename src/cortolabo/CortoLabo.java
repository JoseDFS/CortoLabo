/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cortolabo;

import dao.DegloveDao;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelo.Mascota;

/**
 *
 * @author LN710Q
 */
public class CortoLabo extends JFrame {

    public JLabel lblnumInscripcion, lblNombre, lblPropietario, lblRaza, lblEstado, lblEdad;

    public JTextField numInscripcion, nombre, Propietario, edad;
    public JComboBox raza;

    ButtonGroup estado = new ButtonGroup();
    public JRadioButton no;
    public JRadioButton si;
    public JTable resultados;

    public JPanel table;

    public JButton buscar, eliminar, insertar, limpiar, actualizar;

    private static final int ANCHOC = 130, ALTOC = 30;

    DefaultTableModel tm;

    public CortoLabo() {
        super("Inscripciones");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        agregarLabels();
        formulario();
        llenarTabla();
        Container container = getContentPane();
        container.add(lblnumInscripcion);
        container.add(lblNombre);
        container.add(lblPropietario);
        container.add(lblRaza);
        container.add(lblEdad);
        container.add(lblEstado);
        container.add(numInscripcion);
        container.add(edad);
        container.add(nombre);
        container.add(raza);
        container.add(Propietario);
        container.add(si);
        container.add(no);
        container.add(buscar);
        container.add(insertar);
        container.add(actualizar);
        container.add(eliminar);
        container.add(limpiar);
        container.add(table);
        setSize(800, 600);
        eventos();

    }

    private void agregarLabels() {
        lblnumInscripcion = new JLabel("N° Inscripcion");
        lblEdad = new JLabel("Edad");
        lblNombre = new JLabel("Nombre");
        lblPropietario = new JLabel("Propietario");
        lblRaza = new JLabel("Raza");
        lblEstado = new JLabel("Estado");
        lblnumInscripcion.setBounds(10, 10, ANCHOC, ALTOC);
        lblNombre.setBounds(10, 60, ANCHOC, ALTOC);
        lblPropietario.setBounds(420, 60, ANCHOC, ALTOC);
        lblRaza.setBounds(10, 140, ANCHOC, ALTOC);
        lblEdad.setBounds(10, 100, ANCHOC, ALTOC);
        lblEstado.setBounds(10, 180, ANCHOC, ALTOC);

    }

    private void formulario() {
        numInscripcion = new JTextField();
        edad = new JTextField();
        nombre = new JTextField();
        raza = new JComboBox();
        Propietario = new JTextField();
        si = new JRadioButton("si", true);
        no = new JRadioButton("no", false);
        resultados = new JTable();
        buscar = new JButton("Buscar");
        insertar = new JButton("Insertar");
        eliminar = new JButton("Eliminar");
        actualizar = new JButton("Actualizar");
        limpiar = new JButton("Limpiar");

        table = new JPanel();
        raza.addItem("PITBULL");
        raza.addItem("PASTOR ALVINO");
        raza.addItem("SIBERIANO");
        raza.addItem("BOXER");

        estado = new ButtonGroup();
        estado.add(si);
        estado.add(no);

        numInscripcion.setBounds(120, 10, ANCHOC, ALTOC);
        nombre.setBounds(120, 60, ANCHOC, ALTOC);
        edad.setBounds(120, 100, ANCHOC, ALTOC);
        raza.setBounds(50, 140, ANCHOC, ALTOC);
        Propietario.setBounds(500, 60, ANCHOC, ALTOC);
        si.setBounds(50, 180, 50, ALTOC);
        no.setBounds(100, 180, 50, ALTOC);
        buscar.setBounds(300, 10, ANCHOC, ALTOC);
        insertar.setBounds(10, 210, ANCHOC, ALTOC);
        actualizar.setBounds(150, 210, ANCHOC, ALTOC);
        eliminar.setBounds(300, 210, ANCHOC, ALTOC);
        limpiar.setBounds(450, 210, ANCHOC, ALTOC);
        resultados = new JTable();
        table.setBounds(10, 250, 800, 300);
        table.add(new JScrollPane(resultados));

    }

    private void llenarTabla() {
        tm = new DefaultTableModel() {
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    case 2:
                        return String.class;

                    case 3:
                        return String.class;

                    default:
                        return Boolean.class;
                }
            }
        };

        tm.addColumn("N° Inscripcion");
        tm.addColumn("Nombre");
        tm.addColumn("Propietario");
        tm.addColumn("Raza");
        tm.addColumn("Estado");

        DegloveDao fd = new DegloveDao();
        ArrayList<Mascota> mascotas = fd.readAll();

        for (Mascota fi : mascotas) {
            tm.addRow(new Object[]{fi.getNumInscripcion(), fi.getNombre(), fi.getPropietario(), fi.getRaza(), fi.isEstado()});
        }

        resultados.setModel(tm);
    }

    private void eventos() {

        insertar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                DegloveDao fd = new DegloveDao();
                Mascota f = new Mascota(numInscripcion.getText(), nombre.getText(), Propietario.getText(), Integer.parseInt(edad.getText()), raza.getSelectedItem().toString());

                if (no.isSelected()) {
                    f.setEstado(false);
                }
                if (fd.create(f)) {
                    JOptionPane.showMessageDialog(null, "Mascota registrada con exito");
                    limpiarCampos();
                    llenarTabla();
                } else {
                    JOptionPane.showMessageDialog(null, "Ocurrio un problema a la hora de insertar la inscripcion");
                }
            }

        });

        actualizar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                DegloveDao fd = new DegloveDao();
                Mascota f = new Mascota(numInscripcion.getText(), nombre.getText(), Propietario.getText(), Integer.parseInt(edad.getText()), raza.getSelectedItem().toString());

                if (no.isSelected()) {
                    f.setEstado(false);
                }
                if (fd.update(f)) {
                    JOptionPane.showMessageDialog(null, "Mascota modificada con exito");
                    limpiarCampos();
                    llenarTabla();
                } else {
                    JOptionPane.showMessageDialog(null, "Ocurrio un problema a la hora de modificar la inscripcion");
                }
            }

        });

        eliminar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                DegloveDao fd = new DegloveDao();

                if (fd.delete(numInscripcion.getText())) {
                    JOptionPane.showMessageDialog(null, "Inscripcion eliminado con exito");
                    limpiarCampos();
                    llenarTabla();
                } else {
                    JOptionPane.showMessageDialog(null, "Ocurrio un problema a la hora de eliminar la inscripcion");
                }
            }

        });

        buscar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                DegloveDao fd = new DegloveDao();
                Mascota f = fd.read(numInscripcion.getText());
                if (f == null) {
                    JOptionPane.showMessageDialog(null, "La inscripcion no se ha encontrado");
                } else {
                    numInscripcion.setText(f.getNumInscripcion());
                    raza.setSelectedItem(f.getRaza());
                    Propietario.setText(f.getPropietario());

                    if (f.isEstado()) {
                        si.setSelected(true);
                    } else {
                        no.setSelected(false);
                    }
                }
            }

        });

        limpiar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }

        });

    }

    public void limpiarCampos() {
        numInscripcion.setText("");
        raza.setSelectedItem("SIBERIANO");
        Propietario.setText("");
        nombre.setText("");
        edad.setText("");
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CortoLabo().setVisible(true);
            }
        });
    }
}
