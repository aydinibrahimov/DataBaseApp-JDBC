
package com.company.dao.inter;

import com.company.entity.User;
import com.company.entity.UserSkill;
import java.util.List;


public interface UserDaoInter {
    public List<User> getAll(String name,String surname,Integer nationalityId);
    public User getById(int id);
    public boolean updateUser(User u);
    public boolean addUser(User u);
    public boolean remove(int id);
}
