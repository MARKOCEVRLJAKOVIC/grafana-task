package dev.marko;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.grafana.foundation.dashboard.*;
import com.grafana.foundation.prometheus.DataqueryBuilder;

import java.util.List;


public class CpuUsageDashboard {

    public static Dashboard build() {
//        DataSourceRef prometheus = new DataSourceRef.Builder()
//                .uid("Prometheus")
//                .build();

        DataSourceRef prometheus = new DataSourceRef("Prometheus", "prometheus");

        Panel timeseries = new PanelBuilder()
                .title("CPU usage — timeseries (avg_over_time 5m)")
                .datasource(prometheus)
                .unit("percent")
                .min(0.0)
                .max(100.0)
                .gridPos(getGridPos(0, 0, 24, 9))
                .withTarget(
                        new DataqueryBuilder()
                                .expr("avg_over_time(cpu_usage[5m])")
                                .legendFormat("avg_5m {{instance}}")
                                .refId("A")
                )
                .build();

        Panel overallAvg = new PanelBuilder()
                .title("CPU usage — overall average (5m)")
                .datasource(prometheus)
                .unit("percent")
                .min(0.0)
                .max(100.0)
                .gridPos(getGridPos(0, 9, 6, 4))
                .withTarget(
                        new DataqueryBuilder()
                                .expr("avg(avg_over_time(cpu_usage[5m]))")
                                .legendFormat("overall avg")
                                .refId("B")
                )
                .build();

        Panel byInstanceTable = new PanelBuilder()
                .title("CPU usage — by instance (latest 5m avg)")
                .datasource(prometheus)
                .gridPos(getGridPos(6, 9, 18, 8))
                .withTarget(
                        new DataqueryBuilder()
                                .expr("avg_over_time(cpu_usage[5m])")
                                .legendFormat("{{instance}}")
                                .refId("C")
                )
                .build();

        Dashboard dashboard = new DashboardBuilder("Dashboard")
                .title("Demo - cpu_usage")
                .uid("cpu-usage-demo")
                .refresh("5s")
                .time(new DashboardDashboardTimeBuilder()
                        .from("now-5m")
                        .to("now"))
                .withPanel(new PanelBuilder()
                        .title("CPU usage — timeseries (avg_over_time 5m)")
                        .datasource(prometheus)
                        .unit("percent")
                        .min(0.0)
                        .max(100.0)
                        .gridPos(new GridPos(9, 24, 0, 0, false))
                        .withTarget(
                                new DataqueryBuilder()
                                        .expr("avg_over_time(cpu_usage[5m])")
                                        .legendFormat("avg_5m {{instance}}")
                                        .refId("A")
                        )
                )
                .withPanel(new PanelBuilder()
                        .title("CPU usage — overall average (5m)")
                        .datasource(prometheus)
                        .unit("percent")
                        .min(0.0)
                        .max(100.0)
                        .gridPos(new GridPos(4, 6, 0, 9, false))
                        .withTarget(
                                new DataqueryBuilder()
                                        .expr("avg(avg_over_time(cpu_usage[5m]))")
                                        .legendFormat("overall avg")
                                        .refId("B")
                        )
                )
                .withPanel(new PanelBuilder()
                        .title("CPU usage — by instance (latest 5m avg)")
                        .datasource(prometheus)
                        .gridPos(new GridPos(8, 18, 6, 9, false))
                        .withTarget(
                                new DataqueryBuilder()
                                        .expr("avg_over_time(cpu_usage[5m])")
                                        .legendFormat("{{instance}}")
                                        .refId("C")
                        )
                )
                .tags(List.of("demo", "cpu_usage"))
                .build();

        return dashboard;
    }

    private static GridPos getGridPos(int x, int y, int w, int h) {
        return new GridPos(x, y, w, h, false);
    }

    public static void main(String[] args) throws JsonProcessingException {
        Dashboard dashboard = build();
        System.out.println(dashboard.toJSON());
    }

}
