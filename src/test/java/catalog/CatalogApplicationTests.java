package catalog;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfSystemProperty(
        named = "RUN_CATALOG_ORACLE_INTEGRATION_TESTS",
        matches = "true"
)
class CatalogApplicationTests {

	@Test
	void contextLoads() {
	}

}
