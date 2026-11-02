
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

    public List<String> findMxRecords(String domain) {
        List<String> results = new ArrayList<>();

        try {
            Lookup lookup = new Lookup(domain, Type.MX);
            Record[] records = lookup.run();

            if (records == null || lookup.getResult() != Lookup.SUCCESSFUL) {
                return results;
            }

            for (Record record : records) {
                MXRecord mx = (MXRecord) record;

                results.add(
                    mx.getPriority() + " " + mx.getTarget()
                );
            }

        } catch (TextParseException e) {
            return results;
        }

        return results;
    }

    public List<String> findNsRecords(String domain) {
        List<String> results = new ArrayList<>();

        try {
            Lookup lookup = new Lookup(domain, Type.NS);
            Record[] records = lookup.run();

            if (records == null || lookup.getResult() != Lookup.SUCCESSFUL) {
                return results;
            }

            for (Record record : records) {
                NSRecord ns = (NSRecord) record;
                results.add(ns.getTarget().toString());
            }

        } catch (TextParseException e) {
            return results;
        }

        return results;
    }
}

