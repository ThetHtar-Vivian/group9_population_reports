package com.napier.population;

/**
 * Represents a Country entity from the world database.
 * Includes both raw country table fields and extra fields
 * used for reporting (e.g., district, capital name).
 */
public class Country {

    // Core attributes
    private String code;
    private String name;
    private String continent;
    private String region;
    private double surface_area;
    private int indep_year;
    private int population;
    private double life_expectancy;
    private double gnp;
    private double gnp_old;
    private String local_name;
    private String government_form;
    private String head_of_state;
    private int capital;
    private String code2;

    // Extra fields for reporting (not directly in the Country table)
    private String district;
    private String capitalName;

    /**
     * Default constructor for creating an empty Country object.
     * Useful when values will be set later or when frameworks require it.
     */
    public Country() {
        // Default constructor
    }

    /**
     * Parameterized constructor for creating a fully initialized Country object.
     *
     * @param code             ISO country code
     * @param name             Country name
     * @param continent        Continent name
     * @param region           Region name
     * @param surface_area     Surface area
     * @param indep_year       Year of independence
     * @param population       Population of the country
     * @param life_expectancy  Average life expectancy
     * @param gnp              Gross National Product (GNP)
     * @param gnp_old          Previous GNP
     * @param local_name       Local name of the country
     * @param government_form  Government form
     * @param head_of_state    Head of state
     * @param capital          Capital city ID
     * @param code2            Two-letter ISO code
     * @param district         District (for reporting)
     * @param capitalName      Capital city name (for reporting)
     */
    public Country(String code, String name, String continent, String region, double surface_area,
                   int indep_year, int population, double life_expectancy, double gnp, double gnp_old,
                   String local_name, String government_form, String head_of_state, int capital,
                   String code2, String district, String capitalName) {
        this.code = code;
        this.name = name;
        this.continent = continent;
        this.region = region;
        this.surface_area = surface_area;
        this.indep_year = indep_year;
        this.population = population;
        this.life_expectancy = life_expectancy;
        this.gnp = gnp;
        this.gnp_old = gnp_old;
        this.local_name = local_name;
        this.government_form = government_form;
        this.head_of_state = head_of_state;
        this.capital = capital;
        this.code2 = code2;
        this.district = district;
        this.capitalName = capitalName;
    }

    // Getters and Setters

    /**
     * @return ISO country code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code ISO country code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return Country name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name Country name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Continent name
     */
    public String getContinent() {
        return continent;
    }

    /**
     * @param continent Continent name
     */
    public void setContinent(String continent) {
        this.continent = continent;
    }

    /**
     * @return Region
     */
    public String getRegion() {
        return region;
    }

    /**
     * @param region Region name
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * @return District (from reports)
     */
    public String getDistrict() {
        return district;
    }

    /**
     * @param district District (from reports)
     */
    public void setDistrict(String district) {
        this.district = district;
    }

    /**
     * @return Surface area
     */
    public double getSurface_area() {
        return surface_area;
    }

    /**
     * @param surface_area Surface area
     */
    public void setSurface_area(double surface_area) {
        this.surface_area = surface_area;
    }

    /**
     * @return Year of independence
     */
    public int getIndep_year() {
        return indep_year;
    }

    /**
     * @param indep_year Year of independence
     */
    public void setIndep_year(int indep_year) {
        this.indep_year = indep_year;
    }

    /**
     * @return Population
     */
    public int getPopulation() {
        return population;
    }

    /**
     * @param population Population
     */
    public void setPopulation(int population) {
        this.population = population;
    }

    /**
     * @return Life expectancy
     */
    public double getLife_expectancy() {
        return life_expectancy;
    }

    /**
     * @param life_expectancy Life expectancy
     */
    public void setLife_expectancy(double life_expectancy) {
        this.life_expectancy = life_expectancy;
    }

    /**
     * @return GNP
     */
    public double getGnp() {
        return gnp;
    }

    /**
     * @param gnp GNP
     */
    public void setGnp(double gnp) {
        this.gnp = gnp;
    }

    /**
     * @return Old GNP
     */
    public double getGnp_old() {
        return gnp_old;
    }

    /**
     * @param gnp_old Old GNP
     */
    public void setGnp_old(double gnp_old) {
        this.gnp_old = gnp_old;
    }

    /**
     * @return Local name
     */
    public String getLocal_name() {
        return local_name;
    }

    /**
     * @param local_name Local name
     */
    public void setLocal_name(String local_name) {
        this.local_name = local_name;
    }

    /**
     * @return Government form
     */
    public String getGovernment_form() {
        return government_form;
    }

    /**
     * @param government_form Government form
     */
    public void setGovernment_form(String government_form) {
        this.government_form = government_form;
    }

    /**
     * @return Head of state
     */
    public String getHead_of_state() {
        return head_of_state;
    }

    /**
     * @param head_of_state Head of state
     */
    public void setHead_of_state(String head_of_state) {
        this.head_of_state = head_of_state;
    }

    /**
     * @return Capital city ID
     */
    public int getCapital() {
        return capital;
    }

    /**
     * @param capital Capital city ID
     */
    public void setCapital(int capital) {
        this.capital = capital;
    }

    /**
     * @return Capital city name
     */
    public String getCapitalName() {
        return capitalName;
    }

    /**
     * @param capitalName Capital city name
     */
    public void setCapitalName(String capitalName) {
        this.capitalName = capitalName;
    }

    /**
     * @return 2-letter ISO code
     */
    public String getCode2() {
        return code2;
    }

    /**
     * @param code2 2-letter ISO code
     */
    public void setCode2(String code2) {
        this.code2 = code2;
    }

    /**
     * Returns a string representation of the Country object.
     *
     * @return A formatted string with country details
     */
    @Override
    public String toString() {
        return String.format(
                "Country{code='%s', name='%s', continent='%s', region='%s', surface_area=%.2f, indep_year=%d, population=%d, life_expectancy=%.2f, gnp=%.2f, gnp_old=%.2f, local_name='%s', government_form='%s', head_of_state='%s', capital=%d, code2='%s', district='%s', capitalName='%s'}",
                code, name, continent, region, surface_area, indep_year, population, life_expectancy,
                gnp, gnp_old, local_name, government_form, head_of_state, capital, code2, district, capitalName
        );
    }
}
