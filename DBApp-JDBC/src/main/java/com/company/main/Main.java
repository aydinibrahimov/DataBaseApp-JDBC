package com.company.main;

import com.company.dao.impl.UserDaoImpl;
import com.company.entity.User;
import com.company.dao.inter.EmploymentHistoryDaoInter;
import com.company.dao.inter.UserDaoInter;
import com.company.dao.inter.UserSkillDaoInter;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        EmploymentHistoryDaoInter userDao = Context.instanceEmploymentHistoryDao();
        System.out.println( userDao.getAllEmploymentHistoryByUserId(6));
    }
}
