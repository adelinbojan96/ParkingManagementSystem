package com.example.implementation;

import com.example.UserRepository;
import com.example.UserService;
import com.example.domain.User;
import com.example.dto.UserCreateDto;
import com.example.dto.UserViewDto;
import com.example.mapper.UserMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, JavaMailSender mailSender, TemplateEngine templateEngine, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = false)
    public void save(UserCreateDto userCreateDto) {
        User user = userMapper.toEntity(userCreateDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserViewDto> getAllUsers() {
        return userRepository.getAll().stream().map(userMapper::toViewDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UserViewDto getByUsername(String username) {
        return userMapper.toViewDto(userRepository.getByUsername(username));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserViewDto> getUsersByFirstname(String firstname) {
        List<User> users = firstname == null ? userRepository.getAll() : userRepository.getByFirstname(firstname);
        return users.stream().map(userMapper::toViewDto).toList();
    }

    private String getResetCode() {
        return "1A2B3C";
    }

    @Override
    public void sendMail(String mail) {
        Context context = new Context();
        String resetCode = getResetCode();
        context.setVariable("code", resetCode);

        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("mailsenderubb@gmail.com", "Mail Sender");
            helper.setTo(mail);
            helper.setSubject("Reset Your Password");

            String content = templateEngine.process("emailtemplate", context);
            helper.setText(content, true);

            // Add inline image
            InputStream imageStream = getClass().getResourceAsStream("/Images/Authentication.png");
            if (imageStream != null) {
                ByteArrayDataSource dataSource = new ByteArrayDataSource(imageStream, "image/png");
                helper.addInline("authImage", dataSource);
            } else {
                throw new RuntimeException("Image not found in resources");
            }

            // Send the email
            mailSender.send(message);

        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
