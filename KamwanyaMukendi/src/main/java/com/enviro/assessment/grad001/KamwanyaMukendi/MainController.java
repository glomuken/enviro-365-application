package com.enviro.assessment.grad001.KamwanyaMukendi;

import com.enviro.assessment.grad001.KamwanyaMukendi.model.Product;
import com.enviro.assessment.grad001.KamwanyaMukendi.model.Profile;
import com.enviro.assessment.grad001.KamwanyaMukendi.repositories.ProductRepository;
import com.enviro.assessment.grad001.KamwanyaMukendi.services.ClientService;
import com.enviro.assessment.grad001.KamwanyaMukendi.services.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.enviro.assessment.grad001.KamwanyaMukendi.repositories.ProfileRepository;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String email, @RequestParam String password, Model model) {
        if (clientService.validateLogin(email, password)) {
            // Login successful, redirect to the profile page with the user's email as a path variable
            return "redirect:/profile/" + email;
        } else {
            // Login failed, add an error message
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        model.addAttribute("userRegistrationDto", userRegistrationDto);
        return "sign_up";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute UserRegistrationDto userRegistrationDto) {
        // Register the user and create corresponding entries in the Client and Profile tables
        clientService.registerUser(userRegistrationDto);
        return "redirect:/login";
    }


    @GetMapping("/profile/{email}")
    public String showProfile(@PathVariable String email, Model model) {
        // Retrieve the user's profile information from the database based on the provided email
        Profile userProfile = profileRepository.findByEmail(email);

        if (userProfile != null) {
            // Add the user's profile information to the model
            model.addAttribute("user", userProfile);
            return "profile";
        } else {
            // Handle the case where the user's profile is not found
            return "redirect:/error";
        }
    }
    @GetMapping("/view-products/{email}")
    public String viewProducts(@PathVariable String email, Model model) {
        // Retrieve the user's profile information from the database based on the provided email
        Profile userProfile = profileRepository.findByEmail(email);

        if (userProfile != null) {
            // Add the user's profile information to the model
            model.addAttribute("user", userProfile);
            return "product_view";
        } else {
            // Handle the case where the user's profile is not found
            return "redirect:/error";
        }
    }

    @GetMapping("/withdraw/{productId}")
    public String showWithdrawForm(@PathVariable Long productId, Model model) {
        // Retrieve the product information based on the provided productId
        Product product = productService.getProductById(productId);

        if (product != null) {
            // Add the product information to the model
            model.addAttribute("product", product);
            model.addAttribute("userRegistrationDto", new UserRegistrationDto());  // Initialize an empty DTO for the form
            return "withdraw_form";
        } else {
            // Handle the case where the product is not found
            // You can redirect to an error page or handle it as needed
            return "redirect:/error";
        }
    }


    @PostMapping("/withdraw/{productId}")
    public ResponseEntity<Object> processWithdrawal(
            @PathVariable Long productId,
            @ModelAttribute UserRegistrationDto userRegistrationDto,
            Model model) {

        // Retrieve the product information based on the provided productId
        Product product = productService.getProductById(productId);

        if (product != null) {
            // Additional validation checks
            if ("retirement".equalsIgnoreCase(product.getType()) && userRegistrationDto.getAge() < 65) {
                // If product type is retirement and age < 65, return validation error
                return ResponseEntity.badRequest().body("Validation Error: You cannot withdraw from a retirement product if your age is less than 65.");
            }

            double withdrawalAmount = userRegistrationDto.getAmount();
            double currentBalance = product.getCurrentBalance();

            if (withdrawalAmount > currentBalance || withdrawalAmount > 0.9 * currentBalance) {
                // If withdrawal amount > current balance or > 90% of current balance, return validation error
                return ResponseEntity.badRequest().body("Validation Error: Withdrawal amount exceeds current balance or is more than 90% of current balance.");
            }

            // Perform withdrawal logic (subtract the withdrawal amount from the current balance)
            double previousBalance = currentBalance;
            double newBalance = previousBalance - withdrawalAmount;

            // Update the product in the database
            product.setCurrentBalance(newBalance);

            // Create a new WithdrawalNotice
            WithdrawalNotice withdrawalNotice = new WithdrawalNotice();
            withdrawalNotice.setWithdrawalAmount(withdrawalAmount);
            withdrawalNotice.setWithdrawalDate(userRegistrationDto.getDate());
            withdrawalNotice.setProduct(product);

            // Add the withdrawal notice to the product
            product.addWithdrawalNotice(withdrawalNotice);

            // Save the product to update the withdrawal history
            productRepository.save(product);

            // Prepare data for the response
            WithdrawalResponse withdrawalResponse = new WithdrawalResponse();
            withdrawalResponse.setPreviousBalance(previousBalance);
            withdrawalResponse.setWithdrawalAmount(withdrawalAmount);
            withdrawalResponse.setCurrentBalance(newBalance);
            withdrawalResponse.setEmail(product.getProfile().getEmail());

            return ResponseEntity.ok(withdrawalResponse);
        } else {
            // Handle the case where the product is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }
    }


    @GetMapping("/edit-info")
    public String showEditInfoForm(@ModelAttribute UserRegistrationDto userRegistrationDto, Model model) {

        model.addAttribute("userRegistrationDto", userRegistrationDto);
        return "edit_info";
    }

    @PostMapping("/update-info")
    public String processUpdateInfo(@ModelAttribute UserRegistrationDto updatedUserDto, Model model) {

        clientService.updateUser(updatedUserDto);
        model.addAttribute("user", updatedUserDto);
        return "redirect:/profile/" + updatedUserDto.getEmail();
    }
    @GetMapping("/contact")
    public String showContact(){
        return "contact_form";
    }
    @PostMapping("/contact")
    public String postContact(){
        return "redirect:/";
    }
    @GetMapping("/withdraw-history/{productId}")
    public String showWithdrawalHistory(@PathVariable Long productId, Model model) {
        Product product = productService.getProductById(productId);

        if (product != null) {
            model.addAttribute("product", product);
            return "withdrawal_history";
        } else {
            return "redirect:/error";
        }
    }
    @GetMapping("/download-notices/{productId}")
    public void downloadWithdrawalNotices(@PathVariable Long productId, HttpServletResponse response) throws IOException, IOException {
        Product product = productService.getProductById(productId);

        if (product != null) {
            List<WithdrawalNotice> withdrawalNotices = product.getWithdrawalNotices();

            // Generate CSV content
            String csvContent = "Withdrawal Amount     ,     Withdrawal Date\n";
            for (WithdrawalNotice notice : withdrawalNotices) {
                csvContent += notice.getWithdrawalAmount() + "    ,    " + notice.getWithdrawalDate() + "\n";
            }

            // Set response headers
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=withdrawal_notices.csv");

            // Write CSV content to response
            PrintWriter writer = response.getWriter();
            writer.write(csvContent);
            writer.close();
        }
    }
}
