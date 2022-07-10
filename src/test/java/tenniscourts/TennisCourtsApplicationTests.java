package tenniscourts;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tenniscourts.controllers.RequestController;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TennisCourtsApplicationTests {

	@Autowired
	private RequestController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
