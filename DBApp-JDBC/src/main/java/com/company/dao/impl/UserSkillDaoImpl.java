package com.company.dao.impl;

import com.company.entity.Country;
import com.company.entity.Skill;
import com.company.entity.User;
import com.company.entity.UserSkill;
import com.company.dao.inter.AbstractDAO;
import com.company.dao.inter.UserSkillDaoInter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserSkillDaoImpl extends AbstractDAO implements UserSkillDaoInter {

    private UserSkill getUserSkill(ResultSet rs) throws Exception {
        int userSkillId = rs.getInt("userSkillId");
        int userId = rs.getInt("id");
        int skillId = rs.getInt("skill_id");
        String skillName = rs.getString("skill_name");
        int power = rs.getInt("power");
        return new UserSkill(userSkillId, new User(userId), new Skill(skillId, skillName), power);
    }

    @Override
    public List<UserSkill> getAllSkillByUserId(int userId) {
        List<UserSkill> result = new ArrayList<>();
        try (Connection c = connect()) {
            PreparedStatement smt = c.prepareStatement(" SELECT "
                    + " us.id as userSkillId, "
                    + "	u.* , "
                    + "	us.skill_id , "
                    + "	s.NAME AS skill_name, "
                    + "	us.power  "
                    + " FROM "
                    + "	user_skill us "
                    + "	LEFT JOIN USER u ON us.user_id = u.id "
                    + "	LEFT JOIN skill s ON us.skill_id = s.id  "
                    + " WHERE "
                    + "	us.user_id = ? ");
            smt.setInt(1, userId);
            smt.execute();
            ResultSet rs = smt.getResultSet();
            while (rs.next()) {
                UserSkill u = getUserSkill(rs);

                result.add(u);

            }

            System.out.println(c.getClass().getName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean insertUserSkill(UserSkill u) {
        try (Connection con = connect()) {
            PreparedStatement stm = con.prepareStatement("insert into user_skill (skill_id , user_id , power) values(?,?,?);");
            stm.setInt(1, u.getSkill().getId());
            stm.setInt(2, u.getUser().getId());
            stm.setInt(3, u.getPower());
            return stm.execute();
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public boolean updateUserSkill(UserSkill u) {
        try (Connection con = connect()) {
            PreparedStatement stm = con.prepareStatement(" update into user_skill set skill_id=? , user_id=? , power=? where id=? ");
            stm.setInt(1, u.getSkill().getId());
            stm.setInt(2, u.getUser().getId());
            stm.setInt(3, u.getPower());
            return stm.execute();
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public boolean removeUserSkill(int id) {
        try (Connection con = connect()) {
            PreparedStatement stm = con.prepareStatement("DELETE FROM user_skill where id=? ");
            stm.setInt(1, id);
            System.out.println("id=" + String.valueOf(id));
            return stm.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }

}
