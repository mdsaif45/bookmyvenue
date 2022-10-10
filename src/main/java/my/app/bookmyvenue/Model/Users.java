package my.app.bookmyvenue.Model;

import java.sql.Timestamp;

import javax.persistence.*;

import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
@Entity
@Table(name = "users")
public class Users{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(name = "fname")
    private String firstName;
    @Column(name = "lname")    
    private String lastName;
    @Column(name = "email")
    private String emailId;
    private Long phoneNo;
    private String password;
	@Transient
	private String confirmPassword;
    // @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createdOn;  
	private String status="No";
    private String role;  
    
}