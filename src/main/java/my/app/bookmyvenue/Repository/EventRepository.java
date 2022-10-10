package my.app.bookmyvenue.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import my.app.bookmyvenue.Model.EventList;

@Repository
public interface EventRepository extends JpaRepository<EventList, Long> {
    
    // count number of Events
    @Query("SELECT COUNT(e) FROM EventList e")
    long countOfEvents();
}
