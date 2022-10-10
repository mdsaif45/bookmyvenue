package my.app.bookmyvenue.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import my.app.bookmyvenue.Model.Users;
import my.app.bookmyvenue.Repository.UserRepository;

@Service
public class UserService implements UserInterface{

    @Autowired
    private UserRepository userRepository; 
    
    // verify login
    @Override
    public String verify(Users user) {
        
        return null;
    }

    // add user
    @Override
    public String saveUsers(Users user) {
        userRepository.save(user);
        return "User added to database.."; 
    }

    // update user
    @Override
    public void updateUser(Users user){
        userRepository.save(user);
    }   
    
    // delete user by ID
    @Override
    public void deleteUserById(long id){
        userRepository.deleteById(id);
        System.out.println("running deleting from database");
    }
    
    // get user
    @Override
    public List<Users> getUsers() {
        // List<Users> findByEmailId = userRepository.findAll();
        // findByEmailId.forEach(e -> System.out.println(e));
        return userRepository.findAll();
    }
    
    // get page of users
    @Override
    public Page<Users> getUserByPaginate(int currentPage, int size){
        return userRepository.findAll(PageRequest.of(currentPage, size));
    }

    // get user by id
    @Override
    public Users getUserById(long id) {
        Optional<Users> user = userRepository.findById(id);
		if (user.isPresent()) {
			return user.get();
		}
		return null;
    }

    // get user by first name
    @Override
    public List<Users> getUsersByName(String name) {

        List<Users> user = userRepository.findByFirstName(name);
        user.forEach(e->System.out.println(e));
        return null;
    }
 
    // get user by name (with first and last name)
    @Override
    public List<Users> getUserByName(String firstname, String lastname) {
        List<Users> user = userRepository.findByFirstNameAndLastName(firstname, lastname);
        user.forEach(e-> System.out.println(e));
        return null;
    }    
    
    // get user by email
    @Override
    public Users getUsersByEmailId(String email) {
        Users user = userRepository.findByEmailId(email);
        System.out.println(user);
        return user;
    }

    // get client user
    @Override 
    public List<Users> getUsersRole(){
        return userRepository.getUsersRole();
    }

    // get  admin user 
    @Override
    public List<Users> getAdminRole(){
        return userRepository.getAdminRole();
    }

    // search user with name
    @Override
    public Users searchWithName(String keyword) {
        Users user = userRepository.findByFirstNameLike(keyword);
        return user;
    }

    // not used in project it is just for  self learning
    // get all phone no 
    @Override
    public List<Long> getAllPhone() {
        List<Long> allPhone = userRepository.getAllPhone();
        allPhone.forEach(e->System.out.println(e));
        return null;
    }
    
    // count admin accounts
    @Override
    public long countOfAdmins(){
        System.out.println(userRepository.countOfAdmins());
        return userRepository.countOfAdmins();
    }

    // count user accounts
    @Override
    public long countOfUsers(){
        System.out.println(userRepository.countOfUsers());
        return userRepository.countOfUsers();
    }
}
