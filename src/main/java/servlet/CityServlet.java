package servlet;

import com.google.gson.Gson;
import model.City;

import store.CityEntity;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CityServlet extends HttpServlet {

    public List<String> getCitesName(ArrayList<City> cities) {
        return cities.stream().map(City::getName).collect(Collectors.toList());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        System.out.println("DoGet  CityServlet");
        ArrayList<City> cities = (ArrayList<City>) new CityEntity().findAllEntity();
        List<String> citiesName = this.getCitesName(cities);
        String js = new Gson().toJson(citiesName);
        PrintWriter writer = new PrintWriter(resp.getOutputStream(), true, StandardCharsets.UTF_8);
        writer.println(js);
        writer.flush();
    }
}
