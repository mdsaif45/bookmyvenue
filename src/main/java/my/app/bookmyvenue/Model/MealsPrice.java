package my.app.bookmyvenue.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MealsPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long mealId;
    private long venueId;
    @Column(name = "veg")
    private int vegPrice;
    @Column(name = "non_veg")
    private int nonvegPrice;

}
