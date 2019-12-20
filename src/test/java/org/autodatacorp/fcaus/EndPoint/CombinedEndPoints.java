package org.autodatacorp.fcaus.EndPoint;

public enum CombinedEndPoints {
    configurationCompare("/configuration-compare/compare"),
    competitiveCompare("/competitive-compare/compare"),
    vehicleCompare("/vehicle-compare/compare");

    private final String path;

    CombinedEndPoints(String path)
    {
        this.path = path;
    }

    public String path()
    {
        return path;
    }
}