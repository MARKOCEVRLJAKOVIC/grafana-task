# CPU Usage Dashboard — Grafana Observability-as-Code

This project demonstrates generating a Grafana dashboard programmatically 
using the Grafana Foundation SDK in Java. It visualizes the `cpu_usage` metric
from Prometheus in different panel types: timeseries, stat, and table.

## Requirements

- Java 17+ 
- Gradle
- Prometheus datasource in Grafana with UID `Prometheus`

## Usage

1. Build and run the project:

./gradlew run

## JSON for Grafana:

1. Open Grafana
2. Go to Dashboards → Import
3. Upload dashboards/cpu_usage_dashboard.json
4. Make sure the Prometheus datasource is configured correctly

## Notes

The dashboard includes panels for timeseries visualization, single-stat metrics, and tables.
Dashboards are generated programmatically and can be version-controlled.
You can modify dashboard-config.yaml to customize dashboard panels or queries.

## References

https://github.com/grafana/demo-prometheus-and-grafana-alerts
https://github.com/grafana/foundation-sdk-java
