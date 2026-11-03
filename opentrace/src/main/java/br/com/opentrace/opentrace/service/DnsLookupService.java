package br.com.opentrace.opentrace.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class DnsLookupService {

    public DnsQueryResult findIpAddresses(String domain) {

        List<String> results = new ArrayList<>();

        try {

            InetAddress[] addresses =
                    InetAddress.getAllByName(domain);

            results = java.util.Arrays.stream(addresses)
                    .map(InetAddress::getHostAddress)
                    .distinct()
                    .toList();

            if (results.isEmpty()) {
                return new DnsQueryResult("NO_RECORDS", results);
            }

            return new DnsQueryResult("SUCCESSFUL", results);

        } catch (UnknownHostException e) {

            return new DnsQueryResult("HOST_NOT_FOUND", results);
        }
    }
}