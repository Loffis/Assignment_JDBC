package se.ecutb.loffe;

import se.ecutb.loffe.data.CityDaoJDBC;
import se.ecutb.loffe.data.Database;

import java.sql.SQLException;

public class App
{
    public static void main( String[] args ) throws SQLException {
        Database.getConnection();
        CityDaoJDBC cityDao = new CityDaoJDBC();
        
    }
}
