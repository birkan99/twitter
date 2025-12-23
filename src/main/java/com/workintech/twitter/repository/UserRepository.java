package com.workintech.twitter.repository;


//repository : repositoynin görevi veritabanıyla bağlantı kurup konuşmaktır. veritabanıyla konuşurken entityleri kullanır.
// hangi entityi kullanacağını <> arasında söylüyoruz.

// bizim verdiğimiz işlemleri yapmak için çağırdığımız metodlardan sqller oluşturmak ve bu sqlleri jpa aracılığıyla
// veritabanıyla göndermek veritabanına postgresql driver tarafından gönderilip işlem yapıldıktan sonra dönüş değeriyle birlikte
// bize yine verdiğimiz enity tipinde dönüş sağlamaktır.



import com.workintech.twitter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
