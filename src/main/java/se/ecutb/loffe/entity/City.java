package se.ecutb.loffe.entity;

import java.util.Objects;

public class City {
    private int cityId;
    private String name;
    private String code;
    private String district;
    private int population;

    public City(int cityId, String name, String code, String district, int population) {
        this.cityId = cityId;
        this.name = name;
        this.code = code;
        this.district = district;
        this.population = population;
    }

    public City(String name, String code, String district, int population) {
        this(0, name, code, district, population);
    }

    public int getCityId() {
        return cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return cityId == city.cityId &&
                population == city.population &&
                Objects.equals(name, city.name) &&
                Objects.equals(code, city.code) &&
                Objects.equals(district, city.district);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityId, name, code, district, population);
    }

    @Override
    public String toString() {
        return "City{" +
                "cityId=" + cityId +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", district='" + district + '\'' +
                ", population=" + population +
                "}\n";
    }
}
