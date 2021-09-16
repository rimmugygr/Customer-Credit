package springboot.credit.repository;

import org.springframework.data.repository.CrudRepository;
import springboot.credit.model.CreditInfo;

public interface CreditInfoRepository extends CrudRepository<CreditInfo, Long> {
}
