package my.app.bookmyvenue.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import my.app.bookmyvenue.Model.Admin;


@Repository
public interface AdminRepo extends JpaRepository<Admin, Long> {
    
}
