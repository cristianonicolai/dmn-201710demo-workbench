package com.myteam.dmn_demo_201710wb;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNDecisionResult;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;
import org.kie.dmn.feel.util.EvalHelper;

public class DMNModelsTest {

    @Test
    public void testLoanPreQualification() {
        KieServices kieServices = KieServices.Factory.get();

        KieContainer kieContainer = kieServices.getKieClasspathContainer();

        DMNRuntime dmnRuntime = kieContainer.newKieSession().getKieRuntime(DMNRuntime.class);

        ApplicantData applicantData = new ApplicantData();
        applicantData.setAge(51);
        applicantData.setMaritalStatus("M");
        applicantData.setEmploymentStatus("Employed");
        applicantData.setExistingCustomer(false);
        applicantData.setMonthly(new Monthly());
        applicantData.getMonthly().setIncome(EvalHelper.getBigDecimalOrNull(16_500));
        applicantData.getMonthly().setRepayments(EvalHelper.getBigDecimalOrNull(2_500));
        applicantData.getMonthly().setExpenses(EvalHelper.getBigDecimalOrNull(3_000));
        applicantData.getMonthly().setTax(EvalHelper.getBigDecimalOrNull(0.36));
        applicantData.getMonthly().setInsurance(EvalHelper.getBigDecimalOrNull(1_000));
        RequestedProduct requestedProduct = new RequestedProduct();
        requestedProduct.setType("Standard Loan");
        requestedProduct.setRate(EvalHelper.getBigDecimalOrNull(0.08));
        requestedProduct.setTerm(EvalHelper.getBigDecimalOrNull(36));
        requestedProduct.setAmount(EvalHelper.getBigDecimalOrNull(100_000));
        CreditScore creditScore = new CreditScore();
        creditScore.setFICO(EvalHelper.getBigDecimalOrNull(650));

        {
            DMNModel dmnModel = dmnRuntime.getModel("http://www.trisotech.com/definitions/_1d6da4b6-ac55-4921-8353-9a6fa05ba5c6", "Loan Pre-Qualification");

            DMNContext dmnContext = dmnRuntime.newContext();
            dmnContext.set("Applicant Data", applicantData);
            dmnContext.set("Requested Product", requestedProduct);
            dmnContext.set("Credit Score", creditScore);

            DMNResult dmnResult = dmnRuntime.evaluateAll(dmnModel, dmnContext);
            System.out.println(dmnResult);

            for (DMNDecisionResult dr : dmnResult.getDecisionResults()) {
                System.out.println("Decision '" + dr.getDecisionName() + "' : " + dr.getResult());
            }

            assertEquals(((Map) dmnResult.getDecisionResultByName("Loan Pre-Qualification").getResult()).get("Qualification"), "Qualified");
        }
        {
            DMNModel dmnModel = dmnRuntime.getModel("http://www.trisotech.com/definitions/_bb56d276-c1b0-4bbf-b4d4-03aae8e40a73", "PROXY Loan Pre-Qualification");

            DMNContext dmnContext = dmnRuntime.newContext();
            dmnContext.set("ApplicantData", applicantData);
            dmnContext.set("RequestedProduct", requestedProduct);
            dmnContext.set("CreditScore", creditScore);

            DMNResult dmnResult = dmnRuntime.evaluateAll(dmnModel, dmnContext);
            System.out.println(dmnResult);

            for (DMNDecisionResult dr : dmnResult.getDecisionResults()) {
                System.out.println("Decision '" + dr.getDecisionName() + "' : " + dr.getResult());
            }

            assertEquals(dmnResult.getDecisionResultByName("LoanPre-Qualification").getResult(), "Qualified");
        }
    }
}
