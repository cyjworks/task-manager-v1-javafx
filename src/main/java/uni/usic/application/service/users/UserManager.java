package uni.usic.application.service.users;

import uni.usic.domain.entity.users.User;
import uni.usic.domain.entity.users.enums.SignInResult;
import uni.usic.domain.entity.users.enums.SignUpResult;

public class UserManager {
    private final UserService userService;

    public UserManager(UserService service) {
        this.userService = service;
    }

    public SignUpResult signUp(String username, String password, String fullName, String email) {
        SignUpResult validationResult = validateSignUp(username, password, fullName, email);
        if (validationResult != SignUpResult.SUCCESS) return validationResult;

        User existUser = userService.findByUsername(username);
        if (existUser != null) return SignUpResult.USERNAME_ALREADY_EXISTS;

        User user = userService.signUp(username, password, fullName, email);
        return user != null ? SignUpResult.SUCCESS : SignUpResult.SERVER_ERROR;
    }

    public SignInResult signIn(String username, String password) {
        SignInResult validationResult = validateSignIn(username, password);
        if (validationResult != SignInResult.SUCCESS) return validationResult;

        System.out.println(username+"/"+password);
        User existUser = userService.findByUsername(username);
        if (existUser == null) return SignInResult.USER_NOT_FOUND;

        if (!existUser.getPassword().equals(password)) return SignInResult.WRONG_PASSWORD;

        return SignInResult.SUCCESS;
    }

    private SignUpResult validateSignUp(String username, String password, String fullName, String email) {
        if (isEmpty(username)) return SignUpResult.USERNAME_REQUIRED;
        if (isEmpty(password)) return SignUpResult.PASSWORD_REQUIRED;
        if (isEmpty(fullName)) return SignUpResult.FULLNAME_REQUIRED;
        if (isEmpty(email)) return SignUpResult.EMAIL_REQUIRED;
        return SignUpResult.SUCCESS;
    }

    private SignInResult validateSignIn(String username, String password) {
        if (isEmpty(username)) return SignInResult.USERNAME_REQUIRED;
        if (isEmpty(password)) return SignInResult.PASSWORD_REQUIRED;
        return SignInResult.SUCCESS;
    }

    private boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public User findByUsername(String username) {
        return userService.findByUsername(username);
    }
}
