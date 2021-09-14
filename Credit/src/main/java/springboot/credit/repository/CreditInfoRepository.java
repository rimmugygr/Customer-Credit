package springboot.credit.repository;

import org.springframework.data.repository.CrudRepository;
import springboot.credit.model.Credit;

public interface CreditRepository extends CrudRepository<Credit, Long> {
}
