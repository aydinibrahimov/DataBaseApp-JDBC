
package com.company.dao.inter;

import com.company.entity.EmploymentHistory;
import java.util.List;


public interface EmploymentHistoryDaoInter {

    public  List<EmploymentHistory> getAllEmploymentHistoryByUserId(int userId);
}
