package com.company.dao.impl;

import com.company.entity.EmploymentHistory;
import com.company.entity.User;
import com.company.entity.UserSkill;
import com.company.dao.inter.AbstractDAO;
import com.company.dao.inter.EmploymentHistoryDaoInter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmploymentHistoryDaoImpl extends AbstractDAO implements EmploymentHistoryDaoInter {

     public List<EmploymentHistory> getAllEmploymentHistoryByUserId(int userId) {
        List<EmploymentHistory> result = new ArrayList<>();
        try (Connection c = connect()) {
            PreparedStatement smt = c.prepareStatement(" SELECT * from employment_history where user_id =?  ");
            smt.setInt(1, userId);
            smt.execute();
            ResultSet rs = smt.getResultSet();
            while (rs.next()) {
                EmploymentHistory u=getEmploymentHistory(rs);
                
                result.add(u);
                
            }

            System.out.println(c.getClass().getName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    private EmploymentHistory getEmploymentHistory(ResultSet rs) throws Exception{
        String header=rs.getString("header");
        String jobDescription=rs.getString("job_description");
        Date beginDate=rs.getDate("begin_date");
        Date endDate=rs.getDate("end_date");
        int userId=rs.getInt("user_id");
EmploymentHistory emp=new EmploymentHistory(null, header, beginDate, endDate, jobDescription, new User(userId));
   return emp;
    }
   

}
