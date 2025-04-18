package jpa.systemstudent.service.serviceImpl;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        // Trả về tên người dùng hiện tại
        return Optional.of("admin");
    }
}
