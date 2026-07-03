package com.billingportal.model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The schema has no `tiers` table, so plan details live here as a simple
 * in-memory catalog. If the team later adds a tiers table, this class can
 * be swapped for a TierDAO without changing the servlets much.
 */
public class TierCatalog {

    public static class TierInfo {
        private final String name;
        private final double monthlyPrice;
        private final int apiCallQuota;
        private final int storageQuotaMb;

        public TierInfo(String name, double monthlyPrice, int apiCallQuota, int storageQuotaMb) {
            this.name = name;
            this.monthlyPrice = monthlyPrice;
            this.apiCallQuota = apiCallQuota;
            this.storageQuotaMb = storageQuotaMb;
        }

        public String getName() {
            return name;
        }

        public double getMonthlyPrice() {
            return monthlyPrice;
        }

        public int getApiCallQuota() {
            return apiCallQuota;
        }

        public int getStorageQuotaMb() {
            return storageQuotaMb;
        }
    }

    private static final Map<String, TierInfo> TIERS = new LinkedHashMap<>();

    static {
        TIERS.put("BASIC", new TierInfo("BASIC", 29.00, 1000, 2000));
        TIERS.put("PRO", new TierInfo("PRO", 99.00, 10000, 20000));
        TIERS.put("ENTERPRISE", new TierInfo("ENTERPRISE", 299.00, 100000, 200000));
    }

    private TierCatalog() {
    }

    public static TierInfo get(String tierName) {
        return TIERS.get(tierName);
    }

    public static boolean isValidTier(String tierName) {
        return TIERS.containsKey(tierName);
    }

    public static Map<String, TierInfo> all() {
        return TIERS;
    }
}