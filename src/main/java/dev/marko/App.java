package dev.marko;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.grafana.foundation.common.Constants;
import com.grafana.foundation.dashboard.*;
import com.grafana.foundation.logs.PanelBuilder;
import com.grafana.foundation.loki.DataqueryBuilder;

import java.lang.annotation.Target;
import java.util.List;

public class App {

    private static final String prometheusName = "Prometheus";

    public static void main(String[] args) {

    Dashboard dashboard = new DashboardBuilder("Sample Dashboard")
            .uid("generated-from-java").tags(List.of("generated", "from", "java")).
            refresh("1m").time(new DashboardDashboardTimeBuilder().
                    from("now-30m").
                    to("now")
            ).
            timezone(Constants.TimeZoneBrowser).
            withRow(new RowBuilder("Overview")).
            withPanel(new PanelBuilder().
                    title("Network Received").
                    unit("bps").
                    min(0.0).
                    withTarget(new DataqueryBuilder().
                            expr("rate(node_network_receive_bytes_total{job=\"integrations/raspberrypi-node\", device!=\"lo\"}[$__rate_interval]) * 8").
                            legendFormat("{{ device }}")
                    )
            ).build();


    try {
        System.out.println(dashboard.toJSON());
    } catch (JsonProcessingException e) {
        e.printStackTrace();
    }

    }
}