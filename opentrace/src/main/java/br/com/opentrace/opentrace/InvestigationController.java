
package br.com.opentrace.opentrace;

import br.com.opentrace.opentrace.service.InvestigationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InvestigationController {

    private final InvestigationService investigationService;

    public InvestigationController(
            InvestigationService investigationService) {
        this.investigationService = investigationService;
    }

    @GetMapping("/api/investigations")
    public Investigation investigate(@RequestParam String target) {
        return investigationService.prepareInvestigation(target);
    }
}
