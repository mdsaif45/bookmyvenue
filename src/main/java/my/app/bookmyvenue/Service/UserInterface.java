package my.app.bookmyvenue.Service;

import java.util.List;

import org.springframework.data.domain.Page;

// import org.springframework.stereotype.Service;

import my.app.bookmyvenue.Model.Users;

public interface UserInterface {
    // verify login
    public String verify(Users Users);
    // save user
    public String saveUsers(Users Users);
    // update user
    public void updateUser(Users user);
    // delete user
    public void deleteUserById(long id);
    
    // get all user
    public List<Users> getUsers();
    // get user by id
    public Users getUserById(long id);

    // get user by name
    public List<Users> getUsersByName(String str);

    // get user by Email ID
    public Users getUsersByEmailId(String email);

    // get client user 
    public List<Users> getUsersRole();

    // get  admin user 
    public List<Users> getAdminRole();

    // find by firstname and lastname
    public List<Users> getUserByName(String firstname, String lastname);


    // search with first name
    public Users searchWithName(String keyword);


    // get all phonenumbers
    public List<Long> getAllPhone();

    // count user accounts
    public long countOfUsers();

    // count admin accounts
    public long countOfAdmins();

    // get page of users
    public Page<Users> getUserByPaginate(int currentPage, int size);

}
