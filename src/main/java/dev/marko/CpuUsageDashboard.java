package dev.marko;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.grafana.foundation.dashboard.*;
import com.grafana.foundation.prometheus.DataqueryBuilder;

import java.io.InputStream;

public class CpuUsageDashboard {

    public static Dashboard build() throws Exception {
        var config = loadConfig();

        var prometheus = new DataSourceRef(
                config.datasource.uid,
                config.datasource.name
        );

        var builder = new DashboardBuilder("Dashboard")
                .title(config.dashboard.title)
                .uid(config.dashboard.uid)
                .refresh(config.dashboard.refresh)
                .time(new DashboardDashboardTimeBuilder()
                        .from(config.dashboard.time_from)
                        .to(config.dashboard.time_to))
                .tags(config.dashboard.tags);

        for (DashboardConfig.PanelConfig p : config.panels) {

            Panel panel = new PanelBuilder()
                    .title(p.title)
                    .type(p.type)
                    .datasource(prometheus)
                    .unit(p.unit)
                    .min(p.min)
                    .max(p.max)
                    .gridPos(new GridPos(p.grid.h, p.grid.w, p.grid.x, p.grid.y, false))
                    .withTarget(new DataqueryBuilder()
                            .expr(p.expr)
                            .legendFormat(p.legend)
                            .refId(p.refId))
                    .build();
            builder.withPanel(() -> panel);
        }

        return builder.build();
    }

    private static DashboardConfig loadConfig() throws Exception {
        var mapper = new ObjectMapper(new YAMLFactory());
        try (InputStream in = CpuUsageDashboard.class.getResourceAsStream("/dashboard-config.yaml")) {
            return mapper.readValue(in, DashboardConfig.class);
        }
    }

    public static void main(String[] args) throws Exception {
        var dashboard = build();
        System.out.println(dashboard.toJSON());
    }
}
