package com.nbu.medicalrecords;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles({"test", "test-local"})
class MedicalrecordsApplicationTests {

	@Test
	void contextLoads() {
	}
}
