package net.slipp.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByUserId(String userId);	//findBy조회하고 싶은 속성이름(인자 )
}
