package comcircus.fashionweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StoreController {

    @GetMapping("/")
    public String getHomeScreen() {
    
       return "index";
    }

    @GetMapping("/contact")
    public String getContactScreen() {
        return "contact";
    }
    
}
