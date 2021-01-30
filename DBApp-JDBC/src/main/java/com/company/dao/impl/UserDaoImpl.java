package com.company.dao.impl;

import com.company.entity.Country;
import com.company.entity.Skill;
import com.company.entity.User;
import com.company.entity.UserSkill;
import com.company.dao.inter.AbstractDAO;
import com.company.dao.inter.UserDaoInter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class UserDaoImpl extends AbstractDAO implements UserDaoInter {

    private User getUser(ResultSet rs) throws Exception {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String surname = rs.getString("surname");
        String phone = rs.getString("phone");
        String email = rs.getString("email");
        String profileDescription=rs.getString("profile_description");
        int nationalityId = rs.getInt("nationality_id");
        int birthplaceId = rs.getInt("birthplace_id");
        String nationalityStr = rs.getString("nationality");
        String birthplaceStr = rs.getString("birthplace");
        Date birthDate = rs.getDate("birthdate");

        Country nationality = new Country(nationalityId, null, nationalityStr);
        Country birthplace = new Country(birthplaceId, birthplaceStr, null);
        return new User(id, name, surname, phone, email, profileDescription, birthDate, nationality, birthplace);

    }

    @Override
    public List<User> getAll(String name,String surname,Integer nationalityId) {
        List<User> result = new ArrayList<>();
        try (Connection c = connect()) {

            String sql=" select "
                    + "	u.*, "
                    + "	n.nationality, "
                    + "	c.name as birthplace "
                    + " from user u "
                    + "	left join country n on u.nationality_id = n.id "
                    + "	left join country c on u.birthplace_id = c.id where 1+1 ";
            if(name!=null){
              sql+=" and  u.name=? ";
            }
            if(surname!=null){
                sql+=" and  u.surname=? ";
            }
            if(nationalityId!=null){
                sql+=" and  u.nationality_id=? ";
            }
            PreparedStatement smt = c.prepareStatement(sql);
            int i=1;
            if(name!=null&& !name.trim().isEmpty()){
                smt.setString(i,name);
                i++;
            }
            if(surname!=null&& !surname.trim().isEmpty()){
                smt.setString(i,surname);
                i++;
            }
            if(nationalityId!=null){
                smt.setInt(i,nationalityId);

            }
            smt.execute();

            ResultSet rs = smt.getResultSet();
            while (rs.next()) {
                User u = getUser(rs);
                result.add(u);
            }

            System.out.println(c.getClass().getName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean updateUser(User u) {
        try (Connection c = connect()) {

            PreparedStatement smt = c.prepareStatement("update user set name=?,surname=?,phone=?,email=?, profile_description=?, birthdate=?, birthplace_id=? where id=?");
            smt.setString(1, u.getName());
            smt.setString(2, u.getSurname());
            smt.setString(3, u.getPhone());
            smt.setString(4, u.getEmail());
            smt.setString(5, u.getProfileDescription());
            smt.setDate(6, u.getBirthDate());
            smt.setInt(7, u.getBirthPlace().getId());
            smt.setInt(8, u.getId());
            return smt.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean remove(int id) {
        try (Connection c = connect()) {
            Statement smt = c.createStatement();
            return smt.execute("delete from user where id=" + id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public User getById(int userId) {
        User result = null;
        try (Connection c = connect()) {
            Statement smt = c.createStatement();
            smt.execute(" select "
                    + "	u.*, "
                    + "	n.nationality, "
                    + "	c.name as birthplace "
                    + " from user u "
                    + "	left join country n on u.nationality_id = n.id "
                    + "	left join country c on u.birthplace_id = c.id  where u.id= " + userId);
            ResultSet rs = smt.getResultSet();
            while (rs.next()) {

                result = getUser(rs);
            }

            System.out.println(c.getClass().getName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean addUser(User u) {
        try (Connection c = connect()) {
            PreparedStatement smt = c.prepareStatement("insert into user(name,surname,phone,email,profile_description) values(?,?,?,?,?)");
            smt.setString(1, u.getName());
            smt.setString(2, u.getSurname());
            smt.setString(3, u.getPhone());
            smt.setString(4, u.getEmail());
            smt.setString(5, u.getProfileDescription());
            return smt.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    

   

}
