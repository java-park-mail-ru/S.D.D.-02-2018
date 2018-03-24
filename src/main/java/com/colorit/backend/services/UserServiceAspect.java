package com.colorit.backend.services;

import com.colorit.backend.services.responses.UserServiceResponse;
import com.colorit.backend.services.statuses.UserServiceStatus;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Aspect
@Component
public class UserServiceAspect {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceAspect.class);
    @Around(value = "execution (* com.colorit.backend.services.UserService.* (..)))")
    public UserServiceResponse errorHandler(ProceedingJoinPoint procPoint) {
        try {
            LOGGER.info("Call {}", procPoint.getSignature().getName());
            return (UserServiceResponse) procPoint.proceed();
        } catch (DataIntegrityViolationException dIVEx) {
            SQLException sqlEx = (SQLException)dIVEx.getCause().getCause();
            LOGGER.error("Constraint error: {}", dIVEx.getLocalizedMessage());
            if (sqlEx.getLocalizedMessage().contains("nickname_constraint")) {
                return  new UserServiceResponse(UserServiceStatus.CONFLICT_NAME_STATE);
            } else {
                return new UserServiceResponse(UserServiceStatus.CONFLICT_EMAIL_STATE);
            }
        } catch (DataAccessException dAEx) {
            LOGGER.error("DB Error {}", dAEx.getLocalizedMessage());
            return new UserServiceResponse(UserServiceStatus.DB_ERROR_STATE);
        } catch (Throwable th) {
            LOGGER.error("Other Error {}", th.getLocalizedMessage());
            return new UserServiceResponse(UserServiceStatus.DB_ERROR_STATE);
        }
    }
}
