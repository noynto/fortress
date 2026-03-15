package me.noynto.fortress.infrastructure.controller.view;

import io.helidon.http.HeaderNames;
import io.helidon.webserver.http.HttpRules;
import io.helidon.webserver.http.HttpService;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;
import me.noynto.fortress.application.transactions.query.handler.*;
import me.noynto.fortress.infrastructure.controller.view.config.TemplateRenderer;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Contrôleur pour le rendu HTML des transactions avec JTE
 */
public record DashboardViewController(
        CalculateBalance calculateBalance,
        CalculateForecastBalance calculateForecastBalance,
        CalculatePendingCredits calculatePendingCredits,
        CalculatePendingDebits calculatePendingDebits,
        CountPending countPending,
        CountApproved countApproved,
        CountRejected countRejected,
        TemplateRenderer templateRenderer
) implements HttpService {
    @Override
    public void routing(HttpRules rules) {
        rules
                // Pages
                .get("/", this::showDashboard);
    }

    // ============= PAGES (Queries) =============

    private void showDashboard(ServerRequest req, ServerResponse res) {
        BigDecimal balance = this.calculateBalance().handle();
        BigDecimal forecastBalance = this.calculateForecastBalance.handle();
        BigDecimal pendingCredits = this.calculatePendingCredits.handle();
        BigDecimal pendingDebits = this.calculatePendingDebits.handle();
        Long pendingCount = this.countPending.handle();
        Long approvedCount = this.countApproved.handle();
        Long rejectedCount = this.countRejected.handle();
        Map<String, Object> params = new HashMap<>();
        params.put("balance", balance);
        params.put("forecastBalance", forecastBalance);
        params.put("pendingCredits", pendingCredits);
        params.put("pendingDebits", pendingDebits);
        params.put("pendingCount", pendingCount);
        params.put("approvedCount", approvedCount);
        params.put("rejectedCount", rejectedCount);
        String html = templateRenderer.render("page/dashboard.jte", params);
        res.header(HeaderNames.CONTENT_TYPE, "text/html");
        res.send(html);
    }

}