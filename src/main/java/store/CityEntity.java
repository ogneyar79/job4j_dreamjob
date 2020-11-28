package store;

import model.Candidate;
import model.City;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CityEntity implements IPsqlStoreBase<City> {

    final BasicDataSource pool = PsqlStore.instOf().getPool();

    @Override
    public Collection<City> findAllEntity() {
        List<City> cities = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM city")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    cities.add(new City(it.getInt("id"), it.getString("name")));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cities;
    }

    @Override
    public void save(City city) {
        if (city.getId() == 0) {
            create(city);
        } else {
            update(city);
        }
    }

    private void update(City city) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(" UPDATE city set name = ? where id = ? ")
        ) {
            ps.setString(1, city.getName());
            ps.setInt(2, city.getId());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private City create(City city) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO city(name) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, city.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    city = new City(id.getInt(1), city.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return city;
    }

    @Override
    public City findById(int id) {
        City city = new City(0, "Zero");
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM city where id =?")
        ) {
            ps.setString(1, String.valueOf(id));
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    city = new City(it.getInt(1), it.getString(2));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return city;
    }

    public City getCityId(String cityName) {
        City city = new City(0, "Zero");
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM city where name =?")
        ) {
            ps.setString(1, String.valueOf(cityName));
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    city = new City(it.getInt(1), it.getString(2));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return city;

    }


}
