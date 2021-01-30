package com.company.dao.impl;

import com.company.dao.inter.AbstractDAO;
import com.company.dao.inter.CountryDaoInter;
import com.company.entity.Country;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class CountryDaoImpl extends AbstractDAO implements CountryDaoInter {
    @Override
    public List<Country> getAll() {
        List<Country> result = new ArrayList<>();
        try (Connection c = connect()) {
            PreparedStatement smt = c.prepareStatement(" SELECT * from country  ");
            smt.execute();
            ResultSet rs = smt.getResultSet();
            while (rs.next()) {
                Country u = getCountry(rs);
                result.add(u);
            }
            System.out.println(c.getClass().getName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
    private Country getCountry(ResultSet rs) throws Exception {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String nationality = rs.getString("nationality");
        Country country = new Country(id, name, nationality);
        return country;
    }
}
