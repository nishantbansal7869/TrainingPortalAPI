package com.trainingportalapi.trainingportal.repository;

import com.trainingportalapi.trainingportal.domain.User;
import com.trainingportalapi.trainingportal.exceptions.TPAuthException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class UserRepoImpl implements UserRepo{

    private static final String SQL_REGISTER_USER = "INSERT INTO TP_USER(USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD)"
            + " VALUES(NEXTVAL('TP_USERS_SEQ'), ?, ?, ?, ?)";

    private static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT(*) FROM TP_USER WHERE EMAIL = ?";

    private static final String SQL_FIND_BY_ID = "SELECT USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD FROM TP_USER"
            + " WHERE USER_ID = ?";

    private static final String SQL_FIND_BY_EMAIL= "SELECT USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD FROM TP_USER"
            + " WHERE EMAIL = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer registerUser(String firstName, String lastName, String email, String password) throws TPAuthException {
        String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_REGISTER_USER, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, firstName);
                ps.setString(2, lastName);
                ps.setString(3, email);
                ps.setString(4, hashPassword);
                return ps;
            }, keyHolder);
            return (Integer)keyHolder.getKeys().get("USER_ID");
        }
        catch (Exception e){
            throw new TPAuthException("Invalid details, unable to register user");
        }
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        try {
            User user = jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, new Object[]{email}, userRowMapper);
            if (!BCrypt.checkpw(password, user.getPassword())){
                throw new TPAuthException("Invalid password");
            }
            return user;
        }
        catch (EmptyResultDataAccessException e){
            throw new TPAuthException("Invalid email or password");
        }
    }

    @Override
    public User findByUserID(Integer userID) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userID}, userRowMapper);
    }

    @Override
    public Integer getEmailCount(String email) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL, new Object[]{email}, Integer.class);
    }

    private RowMapper<User> userRowMapper = ((rs, rowNum) -> {
        return new User(rs.getInt("USER_ID"),
                rs.getString("FIRST_NAME"),
                rs.getString("LAST_NAME"),
                rs.getString("EMAIL"),
                rs.getString("PASSWORD"));
    });
}
