package my.app.bookmyvenue.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import my.app.bookmyvenue.Model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
 
    // get account by Email Id
    public Users findByEmailId(String email);
    // get all account by First name
    public List<Users> findByFirstName(String firstname);
    // get all accounts by first and lastname
    public List<Users> findByFirstNameAndLastName(String firstname, String lastname);
    // search account with first name
    public Users findByFirstNameLike(String keyword);

    // get count of user accounts
    @Query("SELECT COUNT(u) FROM Users u WHERE u.role = 'ROLE_USER'")
    long countOfUsers();

    // get count of admin accounts
    @Query("SELECT COUNT(u) FROM Users u WHERE u.role='ROLE_ADMIN'")
    long countOfAdmins();

    // get contacts of all users
    @Query("select phoneNo from Users")
    public List<Long> getAllPhone();

    // get all user accounts
    @Query("select u from Users u where u.role = 'ROLE_USER'")
    public List<Users> getUsersRole();

    // get all admin accounts
    @Query("select u from Users u where u.role = 'ROLE_ADMIN'")
    public List<Users> getAdminRole();
    // @Query("select * from users where email like %:keyword%")
    // public List<Users> findByEmailId(@Param("keyword") String str);
}
