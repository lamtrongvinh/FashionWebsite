package comcircus.fashionweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import comcircus.fashionweb.model.person.user.PasswordResetOtp;
import comcircus.fashionweb.repository.PasswordResetOtpRepository;

@Service
public class PasswordResetOtpService {
    @Autowired
    private PasswordResetOtpRepository passwordResetOtpRepository;

    public PasswordResetOtp getPasswordResetOtpByUserID(Long id) {
       List<PasswordResetOtp> list = passwordResetOtpRepository.findAll();
       for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUser_id() - id == 0) {
                return list.get(i);
            }
       }

       return null;
    }

    public void deletePasswordResetOtp(PasswordResetOtp passwordResetOtp) {
        passwordResetOtpRepository.delete(passwordResetOtp);
    }
}
