package se.ecutb.loffe.data;

import se.ecutb.loffe.entity.City;

import java.util.List;

public interface CityDao {
    public City findById(int id);
    public List<City> findByCode(String code);
    public List<City> findByName(String name);
    public List<City> findAll();
    public City add(City city);
    public City update(City city);
    public int delete(City city);
}
