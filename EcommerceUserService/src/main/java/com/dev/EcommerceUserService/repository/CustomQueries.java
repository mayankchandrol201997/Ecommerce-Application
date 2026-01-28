package com.dev.EcommerceUserService.repository;

public interface CustomQueries {
    String FIND_ACTIVE_SESSION_BY_USER_EMAIL = """
            SELECT s.*
                    FROM session s
                    JOIN user u ON s.user_id = u.id
                    WHERE u.email = :email
                      AND s.session_status = 'ACTIVE'""";
}
