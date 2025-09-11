package ua.edu.ukma.event_management_system.aop.rate_limit;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class RateLimitAspect {

    private final Map<String, UserRequestInfo> requestCounts = new ConcurrentHashMap<>();

    @Before("@annotation(rateLimit)")
    public void rateLimit(RateLimit rateLimit) throws Exception {
        String userId = getCurrentUserId();
        if (userId == null) {
            throw new Exception("User is not authenticated");
        }

        int maxRequests = rateLimit.maxRequests();
        UserRequestInfo userRequestInfo = requestCounts.computeIfAbsent(userId, k -> new UserRequestInfo());
        long currentTime = Instant.now().getEpochSecond();

        if (currentTime - userRequestInfo.startTime >= 60) {
            userRequestInfo.requestCount = 0;
            userRequestInfo.startTime = currentTime;
        }

        if (userRequestInfo.requestCount >= maxRequests) {
            throw new Exception("Exceeded the rate limit for user " + userId);
        }

        userRequestInfo.requestCount++;
    }

    private String getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails details) {
            return (details).getUsername();
        } else {
            return null;
        }
    }

    private static class UserRequestInfo {
        long startTime = Instant.now().getEpochSecond();
        int requestCount = 0;
    }
}


