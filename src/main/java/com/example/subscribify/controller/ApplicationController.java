package com.example.subscribify.controller;

import com.example.subscribify.domain.AuthUser;
import com.example.subscribify.dto.UpdateApplicationDto;
import com.example.subscribify.dto.controller.CreateApplicationDto;
import com.example.subscribify.entity.Application;
import com.example.subscribify.entity.DuplicatePaymentOption;
import com.example.subscribify.entity.User;
import com.example.subscribify.service.application.ApplicationService;
import com.example.subscribify.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;
    private final PaymentService paymentService;

    @GetMapping("/applications")
    public String applicationListForm(@AuthUser User user, Model model) {
        List<Application> applications = applicationService.findApplicationsByUserId(user.getId());
        model.addAttribute("applications", applications);
        return "application/list";
    }

    @GetMapping("/applications/{applicationId}")
    public String applicationDetailForm(@PathVariable Long applicationId, Model model) {
        Application application = applicationService.getApplicationWithSubscriptionPlan(applicationId);

        Long totalAmount = paymentService.sumAmountByApplicationId(applicationId);

        model.addAttribute("app", application);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("subscriptionPlans", application.getSubscriptionPlans());
        return "application/detail";
    }

    @GetMapping("/applications/{applicationId}/options")
    public String optionsUpdateForm(@PathVariable Long applicationId, Model model) {
        Application application = applicationService.getApplicationWithSubscriptionPlan(applicationId);

        model.addAttribute("app", application);
        return "application/options";
    }

    @PostMapping("/applications/{applicationId}/options")
    public String optionsUpdate(@PathVariable Long applicationId,
                                @RequestParam("duplicatePaymentOption") DuplicatePaymentOption duplicatePaymentOption) {
        Application application = applicationService.getApplicationWithSubscriptionPlan(applicationId);

        UpdateApplicationDto updateApplicationDto = new UpdateApplicationDto(duplicatePaymentOption);

        applicationService.updateOptions(application, updateApplicationDto);
        return "redirect:/applications/" + applicationId;
    }

    @GetMapping("/applications/enroll")
    public String enrollApplicationForm(Model model) {
        model.addAttribute("createApplicationDto", new CreateApplicationDto());
        return "application/enroll";
    }

    @PostMapping("/applications/enroll")
    public String enrollApplication(@ModelAttribute @Validated CreateApplicationDto createApplicationDto, @AuthUser User user) {
        applicationService.createApplication(createApplicationDto, user);
        return "redirect:/applications";
    }


    @PostMapping("/applications/keys/generate")
    public String generateApiKey(@RequestParam("applicationId") Long applicationId, @AuthUser User user, Model model) {
        Application updatedApplication = applicationService.updateKeys(applicationId, user);
        model.addAttribute("app", updatedApplication);
        return "redirect:/applications/" + applicationId;
    }

}
