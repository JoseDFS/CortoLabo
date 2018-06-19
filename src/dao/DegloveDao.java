/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import conexion.Conexion;
import interfaces.metodos;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Mascota;

/**
 *
 * @author LN710Q
 */
public class DegloveDao implements metodos<Mascota> {

    private static final String SQL_INSERT = "INSERT INTO mascotas (numInscripcion,nombre,propietario,edad,raza,estado) VALUES (?,?,?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE mascotas SET nombre = ?,propietario = ?,edad =? ,raza = ?,estado = ? WHERE numInscripcion = ?";
    private static final String SQL_DELETE = "DELETE FROM mascotas WHERE numInscripcion=?";

    private static final String SQL_READ = "SELECT * FROM mascotas WHERE numInscripcion=?";
    private static final String SQL_READALL = "SELECT * FROM mascotas";
    private static final Conexion con = Conexion.conectar();

    @Override
    public boolean create(Mascota g) {
        PreparedStatement ps;
        try {
            ps = con.getCnx().prepareStatement(SQL_INSERT);
            ps.setString(1, g.getNumInscripcion());
            ps.setString(2, g.getNombre());
            ps.setString(3, g.getPropietario());
            ps.setInt(4, g.getEdad());
            ps.setString(5, g.getRaza());
            ps.setBoolean(6, true);
            if (ps.executeUpdate() > 0) {
                return true;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(DegloveDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.cerrarConexion();
        }
        return false;
    }

    @Override
    public boolean delete(Object key) {

        try {
            PreparedStatement ps;
            ps = con.getCnx().prepareStatement(SQL_DELETE);
            ps.setString(1, key.toString());

            if (ps.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(DegloveDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.cerrarConexion();
        }
        return false;
    }

    @Override
    public boolean update(Mascota c) {
        try {
            PreparedStatement ps;
            System.out.println(c.getNumInscripcion());
            ps = con.getCnx().prepareStatement(SQL_UPDATE);

            ps.setString(1, c.getNombre());
            ps.setString(2, c.getPropietario());
            ps.setInt(3, c.getEdad());
            ps.setString(4, c.getRaza());
            ps.setBoolean(5, c.isEstado());
            ps.setString(6, c.getNumInscripcion());
            if (ps.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(DegloveDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.cerrarConexion();
        }
        return false;

    }

    @Override
    public Mascota read(Object key) {

        Mascota f = null;
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = con.getCnx().prepareStatement(SQL_READ);
            ps.setString(1, key.toString());

            rs = ps.executeQuery();
            while (rs.next()) {
                f = new Mascota(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getString(6),rs.getBoolean(7));

            }
            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(DegloveDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.cerrarConexion();
        }
        return f;
    }

    @Override
    public ArrayList<Mascota> readAll() {

        ArrayList<Mascota> all = new ArrayList();
        Statement s;
        ResultSet rs;
        try {
            s = con.getCnx().prepareStatement(SQL_READALL);
            rs = s.executeQuery(SQL_READALL);
            while (rs.next()) {
                all.add(new Mascota(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getString(6),rs.getBoolean(7)));

            }
            rs.close();
        } catch (SQLException ex) {

            Logger.getLogger(DegloveDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return all;
    }

}
