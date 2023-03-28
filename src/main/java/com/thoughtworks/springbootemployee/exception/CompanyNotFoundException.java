package com.thoughtworks.springbootemployee.exception;

public class CompanyNotFoundException extends RuntimeException {
    private Long companyId;

    public CompanyNotFoundException(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public String getMessage() {
        return "Not found company which id is " + companyId;
    }
}
