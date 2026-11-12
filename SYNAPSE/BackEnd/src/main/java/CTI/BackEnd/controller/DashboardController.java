package CTI.BackEnd.controller;

import java.util.List;
import java.util.Map;

import CTI.BackEnd.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public Map<String, Object> obter(
            @RequestParam(required = false) Long loteId
    ) {
        return dashboardService.obter(loteId);
    }

    @GetMapping("/lotes")
    public List<Map<String, Object>> listarLotes() {
        return dashboardService.listarLotes();
    }
}
