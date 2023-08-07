package grandchild.grandchild;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing //(modifyOnCreate =false)를 넣으면 저장시점에 저장데이터만 입력
@SpringBootApplication
public class GrandchildApplication {

	public static void main(String[] args) {
		SpringApplication.run(GrandchildApplication.class, args);
	}

}
