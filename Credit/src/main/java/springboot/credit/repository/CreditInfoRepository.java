package springboot.credit.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import springboot.credit.model.CreditInfo;

@Repository
public interface CreditInfoRepository extends CrudRepository<CreditInfo, Long> {
}
