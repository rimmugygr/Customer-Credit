package springboot.credit.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import springboot.credit.model.Credit;

@Repository
public interface CreditInfoRepository extends CrudRepository<Credit, Integer> {
}
