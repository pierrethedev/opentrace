package br.com.opentrace.opentrace.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

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

    public Investigation investigate(String target) {

        // 1. Validar o domínio
        if (!domainValidator.isValid(target)) {

            return new Investigation(
                    target,
                    "INVALID_DOMAIN",
                    "O domínio informado é inválido.",
                    List.of(),
                    List.of(),
                    List.of(),
                    Map.of()
            );
        }

        // 2. Consultar endereços IP
        DnsQueryResult ipResult =
                dnsLookupService.findIpAddresses(target);

        // Extrair os endereços IP
        List<String> ipAddresses = ipResult.getRecords();

        // 3. Consultar registros MX e NS
        DnsQueryResult mxResult =
                dnsRecordsService.findMxRecords(target);

        DnsQueryResult nsResult =
                dnsRecordsService.findNsRecords(target);

        // 4. Extrair os registros
        List<String> mxRecords = mxResult.getRecords();
        List<String> nsRecords = nsResult.getRecords();

        // 5. Organizar os status individuais
        Map<String, String> dnsStatus = Map.of(
                "ip", ipResult.getStatus(),
                "mx", mxResult.getStatus(),
                "ns", nsResult.getStatus()
        );

        // 6. Determinar o status geral
        boolean hasResults =
                !ipAddresses.isEmpty()
                || !mxRecords.isEmpty()
                || !nsRecords.isEmpty();

        String status = hasResults
                ? "COMPLETED"
                : "NO_RESULTS";

        String message = hasResults
                ? "Investigação DNS concluída."
                : "Nenhum registro DNS foi encontrado.";

        // 7. Retornar a investigação
        return new Investigation(
                target,
                status,
                message,
                ipAddresses,
                mxRecords,
                nsRecords,
                dnsStatus
        );
    }
}