
package br.com.opentrace.opentrace.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.opentrace.opentrace.Investigation;
import br.com.opentrace.opentrace.validation.DomainValidator;

@Service
public class InvestigationService {

    private final DomainValidator domainValidator;
    private final DnsLookupService dnsLookupService;
    private final DnsRecordsService dnsRecordsService;

    public InvestigationService(
            DomainValidator domainValidator,
            DnsLookupService dnsLookupService,
            DnsRecordsService dnsRecordsService) {

        this.domainValidator = domainValidator;
        this.dnsLookupService = dnsLookupService;
        this.dnsRecordsService = dnsRecordsService;
    }

    public Investigation prepareInvestigation(String target) {

        if (!domainValidator.isValid(target)) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Informe somente um domínio válido, como example.com."
            );
        }

        target = target.trim();

        List<String> ipAddresses =
            dnsLookupService.findIpAddresses(target);

        List<String> mxRecords =
            dnsRecordsService.findMxRecords(target);

        List<String> nsRecords =
            dnsRecordsService.findNsRecords(target);

        String status = (
            ipAddresses.isEmpty()
            && mxRecords.isEmpty()
            && nsRecords.isEmpty()
        ) ? "NO_RESULTS" : "COMPLETED";

        String message = status.equals("COMPLETED")
            ? "Investigação DNS concluída."
            : "Nenhum registro DNS foi encontrado.";

        return new Investigation(
            target,
            status,
            message,
            ipAddresses,
            mxRecords,
            nsRecords
        );
    }
}
