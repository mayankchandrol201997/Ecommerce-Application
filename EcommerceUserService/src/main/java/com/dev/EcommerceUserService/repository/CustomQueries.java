package com.dev.EcommerceUserService.repository;

public interface CustomQueries {
    String FIND_ACTIVE_SESSION_BY_USER_EMAIL = """
            SELECT t.*
                    FROM token t
                    JOIN user u ON t.user_id = u.id
                    WHERE u.email = :email
                      AND t.token_status = 'ACTIVE'""";
}
