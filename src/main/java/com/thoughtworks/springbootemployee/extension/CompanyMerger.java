package com.thoughtworks.springbootemployee.extension;

import com.thoughtworks.springbootemployee.model.entity.Company;

public class CompanyMerger {
    public static void merge(Company source, Company target) {
        if (!source.getName().isEmpty()) target.setName(source.getName());
    }
}
