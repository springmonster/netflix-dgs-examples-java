package com.webflux.datafetcher;

import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.exceptions.QueryException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class SecureDataFetcherTest {

    @Autowired
    private DgsQueryExecutor dgsQueryExecutor;

    @Test
    void secureNone() {
        String result = dgsQueryExecutor.executeAndExtractJsonPathAsObject(
                " { secureNone }",
                "data.secureNone",
                String.class);

        Assertions.assertThat(result).isEqualTo("hello everyone!");
    }

    @Test
    void secureUserWithNone() {
        assertThrows(QueryException.class, () -> {
            String result = dgsQueryExecutor.executeAndExtractJsonPathAsObject(
                    " { secureUser }",
                    "data.secureUser",
                    String.class);

            Assertions.assertThat(result).isEqualTo("hello user or admin!");
        });
    }

    @Test
    @WithMockUser(roles = {"NONE"})
    void secureUserWithWrongRole() {
        assertThrows(QueryException.class, () -> {
            String result = dgsQueryExecutor.executeAndExtractJsonPathAsObject(
                    " { secureUser }",
                    "data.secureUser",
                    String.class);

            Assertions.assertThat(result).isEqualTo("hello user or admin!");
        });
    }

    @Disabled
    @Test
    @WithMockUser(username = "", password = "")
    void secureUserWithWrongUser() {
        assertThrows(QueryException.class, () -> {
            String result = dgsQueryExecutor.executeAndExtractJsonPathAsObject(
                    " { secureUser }",
                    "data.secureUser",
                    String.class);

            Assertions.assertThat(result).isEqualTo("hello user or admin!");
        });
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    void secureUserWithUser() {
        String result = dgsQueryExecutor.executeAndExtractJsonPathAsObject(
                " { secureUser }",
                "data.secureUser",
                String.class);

        Assertions.assertThat(result).isEqualTo("hello user or admin!");
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void secureUserWithAdmin() {
        String result = dgsQueryExecutor.executeAndExtractJsonPathAsObject(
                " { secureUser }",
                "data.secureUser",
                String.class);

        Assertions.assertThat(result).isEqualTo("hello user or admin!");
    }

    @Disabled
    @Test
    void secureAdmin() {
        String result = dgsQueryExecutor.executeAndExtractJsonPathAsObject(
                " { secureAdmin }",
                "data.secureAdmin",
                String.class);

        Assertions.assertThat(result).isEqualTo("hello admin!");
    }
}
