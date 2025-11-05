package dev.marko;

public class Metric {
    private final String name;
    private final String query;

    public Metric(String name, String query) {
        this.name = name;
        this.query = query;
    }

    public static Metric fromEnv() {
        String metricName = System.getenv().getOrDefault("METRIC_NAME", "cpu_usage");
        String expr = String.format("avg_over_time(%s[5m])", metricName);
        return new Metric(metricName, expr);
    }

    public String getQuery() {
        return query;
    }

    public String getName() {
        return name;
    }
}
