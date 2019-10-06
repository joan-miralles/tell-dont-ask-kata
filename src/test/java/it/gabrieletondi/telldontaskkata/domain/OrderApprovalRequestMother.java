package it.gabrieletondi.telldontaskkata.domain;

import it.gabrieletondi.telldontaskkata.useCase.OrderApprovalRequest;

public class OrderApprovalRequestMother {

    public static OrderApprovalRequest approvedRequest() {
        OrderApprovalRequest request = new OrderApprovalRequest();
        request.setOrderId(1);
        request.setApproved(true);
        return request;
    }

    public static OrderApprovalRequest rejectedRequest() {
        OrderApprovalRequest request = new OrderApprovalRequest();
        request.setOrderId(1);
        request.setApproved(false);
        return request;
    }
}
