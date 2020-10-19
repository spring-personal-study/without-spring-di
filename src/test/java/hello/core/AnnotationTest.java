package hello.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AnnotationTest {

    int count = 0;

    @Test
    @DisplayName("주석 무력화 테스트")
    public void meetsAnnotationIgnore() throws Exception {
        // setup resource
        int expectedCount = 1;
        // when resource works below.
        onClick();
        // verify.
        assertThat(expectedCount).isEqualTo(count);
    }

    private void onClick() {
        // \u000a count++;
        /*   \u000d count++; */
    }
}
