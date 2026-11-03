package br.com.opentrace.opentrace.service;

import java.util.List;

public class DnsQueryResult {

    private final String status;
    private final List<String> records;

    public DnsQueryResult(String status, List<String> records) {
        this.status = status;
        this.records = records;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getRecords() {
        return records;
    }
}