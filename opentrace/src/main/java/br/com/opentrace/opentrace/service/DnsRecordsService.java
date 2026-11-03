package br.com.opentrace.opentrace.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.MXRecord;
import org.xbill.DNS.NSRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

@Service
public class DnsRecordsService {

    public DnsQueryResult findMxRecords(String domain) {
        return executeLookup(domain, Type.MX);
    }

    public DnsQueryResult findNsRecords(String domain) {
        return executeLookup(domain, Type.NS);
    }

    private DnsQueryResult executeLookup(String domain, int recordType) {

        List<String> results = new ArrayList<>();

        try {
            Lookup lookup = new Lookup(domain, recordType);
            Record[] records = lookup.run();

            int resultCode = lookup.getResult();

            if (resultCode == Lookup.SUCCESSFUL && records != null) {

                for (Record record : records) {

                    if (record instanceof MXRecord mx) {
                        results.add(
                            mx.getPriority() + " " + mx.getTarget()
                        );
                    }

                    if (record instanceof NSRecord ns) {
                        results.add(ns.getTarget().toString());
                    }
                }

                return new DnsQueryResult("SUCCESSFUL", results);
            }

            if (resultCode == Lookup.TYPE_NOT_FOUND) {
                return new DnsQueryResult("NO_RECORDS", results);
            }

            if (resultCode == Lookup.HOST_NOT_FOUND) {
                return new DnsQueryResult("HOST_NOT_FOUND", results);
            }

            if (resultCode == Lookup.TRY_AGAIN) {
                return new DnsQueryResult("TRY_AGAIN", results);
            }

            return new DnsQueryResult("DNS_ERROR", results);

        } catch (TextParseException e) {
            return new DnsQueryResult("INVALID_QUERY", results);
        }
    }
}