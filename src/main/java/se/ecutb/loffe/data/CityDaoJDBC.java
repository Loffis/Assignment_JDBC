package se.ecutb.loffe.data;

import se.ecutb.loffe.entity.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityDaoJDBC implements CityDao {

    private static final String FIND_BY_ID =
            "SELECT * FROM city WHERE id = ?";

    private static final String FIND_BY_CODE =
            "SELECT * FROM city WHERE countrycode = ?";

    private static final String FIND_BY_NAME =
            "SELECT * FROM city WHERE name LIKE ?";

    private static final String FIND_ALL =
            "SELECT * FROM city";

    private static final String ADD =
            "INSERT INTO city(name, countrycode, district, population) VALUES(?,?,?,?)";

    private static final String UPDATE =
            "UPDATE city SET name = ?, countrycode = ?, district = ?, population = ? WHERE city.id = ?";

    private static final String DELETE =
            "DELETE FROM city WHERE id = ?";

    private PreparedStatement create_findById(Connection connection, int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
        statement.setInt(1, id);
        return statement;
    }

    private PreparedStatement create_findByCode(Connection connection, String code) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(FIND_BY_CODE);
        statement.setString(1, code);
        return statement;
    }

    private PreparedStatement create_findByName(Connection connection, String name) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME);
        statement.setString(1, name);
        return statement;
    }

    private PreparedStatement create_findAll(Connection connection) throws SQLException {
        return connection.prepareStatement(FIND_ALL);
    }

    private PreparedStatement create_delete(Connection connection, City city) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(DELETE);
        statement.setInt(1, city.getCityId());
        return statement;
    }

    private City createCityFromResultSet(ResultSet resultSet) throws SQLException {
        return new City(
                resultSet.getInt(1),
                resultSet.getString("name"),
                resultSet.getString("countrycode"),
                resultSet.getString("district"),
                resultSet.getInt("population")
        );
    }

    @Override
    public City findById(int id) {
        City city = null;
        try (
                Connection connection = Database.getConnection();
                PreparedStatement statement = create_findById(connection, id);
                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                city = createCityFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return city;
    }

    @Override
    public List<City> findByCode(String code) {
        List<City> cities = new ArrayList<>();
        try (
                Connection connection = Database.getConnection();
                PreparedStatement statement = create_findByCode(connection, code);
                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                cities.add(createCityFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }

    @Override
    public List<City> findByName(String name) {
        List<City> cities = new ArrayList<>();
        try (
                Connection connection = Database.getConnection();
                PreparedStatement statement = create_findByName(connection, name);
                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                cities.add(createCityFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }

    @Override
    public List<City> findAll() {
        List<City> cities = new ArrayList<>();
        try (
                Connection connection = Database.getConnection();
                PreparedStatement statement = create_findAll(connection);
                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                cities.add(createCityFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }

    @Override
    public City add(City city) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            preparedStatement = connection.prepareStatement(ADD, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, city.getName());
            preparedStatement.setString(2, city.getCode());
            preparedStatement.setString(3, city.getDistrict());
            preparedStatement.setInt(4, city.getPopulation());
            preparedStatement.execute();
            resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()) {
                city = new City(
                        resultSet.getInt(1),
                        city.getName(),
                        city.getCode(),
                        city.getDistrict(),
                        city.getPopulation()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return city;
    }

    @Override
    public City update(City city) {
        try {
            int nullCheck = city.hashCode();
        } catch (Exception e) {
            System.out.println("UPDATE CITY: City does not exist!");
            return city;
        }

        try (
                Connection connection = Database.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE)
        ) {
            statement.setString(1, city.getName());
            statement.setString(2, city.getCode());
            statement.setString(3, city.getDistrict());
            statement.setInt(4, city.getPopulation());
            statement.setInt(5, city.getCityId());
            statement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
            return city;
    }


    @Override
    public int delete(City city) {
        try {
            int nullCheck = city.hashCode();
        } catch (Exception e) {
            System.out.println("DELETE CITY: City does not exist!");
            return 0;
        }
        int numberOfDeleted = 0;
        try (
                Connection connection = Database.getConnection();
                PreparedStatement statement = create_delete(connection, city);
        ) {
            numberOfDeleted = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numberOfDeleted;
    }
}