
package br.com.opentrace.opentrace;

import java.util.List;

public class Investigation {

    private final String target;
    private String status;
    private String message;
    private List<String> ipAddresses;
    private List<String> mxRecords;
    private List<String> nsRecords;

    public Investigation(
            String target,
            String status,
            String message,
            List<String> ipAddresses,
            List<String> mxRecords,
            List<String> nsRecords) {

        this.target = target;
        this.status = status;
        this.message = message;
        this.ipAddresses = ipAddresses;
        this.mxRecords = mxRecords;
        this.nsRecords = nsRecords;
    }

    public String getTarget() {
        return target;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getIpAddresses() {
        return ipAddresses;
    }

    public List<String> getMxRecords() {
        return mxRecords;
    }

    public List<String> getNsRecords() {
        return nsRecords;
    }
}
