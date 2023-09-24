package com.yourname.hexcrypt.controller;

import com.yourname.hexcrypt.service.EncryptionService;
import com.yourname.hexcrypt.service.DecryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HexController {

    private final EncryptionService encryptionService;
    private final DecryptionService decryptionService;

    @Autowired
    public HexController(EncryptionService encryptionService, DecryptionService decryptionService) {
        this.encryptionService = encryptionService;
        this.decryptionService = decryptionService;
    }

    @RequestMapping("/")
    public String home() {
        return "home";
    }

    @PostMapping("/encrypt")
    public String encrypt(@RequestParam String input, Model model) {
        String result = encryptionService.encrypt(input);
        model.addAttribute("result", result);
        return "result";
    }

    @PostMapping("/decrypt")
    public String decrypt(@RequestParam String input, Model model) {
        String result = decryptionService.decrypt(input);
        model.addAttribute("result", result);
        return "result";
    }
}
