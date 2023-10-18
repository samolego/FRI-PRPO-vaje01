package org.samo_lego.prve_vaje.servleti;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import org.samo_lego.prve_vaje.jdbc.BaseDao;
import org.samo_lego.prve_vaje.jdbc.Uporabnik;
import org.samo_lego.prve_vaje.jdbc.UporabnikDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@WebServlet("/servlet")
public class PrviJdbcServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter writer = resp.getWriter();

        //System.out.println("Moj prvi servlet.");

        //izpis konfiguracije mikrostoritev
        Optional<String> microserviceName = ConfigurationUtil.getInstance().get("kumuluzee.name");
        microserviceName.ifPresent(s -> writer.println("Izpis generiran v mikrostoritvi: " + s + "\n"));

        // dostop do podatkovne baye
        BaseDao<Uporabnik> uporabnikDao = UporabnikDaoImpl.getInstance();

        Uporabnik uporabnik = new Uporabnik("Miha", "Novak", "mihanovak");

        // dodajanje uporabnika
        /*writer.append("Dodajam uporabnika:\n").append(uporabnik.toString());
        uporabnikDao.vstavi(uporabnik);*/
        List<Uporabnik> uporabniki2 = uporabnikDao.vrniVse();
        writer.append("\n\nSeznam obstojecih uporabnikov (len= ").append(String.valueOf(uporabniki2.size())).append("):\n");
        uporabniki2.forEach(u -> writer.append(u.toString()).append("\n"));
        writer.append("\n\n");

        // demonstracija preostalih metod
        final var posodobljen = uporabnikDao.vrni(2);
        posodobljen.ifPresent(up -> {
            up.setIme("Janez" + (char) ('A' + new Random().nextInt(24)));
            uporabnikDao.posodobi(up);
            writer.append("Uporabnik ").append(up.getIme()).append(" posodobljen!");

            List<Uporabnik> uporabniki1 = uporabnikDao.vrniVse();
            writer.append("\n\nSeznam obstojecih uporabnikov (len= ").append(String.valueOf(uporabniki1.size())).append("):\n");
            uporabniki1.forEach(u -> writer.append(u.toString()).append("\n"));

            // Izbris
            //uporabnikDao.odstrani(up.getId());
        });


        // izpis vseh uporabnikov
        List<Uporabnik> uporabniki = uporabnikDao.vrniVse();
        writer.append("\n\nSeznam obstojecih uporabnikov (len= ").append(String.valueOf(uporabniki.size())).append("):\n");
        uporabniki.forEach(u -> writer.append(u.toString()).append("\n"));
    }
}
