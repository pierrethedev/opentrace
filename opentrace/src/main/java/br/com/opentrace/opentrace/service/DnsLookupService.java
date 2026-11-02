package br.com.opentrace.opentrace.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class DnsLookupService {

    public List<String> findIpAddresses(String domain) {
        try {
            
        InetAddress[] addresses =
        InetAddress.getAllByName(domain);

        return Arrays.stream(addresses)
            .map(InetAddress::getHostAddress)
            .distinct()
            .toList();

        } catch (UnknownHostException e) {
            return List.of();
        }
    }
    
}
