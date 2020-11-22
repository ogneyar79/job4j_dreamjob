package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.City;
import store.CityEntity;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CityServlet extends HttpServlet {

    public List<String> getCitesName(ArrayList<City> cities) {
        return cities.stream().map(City::getName).collect(Collectors.toList());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
              System.out.println("DoPost  CityServlet");
        ArrayList<City> cities = (ArrayList<City>) new CityEntity().findAllEntity();
        List<String> citiesName = this.getCitesName(cities);

        String js = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().
                create().toJson(citiesName);

        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        resp.setHeader("Access-Control-Allow-Origin", "*");
     //   System.out.println(js);
        writer.println(js);
        writer.flush();
    }


}
