package dev.marko;

import java.util.List;

public class DashboardConfig {

    public DataSource datasource;
    public Dashboard dashboard;
    public List<PanelConfig> panels;

    public static class DataSource {
        public String uid;
        public String name;
    }

    public static class Dashboard {
        public String title;
        public String uid;
        public String refresh;
        public String time_from;
        public String time_to;
        public List<String> tags;
    }

    public static class PanelConfig {
        public String title;
        public String type;
        public String expr;
        public String legend;
        public String refId;
        public String unit;
        public Double min;
        public Double max;
        public Grid grid;
    }

    public static class Grid {
        public int x;
        public int y;
        public int w;
        public int h;
    }
}
